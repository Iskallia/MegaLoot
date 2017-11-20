package zairus.megaloot.client.model;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.megaloot.loot.LootSet.LootSetType;

@SideOnly(Side.CLIENT)
public class MLModelArmorDragon extends MLModelArmorBase
{
	public MLModelArmorDragon(float scale, LootSetType partType)
	{
		super(scale, partType);
	}
	
	@Override
	protected void createModel(float scale, LootSetType partType)
	{
		// ## Helmet
		
		if (partType == LootSetType.ARMOR_HEAD)
		{
			this.bipedHead.addBox(-4F, -8F, -4F, 8, 8, 8, scale);
			this.bipedHeadwear.addBox(-4F, -8F, -4F, 8, 8, 8, scale + 0.5F);
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
