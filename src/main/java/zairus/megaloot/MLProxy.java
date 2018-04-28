package zairus.megaloot;

import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import zairus.megaloot.tileentity.MLTileEntityDisenchanter;
import zairus.megaloot.tileentity.MLTileEntityEvolutionChamber;
import zairus.megaloot.tileentity.MLTileEntitySkinTable;

public class MLProxy
{
	public void preInit(FMLPreInitializationEvent e)
	{
		;
	}
	
	public void init(FMLInitializationEvent e)
	{
		GameRegistry.registerTileEntity(MLTileEntitySkinTable.class, "tileEntitySkinTable");
		GameRegistry.registerTileEntity(MLTileEntityDisenchanter.class, "tileEntityDisenchanter");
		GameRegistry.registerTileEntity(MLTileEntityEvolutionChamber.class, "tileEntityEvolutionChamber");
	}
	
	public void postInit(FMLPostInitializationEvent e)
	{
		;
	}
	
	public void initBuiltinShapes()
	{
	}
	
	public void sendBlockBreakPacket(BlockPos pos)
	{
		;
	}
}
