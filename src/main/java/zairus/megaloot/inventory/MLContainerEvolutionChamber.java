package zairus.megaloot.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import zairus.megaloot.item.MLItems;
import zairus.megaloot.tileentity.MLTileEntityBase;

public class MLContainerEvolutionChamber extends MLContainerBase
{
	public MLContainerEvolutionChamber(IInventory playerInventory, IInventory inventory, EntityPlayer player)
	{
		super(playerInventory, inventory, player, 0, 0, 8, 16);
		
		this.addSlotToContainer(MLTileEntityBase.getSpecialSlot(
				inventory
				, 0
				, 44
				, 19
				, MLItems.INFUSED_EMERALD_COMMON
				, MLItems.INFUSED_EMERALD_RARE));
		
		this.addSlotToContainer(MLTileEntityBase.getSpecialSlot(
				inventory
				, 1
				, 116
				, 19
				, MLItems.WEAPONCASE_COMMON
				, MLItems.WEAPONCASE_RARE
				, MLItems.WEAPONCASE_EPIC));
		
		this.addSlotToContainer(MLTileEntityBase.getSpecialSlot(
				inventory
				, 2
				, 44
				, 56));
		
		this.addSlotToContainer(MLTileEntityBase.getSpecialSlot(
				inventory
				, 3
				, 80
				, 56));
	}
	
	@Override
	public void addListener(IContainerListener listener)
	{
		super.addListener(listener);
		listener.sendAllWindowProperties(this, inventory);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index)
	{
		return super.transferStackInSlot(player, index);
	}
}
