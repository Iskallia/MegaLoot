package zairus.megaloot.loot;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.client.model.ModelBiped;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.megaloot.client.model.MLModelArmorSets;

public class LootSet
{
	public static final Map<String, LootSet> REGISTRY = new HashMap<String, LootSet>();
	
	public static final LootSet VIKING = get("viking");
	
	//Viking set
	//Samuray set
	//Dragon lord set
	//Knight set (big shoulders)
	
	protected String id;
	
	public static final String[] SWORD_NAMES = {
			"Balance Sword"
			,"The Crusader"
			,"Butcher Blade"
			,"Mageblade"
			,"Long Sword"
			,"The Shadow"
			,"Jade"
			,"Blaze Guard"
			,"Heart Spike"
			,"Torment"
			,"Phantom"
			,"Gladius"
			
			,"Nat Blade"
			,"Vine Sword"
			,"Knight Blade"
			,"Red"
			,"Dog Bone"
			,"Ice Blade"
			,"Sabre"
			,"Molten Sword"
			,"Phase Blade"
			,"Crystal Wing"
			,"Wakizashi"
			,"Wizard Spike"};
	
	public static final String[] BOW_NAMES = {
			"Long Bow"
			,"Venom"
			,"Nat Bow"
			,"Black Widow"
			,"Eagle"
			,"Drawling"
			,"Zach"
			,"Slimestrike"
			,"Thunderforce"
			,"Ghost"
			,"Angel"
			,"Starshot"
	};
	
	public static final String[] RING_NAMES = {
			"The Pearl"
			,"Fire Stone"
			,"Burning Stone"
			,"The Ruby"
			,"The Ruby Eye"
			,"Hope"
			,"Purity Hope"
			,"The Onyx"
			,"Jordan"
			,"Breeze"
			,"Wind"
			,"The Ocelot"};
	
	public static final Map<LootSetType, String[]> LOOT_ITEM_NAMES = new HashMap<LootSetType, String[]>();
	
	static
	{
		LOOT_ITEM_NAMES.put(LootSetType.SWORD, SWORD_NAMES);
		LOOT_ITEM_NAMES.put(LootSetType.BOW, BOW_NAMES);
		LOOT_ITEM_NAMES.put(LootSetType.RING, RING_NAMES);
	}
	
	protected static LootSet get(String name)
	{
		LootSet set = new LootSet();
		set.id = name;
		
		REGISTRY.put(name, set);
		
		return set;
	}
	
	public String getId()
	{
		return this.id;
	}
	
	@SideOnly(Side.CLIENT)
	public static ModelBiped getArmorModel(LootSet set, LootSetType type)
	{
		Map<LootSet, Map<LootSetType, ModelBiped>> armorByType = new HashMap<LootSet, Map<LootSetType, ModelBiped>>();
		
		Map<LootSetType, ModelBiped> vikingSet = new HashMap<LootSetType, ModelBiped>();
		vikingSet.put(LootSetType.ARMOR_HEAD, MLModelArmorSets.ARMOR_VIKING_HELMET);
		vikingSet.put(LootSetType.ARMOR_CHEST, MLModelArmorSets.ARMOR_VIKING_CHESTPLATE);
		vikingSet.put(LootSetType.ARMOR_LEGS, MLModelArmorSets.ARMOR_VIKING_LEGGINGS);
		vikingSet.put(LootSetType.ARMOR_FEET, MLModelArmorSets.ARMOR_VIKING_BOOTS);
		
		armorByType.put(LootSet.VIKING, vikingSet);
		
		return armorByType.get(set).get(type);
	}
	
	public static String getNameForType(LootSetType type, Random rand)
	{
		if (!LOOT_ITEM_NAMES.containsKey(type))
			return "";
		
		String[] names = LOOT_ITEM_NAMES.get(type);
		
		return names[rand.nextInt(names.length)];
	}
	
	public enum LootSetType
	{
		SWORD,
		BOW,
		RING,
		ARMOR_FEET,
		ARMOR_LEGS,
		ARMOR_CHEST,
		ARMOR_HEAD
	}
	
	public class LootSetElement
	{
		public String name;
		public LootSetType type;
	}
}
