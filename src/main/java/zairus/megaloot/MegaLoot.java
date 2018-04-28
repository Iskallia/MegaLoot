package zairus.megaloot;

import org.apache.logging.log4j.Logger;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import zairus.megaloot.gui.MLGuiHandler;
import zairus.megaloot.item.MLItems;
import zairus.megaloot.item.crafting.MLCraftingRecipes;
import zairus.megaloot.util.MLEventHandler;
import zairus.megaloot.util.network.PacketPipeline;

@Mod(modid = MLConstants.MOD_ID, name = MLConstants.MOD_NAME, version = MLConstants.MOD_VERSION)
public class MegaLoot
{
	private static Logger logger;
	
	@SidedProxy(clientSide = MLConstants.MOD_CLIENT_PROXY, serverSide = MLConstants.MOD_COMMON_PROXY)
	public static MLProxy proxy;
	
	public static PacketPipeline packetPipeline = new PacketPipeline();
	
	@Mod.Instance(MLConstants.MOD_ID)
	public static MegaLoot instance;
	
	public static CreativeTabs creativeTabMain = new CreativeTabs("megaLootTabMain") {
		@Override
		public ItemStack getTabIconItem()
		{
			return new ItemStack(MLItems.WEAPONCASE_COMMON);
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
		MegaLoot.proxy.initBuiltinShapes();
		MegaLoot.packetPipeline.initalise();
		
		MLEventHandler eventHandler = new MLEventHandler();
		
		MinecraftForge.EVENT_BUS.register(eventHandler);
		NetworkRegistry.INSTANCE.registerGuiHandler(MegaLoot.instance, new MLGuiHandler());
		
		MLCraftingRecipes.addRecipes();
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		MegaLoot.proxy.postInit(event);
	}
	
	public static void logInfo(String message)
	{
		logger.info(message);
	}
}
