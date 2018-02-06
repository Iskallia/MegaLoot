package zairus.megaloot.item;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import zairus.megaloot.MLConstants;
import zairus.megaloot.MegaLoot;
import zairus.megaloot.loot.LootRarity;
import zairus.megaloot.loot.LootSet.LootSetType;

public class MLItems
{
	public static final Item WEAPONCASE_COMMON;
	public static final Item WEAPONCASE_RARE;
	public static final Item WEAPONCASE_EPIC;
	public static final Item WEAPONSWORD;
	public static final Item WEAPONBOW;
	public static final Item BAUBLERING;
	public static final Item ARMOR_BOOTS;
	public static final Item ARMOR_LEGGINGS;
	public static final Item ARMOR_CHESTPLATE;
	public static final Item ARMOR_HELMET;
	public static final Item TOOL_AXE;
	public static final Item TOOL_PICKAXE;
	public static final Item TOOL_SHOVEL;
	
	public static final Map<Item, LootSetType> ITEMTYPEREGISTRY = new HashMap<Item, LootSetType>();
	
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
		
		ITEMTYPEREGISTRY.put(WEAPONSWORD, LootSetType.SWORD);
		ITEMTYPEREGISTRY.put(WEAPONBOW, LootSetType.BOW);
		ITEMTYPEREGISTRY.put(BAUBLERING, LootSetType.RING);
		ITEMTYPEREGISTRY.put(ARMOR_BOOTS, LootSetType.ARMOR_FEET);
		ITEMTYPEREGISTRY.put(ARMOR_LEGGINGS, LootSetType.ARMOR_LEGS);
		ITEMTYPEREGISTRY.put(ARMOR_CHESTPLATE, LootSetType.ARMOR_CHEST);
		ITEMTYPEREGISTRY.put(ARMOR_HELMET, LootSetType.ARMOR_HEAD);
		ITEMTYPEREGISTRY.put(TOOL_AXE, LootSetType.TOOL);
		ITEMTYPEREGISTRY.put(TOOL_PICKAXE, LootSetType.TOOL);
		ITEMTYPEREGISTRY.put(TOOL_SHOVEL, LootSetType.TOOL);
	}
	
	@Nullable
	public static LootSetType getItemType(Item item)
	{
		return ITEMTYPEREGISTRY.get(item);
	}
	
	public static Item initItem(Item item, String id)
	{
		item.setRegistryName(new ResourceLocation(MLConstants.MOD_ID, id));
		item.setUnlocalizedName(id);
		
		return item;
	}
	
	public static final void register()
	{
		MegaLoot.proxy.registerItem(WEAPONCASE_COMMON, MLItem.WEAPONCASE_COMMON_ID, 0, true);
		MegaLoot.proxy.registerItem(WEAPONCASE_RARE, MLItem.WEAPONCASE_RARE_ID, 0, true);
		MegaLoot.proxy.registerItem(WEAPONCASE_EPIC, MLItem.WEAPONCASE_EPIC_ID, 0, true);
		MegaLoot.proxy.registerItem(WEAPONSWORD, MLItem.WEAPONSWORD_ID, 0, true);
		MegaLoot.proxy.registerItem(WEAPONBOW, MLItem.WEAPONBOW_ID, 0, true);
		MegaLoot.proxy.registerItem(BAUBLERING, MLItem.BAUBLERING_ID, 0, true);
		MegaLoot.proxy.registerItem(ARMOR_BOOTS, MLItem.ARMOR_BOOTS_ID, 0, true);
		MegaLoot.proxy.registerItem(ARMOR_LEGGINGS, MLItem.ARMOR_LEGGINGS_D, 0, true);
		MegaLoot.proxy.registerItem(ARMOR_CHESTPLATE, MLItem.ARMOR_CHESTPLATE_ID, 0, true);
		MegaLoot.proxy.registerItem(ARMOR_HELMET, MLItem.ARMOR_HELMET_ID, 0, true);
		MegaLoot.proxy.registerItem(TOOL_AXE, MLItem.TOOL_AXE_ID, 0, true);
		MegaLoot.proxy.registerItem(TOOL_PICKAXE, MLItem.TOOL_PICKAXE_ID, 0, true);
		MegaLoot.proxy.registerItem(TOOL_SHOVEL, MLItem.TOOL_SHOVEL_ID, 0, true);
	}
}
