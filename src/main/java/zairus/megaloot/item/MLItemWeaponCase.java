package zairus.megaloot.item;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
		super();
		
		this.case_rarity = rarity;
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		String displayName = super.getItemStackDisplayName(stack);
		
		if (case_rarity != null)
		{
			displayName = case_rarity.getColor() + displayName;
		}
		
		return displayName;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		ItemStack itemStack = player.getHeldItem(hand);
		
		world.playSound(
				(EntityPlayer)null
				, player.getPosition()
				, MLSoundEvents.CASE_OPEN
				, SoundCategory.PLAYERS
				, 1.0F
				, 1.2F / (world.rand.nextFloat() * 0.2f + 0.9f));
		
		for (int i = 0; i < 1; ++i)
		{
			ItemStack loot = LootItemHelper.getRandomLoot(itemRand, case_rarity);
			
			LootSetType type = MLItems.getItemType(loot.getItem());
			
			if (type == null)
				type = LootSetType.SWORD;
			
			NBTTagCompound tag = new NBTTagCompound();
			tag.setInteger("HideFlags", 2);
			
			NBTTagCompound mlTag = new NBTTagCompound();
			
			int model = 1 + itemRand.nextInt(type.models);
			
			mlTag.setInteger(MLItem.LOOT_TAG_MODEL, model);
			
			mlTag.setString(MLItem.LOOT_TAG_UUID, UUID.randomUUID().toString());
			
			mlTag.setString(MLItem.LOOT_TAG_RARITY, case_rarity.getId());
			mlTag.setInteger(MLItem.LOOT_TAG_DAMAGE, case_rarity.getDamage(itemRand));
			mlTag.setFloat(MLItem.LOOT_TAG_SPEED, case_rarity.getSpeed(itemRand));
			mlTag.setFloat(MLItem.LOOT_TAG_EFFICIENCY, case_rarity.getEfficiency(itemRand));
			mlTag.setInteger(MLItem.LOOT_TAG_DURABILITY, case_rarity.getDurability(itemRand));
			mlTag.setInteger(MLItem.LOOT_TAG_UPGRADES, case_rarity.getUpgrades(itemRand));
			
			mlTag.setFloat(MLItem.LOOT_TAG_DRAWSPEED, case_rarity.getSpeed(itemRand) + 4.0F);
			mlTag.setFloat(MLItem.LOOT_TAG_POWER, 1.0F + ((float)case_rarity.getDamage(itemRand) / 20.0F));
			
			if (
					type == LootSetType.ARMOR_HEAD
					|| type == LootSetType.ARMOR_CHEST
					|| type == LootSetType.ARMOR_LEGS
					|| type == LootSetType.ARMOR_FEET)
			{
				mlTag.setFloat(MLItem.LOOT_TAG_ARMOR, case_rarity.getArmor(itemRand));
				mlTag.setFloat(MLItem.LOOT_TAG_TOUGHNESS, case_rarity.getToughness(itemRand));
			}
			
			int modifierCount = case_rarity.getModifierCount(itemRand);
			
			if (type == LootSetType.RING && modifierCount == 0)
				modifierCount = 1;
			
			if (type == LootSetType.TOOL)
			{
				modifierCount--;
				if (modifierCount < 0)
					modifierCount = 0;
				if (case_rarity == LootRarity.EPIC && modifierCount == 0)
					modifierCount = 1;
			}
			
			boolean unbreakable = false;
			
			if (modifierCount > 0)
			{
				List<LootWeaponEffect> appliedEffects = new ArrayList<LootWeaponEffect>();
				NBTTagList effectList = new NBTTagList();
				
				appliedEffects.add(LootWeaponEffect.ARMOR);
				appliedEffects.add(LootWeaponEffect.TOUGHNESS);
				
				for (int m = 0; m < modifierCount; ++m)
				{
					LootWeaponEffect me = LootWeaponEffect.getRandomExcluding(itemRand, type, appliedEffects);
					
					if (me != null)
					{
						if (me == LootWeaponEffect.LIFE_LONG)
						{
							unbreakable = true;
						}
						
						effectList.appendTag(me.getNBT(itemRand));
						appliedEffects.add(me);
					}
				}
				
				mlTag.setTag(MLItem.LOOT_TAG_EFFECTLIST, effectList);
			}
			
			tag.setTag(MLItem.LOOT_TAG, mlTag);
			
			if (unbreakable)
			{
				tag.setBoolean("Unbreakable", true);
			}
			
			loot.setTagCompound(tag);
			
			LootItemHelper.setLootStringValue(loot, MLItem.LOOT_TAG_LOOTSET, LootSet.getRandom(itemRand).getId());
			
			String loot_name = LootSet.getNameForType(type, itemRand);
			
			if (loot_name.length() > 0)
			{
				LootItemHelper.setLootStringValue(loot, MLItem.LOOT_TAG_NAME, loot_name);
				//loot.setStackDisplayName(case_rarity.getColor() + loot_name);
			}
			
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
