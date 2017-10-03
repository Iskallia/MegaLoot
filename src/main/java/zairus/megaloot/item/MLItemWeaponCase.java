package zairus.megaloot.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import zairus.megaloot.MegaLoot;
import zairus.megaloot.loot.LootRarity;
import zairus.megaloot.loot.LootSet;
import zairus.megaloot.loot.LootWeaponEffect;
import zairus.megaloot.sound.MLSoundEvents;

public class MLItemWeaponCase extends MLItem
{
	private final LootRarity case_rarity;
	
	protected MLItemWeaponCase(LootRarity rarity)
	{
		this.setCreativeTab(MegaLoot.creativeTabMain);
		
		this.case_rarity = rarity;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand)
	{
		world.playSound((EntityPlayer)null, player.getPosition(), MLSoundEvents.CASE_OPEN, SoundCategory.PLAYERS, 1.0F, 1.2F / (world.rand.nextFloat() * 0.2f + 0.9f));
		
		for (int i = 0; i < 1 && !world.isRemote; ++i)
		{
			ItemStack loot = new ItemStack(MLItems.WEAPONSWORD);
			
			NBTTagCompound tag = new NBTTagCompound();
			tag.setInteger("HideFlags", 2);
			
			NBTTagCompound mlTag = new NBTTagCompound();
			
			int model = 1 + itemRand.nextInt(24);
			
			mlTag.setInteger(MLItem.LOOT_TAG_MODEL, model);
			
			mlTag.setString(MLItem.LOOT_TAG_RARITY, case_rarity.getId());
			mlTag.setInteger(MLItem.LOOT_TAG_DAMAGE, case_rarity.getDamage(itemRand));
			mlTag.setFloat(MLItem.LOOT_TAG_SPEED, case_rarity.getSpeed(itemRand));
			mlTag.setInteger(MLItem.LOOT_TAG_DURABILITY, case_rarity.getDurability(itemRand));
			
			int modifierCount = case_rarity.getModifierCount(itemRand);
			
			if (modifierCount > 0)
			{
				List<LootWeaponEffect> appliedEffects = new ArrayList<LootWeaponEffect>();
				NBTTagList effectList = new NBTTagList();
				
				for (int m = 0; m < modifierCount; ++m)
				{
					LootWeaponEffect me = LootWeaponEffect.getRandomExcluding(itemRand, appliedEffects.toArray(new LootWeaponEffect[] {}));
					
					effectList.appendTag(me.getNBT());
					appliedEffects.add(me);
				}
				
				mlTag.setTag(MLItem.LOOT_TAG_EFFECTLIST, effectList);
			}
			
			tag.setTag(MLItem.LOOT_TAG, mlTag);
			
			loot.setTagCompound(tag);
			loot.setStackDisplayName(case_rarity.getColor() + LootSet.getSwordName(itemRand));
			
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
