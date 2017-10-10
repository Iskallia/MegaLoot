package zairus.megaloot.item;

import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.megaloot.MegaLoot;
import zairus.megaloot.loot.LootSet;

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
}
