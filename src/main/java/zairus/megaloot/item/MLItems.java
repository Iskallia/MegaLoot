package zairus.megaloot.item;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import zairus.megaloot.MLConstants;
import zairus.megaloot.MegaLoot;

public class MLItems
{
	public static Item WEAPONCASE_COMMON;
	public static Item WEAPONSWORD;
	
	static
	{
		WEAPONCASE_COMMON = initItem(new MLItemWeaponCase(), MLItem.WEAPONCASE_COMMON_ID);
		WEAPONSWORD = initItem(new MLItemWeaponSword(), MLItem.WEAPONSWORD_ID);
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
		MegaLoot.proxy.registerItem(WEAPONSWORD, MLItem.WEAPONSWORD_ID, 0, true);
	}
}
