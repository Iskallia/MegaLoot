package zairus.megaloot.tileentity;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.megaloot.MLConstants;
import zairus.megaloot.item.MLItem;
import zairus.megaloot.item.MLItemShard;
import zairus.megaloot.item.MLItems;
import zairus.megaloot.loot.LootItemHelper;
import zairus.megaloot.loot.LootRarity;
import zairus.megaloot.loot.LootSet.LootSetType;
import zairus.megaloot.loot.LootWeaponEffect;
import zairus.megaloot.sound.MLSoundEvents;

public class MLTileEntityDisenchanter extends MLTileEntityBase
{
	private ItemStack[] chestContents = new ItemStack[3];
	
	private final int disenchantStepDuration = 50;
	private int disenchantCounter = 0;
	private int disenchantStep = 0;
	private boolean disenchaning = false;
	
	private int tick = 0;
	
	private EntityPlayer disenchanterPlayer;
	
	public MLTileEntityDisenchanter()
	{
		;
	}
	
	@Override
	public void update()
	{
		if (this.disenchaning)
		{
			disenchant();
			
			this.tick = (this.tick + 1) % 20;
			
			if (this.tick == 0)
			{
				IBlockState state = this.world.getBlockState(getPos());
				this.world.notifyBlockUpdate(getPos(), state, state, 0);
			}
		}
		
		super.update();
	}
	
	public void setDisenchanterPlayer(EntityPlayer disenchanter)
	{
		this.disenchanterPlayer = disenchanter;
	}
	
	public int getDisenchantStep()
	{
		return this.disenchantStep;
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
	
	public void applyUpgrade(ItemStack upgrade, ItemStack tool)
	{
		MLItemShard upgradeItem = (MLItemShard)upgrade.getItem();
		
		LootRarity upgradeRarity = upgradeItem.getShardRariry();
		LootRarity toolRarity = LootRarity.fromId(LootItemHelper.getLootStringValue(tool, MLItem.LOOT_TAG_RARITY));
		
		if (toolRarity == null || !(toolRarity == upgradeRarity))
			return;
		
		int upgrades = LootItemHelper.getLootIntValue(tool, MLItem.LOOT_TAG_UPGRADES);
		
		if (upgrades <= 0)
			return;
		
		List<LootWeaponEffect> effects = LootWeaponEffect.getEffectList(tool);
		LootSetType type = MLItems.getItemType(tool.getItem());
		
		LootWeaponEffect me = LootWeaponEffect.getRandomExcluding(this.world.rand, type, effects);
		
		if (me != null)
		{
			NBTTagList effectList = LootItemHelper.getLootTagList(tool, MLItem.LOOT_TAG_EFFECTLIST);
			
			effectList.appendTag(me.getNBT(this.world.rand));
			
			if (me == LootWeaponEffect.LIFE_LONG)
				tool.getTagCompound().setBoolean("Unbreakable", true);
			
			LootItemHelper.setLootTagList(tool, MLItem.LOOT_TAG_EFFECTLIST, effectList);
			
			--upgrades;
			upgrade.shrink(1);
			
			if (upgrades < 0)
				upgrades = 0;
			
			LootItemHelper.setLootIntValue(tool, MLItem.LOOT_TAG_UPGRADES, upgrades);
			
			this.world.playSound(
					(EntityPlayer)null
					, this.getPos().getX()
					, this.getPos().getY()
					, this.getPos().getZ()
					, MLSoundEvents.TOOL_REPAIR
					, SoundCategory.BLOCKS
					, 0.5F
					, this.world.rand.nextFloat() * 0.1F + 0.9F);
		}
	}
	
	public void applyRepair()
	{
		ItemStack material = this.getStackInSlot(0);
		ItemStack toRepair = this.getStackInSlot(1);
		
		if (material == null || material == ItemStack.EMPTY || material.getCount() == 0 || toRepair == null || toRepair == ItemStack.EMPTY || toRepair.getCount() == 0)
			return;
		
		if (
				material.getItem() == MLItems.UPGRADECHARM_COMMON
				|| material.getItem() == MLItems.UPGRADECHARM_RARE
				|| material.getItem() == MLItems.UPGRADECHARM_EPIC)
		{
			applyUpgrade(material, toRepair);
			return;
		}
		
		if (toRepair.getItemDamage() == 0)
			return;
		
		int newDamage = toRepair.getItemDamage() - (int)((float)toRepair.getMaxDamage() * 0.33);
		if (newDamage < 0)
			newDamage = 0;
		
		if (material.getItem() instanceof MLItemShard)
		{
			LootRarity materialRarity = ((MLItemShard)material.getItem()).getShardRariry();
			LootRarity toolRarity = LootRarity.fromId(LootItemHelper.getLootStringValue(toRepair, MLItem.LOOT_TAG_RARITY));
			
			if (materialRarity == null || toolRarity == null)
				return;
			
			if (materialRarity == toolRarity)
			{
				material.shrink(1);
				toRepair.setItemDamage(newDamage);
				
				this.world.playSound(
						(EntityPlayer)null
						, this.getPos().getX()
						, this.getPos().getY()
						, this.getPos().getZ()
						, MLSoundEvents.TOOL_REPAIR
						, SoundCategory.BLOCKS
						, 0.5F
						, this.world.rand.nextFloat() * 0.1F + 0.9F);
			}
		}
	}
	
	public void disenchantStart()
	{
		if (this.disenchaning)
			return;
		
		ItemStack tool = this.getStackInSlot(1);
		
		if (tool == null || tool == ItemStack.EMPTY || tool.getCount() == 0)
			return;
		
		this.disenchantCounter = 0;
		this.disenchantStep = 1;
		this.disenchaning = true;
	}
	
	public void disenchantStop()
	{
		this.disenchaning = false;
		this.disenchantCounter = 0;
		this.disenchantStep = 0;
	}
	
	private void disenchant()
	{
		ItemStack tool = this.getStackInSlot(1);
		
		if (tool == null || tool == ItemStack.EMPTY || tool.getCount() == 0)
		{
			disenchantStop();
			return;
		}
		
		++this.disenchantCounter;
		
		if (this.disenchantCounter >= this.disenchantStepDuration)
		{
			this.disenchantCounter = 0;
			++this.disenchantStep;
			
			this.world.playSound(
					(EntityPlayer)null
					, this.getPos().getX()
					, this.getPos().getY()
					, this.getPos().getZ()
					, MLSoundEvents.TOOL_BREAK
					, SoundCategory.BLOCKS
					, 0.5F
					, this.world.rand.nextFloat() * 0.1F + 0.9F);
			
			if (this.disenchantStep > 3)
			{
				ItemStack result = this.getStackInSlot(2);
				
				if (result == null)
					result = ItemStack.EMPTY;
				
				int model = LootItemHelper.getLootIntValue(tool, MLItem.LOOT_TAG_MODEL);
				
				LootRarity toolRarity = LootRarity.fromId(LootItemHelper.getLootStringValue(tool, MLItem.LOOT_TAG_RARITY));
				int shardCount = 1 + this.world.rand.nextInt(5);
				
				Item shardItem = MLItems.SHARD_COMMON;
				
				if (toolRarity == LootRarity.RARE)
					shardItem = MLItems.SHARD_RARE;
				
				if (toolRarity == LootRarity.EPIC)
					shardItem = MLItems.SHARD_EPIC;
				
				ItemStack shards = new ItemStack(shardItem, shardCount);
				
				if (!result.isEmpty() && !result.isItemEqual(shards))
				{
					disenchantStop();
					return;
				}
				
				if (shards.getItem() == result.getItem())
				{
					shardCount += result.getCount();
					
					if (shardCount > result.getMaxStackSize())
					{
						disenchantStop();
						return;
					}
					
					result.setCount(shardCount);
				}
				else
				{
					result = shards;
				}
				
				if (disenchanterPlayer != null)
				{
					NBTTagCompound playerTag = disenchanterPlayer.getEntityData();
					
					if (playerTag != null)
					{
						NBTTagCompound playerLootTag = (playerTag.hasKey(MLConstants.MOD_ID)) ? playerTag.getCompoundTag(MLConstants.MOD_ID) : new NBTTagCompound();
						
						NBTTagCompound skinTag = (playerLootTag.hasKey(MLItem.LOOT_TAG_SKIN_LIST)) ? playerLootTag.getCompoundTag(MLItem.LOOT_TAG_SKIN_LIST) : new NBTTagCompound();
						
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
						
						if (modelList == null || modelList.length == 0)
						{
							modelList = new int[] { model };
						}
						else
						{
							List<Integer> ml = new ArrayList<Integer>();
							
							for (int m : modelList)
								ml.add(m);
							
							if (!ml.contains(model))
								ml.add(model);
							
							modelList = ml.stream().mapToInt(i->i).toArray();
						}
						
						skinTag.setIntArray(classKey, modelList);
						
						playerLootTag.setTag(MLItem.LOOT_TAG_SKIN_LIST, skinTag);
						playerTag.setTag(MLConstants.MOD_ID, playerLootTag);
					}
				}
				
				this.setInventorySlotContents(1, ItemStack.EMPTY);
				this.setInventorySlotContents(2, result);
			}
			
			this.markDirty();
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		NBTTagCompound c = super.writeToNBT(compound);
		
		c.setInteger("disenchantCounter", this.disenchantCounter);
		c.setInteger("disenchantStep", this.disenchantStep);
		c.setBoolean("disenchaning", this.disenchaning);
		
		return c;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		
		this.disenchantCounter = compound.getInteger("disenchantCounter");
		this.disenchantStep = compound.getInteger("disenchantStep");
		this.disenchaning = compound.getBoolean("disenchaning");
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
