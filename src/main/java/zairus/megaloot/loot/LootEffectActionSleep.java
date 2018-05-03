package zairus.megaloot.loot;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import zairus.megaloot.MegaLoot;
import zairus.megaloot.util.network.MLPacketSleep;

public class LootEffectActionSleep implements ILootEffectAction
{
	@Override
	public void toggleAction(EntityPlayer player, ItemStack stack)
	{
	}
	
	@Override
	public void handleHarvest(EntityPlayer player, ItemStack stack, List<ItemStack> drops)
	{
	}
	
	@Override
	public void handleUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
	{
	}
	
	@Override
	public ActionResult<ItemStack> handleUse(ActionResult<ItemStack> defaultAction, World world, EntityPlayer player, EnumHand hand)
	{
		if (!player.isSneaking() || player.world.isRemote)
			return defaultAction;
		
		BlockPos pos = player.getPosition();
		net.minecraft.world.WorldProvider.WorldSleepResult sleepResult = world.provider.canSleepAt(player, pos);
		
		if (sleepResult != net.minecraft.world.WorldProvider.WorldSleepResult.BED_EXPLODES)
		{
			if (sleepResult == net.minecraft.world.WorldProvider.WorldSleepResult.DENY)
			{
				return defaultAction;
			}
			
			EntityPlayer.SleepResult entityplayer$sleepresult = this.trySleep(player);
			
			if (entityplayer$sleepresult == EntityPlayer.SleepResult.OK)
			{
				return defaultAction;
			}
			else
			{
				if (entityplayer$sleepresult == EntityPlayer.SleepResult.NOT_POSSIBLE_NOW)
				{
					player.sendStatusMessage(new TextComponentTranslation("tile.bed.noSleep", new Object[0]), true);
				}
				else if (entityplayer$sleepresult == EntityPlayer.SleepResult.NOT_SAFE)
				{
					player.sendStatusMessage(new TextComponentTranslation("tile.bed.notSafe", new Object[0]), true);
				}
				
				return defaultAction;
			}
		}
		
		return defaultAction;
	}
	
	@Override
	public ITextComponent modificationResponseMessage(EntityPlayer player, ItemStack stack)
	{
		return new TextComponentString("");
	}
	
	@Override
	public String getStatusString(ItemStack stack)
	{
		return "";
	}
	
	private EntityPlayer.SleepResult trySleep(EntityPlayer player)
	{
		EntityPlayer.SleepResult ret = net.minecraftforge.event.ForgeEventFactory.onPlayerSleepInBed(player, player.getPosition());
		
		if (ret != null) 
			return ret;
		
		if (!player.world.isRemote)
		{
			if (player.isPlayerSleeping() || !player.isEntityAlive())
			{
				return EntityPlayer.SleepResult.OTHER_PROBLEM;
			}
			
			if (!player.world.provider.isSurfaceWorld())
			{
				return EntityPlayer.SleepResult.NOT_POSSIBLE_HERE;
			}
			
			if (player.world.isDaytime())
			{
				MegaLoot.logInfo("its day");
				return EntityPlayer.SleepResult.NOT_POSSIBLE_NOW;
			}
			
			List<EntityMob> list = player.world.<EntityMob>getEntitiesWithinAABB(
					EntityMob.class
					, new AxisAlignedBB(
							player.posX - 8.0D
							, player.posY - 5.0D
							, player.posZ - 8.0D
							, player.posX + 8.0D
							, player.posY + 5.0D
							, player.posZ + 8.0D)
					, new SleepEnemyPredicate(player));
			
			if (!list.isEmpty())
			{
				return EntityPlayer.SleepResult.NOT_SAFE;
			}
		}
		
		if (player.isRiding())
		{
			player.dismountRidingEntity();
		}
		
		player.spawnShoulderEntities();
		
		MegaLoot.packetPipeline.sendToAll(new MLPacketSleep());
		
		player.sleeping = true;
		player.sleepTimer = 0;
		player.bedLocation = player.getPosition();
		player.motionX = 0.0D;
		player.motionY = 0.0D;
		player.motionZ = 0.0D;
		
		
		if (!player.world.isRemote)
		{
			player.world.updateAllPlayersSleepingFlag();
		}
		
		return EntityPlayer.SleepResult.OK;
	}
	
	private class SleepEnemyPredicate implements Predicate<EntityMob>
	{
		private final EntityPlayer player;
		
		private SleepEnemyPredicate(EntityPlayer player)
		{
			this.player = player;
		}
		
		public boolean apply(@Nullable EntityMob mob)
		{
			return mob.isPreventingPlayerRest(this.player);
		}
	}
}
