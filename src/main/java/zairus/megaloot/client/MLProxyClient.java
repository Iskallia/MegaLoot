package zairus.megaloot.client;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import zairus.megaloot.MLProxy;
import zairus.megaloot.block.MLBlocks;
import zairus.megaloot.client.settings.MLKeyBindings;
import zairus.megaloot.item.MLItems;

@Mod.EventBusSubscriber
public class MLProxyClient extends MLProxy
{
	@Override
	public void preInit(FMLPreInitializationEvent e)
	{
		super.preInit(e);
		MLKeyBindings.init();
	}
	
	@Override
	public void init(FMLInitializationEvent e)
	{
		super.init(e);
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent e)
	{
		super.postInit(e);
	}
	
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event)
	{
		MLItems.registerModels();
		MLBlocks.registerModels();
	}
	
	@Override
	public void initBuiltinShapes()
	{
		MinecraftForge.EVENT_BUS.register(this);
		
		BlockModelShapes shapes = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes();
		
		shapes.registerBuiltInBlocks(new Block[] { });
	}
	
	@Override
	public void sendBlockBreakPacket(BlockPos pos)
	{
		NetHandlerPlayClient netHandlerPlayClient = Minecraft.getMinecraft().getConnection();
		assert netHandlerPlayClient != null;
		netHandlerPlayClient.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, Minecraft.getMinecraft().objectMouseOver.sideHit));
	}
}
