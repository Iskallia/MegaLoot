package zairus.megaloot.item;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;
import zairus.megaloot.loot.LootSet.LootSetType;

public class MLItemTypeRegistry
{
	protected static final Map<Item, LootSetType> ITEMTYPEREGISTRY = new HashMap<Item, LootSetType>();
	
	public static void register(Item item, LootSetType type)
	{
		ITEMTYPEREGISTRY.put(item, type);
	}
	
	public static LootSetType get(Item item)
	{
		return ITEMTYPEREGISTRY.get(item);
	}
}
