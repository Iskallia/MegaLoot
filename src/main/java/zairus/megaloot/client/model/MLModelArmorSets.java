package zairus.megaloot.client.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.megaloot.loot.LootSet.LootSetType;

@SideOnly(Side.CLIENT)
public class MLModelArmorSets
{
	public static final ModelBiped ARMOR_VIKING_HELMET = new MLModelArmorViking(0.1925F, LootSetType.ARMOR_HEAD);
	public static final ModelBiped ARMOR_VIKING_CHESTPLATE = new MLModelArmorViking(0.1925F, LootSetType.ARMOR_CHEST);
	public static final ModelBiped ARMOR_VIKING_LEGGINGS = new MLModelArmorViking(0.1925F, LootSetType.ARMOR_LEGS);
	public static final ModelBiped ARMOR_VIKING_BOOTS = new MLModelArmorViking(0.1925F, LootSetType.ARMOR_FEET);
}
