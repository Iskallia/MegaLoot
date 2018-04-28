package zairus.megaloot.tileentity;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.megaloot.MLConstants;
import zairus.megaloot.inventory.MLContainerBase;

public abstract class MLTileEntityBase extends TileEntityLockableLoot implements ITickable, ISidedInventory
{
	public int playersUsing;
	
	protected String customName;
	protected String defaultName = "MLContainer";
	
	public MLTileEntityBase()
	{
		;
	}
	
	public abstract ItemStack[] getChestContents();
	public abstract void setChestContents(ItemStack[] contents);
	public abstract int getSlotXOffset();
	public abstract int getSlotYOffset();
	
	public abstract Slot getSlot(IInventory inv, int index, int x, int y);
	
	@Nullable
	public abstract SoundEvent getOpenSound();
	
	@Nullable
	public abstract SoundEvent getCloseSound();
	
	@Nullable
	public abstract SoundEvent getItemPlaceSound();
	
	@SideOnly(Side.CLIENT)
	public abstract ResourceLocation getTextures();
	
	public String getDefaultName()
	{
		return this.defaultName;
	}
	
	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer player)
	{
		this.fillWithLoot(player);
		return new MLContainerBase(playerInventory, this, player);
	}
	
	@Override
	public String getGuiID()
	{
		return MLConstants.MOD_ID + ":" + this.defaultName;
	}
	
	public String getGUIDisplayName()
	{
		return this.hasCustomName()? this.customName : "Mega Loot Tile";
	}
	
	@Override
	public String getName()
	{
		return this.hasCustomName()? this.customName : "container." + this.defaultName;
	}
	
	@Override
	public boolean hasCustomName()
	{
		return customName != null;
	}
	
	public void setCustomName(String name)
	{
		this.customName = name;
	}
	
	@Override
	public int getSizeInventory()
	{
		return (getChestContents() != null)? getChestContents().length : 0;
	}
	
	public boolean isEmpty()
	{
		boolean empty = true;
		
		for (int i = 0; getChestContents() != null && i < getChestContents().length; ++i)
		{
			if (getChestContents()[i] != null)
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
		if (getChestContents() == null)
			return ItemStack.EMPTY;
		
		if (index >= getChestContents().length)
			return ItemStack.EMPTY;
		
		ItemStack stack = getChestContents()[index];
		
		if (stack == null)
			stack = ItemStack.EMPTY;
		
		return stack;
	}
	
	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		this.fillWithLoot((EntityPlayer)null);
		
		if (getChestContents() != null && getChestContents()[index] != null)
		{
			if (getChestContents()[index].getCount() <= count)
			{
				ItemStack itemstack1 = getChestContents()[index];
				getChestContents()[index] = ItemStack.EMPTY;
                this.markDirty();
                return itemstack1;
			}
			else
			{
				ItemStack itemstack = getChestContents()[index].splitStack(count);
				
                if (getChestContents()[index].getCount() == 0)
                {
                	getChestContents()[index] = ItemStack.EMPTY;
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
		this.fillWithLoot((EntityPlayer)null);
		
		if (getChestContents() != null && getChestContents()[index] != null)
        {
            ItemStack itemstack = getChestContents()[index];
            getChestContents()[index] = ItemStack.EMPTY;
            return itemstack;
        }
        else
        {
            return ItemStack.EMPTY;
        }
	}
	
	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		this.fillWithLoot((EntityPlayer)null);
		
		if (getChestContents() == null)
			return;
		
		if (index >= getChestContents().length)
			return;
		
		getChestContents()[index] = stack;
		
		if (stack != null && stack.getCount() > this.getInventoryStackLimit())
		{
			stack.setCount(this.getInventoryStackLimit());
		}
		
		this.markDirty();
	}
	
	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}
	
	@Override
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
	}
	
	@Override
	public void openInventory(EntityPlayer player)
	{
		if (!player.isSpectator())
		{
			if (this.playersUsing < 0)
			{
				this.playersUsing = 0;
			}
			
			++this.playersUsing;
			this.world.addBlockEvent(this.pos, this.getBlockType(), 1, this.playersUsing);
			
			this.world.notifyNeighborsOfStateChange(this.pos, this.getBlockType(), true);
			this.world.notifyNeighborsOfStateChange(this.pos.down(), this.getBlockType(), true);
		}
	}
	
	@Override
	public void closeInventory(EntityPlayer player)
	{
		if (!player.isSpectator())
		{
			--this.playersUsing;
			this.world.addBlockEvent(this.pos, this.getBlockType(), 1, this.playersUsing);
			
			this.world.notifyNeighborsOfStateChange(this.pos, this.getBlockType(), true);
			this.world.notifyNeighborsOfStateChange(this.pos.down(), this.getBlockType(), true);
		}
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
		this.fillWithLoot((EntityPlayer)null);
		
		for (int i = 0; getChestContents() != null && i < getChestContents().length; ++i)
		{
			getChestContents()[i] = ItemStack.EMPTY;
		}
	}
	
	@Override
	public void update()
	{
		;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		NBTTagList nbttaglist = compound.getTagList("Items", 10);
		setChestContents(new ItemStack[this.getSizeInventory()]);
		
		if (!this.checkLootAndRead(compound))
		{
			for (int i = 0; i < nbttaglist.tagCount(); ++i)
			{
				NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
				int j = nbttagcompound.getByte("Slot");
				
				if (j >= 0 && getChestContents() != null && j < getChestContents().length)
				{
					ItemStack itemstack = new ItemStack(nbttagcompound);
					
					this.setInventorySlotContents(j, itemstack);
				}
			}
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		
		if (!this.checkLootAndWrite(compound))
		{
			NBTTagList nbttaglist = new NBTTagList();
			
			for (int i = 0; getChestContents() != null && i < getChestContents().length; ++i)
			{
				if (getChestContents()[i] != null)
				{
					NBTTagCompound nbttagcompound = new NBTTagCompound();
					nbttagcompound.setByte("Slot", (byte)i);
					getChestContents()[i].writeToNBT(nbttagcompound);
					nbttaglist.appendTag(nbttagcompound);
				}
			}
			
			compound.setTag("Items", nbttaglist);
		}
		
		if (this.hasCustomName())
		{
			compound.setString("CustomName", this.customName);
		}
		
		return compound;
	}
	
	@Override
	public void updateContainingBlockInfo()
	{
		super.updateContainingBlockInfo();
	}
	
	@Override
	public boolean receiveClientEvent(int id, int type)
	{
		if (id == 1)
		{
			this.playersUsing = type;
			return true;
		}
		else
		{
			return super.receiveClientEvent(id, type);
		}
	}
	
	@Override
	public void invalidate()
	{
		super.invalidate();
		this.updateContainingBlockInfo();
	}
	
	@Override
	@Nullable
	public SPacketUpdateTileEntity getUpdatePacket()
	{
		return new SPacketUpdateTileEntity(this.getPos(), 1, this.getUpdateTag());
	}
	
	@Override
	public NBTTagCompound getUpdateTag()
	{
		return this.writeToNBT(new NBTTagCompound());
	}
	
	@Override
	public void onDataPacket(net.minecraft.network.NetworkManager net, net.minecraft.network.play.server.SPacketUpdateTileEntity pkt)
	{
		this.readFromNBT(pkt.getNbtCompound());
	}
	
	public static MLMaterialSlot getSpecialSlot(IInventory inv, int index, int x, int y, Item... validItems)
	{
		MLMaterialSlot slot = new MLMaterialSlot(inv, index, x, y, validItems);
		return slot;
	}
	
	public static class MLMaterialSlot extends Slot
	{
		private List<Item> validItems = new ArrayList<Item>();
		private boolean locked = false;
		
		public MLMaterialSlot(IInventory inventory, int index, int xPosition, int yPosition, Item... validItems)
		{
			super(inventory, index, xPosition, yPosition);
			
			if (validItems != null)
			{
				for (Item i : validItems)
					this.validItems.add(i);
			}
		}
		
		public MLMaterialSlot setLocked(boolean locked)
		{
			this.locked = locked;
			return this;
		}
		
		@Override
		public boolean isItemValid(ItemStack stack)
		{
			if (stack == null)
				return false;
			
			if (validItems.contains(stack.getItem()))
				return true;
			
			return false;
		}
		
		@Override
		public boolean canTakeStack(EntityPlayer player)
		{
			boolean canTake = super.canTakeStack(player);
			
			if (locked)
				canTake = false;
			
			return canTake;
		}
		
		@Override
		public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack)
		{
			stack = super.onTake(thePlayer, stack);
			return stack;
		}
		
		@Override
		public boolean getHasStack()
		{
			if (this.getStack() == null)
				return false;
			
			return super.getHasStack();
		}
		
		@Override
		public ItemStack getStack()
		{
			ItemStack stack = super.getStack();
			
			return (stack == null) ? ItemStack.EMPTY : stack;
		}
	}
}
