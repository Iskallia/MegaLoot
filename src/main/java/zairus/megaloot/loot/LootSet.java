package zairus.megaloot.loot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.client.model.ModelBiped;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.megaloot.client.model.MLModelArmorSets;

public class LootSet
{
	public static final Map<String, LootSet> REGISTRY = new HashMap<String, LootSet>();
	
	public static final LootSet VIKING = get("viking", LootWeaponEffect.STRENGTH);
	public static final LootSet DRAGON = get("dragon", LootWeaponEffect.FIRE_RESISTANT);
	public static final LootSet KNIGHT = get("knight", LootWeaponEffect.RESISTANCE);
	public static final LootSet SAMURAI = get("samurai", LootWeaponEffect.SPEED);
	
	public final int itemModel;
	public final LootWeaponEffect bonusEffect;
	
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
			,"Wizard Spike"
			,"Knightfall"
			,"Catastrophe"
			,"Ragespike"
			,"Improved Reaver"
			,"Mourning Etcher"
			,"Dragonbreath Copper Greatsword"
			,"Rusty Diamond Katana"
			,"Heartstriker, Guardian of Grace"
			,"The Black Blade, Cry of Pride"
			,"Nethersbane, Reaper of the Lost"
			,"Massacre"
			,"Lightning"
			,"Silencer"
			,"Grieving Carver"
			,"Renewed Scimitar"
			,"Crazed Silver Slicer"
			,"Annihilation Gold Claymore"
			,"Stormcaller, Betrayer of Summoning"
			,"Warmonger, Vengeance of the Dreadlord"
			,"Rigormortis, Bond of Agony"
			,"The Ambassador"
			,"Endbringer"
			,"Blight's Plight"
			,"Rusty Guardian"
			,"Stormfury Skewer"
			,"Trainee's Iron Guardian"
			,"Cataclysm Steel Doomblade"
			,"Lightning, Hope of the Forsaken"
			,"Suspension, Pact of Ashes"
			,"The Black Blade, Warglaive of Anguish"
			,"Hope's End"
			,"Requiem"
			,"Blight's Plight"
			,"Tyrannical Protector"
			,"Isolated Greatsword"
			,"Heartless Glass Sculptor"
			,"Exiled Adamantite Shortsword"
			,"Massacre, Blessed Blade of Delusions"
			,"Brutality, Longsword of Decay"
			,"Toothpick, Skewer of the Incoming Storm"};
	
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
	
	public static final String[] TOOL_NAMES = {
			"The Pawpaw"
			,"The Single Wing"
			,"The Godzilla"
			,"The Picker"
			,"The Little Green"
			,"The Big Buddy"
			,"The Guzzler"
			,"The Wiggle Stick"
			,"The Kicker"
			,"The Ground Crusher"
			,"The Slacker"
			,"The Kicker"
			,"The Sludge"
			,"The Commander"
			,"The Flat Nose"
			,"The Toothpick"
			,"The Measurer"
			,"The Belly"
			,"The Clicker"
			,"The Parrot"
			,"The Bigwig"
			,"The Drag Bag"
			,"The Chicken Beak"
			,"The Whiskers"
			,"The Fury"
			,"The Pokey"
			,"The Wedger"
			,"The Friendly One"
			,"The Goofy Hook"
			,"The Gobbler"
			,"The Dislocator"
			,"The Winger"
			,"The Knockout"
			,"The Pully"
			,"The Prickle"
			,"The Killer"
			,"The Band Aid"
			,"The Punisher"
			,"The Spiker"
			,"The Weeping Bell"
	};
	
	public static final Map<LootSetType, String[]> LOOT_ITEM_NAMES = new HashMap<LootSetType, String[]>();
	
	static
	{
		LOOT_ITEM_NAMES.put(LootSetType.SWORD, SWORD_NAMES);
		LOOT_ITEM_NAMES.put(LootSetType.BOW, BOW_NAMES);
		LOOT_ITEM_NAMES.put(LootSetType.RING, RING_NAMES);
		LOOT_ITEM_NAMES.put(LootSetType.TOOL, TOOL_NAMES);
	}
	
	protected LootSet(int itemModel, LootWeaponEffect bonus)
	{
		this.itemModel = itemModel;
		this.bonusEffect = bonus;
	}
	
	protected static LootSet get(String name, LootWeaponEffect bonus)
	{
		LootSet set = new LootSet(REGISTRY.size() + 1, bonus);
		set.id = name;
		
		REGISTRY.put(name, set);
		
		return set;
	}
	
	public static LootSet getRandom(Random rand)
	{
		List<String> lootSetKeys = new ArrayList<String>(REGISTRY.keySet());
		
		String setId = lootSetKeys.get(rand.nextInt(lootSetKeys.size()));
		
		return getById(setId);
	}
	
	public static LootSet getById(String id)
	{
		LootSet lootSet = LootSet.VIKING;
		
		if (id.length() > 0 && REGISTRY.containsKey(id))
			lootSet = REGISTRY.get(id);
		
		return lootSet;
	}
	
	public String getId()
	{
		return this.id;
	}
	
	@SideOnly(Side.CLIENT)
	public static ModelBiped getArmorModel(LootSet set, LootSetType type)
	{
		Map<LootSet, Map<LootSetType, ModelBiped>> armorByType = new HashMap<LootSet, Map<LootSetType, ModelBiped>>();
		
		Map<LootSetType, ModelBiped> armorSet = new HashMap<LootSetType, ModelBiped>();
		armorSet.put(LootSetType.ARMOR_HEAD, MLModelArmorSets.ARMOR_VIKING_HELMET);
		armorSet.put(LootSetType.ARMOR_CHEST, MLModelArmorSets.ARMOR_VIKING_CHESTPLATE);
		armorSet.put(LootSetType.ARMOR_LEGS, MLModelArmorSets.ARMOR_VIKING_LEGGINGS);
		armorSet.put(LootSetType.ARMOR_FEET, MLModelArmorSets.ARMOR_VIKING_BOOTS);
		
		armorByType.put(LootSet.VIKING, armorSet);
		
		armorSet = new HashMap<LootSetType, ModelBiped>();
		armorSet.put(LootSetType.ARMOR_HEAD, MLModelArmorSets.ARMOR_DRAGON_HELMET);
		armorSet.put(LootSetType.ARMOR_CHEST, MLModelArmorSets.ARMOR_DRAGON_CHESTPLATE);
		armorSet.put(LootSetType.ARMOR_LEGS, MLModelArmorSets.ARMOR_DRAGON_LEGGINGS);
		armorSet.put(LootSetType.ARMOR_FEET, MLModelArmorSets.ARMOR_DRAGON_BOOTS);
		
		armorByType.put(LootSet.DRAGON, armorSet);
		
		armorSet = new HashMap<LootSetType, ModelBiped>();
		armorSet.put(LootSetType.ARMOR_HEAD, MLModelArmorSets.ARMOR_KNIGHT_HELMET);
		armorSet.put(LootSetType.ARMOR_CHEST, MLModelArmorSets.ARMOR_KNIGHT_CHESTPLATE);
		armorSet.put(LootSetType.ARMOR_LEGS, MLModelArmorSets.ARMOR_KNIGHT_LEGGINGS);
		armorSet.put(LootSetType.ARMOR_FEET, MLModelArmorSets.ARMOR_KNIGHT_BOOTS);
		
		armorByType.put(LootSet.KNIGHT, armorSet);
		
		armorSet = new HashMap<LootSetType, ModelBiped>();
		armorSet.put(LootSetType.ARMOR_HEAD, MLModelArmorSets.ARMOR_SAMURAI_HELMET);
		armorSet.put(LootSetType.ARMOR_CHEST, MLModelArmorSets.ARMOR_SAMURAI_CHESTPLATE);
		armorSet.put(LootSetType.ARMOR_LEGS, MLModelArmorSets.ARMOR_SAMURAI_LEGGINGS);
		armorSet.put(LootSetType.ARMOR_FEET, MLModelArmorSets.ARMOR_SAMURAI_BOOTS);
		
		armorByType.put(LootSet.SAMURAI, armorSet);
		
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
		SWORD("sword", 76),
		BOW("bow", 12),
		RING("ring", 13),
		ARMOR_FEET("armor_feet", 4),
		ARMOR_LEGS("armor_legs", 4),
		ARMOR_CHEST("armor_chest", 4),
		ARMOR_HEAD("armor_head", 4),
		TOOL("tool", 21);
		
		public final int models;
		private String id;
		
		private LootSetType(String typeId, int totalModels)
		{
			this.id = typeId;
			this.models = totalModels;
		}
		
		public String getId()
		{
			return this.id;
		}
	}
	
	public class LootSetElement
	{
		public String name;
		public LootSetType type;
	}
}
