package zairus.megaloot.loot;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import zairus.megaloot.item.MLItem;

public class LootItemHelper
{
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
}
