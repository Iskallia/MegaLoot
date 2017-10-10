package zairus.megaloot.client.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.megaloot.loot.LootSet.LootSetType;

@SideOnly(Side.CLIENT)
public class MLModelArmorViking extends ModelBiped
{
	public MLModelArmorViking()
	{
		this(0.0625F, LootSetType.ARMOR_HEAD);
	}
	
	public MLModelArmorViking(float scale, LootSetType partType)
	{
		super(scale);
		
		createModel(scale, partType);
	}
	
	private void createModel(float scale, LootSetType partType)
	{
		this.textureWidth = 64;
		this.textureHeight = 64;
		
		// ## Helmet
		
		this.bipedHead = new ModelRenderer(this, 0, 0);
		this.bipedHeadwear.isHidden = true;
		
		if (partType == LootSetType.ARMOR_HEAD)
		{
			this.bipedHead.addBox(-4F, -8F, -4F, 8, 8, 8, scale);
			this.bipedHead.addBox(4F, -6F, -1F, 2, 2, 2, scale);
			this.bipedHead.addBox(6F, -8F, -1F, 2, 4, 2, scale);
			this.bipedHead.addBox(6.5F, -10F, -0.5F, 1, 2, 1, scale);
			this.bipedHead.addBox(-6F, -6F, -1F, 2, 2, 2, scale);
			this.bipedHead.addBox(-8F, -8F, -1F, 2, 4, 2, scale);
			this.bipedHead.addBox(-7.5F, -10F, -0.5F, 1, 2, 1, scale);
		}
		
		// ## Chestplate
		
		this.bipedBody = new ModelRenderer(this, 16, 16);
		this.bipedRightArm = new ModelRenderer(this, 40, 16);
		this.bipedLeftArm = new ModelRenderer(this, 40, 16);
		this.bipedLeftArm.mirror = true;
		
		this.bipedBody.setRotationPoint(0.0F, 0.0F + 0, 0.0F);
		this.bipedLeftArm.setRotationPoint(5.0F, 2.0F + 0, 0.0F);
		this.bipedRightArm.setRotationPoint(-5.0F, 2.0F + 0, 0.0F);
		
		if (partType == LootSetType.ARMOR_CHEST)
		{
			this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, scale * 3.5F);
			
			this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, scale);
			ModelRenderer bipedRightShoulder = new ModelRenderer(this, 32, 32);
			bipedRightShoulder.addBox(-6.0F, -3.0F, -3.0F, 8, 6, 6, scale);
			this.bipedRightArm.addChild(bipedRightShoulder);
			
			this.bipedLeftArm.addBox(-1.0F, -2.0F, -3.0F, 4, 12, 4, scale);
			ModelRenderer bipedLeftShoulder = new ModelRenderer(this, 32, 32);
			bipedLeftShoulder.addBox(-1.0F, -3.0F, -4.0F, 8, 6, 6, scale);
			this.bipedLeftArm.addChild(bipedLeftShoulder);
		}
		
		// ## Leggings
		
		this.bipedRightLeg = new ModelRenderer(this, 0, 48);
		this.bipedLeftLeg = new ModelRenderer(this, 0, 48);
		this.bipedLeftLeg.mirror = true;
		
		this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F + 0, 0.0F);
		this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F + 0, 0.0F);
		
		if (partType == LootSetType.ARMOR_LEGS)
		{
			ModelRenderer bodyBelt = new ModelRenderer(this, 16, 49);
			bodyBelt.addBox(-4.0F, 7.0F, -2.0F, 8, 5, 4, scale * 2.0F);
			
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
	
	public void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
	
	@Override
	public void setRotationAngles(float f, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale, Entity entity)
	{
		super.setRotationAngles(f, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);
	}
	
	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);
	}
}
