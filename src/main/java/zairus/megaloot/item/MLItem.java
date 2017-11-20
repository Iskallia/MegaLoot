package zairus.megaloot.item;

import java.util.List;

import com.google.common.collect.Multimap;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import zairus.megaloot.MegaLoot;
import zairus.megaloot.loot.LootWeaponEffect;

public class MLItem extends Item
{
	protected MLItem()
	{
		this.setCreativeTab(MegaLoot.creativeTabMain);
	}
	
	public static final String WEAPONCASE_COMMON_ID = "weaponcase_common";
	public static final String WEAPONCASE_RARE_ID = "weaponcase_rare";
	public static final String WEAPONCASE_EPIC_ID = "weaponcase_epic";
	public static final String WEAPONSWORD_ID = "weaponsword";
	public static final String WEAPONBOW_ID = "weaponbow";
	public static final String BAUBLERING_ID = "baublering";
	public static final String ARMOR_BOOTS_ID = "armor_boots";
	public static final String ARMOR_LEGGINGS_D = "armor_leggings";
	public static final String ARMOR_CHESTPLATE_ID = "armor_chestplate";
	public static final String ARMOR_HELMET_ID = "armor_helmet";
	
	public static final String LOOT_TAG = "MegaLoot";
	public static final String LOOT_TAG_RARITY = "rarity";
	public static final String LOOT_TAG_MODEL = "model";
	public static final String LOOT_TAG_DAMAGE = "damage";
	public static final String LOOT_TAG_SPEED = "speed";
	public static final String LOOT_TAG_DURABILITY = "durability";
	public static final String LOOT_TAG_KILLS = "playerkills";
	public static final String LOOT_TAG_EFFECTLIST = "effectList";
	
	public static final String LOOT_TAG_DRAWSPEED = "drawspeed";
	public static final String LOOT_TAG_MULTISHOT = "miltishot";
	public static final String LOOT_TAG_POWER = "power";
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack)
	{
		Multimap<String, AttributeModifier> modifiers = modifiersForStack(slot, stack, super.getAttributeModifiers(slot, stack));
		
		return modifiers;
	}
	
	public static void applyEffects(ItemStack stack, EntityPlayer player)
	{
		List<LootWeaponEffect> effects = LootWeaponEffect.getEffectList(stack);
		
		for (LootWeaponEffect effect : effects)
		{
			player.addPotionEffect(effect.getPotionEffect(2, LootWeaponEffect.getAmplifierFromStack(stack, effect.getId())));
		}
	}
	
	public static Multimap<String, AttributeModifier> modifiersForStack(EntityEquipmentSlot slot, ItemStack stack, Multimap<String, AttributeModifier> initial)
	{
		Multimap<String, AttributeModifier> modifiers = initial;
		
		return modifiers;
	}
}
