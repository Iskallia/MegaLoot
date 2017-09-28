package zairus.megaloot;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class MLProxy
{
	public void preInit(FMLPreInitializationEvent e)
	{
		;
	}
	
	public void init(FMLInitializationEvent e)
	{
		;
	}
	
	public void postInit(FMLPostInitializationEvent e)
	{
		;
	}
	
	public void registerItem(Item item, String name, int meta, boolean model)
	{
		if (meta == 0)
			GameRegistry.register(item);
	}
}
