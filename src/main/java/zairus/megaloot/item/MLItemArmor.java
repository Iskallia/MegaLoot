package zairus.megaloot.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.collect.Multimap;

import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.megaloot.MLConfig;
import zairus.megaloot.MegaLoot;
import zairus.megaloot.loot.LootItemHelper;
import zairus.megaloot.loot.LootSet;
import zairus.megaloot.loot.LootWeaponEffect;

public class MLItemArmor extends ItemArmor
{
	private static final UUID[] ARMOR_MODIFIERS = new UUID[] {UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"), UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")};
	
	protected MLItemArmor(EntityEquipmentSlot equipmentSlot)
	{
		super(ArmorMaterial.DIAMOND, 3, equipmentSlot);
		this.setCreativeTab(MegaLoot.creativeTabMain);
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, DISPENSER_BEHAVIOR);
		
		this.addPropertyOverride(new ResourceLocation("model"), new IItemPropertyGetter() {
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity)
			{
				float model = 1.0F;
				
				String lootSetId = LootItemHelper.getLootStringValue(stack, MLItem.LOOT_TAG_LOOTSET);
				LootSet lootSet = LootSet.getById(lootSetId);
				
				if (lootSet != null)
					model = (float)lootSet.itemModel;
				
				return model;
			}
		});
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		String unlocalizedName = super.getUnlocalizedName(stack);
		String lootSetId = LootItemHelper.getLootStringValue(stack, MLItem.LOOT_TAG_LOOTSET);
		
		return unlocalizedName + ((lootSetId.equals(""))? "" : ("." + lootSetId));
	}
	
	@SideOnly(Side.CLIENT)
	public net.minecraft.client.model.ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, net.minecraft.client.model.ModelBiped _default)
	{
		String lootSetId = LootItemHelper.getLootStringValue(itemStack, MLItem.LOOT_TAG_LOOTSET);
		
		return LootSet.getArmorModel(LootSet.getById(lootSetId), MLItems.getItemType(itemStack.getItem()));
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
	{
		String lootSetId = LootItemHelper.getLootStringValue(stack, MLItem.LOOT_TAG_LOOTSET);
		String textureString = "megaloot:textures/models/armor/1.png";
		
		if (lootSetId != null && lootSetId.length() > 0)
			textureString = "megaloot:textures/models/armor/" + lootSetId + ".png";
		
		return textureString;
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack)
	{
		Multimap<String, AttributeModifier> modifiers = MLItem.modifiersForStack(slot, stack, super.getAttributeModifiers(slot, stack));
		
		if (this.armorType == slot)
		{
			modifiers.put(SharedMonsterAttributes.ARMOR.getAttributeUnlocalizedName(), new AttributeModifier(ARMOR_MODIFIERS[slot.getIndex()], "Armor modifier", (double)this.damageReduceAmount, 0));
			modifiers.put(SharedMonsterAttributes.ARMOR_TOUGHNESS.getAttributeUnlocalizedName(), new AttributeModifier(ARMOR_MODIFIERS[slot.getIndex()], "Armor toughness", (double)this.toughness, 0));
			
			List<LootWeaponEffect> effects = LootWeaponEffect.getEffectList(stack);
			
			for (LootWeaponEffect effect : effects)
			{
				if (effect.getAttribute() != null)
				{
					modifiers.put(effect.getAttribute().getAttributeUnlocalizedName(), new AttributeModifier(ARMOR_MODIFIERS[slot.getIndex()], "Armor modifier", (double)LootWeaponEffect.getAmplifierFromStack(stack, effect.getId()), 0));
				}
			}
		}
		
		return modifiers;
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
	{
		BonusHelper helper = new BonusHelper();
		
		helper.add(getSetIdFromSlot(player, EntityEquipmentSlot.FEET));
		helper.add(getSetIdFromSlot(player, EntityEquipmentSlot.LEGS));
		helper.add(getSetIdFromSlot(player, EntityEquipmentSlot.CHEST));
		helper.add(getSetIdFromSlot(player, EntityEquipmentSlot.HEAD));
		
		List<String> setIds = helper.getGreatherThan(MLConfig.armorPiecesForBonus);
		
		for (String setId : setIds)
		{
			player.addPotionEffect(LootSet.getById(setId).bonusEffect.getPotionEffect(2, 0));
		}
	}
	
	private String getSetIdFromSlot(EntityPlayer player, EntityEquipmentSlot equipmentSlot)
	{
		String setId = "";
		
		ItemStack stack;
		
		stack = player.getItemStackFromSlot(equipmentSlot);
		
		if (stack != null)
		{
			setId = LootItemHelper.getLootStringValue(stack, MLItem.LOOT_TAG_LOOTSET);
		}
		
		return setId;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced)
	{
		tooltip.add("");
		tooltip.add(TextFormatting.GRAY + "Armor set " + TextFormatting.BOLD + "" + LootItemHelper.getLootStringValue(stack, MLItem.LOOT_TAG_LOOTSET));
		tooltip.add("");
		
		LootItemHelper.addInformation(stack, tooltip, false);
	}
	
	private class BonusHelper
	{
		private final Map<String, Integer> sets = new HashMap<String, Integer>();
		
		public void add(String setId)
		{
			if (setId == "")
					return;
			
			if (!sets.containsKey(setId))
			{
				sets.put(setId, 1);
			}
			else
			{
				int curValue = sets.get(setId);
				++curValue;
				sets.put(setId, curValue);
			}
		}
		
		public List<String> getGreatherThan(int value)
		{
			List<String> ids = new ArrayList<String>();
			
			for (String id : sets.keySet())
			{
				if (sets.get(id) >= value)
				{
					ids.add(id);
				}
			}
			
			return ids;
		}
	}
}
