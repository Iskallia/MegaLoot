package zairus.megaloot.tileentity;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.megaloot.MegaLoot;
import zairus.megaloot.item.MLItems;

public class MLTileEntityEvolutionChamber extends MLTileEntityBase
{
	public static final int TOTAL_EVOLUTION_TIME = 10;
	
	private ItemStack[] chestContents = new ItemStack[4];
	
	private int tick = 0;
	private int evolution_time = 0;
	
	public MLTileEntityEvolutionChamber()
	{
		;
	}
	
	private boolean canInfuse()
	{
		ItemStack ingredient = this.getStackInSlot(0);
		ItemStack upgradable = this.getStackInSlot(1);
		
		ItemStack shard = this.getStackInSlot(2);
		ItemStack weaponcase = this.getStackInSlot(3);
		
		if (ingredient.isEmpty() || upgradable.isEmpty())
		{
			MegaLoot.logInfo("empty ingredients");
			return false;
		}
		
		if (
				ingredient.getItem() == MLItems.INFUSED_EMERALD_COMMON 
				&& upgradable.getItem() == MLItems.WEAPONCASE_COMMON
				&& (shard.isEmpty() || shard.getItem() == MLItems.SHARD_COMMON)
				&& (weaponcase.isEmpty() || weaponcase.getItem() == MLItems.WEAPONCASE_RARE))
		{
			return true;
		}
		
		if (
				ingredient.getItem() == MLItems.INFUSED_EMERALD_RARE 
				&& upgradable.getItem() == MLItems.WEAPONCASE_RARE
				&& (shard.isEmpty() || shard.getItem() == MLItems.SHARD_RARE)
				&& (weaponcase.isEmpty() || weaponcase.getItem() == MLItems.WEAPONCASE_EPIC))
		{
			return true;
		}
		
		return false;
	}
	
	@Override
	public void update()
	{
		this.tick = (this.tick + 1) % 20;
		
		evolution_progress:
		if (this.tick == 0)
		{
			ItemStack ingredient = this.getStackInSlot(0);
			ItemStack upgradable = this.getStackInSlot(1);
			
			if (
					ingredient.isEmpty() 
					|| upgradable.isEmpty())
			{
				evolution_time = 0;
				break evolution_progress;
			}
			
			if (canInfuse())
			{
				// Continue 2 minutes to evolve
				++evolution_time;
				
				if (evolution_time > TOTAL_EVOLUTION_TIME)
				{
					int chance = 0;
					
					if (ingredient.hasTagCompound())
					{
						chance = ingredient.getTagCompound().getInteger("evolve_chance");
						
						ItemStack failed = ItemStack.EMPTY;
						ItemStack success = ItemStack.EMPTY;
						
						if (chance > this.world.rand.nextInt(100))
						{
							if (ingredient.getItem() == MLItems.INFUSED_EMERALD_COMMON && upgradable.getItem() == MLItems.WEAPONCASE_COMMON)
								success = new ItemStack(MLItems.WEAPONCASE_RARE);
							else if (ingredient.getItem() == MLItems.INFUSED_EMERALD_RARE && upgradable.getItem() == MLItems.WEAPONCASE_RARE)
								success = new ItemStack(MLItems.WEAPONCASE_EPIC);
						}
						else
						{
							if (ingredient.getItem() == MLItems.INFUSED_EMERALD_COMMON)
								failed = new ItemStack(MLItems.SHARD_COMMON, 1);
							else if (ingredient.getItem() == MLItems.INFUSED_EMERALD_RARE)
								failed = new ItemStack(MLItems.SHARD_RARE, 4 + this.world.rand.nextInt(6));
						}
						
						if (!failed.isEmpty())
						{
							ItemStack shard = this.getStackInSlot(2);
							if (shard.isEmpty())
							{
								this.setInventorySlotContents(2, failed.copy());
								ingredient.shrink(1);
								upgradable.shrink(1);
							}
							else if (failed.isItemEqual(shard))
							{
								if (shard.getCount() + shard.getCount() <= shard.getMaxStackSize())
								{
									shard.grow(failed.getCount());
									ingredient.shrink(1);
									upgradable.shrink(1);
								}
							}
						}
						else if (!success.isEmpty())
						{
							ItemStack weaponcase = this.getStackInSlot(3);
							if (weaponcase.isEmpty())
							{
								this.setInventorySlotContents(3, success.copy());
								ingredient.shrink(1);
								upgradable.shrink(1);
							}
							else if (success.isItemEqual(weaponcase))
							{
								if (weaponcase.getCount() < weaponcase.getMaxStackSize())
								{
									weaponcase.grow(1);
									ingredient.shrink(1);
									upgradable.shrink(1);
								}
							}
						}
					}
					this.updateInWorld();
					evolution_time = 0;
					break evolution_progress;
				}
			}
			else
			{
				evolution_time = 0;
				break evolution_progress;
			}
			
			this.updateInWorld();
		}
		
		super.update();
	}
	
	private void updateInWorld()
	{
		this.markDirty();
		IBlockState state = this.world.getBlockState(getPos());
		this.world.notifyBlockUpdate(getPos(), state, state, 0);
	}
	
	public int getEvolutionTime()
	{
		return this.evolution_time;
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
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		NBTTagCompound c = super.writeToNBT(compound);
		
		compound.setInteger("evolution_time", this.evolution_time);
		
		return c;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		this.evolution_time = compound.getInteger("evolution_time");
		
		super.readFromNBT(compound);
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
}
