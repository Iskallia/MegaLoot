package zairus.megaloot.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

@Deprecated
public class MLInventorySkinList implements IInventory
{
	private ItemStack[] skinList = new ItemStack[7];
	
	@Override
	public String getName()
	{
		return "skinlist";
	}
	
	@Override
	public boolean hasCustomName()
	{
		return false;
	}
	
	@Override
	public ITextComponent getDisplayName()
	{
		return new TextComponentString(this.getName());
	}
	
	@Override
	public int getSizeInventory()
	{
		return this.skinList.length;
	}
	
	@Override
	public boolean isEmpty()
	{
		boolean empty = true;
		
		for (int i = 0; this.skinList != null && i < this.skinList.length; ++i)
		{
			if (this.skinList[i] != null && !this.skinList[i].isEmpty())
			{
				empty = false;
				break;
			}
		}
		
		return empty;
	}
	
	@Override
	public ItemStack getStackInSlot(int index)
	{
		return this.skinList[index];
	}
	
	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		if (this.skinList != null && this.skinList[index] != null)
		{
			if (this.skinList[index].getCount() <= count)
			{
				ItemStack itemstack1 = this.skinList[index];
				this.skinList[index] = ItemStack.EMPTY;
                this.markDirty();
                return itemstack1;
			}
			else
			{
				ItemStack itemstack = this.skinList[index].splitStack(count);
				
                if (this.skinList[index].getCount() == 0)
                {
                	this.skinList[index] = ItemStack.EMPTY;
                }
                
                this.markDirty();
                return itemstack;
			}
		}
		else
		{
			return ItemStack.EMPTY;
		}
	}
	
	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		ItemStack stack = this.skinList[index];
		this.skinList[index] = ItemStack.EMPTY;
		return stack;
	}
	
	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		this.skinList[index] = stack;
		
		if (stack != null && stack.getCount() > this.getInventoryStackLimit())
		{
			stack.setCount(this.getInventoryStackLimit());
		}
		
		this.markDirty();
	}
	
	@Override
	public int getInventoryStackLimit()
	{
		return 1;
	}
	
	@Override
	public void markDirty()
	{
	}
	
	@Override
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return true;
	}
	
	@Override
	public void openInventory(EntityPlayer player)
	{
	}
	
	@Override
	public void closeInventory(EntityPlayer player)
	{
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		return true;
	}
	
	@Override
	public int getField(int id)
	{
		return 0;
	}
	
	@Override
	public void setField(int id, int value)
	{
	}
	
	@Override
	public int getFieldCount()
	{
		return 0;
	}
	
	@Override
	public void clear()
	{
		for (int i = 0; this.skinList != null && i < this.skinList.length; ++i)
		{
			this.skinList[i] = ItemStack.EMPTY;
		}
	}
}
