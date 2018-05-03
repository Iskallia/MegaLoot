package zairus.megaloot.block;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.base.Preconditions;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import zairus.megaloot.MLConstants;

@ObjectHolder(MLConstants.MOD_ID)
public class MLBlocks
{
	@ObjectHolder(MLBlock.SKIN_TABLE_ID)
	public static final Block SKIN_TABLE;
	@ObjectHolder(MLBlock.DISENCHANTER_ID)
	public static final Block DISENCHANTER;
	@ObjectHolder(MLBlock.EVOLUTION_CHAMBER_ID)
	public static final Block EVOLUTION_CHAMBER;
	
	protected static final List<Block> BLOCKS = new ArrayList<Block>();
	
	static
	{
		SKIN_TABLE = initBlock(new MLBlockSkinTable(), MLBlock.SKIN_TABLE_ID);
		DISENCHANTER = initBlock(new MLBlockDisenchanter(), MLBlock.DISENCHANTER_ID);
		EVOLUTION_CHAMBER = initBlock(new MLBlockEvolutionChamber(), MLBlock.EVOLUTION_CHAMBER_ID);
	}
	
	public static void initialize()
	{
		;
	}
	
	public static Block initBlock(Block block, String id)
	{
		block.setRegistryName(new ResourceLocation(MLConstants.MOD_ID, id));
		block.setUnlocalizedName(id);
		BLOCKS.add(block);
		
		return block;
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerModels()
	{
		for (final Block block : BLOCKS)
		{
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(MLConstants.MOD_ID + ":" + block.getUnlocalizedName().substring(5), "inventory"));
		}
	}
	
	@Mod.EventBusSubscriber(modid = MLConstants.MOD_ID)
	public static class BlockRegistry
	{
		public static final Set<ItemBlock> ITEM_BLOCKS = new HashSet<ItemBlock>();
		
		@SubscribeEvent
		public static void newRegistry(final RegistryEvent.NewRegistry event)
		{
			;
		}
		
		@SubscribeEvent
		public static void register(final RegistryEvent.Register<Block> event)
		{
			final IForgeRegistry<Block> registry = event.getRegistry();
			
			registry.registerAll(BLOCKS.toArray(new Block[] { }));
		}
		
		@SubscribeEvent
		public static void registerItemBlocks(final RegistryEvent.Register<Item> event)
		{
			final IForgeRegistry<Item> registry = event.getRegistry();
			
			for (final Block block : BLOCKS)
			{
				final ResourceLocation registryName = Preconditions.checkNotNull(block.getRegistryName(), "Block %s has null registry name", block);
				ItemBlock item = new ItemBlock(block);
				registry.register(item.setRegistryName(registryName));
				ITEM_BLOCKS.add(item);
			}
		}
	}
}
