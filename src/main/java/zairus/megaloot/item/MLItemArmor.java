package zairus.megaloot.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import com.google.common.collect.Multimap;

import net.minecraft.block.BlockDispenser;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.megaloot.MLConfig;
import zairus.megaloot.MegaLoot;
import zairus.megaloot.loot.LootItemHelper;
import zairus.megaloot.loot.LootSet;
import zairus.megaloot.loot.LootWeaponEffect;

@SuppressWarnings("deprecation")
public class MLItemArmor extends ItemArmor implements IMegaLoot
{
	protected MLItemArmor(EntityEquipmentSlot equipmentSlot)
	{
		super(ArmorMaterial.DIAMOND, 3, equipmentSlot);
		
		this.setCreativeTab(MegaLoot.creativeTabMain);
		this.setNoRepair();
		
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
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
	{
		return false;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		String unlocalizedName = super.getUnlocalizedName(stack);
		String lootSetId = LootItemHelper.getLootStringValue(stack, MLItem.LOOT_TAG_LOOTSET);
		
		return unlocalizedName + ((lootSetId.equals(""))? "" : ("." + lootSetId));
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		return MLItem.getMegaLootDisplayName(stack, super.getItemStackDisplayName(stack));
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
		Multimap<String, AttributeModifier> modifiers = MLItem.modifiersForStack(slot, this.armorType, stack, super.getAttributeModifiers(slot, stack), "Armor modifier");
		
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
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flagIn)
	{
		if (GuiScreen.isShiftKeyDown())
		{
			String set_id = LootItemHelper.getLootStringValue(stack, MLItem.LOOT_TAG_LOOTSET).toUpperCase();
			
			tooltip.add(TextFormatting.BOLD + "" + TextFormatting.WHITE + "" + set_id + TextFormatting.RESET + "" + TextFormatting.GRAY + " Set");
			
			LootSet set = LootSet.getById(set_id.toLowerCase());
			
			LootWeaponEffect effect = set.bonusEffect;
			
			tooltip.add("If wearing " + MLConfig.armorPiecesForBonus + " pieces:");
			
			tooltip.add(I18n.translateToLocalFormatted("weaponeffect." + effect.getId() + ".description",
					new Object[] { 
							effect.getDurationString(stack, effect.getId()), 
							effect.getAmplifierString(stack, effect.getId()), 
							effect.getAmplifierString(stack, effect.getId(), 1) }));
			
			LootItemHelper.addInformation(stack, tooltip, false);
		}
		else
		{
			for (EntityEquipmentSlot entityequipmentslot : EntityEquipmentSlot.values())
			{
				Multimap<String, AttributeModifier> multimap = stack.getAttributeModifiers(entityequipmentslot);
				
				if (!multimap.isEmpty() && entityequipmentslot != EntityEquipmentSlot.MAINHAND)
				{
					tooltip.add(I18n.translateToLocal("item.modifiers." + entityequipmentslot.getName()));
					for (Entry<String, AttributeModifier> entry : multimap.entries())
					{
						if (entry.getKey().equals("generic.armorToughness") || entry.getKey().equals("generic.armor"))
						{
							AttributeModifier attributemodifier = entry.getValue();
							double d0 = attributemodifier.getAmount();
							
							EntityPlayer player = Minecraft.getMinecraft().player;
							
							if (player != null)
							{
								;
							}
							
							double d1;
							
							if (attributemodifier.getOperation() != 1 && attributemodifier.getOperation() != 2)
								d1 = d0;
							else
								d1 = d0 * 100.0D;
							
							if (d0 > 0.0D)
							{
								tooltip.add(TextFormatting.BLUE + " " + I18n.translateToLocalFormatted("attribute.modifier.plus." + attributemodifier.getOperation(), ItemStack.DECIMALFORMAT.format(d1), I18n.translateToLocal("attribute.name." + (String)entry.getKey())));
							}
							else if (d0 < 0.0D)
							{
								d1 = d1 * -1.0D;
								tooltip.add(TextFormatting.RED + " " + I18n.translateToLocalFormatted("attribute.modifier.take." + attributemodifier.getOperation(), ItemStack.DECIMALFORMAT.format(d1), I18n.translateToLocal("attribute.name." + (String)entry.getKey())));
							}
						}
					}
				}
			}
			
			tooltip.add(TextFormatting.AQUA + "" + TextFormatting.ITALIC + "Shift" + TextFormatting.DARK_GRAY + " for more...");
		}
	}
	
	@Override
	public int getMaxDamage(ItemStack stack)
	{
		return LootItemHelper.getMaxDamage(stack);
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
