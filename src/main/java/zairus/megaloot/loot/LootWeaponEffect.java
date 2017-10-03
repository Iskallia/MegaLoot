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
import zairus.megaloot.item.MLItem;

public class LootWeaponEffect
{
	public static final Map<String, LootWeaponEffect> REGISTRY = new HashMap<String, LootWeaponEffect>();
	
	public static final LootWeaponEffect WITHERING = create("wither", MobEffects.WITHER).setDuration(1, 3);
	public static final LootWeaponEffect POISON = create("poison", MobEffects.POISON).setDuration(1, 6).setAmplifier(0, 1);
	public static final LootWeaponEffect HUNGER = create("hunger", MobEffects.HUNGER).setDuration(1, 10);
	public static final LootWeaponEffect LEVITATION = create("levitation", MobEffects.LEVITATION).setDuration(1, 3);
	public static final LootWeaponEffect WEAKNESS = create("weakness", MobEffects.WEAKNESS).setDuration(1, 3);
	public static final LootWeaponEffect SLOWNESS = create("slowness", MobEffects.SLOWNESS).setDuration(1, 3);
	public static final LootWeaponEffect BLINDNESS = create("blindness", MobEffects.BLINDNESS).setDuration(1, 2);
	
	private String id;
	private Potion effect;
	private int durationMin = 100;
	private int durationMax = 300;
	private int amplifierMin = 0;
	private int amplifierMax = 0;
	
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
	public static LootWeaponEffect getRandomExcluding(Random rand, LootWeaponEffect... exclude)
	{
		LootWeaponEffect weaponEffect = null;
		
		List<LootWeaponEffect> list = new ArrayList<LootWeaponEffect>();
		
		for (LootWeaponEffect e : REGISTRY.values())
		{
			list.add(e);
		}
		
		list.remove(exclude);
		
		if (list.size() > 0)
			weaponEffect = list.get(rand.nextInt(list.size()));
		
		return weaponEffect;
	}
	
	public NBTTagCompound getNBT()
	{
		NBTTagCompound tag = new NBTTagCompound();
		
		tag.setString("id", this.getId());
		
		return tag;
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
	
	public void onHit(Random rand, EntityLivingBase target, EntityLivingBase attacker)
	{
		if (this.effect == null)
			return;
		
		PotionEffect weaponEffect = new PotionEffect(this.effect, this.getDuration(rand), this.getAmplifier(rand), true, false);
		target.addPotionEffect(weaponEffect);
	}
}
