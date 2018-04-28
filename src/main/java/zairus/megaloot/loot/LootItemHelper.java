package zairus.megaloot.loot;

import java.util.List;
import java.util.Random;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.megaloot.item.MLItem;
import zairus.megaloot.item.MLItems;
import zairus.megaloot.loot.LootWeaponEffect.EffectType;
import zairus.megaloot.util.RandomCollection;

@SuppressWarnings("deprecation")
public class LootItemHelper
{
	public static ItemStack getRandomLoot(Random rand, LootRarity rarity)
	{
		RandomCollection<Item> col = new RandomCollection<Item>(rand);
		// Item Weight by rarity
		col.add(3, MLItems.WEAPONSWORD);
		col.add(3, MLItems.WEAPONBOW);
		
		col.add(((rarity == LootRarity.COMMON)? 0 : 1), MLItems.BAUBLERING);
		
		col.add(1, MLItems.ARMOR_HELMET);
		col.add(1, MLItems.ARMOR_CHESTPLATE);
		col.add(1, MLItems.ARMOR_LEGGINGS);
		col.add(1, MLItems.ARMOR_BOOTS);
		
		col.add(3, MLItems.TOOL_AXE);
		col.add(3, MLItems.TOOL_PICKAXE);
		col.add(3, MLItems.TOOL_SHOVEL);
		
		ItemStack stack = new ItemStack(col.next());
		
		return stack;
	}
	
	public static NBTTagList getLootTagList(ItemStack stack, String key)
	{
		NBTTagList list = new NBTTagList();
		
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey(MLItem.LOOT_TAG))
		{
			if (stack.getTagCompound().getCompoundTag(MLItem.LOOT_TAG).hasKey(key))
			{
				list = stack.getTagCompound().getCompoundTag(MLItem.LOOT_TAG).getTagList(key, 10);
			}
		}
		
		return list;
	}
	
	public static void setLootTagList(ItemStack stack, String key, NBTTagList value)
	{
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		
		if (!stack.getTagCompound().hasKey(MLItem.LOOT_TAG))
			stack.getTagCompound().setTag(MLItem.LOOT_TAG, new NBTTagCompound());
		
		stack.getTagCompound().getCompoundTag(MLItem.LOOT_TAG).setTag(key, value);
	}
	
	public static int getLootIntValue(ItemStack stack, String key)
	{
		int value = 0;
		
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey(MLItem.LOOT_TAG))
		{
			if (stack.getTagCompound().getCompoundTag(MLItem.LOOT_TAG).hasKey(key))
			{
				value = stack.getTagCompound().getCompoundTag(MLItem.LOOT_TAG).getInteger(key);
			}
		}
		
		return value;
	}
	
	public static void setLootBooleanValue(ItemStack stack, String key, boolean value)
	{
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		
		if (!stack.getTagCompound().hasKey(MLItem.LOOT_TAG))
			stack.getTagCompound().setTag(MLItem.LOOT_TAG, new NBTTagCompound());
		
		stack.getTagCompound().getCompoundTag(MLItem.LOOT_TAG).setBoolean(key, value);
	}
	
	public static boolean getLootBooleanValue(ItemStack stack, String key)
	{
		boolean value = false;
		
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey(MLItem.LOOT_TAG))
		{
			if (stack.getTagCompound().getCompoundTag(MLItem.LOOT_TAG).hasKey(key))
			{
				value = stack.getTagCompound().getCompoundTag(MLItem.LOOT_TAG).getBoolean(key);
			}
		}
		
		return value;
	}
	
	public static void setLootIntValue(ItemStack stack, String key, int value)
	{
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		
		if (!stack.getTagCompound().hasKey(MLItem.LOOT_TAG))
			stack.getTagCompound().setTag(MLItem.LOOT_TAG, new NBTTagCompound());
		
		stack.getTagCompound().getCompoundTag(MLItem.LOOT_TAG).setInteger(key, value);
	}
	
	public static float getLootFloatValue(ItemStack stack, String key)
	{
		float value = 0;
		
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey(MLItem.LOOT_TAG))
		{
			if (stack.getTagCompound().getCompoundTag(MLItem.LOOT_TAG).hasKey(key))
			{
				value = stack.getTagCompound().getCompoundTag(MLItem.LOOT_TAG).getFloat(key);
			}
		}
		
		return value;
	}
	
	public static void setLootFlaotValue(ItemStack stack, String key, float value)
	{
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		
		if (!stack.getTagCompound().hasKey(MLItem.LOOT_TAG))
			stack.getTagCompound().setTag(MLItem.LOOT_TAG, new NBTTagCompound());
		
		stack.getTagCompound().getCompoundTag(MLItem.LOOT_TAG).setFloat(key, value);
	}
	
	public static String getLootStringValue(ItemStack stack, String key)
	{
		String value = "";
		
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey(MLItem.LOOT_TAG))
		{
			if (stack.getTagCompound().getCompoundTag(MLItem.LOOT_TAG).hasKey(key))
			{
				value = stack.getTagCompound().getCompoundTag(MLItem.LOOT_TAG).getString(key);
			}
		}
		
		return value;
	}
	
	public static void setLootStringValue(ItemStack stack, String key, String value)
	{
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		
		if (!stack.getTagCompound().hasKey(MLItem.LOOT_TAG))
			stack.getTagCompound().setTag(MLItem.LOOT_TAG, new NBTTagCompound());
		
		stack.getTagCompound().getCompoundTag(MLItem.LOOT_TAG).setString(key, value);
	}
	
	public static int getMaxDamage(ItemStack stack)
	{
		int maxDamage = LootItemHelper.getLootIntValue(stack, MLItem.LOOT_TAG_DURABILITY);
		
		if (maxDamage == 0)
			maxDamage = 100;
		
		return maxDamage;
	}
	
	@SideOnly(Side.CLIENT)
	public static void addInformation(ItemStack stack, List<String> tooltip)
	{
		addInformation(stack, tooltip, true);
	}
	
	@SideOnly(Side.CLIENT)
	public static void addInformation(ItemStack stack, List<String> tooltip, boolean show_durability)
	{
		int durability = stack.getMaxDamage();
		
		List<LootWeaponEffect> effects = LootWeaponEffect.getEffectList(stack);
		for (LootWeaponEffect effect : effects)
		{
			if (!(effect == LootWeaponEffect.ARMOR || effect == LootWeaponEffect.TOUGHNESS))
				tooltip.add(
						TextFormatting.RESET 
						+ "- " + effect.getType().getColor() 
						+ I18n.translateToLocalFormatted("weaponeffect." + effect.getId() + ".description",
						new Object[] { 
								effect.getDurationString(stack, effect.getId()), 
								effect.getAmplifierString(stack, effect.getId()), 
								effect.getAmplifierString(stack, effect.getId(), 1), 
								TextFormatting.WHITE + effect.getActionStatus(stack)})
						+ ((effect.getType().equals(EffectType.USE))? TextFormatting.WHITE + " [Shift+Right Click]" : "") );
		}
		
		LootRarity rarity = LootRarity.fromId(getLootStringValue(stack, MLItem.LOOT_TAG_RARITY));
		
		if (rarity != null)
			tooltip.add("Rarity: " + rarity.getColor() + rarity.getId());
		
		if (show_durability)
			tooltip.add(TextFormatting.RESET + "" + durability + "" + TextFormatting.GRAY + " Durability");
		
		int upgrades = LootItemHelper.getLootIntValue(stack, MLItem.LOOT_TAG_UPGRADES);
		
		if (upgrades <= 0)
		{
			tooltip.add(TextFormatting.YELLOW + "" + TextFormatting.ITALIC + "Exhausted");
		}
		else
		{
			tooltip.add(TextFormatting.YELLOW + "Upgrades: " + TextFormatting.BOLD + "" + TextFormatting.WHITE + "" + upgrades);
		}
		
		int kills = LootItemHelper.getLootIntValue(stack, MLItem.LOOT_TAG_KILLS);
		if (kills > 0)
			tooltip.add(TextFormatting.RED + "" + kills + "player kills");
		
		//tooltip.add("");
	}
	
	public static boolean hasEffect(ItemStack stack, LootWeaponEffect effect)
	{
		boolean hasEffect = false;
		
		List<LootWeaponEffect> effects = LootWeaponEffect.getEffectList(stack);
		
		effect_check:
		for (LootWeaponEffect e : effects)
		{
			if (e == effect)
			{
				hasEffect = true;
				break effect_check;
			}
		}
		
		return hasEffect;
	}
}
