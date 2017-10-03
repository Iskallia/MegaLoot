package zairus.megaloot.item;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import zairus.megaloot.MLConstants;
import zairus.megaloot.MegaLoot;
import zairus.megaloot.loot.LootRarity;

public class MLItems
{
	public static Item WEAPONCASE_COMMON;
	public static Item WEAPONCASE_RARE;
	public static Item WEAPONCASE_EPIC;
	public static Item WEAPONSWORD;
	public static Item WEAPONBOW;
	
	static
	{
		WEAPONCASE_COMMON = initItem(new MLItemWeaponCase(LootRarity.COMMON), MLItem.WEAPONCASE_COMMON_ID);
		WEAPONCASE_RARE = initItem(new MLItemWeaponCase(LootRarity.RARE), MLItem.WEAPONCASE_RARE_ID);
		WEAPONCASE_EPIC = initItem(new MLItemWeaponCase(LootRarity.EPIC), MLItem.WEAPONCASE_EPIC_ID);
		WEAPONSWORD = initItem(new MLItemWeaponSword(), MLItem.WEAPONSWORD_ID);
		WEAPONBOW = initItem(new MLItemWeaponBow(), MLItem.WEAPONBOW_ID);
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
	}
}
