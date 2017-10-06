package zairus.megaloot.loot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityLivingBase;
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
	
	public static final LootWeaponEffect WITHERING = create("wither", MobEffects.WITHER).setDuration(1, 3).setItemTypes(LootSetType.SWORD, LootSetType.BOW);
	public static final LootWeaponEffect POISON = create("poison", MobEffects.POISON).setDuration(1, 6).setAmplifier(0, 1).setItemTypes(LootSetType.SWORD, LootSetType.BOW);
	public static final LootWeaponEffect HUNGER = create("hunger", MobEffects.HUNGER).setDuration(1, 10).setItemTypes(LootSetType.SWORD, LootSetType.BOW);
	public static final LootWeaponEffect LEVITATION = create("levitation", MobEffects.LEVITATION).setDuration(1, 3).setItemTypes(LootSetType.SWORD, LootSetType.BOW);
	public static final LootWeaponEffect WEAKNESS = create("weakness", MobEffects.WEAKNESS).setDuration(1, 3).setItemTypes(LootSetType.SWORD, LootSetType.BOW);
	public static final LootWeaponEffect SLOWNESS = create("slowness", MobEffects.SLOWNESS).setDuration(1, 3).setItemTypes(LootSetType.SWORD, LootSetType.BOW);
	public static final LootWeaponEffect BLINDNESS = create("blindness", MobEffects.BLINDNESS).setDuration(1, 2).setItemTypes(LootSetType.SWORD, LootSetType.BOW);
	public static final LootWeaponEffect MULTISHOT = create("multishot", null).setAmplifier(2, 6).setItemTypes(LootSetType.BOW);
	public static final LootWeaponEffect LEECHLIFE = create("leechlife", null).setAmplifier(1, 100).setItemTypes(LootSetType.SWORD);
	
	public static final LootWeaponEffect SPEED = create("speed", MobEffects.SPEED).setAmplifier(0, 2).setItemTypes(LootSetType.RING);
	public static final LootWeaponEffect STRENGTH = create("strength", MobEffects.STRENGTH).setAmplifier(0, 2).setItemTypes(LootSetType.RING);
	public static final LootWeaponEffect JUMP = create("jump", MobEffects.JUMP_BOOST).setAmplifier(0, 2).setItemTypes(LootSetType.RING);
	public static final LootWeaponEffect RESISTANCE = create("resistance", MobEffects.RESISTANCE).setAmplifier(0, 2).setItemTypes(LootSetType.RING);
	public static final LootWeaponEffect HASTE = create("haste", MobEffects.HASTE).setAmplifier(0, 4).setItemTypes(LootSetType.RING);
	
	private String id;
	private Potion effect;
	private int durationMin = 100;
	private int durationMax = 300;
	private int amplifierMin = 0;
	private int amplifierMax = 0;
	private List<LootSetType> applyToItems = new ArrayList<LootSetType>();
	
	public boolean applyToItemType(LootSetType type)
	{
		return applyToItems.contains(type);
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
		
		List<LootWeaponEffect> list = new ArrayList<LootWeaponEffect>();
		
		for (LootWeaponEffect e : REGISTRY.values())
		{
			if (e.applyToItemType(type))
				list.add(e);
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
	
	protected static LootWeaponEffect create(String id, Potion effect)
	{
		LootWeaponEffect weaponEffect = new LootWeaponEffect();
		
		weaponEffect.id = id;
		weaponEffect.effect = effect;
		
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
		return TextFormatting.BOLD + "" + (getAmplifierFromStack(stack, effectId) + add) + "" + TextFormatting.RESET + ""  + TextFormatting.AQUA + "";
	}
	
	public String getDurationString(ItemStack stack, String effectId)
	{
		return TextFormatting.BOLD + "" + (getDurationFromStack(stack, effectId) / 100) + "" + TextFormatting.RESET + "" + TextFormatting.AQUA + "";
	}
}
