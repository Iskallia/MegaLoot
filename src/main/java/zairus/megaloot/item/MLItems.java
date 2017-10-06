package zairus.megaloot.item;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import zairus.megaloot.MLConstants;
import zairus.megaloot.MegaLoot;
import zairus.megaloot.loot.LootRarity;
import zairus.megaloot.loot.LootSet.LootSetType;

public class MLItems
{
	public static Item WEAPONCASE_COMMON;
	public static Item WEAPONCASE_RARE;
	public static Item WEAPONCASE_EPIC;
	public static Item WEAPONSWORD;
	public static Item WEAPONBOW;
	public static Item BAUBLERING;
	
	public static final Map<Item, LootSetType> ITEMTYPEREGISTRY = new HashMap<Item, LootSetType>();
	
	static
	{
		WEAPONCASE_COMMON = initItem(new MLItemWeaponCase(LootRarity.COMMON), MLItem.WEAPONCASE_COMMON_ID);
		WEAPONCASE_RARE = initItem(new MLItemWeaponCase(LootRarity.RARE), MLItem.WEAPONCASE_RARE_ID);
		WEAPONCASE_EPIC = initItem(new MLItemWeaponCase(LootRarity.EPIC), MLItem.WEAPONCASE_EPIC_ID);
		WEAPONSWORD = initItem(new MLItemWeaponSword(), MLItem.WEAPONSWORD_ID);
		WEAPONBOW = initItem(new MLItemWeaponBow(), MLItem.WEAPONBOW_ID);
		BAUBLERING = initItem(new MLItemBauble(), MLItem.BAUBLERING_ID);
		
		ITEMTYPEREGISTRY.put(WEAPONSWORD, LootSetType.SWORD);
		ITEMTYPEREGISTRY.put(WEAPONBOW, LootSetType.BOW);
		ITEMTYPEREGISTRY.put(BAUBLERING, LootSetType.RING);
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
	}
}
