package zairus.megaloot;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.config.Configuration;
import zairus.megaloot.loot.LootWeaponEffect;

public class MLConfig
{
	public static Configuration configuration;
	
	public static int armorPiecesForBonus = 3;
	
	public static String CATEGORY_DEFAULT = "DEFAULT";
	public static String CATEGORY_EFFECT_SPAWN = "EFFECT_SPAWN";
	public static String CATEGORY_EFFECT_ACTIVE = "EFFECT_ACTIVE";
	
	public static List<LootWeaponEffect> spawnDisabledEffects;
	public static List<LootWeaponEffect> activeEffects;
	
	public static void init(File cFile)
	{
		spawnDisabledEffects = new ArrayList<LootWeaponEffect>();
		activeEffects = new ArrayList<LootWeaponEffect>();
		
		configuration = new Configuration(cFile);
		
		configuration.load();
		
		configuration.getInt("armorPiecesForBonus", MLConfig.CATEGORY_DEFAULT, armorPiecesForBonus, 1, 4, "Sets the minimum number of armor pieces required for the set bonus to take effect.");
		
		for (LootWeaponEffect effect : LootWeaponEffect.REGISTRY.values())
		{
			boolean enabled = configuration.getBoolean(effect.getId() + "_spawn", MLConfig.CATEGORY_EFFECT_SPAWN, true, "If enabled, the effect will be available for getting it randomly at opening cases.");
			
			if (!enabled)
				spawnDisabledEffects.add(effect);
			
			enabled = configuration.getBoolean(effect.getId() + "_active", MLConfig.CATEGORY_EFFECT_ACTIVE, true, "If enabled, this effect will be active. Else, it won't be active even if the item has it.");
			
			if (enabled)
				activeEffects.add(effect);
		}
		
		configuration.save();
	}
	
	public static boolean effectActive(LootWeaponEffect effect)
	{
		return activeEffects.contains(effect);
	}
}
