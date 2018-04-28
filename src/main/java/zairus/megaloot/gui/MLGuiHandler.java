package zairus.megaloot.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import zairus.megaloot.client.gui.MLGuiDisenchanter;
import zairus.megaloot.client.gui.MLGuiEvolutionChamber;
import zairus.megaloot.client.gui.MLGuiSkinTable;
import zairus.megaloot.client.gui.MLGuiVoidFilter;
import zairus.megaloot.inventory.MLContainerDisenchanter;
import zairus.megaloot.inventory.MLContainerEvolutionChamber;
import zairus.megaloot.inventory.MLContainerSkinTable;
import zairus.megaloot.inventory.MLContainerStack;
import zairus.megaloot.inventory.MLInventoryStack;
import zairus.megaloot.tileentity.MLTileEntityDisenchanter;
import zairus.megaloot.tileentity.MLTileEntityEvolutionChamber;
import zairus.megaloot.tileentity.MLTileEntitySkinTable;

public class MLGuiHandler implements IGuiHandler
{
	public static final int GUI_SKIN_TABLE = 0;
	public static final int GUI_DISENCHANTER = 1;
	public static final int GUI_VOID_FILTER = 2;
	public static final int GUI_EVOLUTION_CHAMBER = 3;
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tileEntity;
		BlockPos blockPos = new BlockPos(x, y, z);
		
		tileEntity = world.getTileEntity(blockPos);
		
		switch (ID)
		{
		case GUI_SKIN_TABLE:
			if (tileEntity != null && tileEntity instanceof MLTileEntitySkinTable)
			{
				return new MLContainerSkinTable(player.inventory, (MLTileEntitySkinTable)tileEntity, player);
			}
		case GUI_DISENCHANTER:
			if (tileEntity != null && tileEntity instanceof MLTileEntityDisenchanter)
			{
				return new MLContainerDisenchanter(player.inventory, (MLTileEntityDisenchanter)tileEntity, player);
			}
			break;
		case GUI_VOID_FILTER:
			ItemStack heldStack = player.getHeldItemMainhand();
			
			if (heldStack != null && !heldStack.isEmpty())
			{
				MLInventoryStack inventoryStack = new MLInventoryStack(heldStack);
				return new MLContainerStack(player.inventory, inventoryStack, player);
			}
			break;
		case GUI_EVOLUTION_CHAMBER:
			if (tileEntity != null && tileEntity instanceof MLTileEntityEvolutionChamber)
			{
				return new MLContainerEvolutionChamber(player.inventory, (MLTileEntityEvolutionChamber)tileEntity, player);
			}
			break;
		default:
			break;
		}
		
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tileEntity;
		BlockPos blockPos = new BlockPos(x, y, z);
		
		tileEntity = world.getTileEntity(blockPos);
		
		switch (ID)
		{
		case GUI_SKIN_TABLE:
			if (tileEntity != null && tileEntity instanceof MLTileEntitySkinTable)
			{
				return new MLGuiSkinTable(player.inventory, (MLTileEntitySkinTable)tileEntity, player);
			}
		case GUI_DISENCHANTER:
			if (tileEntity != null && tileEntity instanceof MLTileEntityDisenchanter)
			{
				return new MLGuiDisenchanter(player.inventory, (MLTileEntityDisenchanter)tileEntity, player);
			}
			break;
		case GUI_VOID_FILTER:
			ItemStack heldStack = player.getHeldItemMainhand();
			
			if (heldStack != null && !heldStack.isEmpty())
			{
				MLInventoryStack inventoryStack = new MLInventoryStack(heldStack);
				return new MLGuiVoidFilter(player.inventory, inventoryStack, player);
			}
			break;
		case GUI_EVOLUTION_CHAMBER:
			if (tileEntity != null && tileEntity instanceof MLTileEntityEvolutionChamber)
			{
				return new MLGuiEvolutionChamber(player.inventory, (MLTileEntityEvolutionChamber)tileEntity, player);
			}
			break;
		default:
			break;
		}
		
		return null;
	}
}
