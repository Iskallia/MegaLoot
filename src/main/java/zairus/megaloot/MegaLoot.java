package zairus.megaloot;

import org.apache.logging.log4j.Logger;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import zairus.megaloot.item.MLItems;

@Mod(modid = MLConstants.MOD_ID, name = MLConstants.MOD_NAME, version = MLConstants.MOD_VERSION)
public class MegaLoot
{
	private static Logger logger;
	
	@SidedProxy(clientSide = MLConstants.MOD_CLIENT_PROXY, serverSide = MLConstants.MOD_COMMON_PROXY)
	public static MLProxy proxy;
	
	@Mod.Instance(MLConstants.MOD_ID)
	public static MegaLoot instance;
	
	public static CreativeTabs creativeTabMain = new CreativeTabs("megaLootTabMain") {
		@Override
		public Item getTabIconItem()
		{
			return MLItems.WEAPONCASE_COMMON;
		}
	};
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		logger = event.getModLog();
		
		MegaLoot.proxy.preInit(event);
		
		MLConfig.init(event.getSuggestedConfigurationFile());
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		MegaLoot.proxy.init(event);
		
		MLItems.register();
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		MegaLoot.proxy.postInit(event);
	}
	
	public void logInfo(String message)
	{
		logger.info(message);
	}
}
