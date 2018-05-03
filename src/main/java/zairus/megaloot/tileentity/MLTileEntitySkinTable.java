package zairus.megaloot.tileentity;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.megaloot.MLConstants;
import zairus.megaloot.item.MLItem;
import zairus.megaloot.item.MLItems;
import zairus.megaloot.loot.LootItemHelper;
import zairus.megaloot.loot.LootSet.LootSetType;
import zairus.megaloot.sound.MLSoundEvents;

public class MLTileEntitySkinTable extends MLTileEntityBase
{
	private ItemStack[] chestContents = new ItemStack[9];
	
	private int offset = 0;
	//private MLContainerSkinTable container;
	
	public MLTileEntitySkinTable()
	{
		;
	}
	
	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		return new int[] { 0 };
	}
	
	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
	{
		return false;
	}
	
	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
	{
		return false;
	}
	
	@Override
	public ItemStack[] getChestContents()
	{
		return this.chestContents;
	}
	
	@Override
	public void setChestContents(ItemStack[] contents)
	{
		this.chestContents = contents;
	}
	
	@Override
	public int getSlotXOffset()
	{
		return 0;
	}
	
	@Override
	public int getSlotYOffset()
	{
		return 0;
	}
	
	@Override
	public Slot getSlot(IInventory inv, int index, int x, int y)
	{
		Slot slot = new Slot(inv, index, x, y);
		return slot;
	}
	
	@Override
	public SoundEvent getOpenSound()
	{
		return null;
	}
	
	@Override
	public SoundEvent getCloseSound()
	{
		return null;
	}
	
	@Override
	public SoundEvent getItemPlaceSound()
	{
		return null;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public ResourceLocation getTextures()
	{
		return null;
	}
	
	@Override
	protected NonNullList<ItemStack> getItems()
	{
		NonNullList<ItemStack> items = NonNullList.<ItemStack>create();
		
		for (ItemStack stack : chestContents)
		{
			items.add((stack == null)? ItemStack.EMPTY : stack);
		}
		
		return items;
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
	
	public void populateSkins(EntityPlayer player, boolean copyResult, int offsetChange)
	{
		NBTTagCompound playerTag = player.getEntityData();
		ItemStack tool = this.getStackInSlot(0);
		
		if (copyResult)
			offset = 0;
		
		offset += offsetChange;
		
		if (offset < 0)
			offset = 0;
		
		do_populate:
		{
			if (tool == null || tool.isEmpty())
			{
				break do_populate;
			}
			
			if (!playerTag.hasKey(MLConstants.MOD_ID))
			{
				break do_populate;
			}
			
			NBTTagCompound playerLootTag = playerTag.getCompoundTag(MLConstants.MOD_ID);
			
			if (!playerLootTag.hasKey(MLItem.LOOT_TAG_SKIN_LIST))
			{
				break do_populate;
			}
			
			NBTTagCompound skinTag = playerLootTag.getCompoundTag(MLItem.LOOT_TAG_SKIN_LIST);
			
			LootSetType type = MLItems.getItemType(tool.getItem());
			
			String classKey = type.getId();
			
			if (classKey == "tool")
			{
				if (tool.getItem() instanceof ItemPickaxe)
					classKey = "pickaxe";
				if (tool.getItem() instanceof ItemAxe)
					classKey = "axe";
				if (tool.getItem() instanceof ItemSpade)
					classKey = "shovel";
			}
			
			int[] modelList = skinTag.getIntArray(classKey);
			
			int skinSlot = 2;
			int modelCount = 0;
			
			this.world.playSound(
					(EntityPlayer)null
					, this.getPos().getX()
					, this.getPos().getY()
					, this.getPos().getZ()
					, MLSoundEvents.TOOL_PUT
					, SoundCategory.BLOCKS
					, 0.5F
					, this.world.rand.nextFloat() * 0.1F + 0.9F);
			
			if (offset >= modelList.length)
				offset = modelList.length - 1;
			
			for (int sl = 2; sl < this.chestContents.length; ++ sl)
				this.setInventorySlotContents(sl, ItemStack.EMPTY);
			
			model_loop:
			for (int m : modelList)
			{
				if (modelCount >= offset)
				{
					ItemStack skin = tool.copy();
					
					LootItemHelper.setLootIntValue(skin, MLItem.LOOT_TAG_MODEL, m);
					
					this.setInventorySlotContents(skinSlot, skin.copy());
					
					++skinSlot;
					
					if (skinSlot >= this.chestContents.length)
						break model_loop;
				}
				
				++modelCount;
			}
		}
		
		if (copyResult)
			this.setInventorySlotContents(1, tool.copy());
		
		if (tool.isEmpty())
		{
			for (int i = 2; i < 9; ++i)
				this.setInventorySlotContents(i, ItemStack.EMPTY);
		}
		
		IBlockState state = this.world.getBlockState(getPos());
		this.world.notifyBlockUpdate(getPos(), state, state, 0);
		
		this.markDirty();
	}
	
	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		ItemStack stack = super.removeStackFromSlot(index);
		
		return stack;
	}
	
	public void clearInput()
	{
		if (chestContents[0]== null)
			chestContents[0] = ItemStack.EMPTY;
		
		if (!this.chestContents[0].isEmpty())
		{
			this.chestContents[0] = ItemStack.EMPTY;
			
			for (int i = 2; i < this.chestContents.length; ++i)
			{
				this.chestContents[i] = ItemStack.EMPTY;
			}
			
			this.world.playSound(
					(EntityPlayer)null
					, this.getPos().getX()
					, this.getPos().getY()
					, this.getPos().getZ()
					, MLSoundEvents.TOOL_TAKE
					, SoundCategory.BLOCKS
					, 0.5F
					, this.world.rand.nextFloat() * 0.1F + 0.9F);
		}
		
		IBlockState state = this.world.getBlockState(getPos());
		this.world.notifyBlockUpdate(getPos(), state, state, 0);
		
		this.markDirty();
	}
	
	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		ItemStack stack = super.decrStackSize(index, count);
		return stack;
	}
	
	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		super.setInventorySlotContents(index, stack);
	}
}
