package zairus.megaloot.client.model;

import net.minecraft.client.model.ModelRenderer;
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
		
		this.bipedHeadwear.isHidden = true;
		
		if (partType == LootSetType.ARMOR_HEAD)
		{
			this.bipedHead.addBox(-4F, -8F, -4F, 8, 8, 8, scale);
		}
		
		// ## Chestplate
		
		if (partType == LootSetType.ARMOR_CHEST)
		{
			this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, scale);
			
			this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, scale);
			ModelRenderer bipedRightShoulder = new ModelRenderer(this, 32, 32);
			bipedRightShoulder.addBox(-4.0F, -2.1F, -2.5F, 5, 5, 5, scale);
			ModelRenderer shoulderDecor = new ModelRenderer(this, 32, 43);
			shoulderDecor.addBox(-7.0F, -5.0F, -0.5F, 5, 1, 1, scale);
			shoulderDecor.addBox(-5.0F, -3.0F, -1.5F, 5, 1, 3, scale);
			shoulderDecor.addBox(-6.0F, -4.0F, -0.5F, 5, 1, 1, scale);
			bipedRightShoulder.addChild(shoulderDecor);
			this.bipedRightArm.addChild(bipedRightShoulder);
			
			this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, scale);
			ModelRenderer bipedLeftShoulder = new ModelRenderer(this, 32, 32);
			bipedLeftShoulder.addBox(-1.0F, -2.1F, -2.5F, 5, 5, 5, scale);
			shoulderDecor = new ModelRenderer(this, 32, 43);
			shoulderDecor.addBox(0.0F, -3.0F, -1.5F, 5, 1, 3, scale);
			shoulderDecor.addBox(1.0F, -4.0F, -0.5F, 5, 1, 1, scale);
			shoulderDecor.addBox(2.0F, -5.0F, -0.5F, 5, 1, 1, scale);
			bipedLeftShoulder.addChild(shoulderDecor);
			this.bipedLeftArm.addChild(bipedLeftShoulder);
		}
		
		// ## Leggings
		
		if (partType == LootSetType.ARMOR_LEGS)
		{
			ModelRenderer bodyBelt = new ModelRenderer(this, 16, 49);
			bodyBelt.addBox(-4.0F, 7.0F, -2.0F, 8, 5, 4, scale);
			
			this.bipedBody.addChild(bodyBelt);
			
			this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, scale);
			
			this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, scale);
		}
		
		// ## Boots
		
		if (partType == LootSetType.ARMOR_FEET)
		{
			ModelRenderer rightBoot = new ModelRenderer(this, 40, 49);
			rightBoot.addBox(-2.0F, 6.5F, -2.0F, 4, 6, 4, scale * 3.5F);
			
			this.bipedRightLeg.addChild(rightBoot);
			
			this.bipedLeftLeg.addChild(rightBoot);
		}
	}
	
	@Override
	protected void update(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		;
	}
}
