package zairus.megaloot.loot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import zairus.megaloot.item.MLItem;
import zairus.megaloot.loot.LootSet.LootSetType;

public class LootWeaponEffect
{
	public static final Map<String, LootWeaponEffect> REGISTRY = new HashMap<String, LootWeaponEffect>();
	
	public static final LootWeaponEffect WITHERING = create("wither", EffectType.PASSIVE, MobEffects.WITHER).setDuration(1, 2).setItemTypes(LootSetType.SWORD, LootSetType.BOW);
	public static final LootWeaponEffect POISON = create("poison", EffectType.PASSIVE, MobEffects.POISON).setDuration(1, 3).setAmplifier(0, 1).setItemTypes(LootSetType.SWORD, LootSetType.BOW);
	public static final LootWeaponEffect HUNGER = create("hunger", EffectType.PASSIVE, MobEffects.HUNGER).setDuration(1, 10).setItemTypes(LootSetType.SWORD, LootSetType.BOW);
	public static final LootWeaponEffect LEVITATION = create("levitation", EffectType.PASSIVE, MobEffects.LEVITATION).setDuration(1, 3).setItemTypes(LootSetType.SWORD, LootSetType.BOW);
	public static final LootWeaponEffect WEAKNESS = create("weakness", EffectType.PASSIVE, MobEffects.WEAKNESS).setDuration(1, 3).setItemTypes(LootSetType.SWORD, LootSetType.BOW);
	public static final LootWeaponEffect SLOWNESS = create("slowness", EffectType.PASSIVE, MobEffects.SLOWNESS).setDuration(1, 3).setItemTypes(LootSetType.SWORD, LootSetType.BOW);
	public static final LootWeaponEffect BLINDNESS = create("blindness", EffectType.PASSIVE, MobEffects.BLINDNESS).setDuration(1, 2).setItemTypes(LootSetType.SWORD, LootSetType.BOW);
	public static final LootWeaponEffect MULTISHOT = create("multishot", EffectType.PASSIVE).setAmplifier(2, 6).setItemTypes(LootSetType.BOW);
	public static final LootWeaponEffect LEECHLIFE = create("leechlife", EffectType.PASSIVE).setAmplifier(1, 100).setItemTypes(LootSetType.SWORD);
	
	public static final LootWeaponEffect SPEED = create("speed", EffectType.PASSIVE, MobEffects.SPEED).setAmplifier(0, 1).setItemTypes(LootSetType.RING);
	public static final LootWeaponEffect STRENGTH = create("strength", EffectType.PASSIVE, MobEffects.STRENGTH).setAmplifier(0, 1).setItemTypes(LootSetType.RING);
	public static final LootWeaponEffect JUMP = create("jump", EffectType.PASSIVE, MobEffects.JUMP_BOOST).setAmplifier(0, 1).setItemTypes(LootSetType.RING);
	public static final LootWeaponEffect RESISTANCE = create("resistance", EffectType.PASSIVE, MobEffects.RESISTANCE).setAmplifier(0, 1).setItemTypes(LootSetType.RING);
	public static final LootWeaponEffect HASTE = create("haste", EffectType.PASSIVE, MobEffects.HASTE).setAmplifier(0, 1).setItemTypes(LootSetType.RING, LootSetType.TOOL);
	public static final LootWeaponEffect FIRE_RESISTANT = create("fire_resistance", EffectType.PASSIVE, MobEffects.FIRE_RESISTANCE);
	
	public static final LootWeaponEffect HEALTH_BOOST = create("healthboost", EffectType.PASSIVE, SharedMonsterAttributes.MAX_HEALTH).setAmplifier(1, 4).setItemTypes(LootSetType.ARMOR_HEAD, LootSetType.ARMOR_CHEST, LootSetType.ARMOR_LEGS, LootSetType.ARMOR_FEET, LootSetType.TOOL);
	public static final LootWeaponEffect KNOCKBACK_RESISTANCE = create("knockbackresistance", EffectType.PASSIVE, SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setAmplifier(1, 1).setItemTypes(LootSetType.ARMOR_CHEST, LootSetType.ARMOR_LEGS);
	public static final LootWeaponEffect LUCK = create("luck", EffectType.PASSIVE, SharedMonsterAttributes.LUCK).setAmplifier(1, 5).setItemTypes(LootSetType.ARMOR_HEAD);
	
	public static final LootWeaponEffect ARMOR = create("armor", EffectType.PASSIVE, SharedMonsterAttributes.ARMOR).setAmplifier(3, 10).setItemTypes(LootSetType.ARMOR_HEAD, LootSetType.ARMOR_CHEST, LootSetType.ARMOR_LEGS, LootSetType.ARMOR_FEET);
	public static final LootWeaponEffect TOUGHNESS = create("armor_toughness", EffectType.PASSIVE, SharedMonsterAttributes.ARMOR_TOUGHNESS).setAmplifier(3, 6).setItemTypes(LootSetType.ARMOR_HEAD, LootSetType.ARMOR_CHEST, LootSetType.ARMOR_LEGS, LootSetType.ARMOR_FEET);
	
	public static final LootWeaponEffect LIFE_LONG = create("life_long", EffectType.PASSIVE).setItemTypes(LootSetType.TOOL);
	public static final LootWeaponEffect NIGHT_VISION = create("night_vision", EffectType.PASSIVE, MobEffects.NIGHT_VISION).setItemTypes(LootSetType.TOOL);
	public static final LootWeaponEffect AUTO_SMELT = create("auto_smelt", EffectType.ACTIVE).setItemTypes(LootSetType.TOOL).setAction(new LootEffectActionAutoSmelt());
	public static final LootWeaponEffect AREA_MINER = create("area_miner", EffectType.ACTIVE).setItemTypes(LootSetType.TOOL).setAction(new LootEffectActionAreaMiner());
	public static final LootWeaponEffect VOID = create("void", EffectType.ACTIVE).setItemTypes(LootSetType.TOOL).setAction(new LootEffectActionVoid());
	public static final LootWeaponEffect SELECTIVE = create("selective", EffectType.USE).setItemTypes(LootSetType.TOOL).setAction(new LootEffectActionVoidFilter());
	public static final LootWeaponEffect MAGNETIC = create("magnetic", EffectType.PASSIVE).setItemTypes(LootSetType.TOOL).setAction(new LootEffectActionMagnet());
	public static final LootWeaponEffect MULTI = create("multi", EffectType.PASSIVE).setItemTypes(LootSetType.TOOL);
	public static final LootWeaponEffect SLEEP = create("sleep", EffectType.USE).setItemTypes(LootSetType.TOOL).setAction(new LootEffectActionSleep());
	
	public static final LootWeaponEffect JETPACK = create("jetpack", EffectType.PASSIVE).setItemTypes(LootSetType.ARMOR_CHEST).setAmplifier(5, 20).setDuration(2, 5);
	
	private String id;
	private Potion effect;
	private IAttribute attribute;
	private int durationMin = 100;
	private int durationMax = 300;
	private int amplifierMin = 0;
	private int amplifierMax = 0;
	private List<LootSetType> applyToItems = new ArrayList<LootSetType>();
	
	private ILootEffectAction action;
	
	private final EffectType effectType;
	
	public enum EffectType
	{
		ACTIVE(TextFormatting.GOLD),
		PASSIVE(TextFormatting.GREEN),
		USE(TextFormatting.AQUA);
		
		private TextFormatting color;
		
		private EffectType(TextFormatting color)
		{
			this.color = color;
		}
		
		public TextFormatting getColor()
		{
			return this.color;
		}
		
		public boolean equals(EffectType type)
		{
			return type == this;
		}
	}
	
	private LootWeaponEffect(EffectType type)
	{
		this.effectType = type;
	}
	
	public EffectType getType()
	{
		return this.effectType;
	}
	
	public boolean applyToItemType(LootSetType type)
	{
		return applyToItems.contains(type);
	}
	
	public ILootEffectAction getAction()
	{
		return this.action;
	}
	
	protected LootWeaponEffect setAction(ILootEffectAction action)
	{
		this.action = action;
		return this;
	}
	
	protected LootWeaponEffect setItemTypes(LootSetType... itemTypes)
	{
		for (LootSetType itemType : itemTypes)
		{
			applyToItems.add(itemType);
		}
		
		return this;
	}
	
	@Nullable
	public static LootWeaponEffect getById(String id)
	{
		LootWeaponEffect weaponEffect = REGISTRY.get(id);
		
		return weaponEffect;
	}
	
	public String getId()
	{
		return this.id;
	}
	
	@Nullable
	public static LootWeaponEffect getRandomExcluding(Random rand, LootSetType type, List<LootWeaponEffect> exclude)
	{
		LootWeaponEffect weaponEffect = null;
		
		boolean hasActive = false;
		boolean hasUse = false;
		
		for (LootWeaponEffect ex : exclude)
		{
			if (ex.getType() == EffectType.ACTIVE)
				hasActive = true;
			
			if (ex.getType() == EffectType.USE)
				hasUse = true;
		}
		
		List<LootWeaponEffect> list = new ArrayList<LootWeaponEffect>();
		
		for (LootWeaponEffect e : REGISTRY.values())
		{
			if (e.applyToItemType(type))
			{
				if (
						!(hasActive && e.getType() == EffectType.ACTIVE) 
						&& !(hasUse && e.getType() == EffectType.USE))
					list.add(e);
			}
		}
		
		list.removeAll(exclude);
		
		if (list.size() > 0)
			weaponEffect = list.get(rand.nextInt(list.size()));
		
		return weaponEffect;
	}
	
	public NBTTagCompound getNBT(Random rand)
	{
		NBTTagCompound tag = new NBTTagCompound();
		
		tag.setString("id", this.getId());
		tag.setInteger("duration", this.getDuration(rand));
		tag.setInteger("amplifier", this.getAmplifier(rand));
		
		return tag;
	}
	
	public static int getDurationFromStack(ItemStack stack, String effectId)
	{
		int duration = 0;
		
		NBTTagList effectTagList = LootItemHelper.getLootTagList(stack, MLItem.LOOT_TAG_EFFECTLIST);
		
		int count = effectTagList.tagCount();
		
		for (int i = 0; i < count; ++i)
		{
			NBTTagCompound e = effectTagList.getCompoundTagAt(i);
			if (e.getString("id").contains(effectId))
			{
				duration = e.getInteger("duration");
			}
		}
		
		return duration;
	}
	
	public static int getAmplifierFromStack(ItemStack stack, String effectId)
	{
		int amplifier = 0;
		
		NBTTagList effectTagList = LootItemHelper.getLootTagList(stack, MLItem.LOOT_TAG_EFFECTLIST);
		
		int count = effectTagList.tagCount();
		
		for (int i = 0; i < count; ++i)
		{
			NBTTagCompound e = effectTagList.getCompoundTagAt(i);
			if (e.getString("id").contains(effectId))
			{
				amplifier = e.getInteger("amplifier");
			}
		}
		
		return amplifier;
	}
	
	public static List<LootWeaponEffect> getEffectList(ItemStack stack)
	{
		List<LootWeaponEffect> list = new ArrayList<LootWeaponEffect>();
		
		NBTTagList effectTagList = LootItemHelper.getLootTagList(stack, MLItem.LOOT_TAG_EFFECTLIST);
		
		int count = effectTagList.tagCount();
		
		for (int i = 0; i < count; ++i)
		{
			NBTTagCompound e = effectTagList.getCompoundTagAt(i);
			list.add(LootWeaponEffect.getById(e.getString("id")));
		}
		
		return list;
	}
	
	protected static LootWeaponEffect create(String id, EffectType type)
	{
		return create(id, type, null, null);
	}
	
	protected static LootWeaponEffect create(String id, EffectType type, IAttribute attribute)
	{
		return create(id, type, null, attribute);
	}
	
	protected static LootWeaponEffect create(String id, EffectType type, Potion effect)
	{
		return create(id, type, effect, null);
	}
	
	protected static LootWeaponEffect create(String id, EffectType type, Potion effect, IAttribute attribute)
	{
		LootWeaponEffect weaponEffect = new LootWeaponEffect(type);
		
		weaponEffect.id = id;
		weaponEffect.effect = effect;
		weaponEffect.attribute = attribute;
		
		REGISTRY.put(id, weaponEffect);
		
		return weaponEffect;
	}
	
	protected LootWeaponEffect setDuration(int min, int max)
	{
		this.durationMin = min * 100;
		this.durationMax = max * 100;
		return this;
	}
	
	protected LootWeaponEffect setAmplifier(int min, int max)
	{
		this.amplifierMin = min;
		this.amplifierMax = max;
		return this;
	}
	
	private int getDuration(Random rand)
	{
		int duration = this.durationMin;
		
		if (duration < this.durationMax)
			duration += rand.nextInt(this.durationMax - duration + 1);
		
		return duration;
	}
	
	private int getAmplifier(Random rand)
	{
		int amplifier = this.amplifierMin;
		
		if (amplifier < this.amplifierMax)
			amplifier += rand.nextInt(this.amplifierMax - amplifier + 1);
		
		return amplifier;
	}
	
	@Nullable
	public IAttribute getAttribute()
	{
		return this.attribute;
	}
	
	@Nullable
	public PotionEffect getPotionEffect(int duration, int amplifier)
	{
		if (this.effect == null)
			return null;
		
		PotionEffect weaponEffect = new PotionEffect(this.effect, duration, amplifier, true, false);
		return weaponEffect;
	}
	
	public void onHit(int duration, int amplifier, EntityLivingBase target, EntityLivingBase attacker)
	{
		PotionEffect effect = this.getPotionEffect(duration, amplifier);
		
		if (effect != null)
			target.addPotionEffect(effect);
	}
	
	public String getAmplifierString(ItemStack stack, String effectId)
	{
		return getAmplifierString(stack, effectId, 0);
	}
	
	public String getAmplifierString(ItemStack stack, String effectId, int add)
	{
		return TextFormatting.BOLD + "" + (getAmplifierFromStack(stack, effectId) + add) + "" + TextFormatting.RESET + ""  + ((this.getType() == EffectType.PASSIVE)? TextFormatting.GREEN : TextFormatting.GOLD) + "";
	}
	
	public String getDurationString(ItemStack stack, String effectId)
	{
		return TextFormatting.BOLD + "" + (getDurationFromStack(stack, effectId) / 100) + "" + TextFormatting.RESET + "" + TextFormatting.GREEN + "";
	}
	
	public String getActionStatus(ItemStack stack)
	{
		String statusString = "";
		
		if (this.getAction() != null)
		{
			statusString = this.getAction().getStatusString(stack);
		}
		
		return statusString;
	}
}
