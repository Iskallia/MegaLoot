package zairus.megaloot.item;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Multimap;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.megaloot.MegaLoot;
import zairus.megaloot.loot.LootItemHelper;
import zairus.megaloot.loot.LootWeaponEffect;

@SuppressWarnings("deprecation")
public class MLItemWeaponSword extends ItemSword
{
	protected MLItemWeaponSword()
	{
		super(ToolMaterial.DIAMOND);
		
		this.setCreativeTab(MegaLoot.creativeTabMain);
		
		this.addPropertyOverride(new ResourceLocation("model"), new IItemPropertyGetter() {
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity)
			{
				float model = 1.0F;
				
				model = (float)LootItemHelper.getLootIntValue(stack, MLItem.LOOT_TAG_MODEL);
				
				return model;
			}
		});
	}
	
	@Override
	public int getMaxDamage(ItemStack stack)
	{
		return LootItemHelper.getMaxDamage(stack);
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		boolean hit = super.hitEntity(stack, target, attacker);
		
		if (target instanceof EntityPlayer && target.getHealth() <= 0.0)
		{
			int kills = LootItemHelper.getLootIntValue(stack, MLItem.LOOT_TAG_KILLS);
			kills++;
			LootItemHelper.setLootIntValue(stack, MLItem.LOOT_TAG_KILLS, kills);
		}
		
		List<LootWeaponEffect> effects = LootWeaponEffect.getEffectList(stack);
		
		if (effects.size() > 0)
		{
			for (LootWeaponEffect effect : effects)
			{
				effect.onHit(itemRand, target, attacker);
			}
		}
		
		return hit;
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack)
	{
		Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
		
		int attackDamage = LootItemHelper.getLootIntValue(stack, MLItem.LOOT_TAG_DAMAGE);
		float attackSpeed = LootItemHelper.getLootFloatValue(stack, MLItem.LOOT_TAG_SPEED);
		
		if (slot == EntityEquipmentSlot.MAINHAND)
		{
			multimap.removeAll(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName());
			multimap.removeAll(SharedMonsterAttributes.ATTACK_SPEED.getAttributeUnlocalizedName());
			
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)attackDamage, 0));
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", (double)attackSpeed, 0));
		}
		
		return multimap;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced)
	{
		int attackDamage = 7;
		float speedDisplay = 0.0F;
		double sp1 = 0.0D;
		int durability = this.getMaxDamage(stack);
		
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey(MLItem.LOOT_TAG))
		{
			NBTTagCompound tag = stack.getTagCompound().getCompoundTag(MLItem.LOOT_TAG);
			
			if (tag.hasKey(MLItem.LOOT_TAG_DAMAGE))
				attackDamage = tag.getInteger(MLItem.LOOT_TAG_DAMAGE);
			
			if (tag.hasKey(MLItem.LOOT_TAG_SPEED))
			{
				speedDisplay = tag.getFloat(MLItem.LOOT_TAG_SPEED);
				sp1 = speedDisplay;
				
				if (player != null)
				{
					sp1 += player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getBaseValue();
				}
			}
			
			if (tag.hasKey(MLItem.LOOT_TAG_DURABILITY))
				durability = tag.getInteger(MLItem.LOOT_TAG_DURABILITY);
		}
		
		attackDamage += EnchantmentHelper.getModifierForCreature(stack, EnumCreatureAttribute.UNDEFINED);
		
		tooltip.add("");
		tooltip.add(TextFormatting.GRAY + "" + attackDamage + " Damage | " + ItemStack.DECIMALFORMAT.format(sp1) + " Atack Speed");
		tooltip.add(TextFormatting.WHITE + ItemStack.DECIMALFORMAT.format(((float)attackDamage * sp1)) + " DPS");
		tooltip.add("");
		
		List<LootWeaponEffect> effects = LootWeaponEffect.getEffectList(stack);
		for (LootWeaponEffect effect : effects)
		{
			tooltip.add(TextFormatting.RESET + "" + TextFormatting.AQUA + I18n.translateToLocalFormatted("weaponeffect." + effect.getId() + ".description", new Object[] { effect.getDurationString() }));
		}
		
		tooltip.add(durability + " Durability");
		
		int kills = LootItemHelper.getLootIntValue(stack, MLItem.LOOT_TAG_KILLS);
		if (kills > 0)
			tooltip.add(kills + "player kills");
		
		tooltip.add("");
	}
}
