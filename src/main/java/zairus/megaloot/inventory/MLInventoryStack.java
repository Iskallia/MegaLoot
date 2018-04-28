package zairus.megaloot.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class MLInventoryStack extends InventoryBasic
{
	private ItemStack holderStack =  ItemStack.EMPTY;
	
	public MLInventoryStack()
	{
		super("container.megaloot.filter", false, 9);
	}
	
	public MLInventoryStack(ItemStack holder)
	{
		this();
		this.holderStack = holder;
	}
	
	public ItemStack getHolderStack()
	{
		return this.holderStack;
	}
	
	@Override
	public void openInventory(EntityPlayer player)
	{
		if (!this.holderStack.hasTagCompound())
			this.holderStack.setTagCompound(new NBTTagCompound());
		
		if (this.holderStack.getTagCompound().hasKey("void_filter"))
		{
			NBTTagList tag = (NBTTagList) this.holderStack.getTagCompound().getTag("void_filter");
			this.loadInventoryFromNBT(tag);
		}
	}
	
	@Override
	public void closeInventory(EntityPlayer player)
	{
		if (!this.holderStack.hasTagCompound())
			this.holderStack.setTagCompound(new NBTTagCompound());
		
		this.holderStack.getTagCompound().setTag("void_filter", this.saveInventoryToNBT());
	}
	
	public void loadInventoryFromNBT(NBTTagList tag)
	{
		for (int i = 0; i < this.getSizeInventory(); ++i)
			this.setInventorySlotContents(i, ItemStack.EMPTY);
		
		for (int k = 0; k < tag.tagCount(); ++k)
		{
			NBTTagCompound nbttagcompound = tag.getCompoundTagAt(k);
			
			int j = nbttagcompound.getByte("Slot") & 255;
			
			if (j >= 0 && j < this.getSizeInventory())
				this.setInventorySlotContents(j, new ItemStack(nbttagcompound));
		}
	}
	
	public NBTTagList saveInventoryToNBT()
	{
		NBTTagList nbttaglist = new NBTTagList();
		
		for (int i = 0; i < this.getSizeInventory(); ++i)
		{
			ItemStack itemstack = this.getStackInSlot(i);
			
			if (!itemstack.isEmpty())
			{
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte)i);
				itemstack.writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}
		
		return nbttaglist;
	}
}
