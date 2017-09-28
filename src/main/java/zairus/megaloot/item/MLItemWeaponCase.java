package zairus.megaloot.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import zairus.megaloot.MegaLoot;
import zairus.megaloot.sound.MLSoundEvents;

public class MLItemWeaponCase extends MLItem
{
	protected MLItemWeaponCase()
	{
		this.setCreativeTab(MegaLoot.creativeTabMain);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand)
	{
		world.playSound((EntityPlayer)null, player.getPosition(), MLSoundEvents.CASE_OPEN, SoundCategory.PLAYERS, 1.0F, 1.2F / (world.rand.nextFloat() * 0.2f + 0.9f));
		
		for (int i = 0; i < 1; ++i)
		{
			ItemStack loot = new ItemStack(MLItems.WEAPONSWORD); // RRCUtils.getStackFromPool(RRCConfig.rewardCaseLoot, itemRand);
			
			NBTTagCompound tag = new NBTTagCompound();
			tag.setInteger("HideFlags", 2);
			
			loot.setTagCompound(tag);
			loot.setStackDisplayName(TextFormatting.YELLOW + "Mage Blade");
			
			if (loot != null)
			{
				player.dropItem(loot, false, true);
				
				for (int j = 0; j < 5; ++j)
					world.spawnParticle(
							EnumParticleTypes.CLOUD
							, player.posX
							, player.posY
							, player.posZ
							, ((double)(1 - itemRand.nextInt(3))) * 0.6D
							, ((double)(1 - itemRand.nextInt(3))) * 0.6D
							, ((double)(1 - itemRand.nextInt(3))) * 0.6D
							, new int[] { });
			}
		}
		
		--itemStack.stackSize;
		
		return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStack);
	}
}
