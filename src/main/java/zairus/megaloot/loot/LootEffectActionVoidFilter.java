package zairus.megaloot.loot;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class LootEffectActionVoidFilter implements ILootEffectAction
{
	@Override
	public void toggleAction(EntityPlayer player, ItemStack stack)
	{
	}
	
	@Override
	public void handleHarvest(EntityPlayer player, ItemStack stack, List<ItemStack> drops)
	{
		if (!stack.hasTagCompound())
			return;
		
		if (!stack.getTagCompound().hasKey("void_filter"))
			return;
		
		NBTTagList inventoryVoid = (NBTTagList) stack.getTagCompound().getTag("void_filter");
		List<ItemStack> voidStacks = new ArrayList<ItemStack>();
		List<ItemStack> toRemove = new ArrayList<ItemStack>();
		
		for (int k = 0; k < inventoryVoid.tagCount(); ++k)
		{
			NBTTagCompound nbttagcompound = inventoryVoid.getCompoundTagAt(k);
			ItemStack toVoid = new ItemStack(nbttagcompound);
			if (toVoid != null && !toVoid.isEmpty())
			{
				toVoid.setCount(1);
				voidStacks.add(toVoid);
			}
		}
		
		for (ItemStack drop : drops)
		{
			ItemStack compare = drop.copy();
			compare.setCount(1);
			
			for (ItemStack v : voidStacks)
			{
				if (v.isItemEqual(compare))
				{
					toRemove.add(drop);
					break;
				}
			}
		}
		
		drops.removeAll(toRemove);
	}
	
	@Override
	public void handleUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
	{
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
}
