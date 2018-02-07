package zairus.megaloot.item;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nullable;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
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
import zairus.megaloot.MegaLoot;
import zairus.megaloot.loot.LootRarity;
import zairus.megaloot.loot.LootSet.LootSetType;

@ObjectHolder(MLConstants.MOD_ID)
public class MLItems
{
	@ObjectHolder(MLItem.WEAPONCASE_COMMON_ID)
	public static final Item WEAPONCASE_COMMON;
	@ObjectHolder(MLItem.WEAPONCASE_RARE_ID)
	public static final Item WEAPONCASE_RARE;
	@ObjectHolder(MLItem.WEAPONCASE_EPIC_ID)
	public static final Item WEAPONCASE_EPIC;
	@ObjectHolder(MLItem.WEAPONSWORD_ID)
	public static final Item WEAPONSWORD;
	@ObjectHolder(MLItem.WEAPONBOW_ID)
	public static final Item WEAPONBOW;
	@ObjectHolder(MLItem.BAUBLERING_ID)
	public static final Item BAUBLERING;
	@ObjectHolder(MLItem.ARMOR_BOOTS_ID)
	public static final Item ARMOR_BOOTS;
	@ObjectHolder(MLItem.ARMOR_LEGGINGS_D)
	public static final Item ARMOR_LEGGINGS;
	@ObjectHolder(MLItem.ARMOR_CHESTPLATE_ID)
	public static final Item ARMOR_CHESTPLATE;
	@ObjectHolder(MLItem.ARMOR_HELMET_ID)
	public static final Item ARMOR_HELMET;
	@ObjectHolder(MLItem.TOOL_AXE_ID)
	public static final Item TOOL_AXE;
	@ObjectHolder(MLItem.TOOL_PICKAXE_ID)
	public static final Item TOOL_PICKAXE;
	@ObjectHolder(MLItem.TOOL_SHOVEL_ID)
	public static final Item TOOL_SHOVEL;
	
	static
	{
		WEAPONCASE_COMMON = initItem(new MLItemWeaponCase(LootRarity.COMMON), MLItem.WEAPONCASE_COMMON_ID);
		WEAPONCASE_RARE = initItem(new MLItemWeaponCase(LootRarity.RARE), MLItem.WEAPONCASE_RARE_ID);
		WEAPONCASE_EPIC = initItem(new MLItemWeaponCase(LootRarity.EPIC), MLItem.WEAPONCASE_EPIC_ID);
		WEAPONSWORD = initItem(new MLItemWeaponSword(), MLItem.WEAPONSWORD_ID);
		WEAPONBOW = initItem(new MLItemWeaponBow(), MLItem.WEAPONBOW_ID);
		BAUBLERING = initItem(new MLItemBauble(), MLItem.BAUBLERING_ID);
		ARMOR_BOOTS = initItem(new MLItemArmor(EntityEquipmentSlot.FEET), MLItem.ARMOR_BOOTS_ID);
		ARMOR_LEGGINGS = initItem(new MLItemArmor(EntityEquipmentSlot.LEGS), MLItem.ARMOR_LEGGINGS_D);
		ARMOR_CHESTPLATE = initItem(new MLItemArmor(EntityEquipmentSlot.CHEST), MLItem.ARMOR_CHESTPLATE_ID);
		ARMOR_HELMET = initItem(new MLItemArmor(EntityEquipmentSlot.HEAD), MLItem.ARMOR_HELMET_ID);
		TOOL_AXE = initItem(new MLItemToolAxe(), MLItem.TOOL_AXE_ID);
		TOOL_PICKAXE = initItem(new MLItemToolPickaxe(), MLItem.TOOL_PICKAXE_ID);
		TOOL_SHOVEL = initItem(new MLItemToolShovel(), MLItem.TOOL_SHOVEL_ID);
		
		MLItemTypeRegistry.register(WEAPONSWORD, LootSetType.SWORD);
		MLItemTypeRegistry.register(WEAPONBOW, LootSetType.BOW);
		MLItemTypeRegistry.register(BAUBLERING, LootSetType.RING);
		MLItemTypeRegistry.register(ARMOR_BOOTS, LootSetType.ARMOR_FEET);
		MLItemTypeRegistry.register(ARMOR_LEGGINGS, LootSetType.ARMOR_LEGS);
		MLItemTypeRegistry.register(ARMOR_CHESTPLATE, LootSetType.ARMOR_CHEST);
		MLItemTypeRegistry.register(ARMOR_HELMET, LootSetType.ARMOR_HEAD);
		MLItemTypeRegistry.register(TOOL_AXE, LootSetType.TOOL);
		MLItemTypeRegistry.register(TOOL_PICKAXE, LootSetType.TOOL);
		MLItemTypeRegistry.register(TOOL_SHOVEL, LootSetType.TOOL);
	}
	
	public static void initialize()
	{
		;
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerModels()
	{
		final Item[] items = {
				WEAPONCASE_COMMON
				,WEAPONCASE_RARE
				,WEAPONCASE_EPIC
				,WEAPONSWORD
				,WEAPONBOW
				,BAUBLERING
				,ARMOR_BOOTS
				,ARMOR_LEGGINGS
				,ARMOR_CHESTPLATE
				,ARMOR_HELMET
				,TOOL_AXE
				,TOOL_PICKAXE
				,TOOL_SHOVEL
		};
		
		for (final Item item : items)
		{
			MegaLoot.logInfo("s0:" + MLConstants.MOD_ID + ":" + item.getUnlocalizedName());
			MegaLoot.logInfo("s1:" + MLConstants.MOD_ID + ":" + item.getUnlocalizedName().substring(5));
			
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(MLConstants.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
		}
	}
	
	@Nullable
	public static LootSetType getItemType(Item item)
	{
		return MLItemTypeRegistry.get(item);
	}
	
	public static Item initItem(Item item, String id)
	{
		item.setRegistryName(new ResourceLocation(MLConstants.MOD_ID, id));
		item.setUnlocalizedName(id);
		
		return item;
	}
	
	@Mod.EventBusSubscriber(modid = MLConstants.MOD_ID)
	public static class ItemRegistry
	{
		public static final Set<Item> ITEMS = new HashSet<Item>();
		
		@SubscribeEvent
		public static void newRegistry(final RegistryEvent.NewRegistry event)
		{
			;
		}
		
		@SubscribeEvent
		public static void register(final RegistryEvent.Register<Item> event)
		{
			final Item[] items = {
					WEAPONCASE_COMMON
					,WEAPONCASE_RARE
					,WEAPONCASE_EPIC
					,WEAPONSWORD
					,WEAPONBOW
					,BAUBLERING
					,ARMOR_BOOTS
					,ARMOR_LEGGINGS
					,ARMOR_CHESTPLATE
					,ARMOR_HELMET
					,TOOL_AXE
					,TOOL_PICKAXE
					,TOOL_SHOVEL
			};
			
			final IForgeRegistry<Item> registry = event.getRegistry();
			
			for (final Item item : items)
			{
				registry.register(item);
				ITEMS.add(item);
			}
			
			initialize();
		}
	}
}
