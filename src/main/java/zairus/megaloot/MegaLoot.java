package zairus.megaloot;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = MLConstants.MOD_ID, name = MLConstants.MOD_NAME, version = MLConstants.MOD_VERSION)
public class MegaLoot
{
	private static Logger logger;
	
	@SidedProxy(clientSide = MLConstants.MOD_CLIENT_PROXY, serverSide = MLConstants.MOD_COMMON_PROXY)
	public static MLProxy proxy;
	
	@Mod.Instance(MLConstants.MOD_ID)
	public static MegaLoot instance;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		logger = event.getModLog();
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		;
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		;
	}
	
	public void logInfo(String message)
	{
		logger.info(message);
	}
}
