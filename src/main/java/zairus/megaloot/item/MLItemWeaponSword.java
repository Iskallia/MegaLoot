package zairus.megaloot.item;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Multimap;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.megaloot.MegaLoot;
import zairus.megaloot.loot.LootItemHelper;

public class MLItemWeaponSword extends ItemSword
{
	protected MLItemWeaponSword()
	{
		super(ToolMaterial.DIAMOND);
		
		this.setCreativeTab(MegaLoot.creativeTabMain);
		this.setNoRepair();
		
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
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
	{
		return false;
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		return MLItem.getMegaLootDisplayName(stack, super.getItemStackDisplayName(stack));
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
		
		MLItem.handleEffectsAfterHit(stack, target, attacker);
		
		return hit;
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack)
	{
		Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
		
		return MLItem.modifiersForStack(slot, stack, multimap, "Weapon modifier");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
	{
		int attackDamage = LootItemHelper.getLootIntValue(stack, MLItem.LOOT_TAG_DAMAGE);
		float speedDisplay = LootItemHelper.getLootFloatValue(stack, MLItem.LOOT_TAG_SPEED);
		double sp1 = (double)speedDisplay;
		
		EntityPlayer player = Minecraft.getMinecraft().player;
		
		if (player != null)
		{
			sp1 += player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getBaseValue();
		}
		
		attackDamage += EnchantmentHelper.getModifierForCreature(stack, EnumCreatureAttribute.UNDEFINED);
		
		if (GuiScreen.isShiftKeyDown())
		{
			tooltip.add(TextFormatting.WHITE + ItemStack.DECIMALFORMAT.format(((float)attackDamage * sp1)) + " DPS");
			
			LootItemHelper.addInformation(stack, tooltip);
		}
		else
		{
			tooltip.add(TextFormatting.RESET + "" + "Sword");
			
			tooltip.add(TextFormatting.GRAY + "" + attackDamage + " Damage | " + ItemStack.DECIMALFORMAT.format(sp1) + " Atack Speed");
			
			tooltip.add(TextFormatting.AQUA + "" + TextFormatting.ITALIC + "Shift" + TextFormatting.DARK_GRAY + " for more...");
		}
	}
}
