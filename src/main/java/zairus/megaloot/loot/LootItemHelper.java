package zairus.megaloot.loot;

import java.util.ArrayList;
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

@SuppressWarnings("deprecation")
public class LootItemHelper
{
	public static ItemStack getRandomLoot(Random rand)
	{
		List<Item> items = new ArrayList<Item>();
		
		items.add(MLItems.WEAPONSWORD);
		items.add(MLItems.WEAPONBOW);
		items.add(MLItems.BAUBLERING);
		
		ItemStack stack = new ItemStack(items.get(rand.nextInt(items.size())));
		
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
			tooltip.add(
					TextFormatting.RESET + "" + TextFormatting.AQUA + I18n.translateToLocalFormatted("weaponeffect." + effect.getId() + ".description",
					new Object[] { 
							effect.getDurationString(stack, effect.getId()), 
							effect.getAmplifierString(stack, effect.getId()), 
							effect.getAmplifierString(stack, effect.getId(), 1) }));
		}
		
		if (show_durability)
			tooltip.add(durability + " Durability");
		
		int kills = LootItemHelper.getLootIntValue(stack, MLItem.LOOT_TAG_KILLS);
		if (kills > 0)
			tooltip.add(TextFormatting.RED + "" + kills + "player kills");
		
		tooltip.add("");
	}
}
