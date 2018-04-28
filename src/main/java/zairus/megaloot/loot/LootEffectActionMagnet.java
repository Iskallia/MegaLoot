package zairus.megaloot.loot;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import zairus.megaloot.item.MLItem;

public class LootEffectActionMagnet implements ILootEffectAction
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
		if (entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)entity;
			
			double x = player.posX;
			double y = player.posY + 1.5;
			double z = player.posZ;
			
			int range = 5;
			
			List<EntityItem> items = player.world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(x - range, y - range, z - range, x + range, y + range, z + range));
			
			for(EntityItem e: items)
			{
				double factor = 0.03;
				e.addVelocity((x - e.posX) * factor, (y - e.posY) * factor, (z - e.posZ) * factor);
			}
		}
	}
	
	@Override
	public ITextComponent modificationResponseMessage(EntityPlayer player, ItemStack stack)
	{
		return null;
	}
	
	@Override
	public String getStatusString(ItemStack stack)
	{
		String status = "[Off]";
		
		if (LootItemHelper.getLootBooleanValue(stack, MLItem.LOOT_TAG_EFFECT_ACTIVE))
			status = "[On]";
		
		return status;
	}
}
