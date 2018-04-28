package zairus.megaloot.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import zairus.megaloot.MegaLoot;
import zairus.megaloot.item.MLItems;
import zairus.megaloot.tileentity.MLTileEntityBase;
import zairus.megaloot.tileentity.MLTileEntitySkinTable;
import zairus.megaloot.util.network.MLPacketSkinTable;

public class MLContainerSkinTable extends MLContainerBase
{
	public MLContainerSkinTable(IInventory playerInv, IInventory inventorySlots, EntityPlayer player)
	{
		super(playerInv, inventorySlots, player, 0, 0, 8, 16);
		
		this.addSlotToContainer(MLTileEntityBase.getSpecialSlot(
				inventorySlots
				, 0
				, 80
				, 41
				//, MLItems.ARMOR_BOOTS
				//, MLItems.ARMOR_CHESTPLATE
				//, MLItems.ARMOR_HELMET
				//, MLItems.ARMOR_LEGGINGS
				, MLItems.BAUBLERING
				, MLItems.TOOL_AXE
				, MLItems.TOOL_PICKAXE
				, MLItems.TOOL_SHOVEL
				, MLItems.WEAPONBOW
				, MLItems.WEAPONSWORD));
		
		this.addSlotToContainer(MLTileEntityBase.getSpecialSlot(inventorySlots, 1, 80, 64));
		
		this.addSlotToContainer(MLTileEntityBase.getSpecialSlot(inventorySlots, 2, 12, 60).setLocked(true));
		this.addSlotToContainer(MLTileEntityBase.getSpecialSlot(inventorySlots, 3, 24, 31).setLocked(true));
		this.addSlotToContainer(MLTileEntityBase.getSpecialSlot(inventorySlots, 4, 52, 18).setLocked(true));
		this.addSlotToContainer(MLTileEntityBase.getSpecialSlot(inventorySlots, 5, 80, 18).setLocked(true));
		this.addSlotToContainer(MLTileEntityBase.getSpecialSlot(inventorySlots, 6, 108, 18).setLocked(true));
		this.addSlotToContainer(MLTileEntityBase.getSpecialSlot(inventorySlots, 7, 136, 31).setLocked(true));
		this.addSlotToContainer(MLTileEntityBase.getSpecialSlot(inventorySlots, 8, 148, 60).setLocked(true));
	}
	
	@Override
	public void addListener(IContainerListener listener)
	{
		super.addListener(listener);
		listener.sendAllWindowProperties(this, inventory);
	}
	
	@Override
	public ItemStack slotClick(int slotId, int dragType, ClickType clickType, EntityPlayer player)
	{
		ItemStack stack = super.slotClick(slotId, dragType, clickType, player);
		
		if (slotId < 0 || slotId > this.inventorySlots.size())
			return stack;
		
		MLTileEntitySkinTable skinTable = null;
		
		if (this.inventory instanceof MLTileEntitySkinTable)
			skinTable = (MLTileEntitySkinTable)this.inventory;
		
		switch (slotId)
		{
		case 36:
			if (skinTable != null)
				MegaLoot.packetPipeline.sendToServer(new MLPacketSkinTable(skinTable.getPos().getX(), skinTable.getPos().getY(), skinTable.getPos().getZ(), 0));
			break;
		case 37:
			if (skinTable != null)
				MegaLoot.packetPipeline.sendToServer(new MLPacketSkinTable(skinTable.getPos().getX(), skinTable.getPos().getY(), skinTable.getPos().getZ(), 1));
			break;
		case 38:
		case 39:
		case 40:
		case 41:
		case 42:
		case 43:
		case 44:
			ItemStack clickedSkin = this.getSlot(slotId).getStack();
			
			if (!clickedSkin.isEmpty())
				this.putStackInSlot(37, clickedSkin.copy());
			break;
		default:
			break;
		}
		
		return stack;
	}
}
