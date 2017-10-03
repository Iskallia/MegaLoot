package zairus.megaloot.loot;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.util.text.TextFormatting;

public class LootRarity
{
	public static final LootRarity COMMON = get("Common", TextFormatting.WHITE).setDamage(3, 10).setSpeed(-3.1F, -2.399F).setDurability(100, 550);
	public static final LootRarity RARE = get("Rare", TextFormatting.YELLOW).setDamage(5, 12).setSpeed(-2.8F, -2.3F).setDurability(350, 1450).setModifierCount(0, 2);
	public static final LootRarity EPIC = get("Epic", TextFormatting.LIGHT_PURPLE).setDamage(7, 16).setSpeed(-2.69999F, -2.1F).setDurability(850, 2500).setModifierCount(1, 3);
	
	public static final Map<String, LootRarity> REGISTRY = new HashMap<String, LootRarity>();
	
	private TextFormatting color = TextFormatting.WHITE;
	private int damageMin = 0;
	private int damageMax = 7;
	private float speedMin = 0.0F;
	private float speedMax = 1.0F;
	private int durabilityMin = 0;
	private int durabilityMax = 0;
	private int modifiersMin = 0;
	private int modifiersMax = 1;
	private String id;
	
	public TextFormatting getColor()
	{
		return this.color;
	}
	
	public String getId()
	{
		return this.id;
	}
	
	protected LootRarity setModifierCount(int min, int max)
	{
		this.modifiersMin = min;
		this.modifiersMax = max;
		return this;
	}
	
	protected LootRarity setDurability(int min, int max)
	{
		this.durabilityMin = min;
		this.durabilityMax = max;
		return this;
	}
	
	protected LootRarity setDamage(int min, int max)
	{
		this.damageMin = min;
		this.damageMax = max;
		return this;
	}
	
	protected LootRarity setSpeed(float min, float max)
	{
		this.speedMin = min;
		this.speedMax = max;
		return this;
	}
	
	public int getModifierCount(Random rand)
	{
		int modifierCount = this.modifiersMin;
		
		if (modifierCount < this.modifiersMax)
			modifierCount += rand.nextInt(this.modifiersMax - modifierCount);
		
		return modifierCount;
	}
	
	public int getDurability(Random rand)
	{
		int durability = this.durabilityMin;
		
		if (durability < this.durabilityMax)
			durability += rand.nextInt(this.durabilityMax - durability);
		
		return durability;
	}
	
	public int getDamage(Random rand)
	{
		int damage = this.damageMin;
		
		if (damage < this.damageMax)
			damage += rand.nextInt(this.damageMax - damage);
		
		return damage;
	}
	
	public float getSpeed(Random rand)
	{
		float speed = this.speedMin;
		
		speed += (this.speedMax - speed) * rand.nextFloat();
		
		return speed;
	}
	
	protected static LootRarity get(String id, TextFormatting color)
	{
		LootRarity r = new LootRarity();
		
		r.id = id;
		r.color = color;
		
		return r;
	}
}
