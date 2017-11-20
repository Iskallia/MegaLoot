package zairus.megaloot.client.model;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.megaloot.loot.LootSet.LootSetType;

@SideOnly(Side.CLIENT)
public class MLModelArmorSamurai extends MLModelArmorBase
{
	public MLModelArmorSamurai(float scale, LootSetType partType)
	{
		super(scale, partType);
	}
	
	@Override
	protected void createModel(float scale, LootSetType partType)
	{
		// ## Helmet
		
		if (partType == LootSetType.ARMOR_HEAD)
		{
			;
		}
		
		// ## Chestplate
		
		if (partType == LootSetType.ARMOR_CHEST)
		{
			;
		}
		
		// ## Leggings
		
		if (partType == LootSetType.ARMOR_LEGS)
		{
			;
		}
		
		// ## Boots
		
		if (partType == LootSetType.ARMOR_FEET)
		{
			;
		}
	}
	
	@Override
	protected void update(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		;
	}
}
