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
import zairus.megaloot.loot.LootItemHelper;
import zairus.megaloot.loot.LootRarity;
import zairus.megaloot.loot.LootSet;
import zairus.megaloot.loot.LootSet.LootSetType;
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
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		ItemStack itemStack = player.getHeldItem(hand);
		
		world.playSound((EntityPlayer)null, player.getPosition(), MLSoundEvents.CASE_OPEN, SoundCategory.PLAYERS, 1.0F, 1.2F / (world.rand.nextFloat() * 0.2f + 0.9f));
		
		for (int i = 0; i < 1; ++i)
		{
			ItemStack loot = LootItemHelper.getRandomLoot(itemRand);
			
			LootSetType type = MLItems.getItemType(loot.getItem());
			
			if (type == null)
				type = LootSetType.SWORD;
			
			NBTTagCompound tag = new NBTTagCompound();
			tag.setInteger("HideFlags", 2);
			
			NBTTagCompound mlTag = new NBTTagCompound();
			
			int model = 1 + itemRand.nextInt(24);
			
			mlTag.setInteger(MLItem.LOOT_TAG_MODEL, model);
			
			mlTag.setString(MLItem.LOOT_TAG_RARITY, case_rarity.getId());
			mlTag.setInteger(MLItem.LOOT_TAG_DAMAGE, case_rarity.getDamage(itemRand));
			mlTag.setFloat(MLItem.LOOT_TAG_SPEED, case_rarity.getSpeed(itemRand));
			mlTag.setInteger(MLItem.LOOT_TAG_DURABILITY, case_rarity.getDurability(itemRand));
			
			mlTag.setFloat(MLItem.LOOT_TAG_DRAWSPEED, case_rarity.getSpeed(itemRand) + 4.0F);
			mlTag.setFloat(MLItem.LOOT_TAG_POWER, 1.0F + ((float)case_rarity.getDamage(itemRand) / 20.0F));
			
			int modifierCount = case_rarity.getModifierCount(itemRand);
			
			if (type == LootSetType.RING && modifierCount == 0)
				modifierCount = 1;
			
			if (modifierCount > 0)
			{
				List<LootWeaponEffect> appliedEffects = new ArrayList<LootWeaponEffect>();
				NBTTagList effectList = new NBTTagList();
				
				for (int m = 0; m < modifierCount; ++m)
				{
					LootWeaponEffect me = LootWeaponEffect.getRandomExcluding(itemRand, type, appliedEffects);
					
					if (me != null)
					{
						effectList.appendTag(me.getNBT(itemRand));
						appliedEffects.add(me);
					}
				}
				
				mlTag.setTag(MLItem.LOOT_TAG_EFFECTLIST, effectList);
			}
			
			tag.setTag(MLItem.LOOT_TAG, mlTag);
			
			loot.setTagCompound(tag);
			
			LootItemHelper.setLootStringValue(loot, MLItem.LOOT_TAG_LOOTSET, LootSet.getRandom(itemRand).getId());
			
			String loot_name = LootSet.getNameForType(type, itemRand);
			
			if (loot_name.length() > 0)
				loot.setStackDisplayName(case_rarity.getColor() + loot_name);
			
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
		
		itemStack.shrink(1);
		
		return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStack);
	}
}
