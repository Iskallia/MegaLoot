package zairus.megaloot.item;

import java.util.List;
import java.util.UUID;

import com.google.common.collect.Multimap;

import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.megaloot.MegaLoot;
import zairus.megaloot.loot.LootItemHelper;
import zairus.megaloot.loot.LootSet;
import zairus.megaloot.loot.LootWeaponEffect;

public class MLItemArmor extends ItemArmor
{
	protected MLItemArmor(EntityEquipmentSlot equipmentSlot)
	{
		super(ArmorMaterial.DIAMOND, 3, equipmentSlot);
		this.setCreativeTab(MegaLoot.creativeTabMain);
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, DISPENSER_BEHAVIOR);
	}
	
	@SideOnly(Side.CLIENT)
	public net.minecraft.client.model.ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, net.minecraft.client.model.ModelBiped _default)
	{
		return LootSet.getArmorModel(LootSet.VIKING, MLItems.getItemType(itemStack.getItem()));
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
	{
		return "megaloot:textures/models/armor/1.png";
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack)
	{
		Multimap<String, AttributeModifier> modifiers = MLItem.modifiersForStack(slot, stack, super.getAttributeModifiers(slot, stack));
		
		if (this.getEquipmentSlot() == slot)
		{
			List<LootWeaponEffect> effects = LootWeaponEffect.getEffectList(stack);
			
			for (LootWeaponEffect effect : effects)
			{
				if (effect.getAttribute() != null)
				{
					modifiers.removeAll(effect.getAttribute().getAttributeUnlocalizedName());
					
					modifiers.put(effect.getAttribute().getAttributeUnlocalizedName(), new AttributeModifier(new UUID(0, 318145), "Armor modifier", (double)LootWeaponEffect.getAmplifierFromStack(stack, effect.getId()), 0));
				}
			}
		}
		
		return modifiers;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced)
	{
		LootItemHelper.addInformation(stack, tooltip, false);
	}
}
