package zairus.megaloot.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import zairus.megaloot.tileentity.MLTileEntityBase;

public class MLContainerBase extends Container
{
	protected IInventory inventory;
	
	protected int rows = 3;
	protected int columns = 9;
	
	public MLContainerBase(IInventory playerInventory, IInventory inventory, EntityPlayer player, int r, int c, int xOffset, int yOffset)
	{
		this.inventory = inventory;
		this.inventory.openInventory(player);
		
		this.rows = r;
		this.columns = c;
		
		if (inventory instanceof MLTileEntityBase)
		{
			xOffset = ((MLTileEntityBase)inventory).getSlotXOffset();
			yOffset = ((MLTileEntityBase)inventory).getSlotYOffset();
		}
		
		// Container inventory
		for (int i = 0; i < rows; ++i)
		{
			for (int j = 0; j < columns; ++j)
			{
				Slot slot;
				
				if (inventory instanceof MLTileEntityBase)
				{
					slot = ((MLTileEntityBase)inventory).getSlot(inventory, i * columns + j, xOffset + j * 18, yOffset + i * 18);
					
					if (slot != null)
						this.addSlotToContainer(slot);
				}
				else
				{
					slot = new Slot(inventory, i * columns + j, xOffset + j * 18, yOffset + i * 18);
					
					this.addSlotToContainer(slot);
				}
			}
		}
		
		// Player inventory
		for (int l = 0; l < 3; ++l)
		{
			for (int j1 = 0; j1 < 9; ++j1)
			{
				this.addSlotToContainer(new Slot(playerInventory, j1 + l * 9 + 9, 8 + j1 * 18, (18 + 66) + l * 18));
			}
		}
		
		// Hotbar
        for (int i1 = 0; i1 < 9; ++i1)
        {
            this.addSlotToContainer(new Slot(playerInventory, i1, 8 + i1 * 18, 18 + 124));
        }
	}
	
	public MLContainerBase(IInventory playerInventory, IInventory inventory, EntityPlayer player, int r, int c)
	{
		this(playerInventory, inventory, player, r, c, 8, 16);
	}
	
	public MLContainerBase(IInventory playerInventory, IInventory inventory, EntityPlayer player)
	{
		this(playerInventory, inventory, player, 3, 9);
	}
	
	public MLContainerBase setGrid(int rows, int cols)
	{
		this.rows = rows;
		this.columns = cols;
		
		return this;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return this.inventory.isUsableByPlayer(player);
	}
	
	@Override
	public void onContainerClosed(EntityPlayer player)
	{
		super.onContainerClosed(player);
		this.inventory.closeInventory(player);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = (Slot)this.inventorySlots.get(index);
		
		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			if (itemstack1 == null)
				itemstack1 = ItemStack.EMPTY;
			itemstack = itemstack1.copy();
			
			if (index < (this.rows * this.columns))
			{
				if (!this.mergeItemStack(itemstack1, ((this.rows * this.columns) - 0), this.inventorySlots.size(), true))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.mergeItemStack(itemstack1, 0, (this.rows * this.columns), false))
			{
				return ItemStack.EMPTY;
			}
			
			if (itemstack1.getCount() == 0)
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }
		}
		
		if (itemstack == null)
			itemstack = ItemStack.EMPTY;
		
		return itemstack;
	}
	
	@Override
	public void detectAndSendChanges()
	{
		for (int i = 0; i < this.inventorySlots.size(); ++i)
		{
			ItemStack itemstack = ((Slot)this.inventorySlots.get(i)).getStack();
			ItemStack itemstack1 = this.inventoryItemStacks.get(i);
			
			if (itemstack == null)
				itemstack = ItemStack.EMPTY;
			
			if(itemstack1 == null)
				itemstack = ItemStack.EMPTY;
			
			if (!ItemStack.areItemStacksEqual(itemstack1, itemstack))
			{
				boolean clientStackChanged = !ItemStack.areItemStacksEqualUsingNBTShareTag(itemstack1, itemstack);
				itemstack1 = itemstack.isEmpty() ? ItemStack.EMPTY : itemstack.copy();
				this.inventoryItemStacks.set(i, itemstack1);
				
				if (clientStackChanged)
				{
					for (int j = 0; j < this.listeners.size(); ++j)
					{
						((IContainerListener)this.listeners.get(j)).sendSlotContents(this, i, itemstack1);
					}
				}
			}
		}
	}
	
	@Override
	public void putStackInSlot(int slotID, ItemStack stack)
	{
		if (stack == null)
			stack = ItemStack.EMPTY;
		
		super.putStackInSlot(slotID, stack);
	}
	
	@Override
	public ItemStack slotClick(int slotId, int dragType, ClickType clickType, EntityPlayer player)
	{
		return super.slotClick(slotId, dragType, clickType, player);
	}
	
	@Override
	public NonNullList<ItemStack> getInventory()
	{
		NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>create();
		
		for (int i = 0; i < this.inventorySlots.size(); ++i)
		{
			ItemStack itemstack = ((Slot)this.inventorySlots.get(i)).getStack();
			
			if (itemstack == null)
				itemstack = ItemStack.EMPTY;
			
			nonnulllist.add(itemstack);
		}
		
		return nonnulllist;
	}
}
