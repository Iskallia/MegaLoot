package zairus.megaloot.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import zairus.megaloot.item.MLItems;
import zairus.megaloot.tileentity.MLTileEntityBase;

public class MLContainerDisenchanter extends MLContainerBase
{
	public MLContainerDisenchanter(IInventory playerInv, IInventory inventorySlots, EntityPlayer player)
	{
		super(playerInv, inventorySlots, player, 0, 0, 8, 16);
		
		this.addSlotToContainer(MLTileEntityBase.getSpecialSlot(
				inventorySlots
				, 0
				, 44
				, 16
				, MLItems.SHARD_COMMON
				, MLItems.SHARD_RARE
				, MLItems.SHARD_EPIC
				, MLItems.UPGRADECHARM_COMMON
				, MLItems.UPGRADECHARM_RARE
				, MLItems.UPGRADECHARM_EPIC));
		
		this.addSlotToContainer(MLTileEntityBase.getSpecialSlot(
				inventorySlots
				, 1
				, 80
				, 39
				, MLItems.ARMOR_BOOTS
				, MLItems.ARMOR_CHESTPLATE
				, MLItems.ARMOR_HELMET
				, MLItems.ARMOR_LEGGINGS
				, MLItems.BAUBLERING
				, MLItems.TOOL_AXE
				, MLItems.TOOL_PICKAXE
				, MLItems.TOOL_SHOVEL
				, MLItems.WEAPONBOW
				, MLItems.WEAPONSWORD));
		
		this.addSlotToContainer(MLTileEntityBase.getSpecialSlot(inventorySlots, 2, 116, 63));
	}
	
	@Override
	public void addListener(IContainerListener listener)
	{
		super.addListener(listener);
		listener.sendAllWindowProperties(this, inventory);
	}
}
