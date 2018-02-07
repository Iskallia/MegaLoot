package zairus.megaloot.client.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zairus.megaloot.loot.LootSet.LootSetType;

@SideOnly(Side.CLIENT)
public abstract class MLModelArmorBase extends ModelBiped
{
	public MLModelArmorBase()
	{
		this(0.0625F, LootSetType.ARMOR_HEAD);
	}
	
	public MLModelArmorBase(float scale, LootSetType partType)
	{
		super(scale);
		
		this.textureWidth = 64;
		this.textureHeight = 64;
		
		// ## Helmet
		
		this.bipedHead = new ModelRenderer(this, 0, 0);
		this.bipedHeadwear = new ModelRenderer(this, 0, 0);
		
		// ## Chestplate
		
		this.bipedBody = new ModelRenderer(this, 16, 16);
		this.bipedRightArm = new ModelRenderer(this, 40, 16);
		this.bipedLeftArm = new ModelRenderer(this, 40, 16);
		this.bipedLeftArm.mirror = true;
		
		this.bipedBody.setRotationPoint(0.0F, 0.0F + 0, 0.0F);
		this.bipedLeftArm.setRotationPoint(5.0F, 2.0F + 0, 0.0F);
		this.bipedRightArm.setRotationPoint(-5.0F, 2.0F + 0, 0.0F);
		
		// ## Leggings
		
		this.bipedRightLeg = new ModelRenderer(this, 0, 48);
		this.bipedLeftLeg = new ModelRenderer(this, 0, 48);
		this.bipedLeftLeg.mirror = true;
		
		this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F + 0, 0.0F);
		this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F + 0, 0.0F);
		
		createModel(scale, partType);
	}
	
	protected abstract void createModel(float scale, LootSetType partType);
	protected abstract void update(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale);
	
	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale, Entity entity)
	{
		if (entity instanceof EntityArmorStand)
        {
			EntityArmorStand entityarmorstand = (EntityArmorStand)entity;
			this.bipedHead.rotateAngleX = 0.017453292F * entityarmorstand.getHeadRotation().getX();
			this.bipedHead.rotateAngleY = 0.017453292F * entityarmorstand.getHeadRotation().getY();
			this.bipedHead.rotateAngleZ = 0.017453292F * entityarmorstand.getHeadRotation().getZ();
			this.bipedHead.setRotationPoint(0.0F, 1.0F, 0.0F);
			this.bipedBody.rotateAngleX = 0.017453292F * entityarmorstand.getBodyRotation().getX();
			this.bipedBody.rotateAngleY = 0.017453292F * entityarmorstand.getBodyRotation().getY();
			this.bipedBody.rotateAngleZ = 0.017453292F * entityarmorstand.getBodyRotation().getZ();
			this.bipedLeftArm.rotateAngleX = 0.017453292F * entityarmorstand.getLeftArmRotation().getX();
			this.bipedLeftArm.rotateAngleY = 0.017453292F * entityarmorstand.getLeftArmRotation().getY();
			this.bipedLeftArm.rotateAngleZ = 0.017453292F * entityarmorstand.getLeftArmRotation().getZ();
			this.bipedRightArm.rotateAngleX = 0.017453292F * entityarmorstand.getRightArmRotation().getX();
			this.bipedRightArm.rotateAngleY = 0.017453292F * entityarmorstand.getRightArmRotation().getY();
			this.bipedRightArm.rotateAngleZ = 0.017453292F * entityarmorstand.getRightArmRotation().getZ();
			this.bipedLeftLeg.rotateAngleX = 0.017453292F * entityarmorstand.getLeftLegRotation().getX();
			this.bipedLeftLeg.rotateAngleY = 0.017453292F * entityarmorstand.getLeftLegRotation().getY();
			this.bipedLeftLeg.rotateAngleZ = 0.017453292F * entityarmorstand.getLeftLegRotation().getZ();
			this.bipedLeftLeg.setRotationPoint(1.9F, 11.0F, 0.0F);
			this.bipedRightLeg.rotateAngleX = 0.017453292F * entityarmorstand.getRightLegRotation().getX();
			this.bipedRightLeg.rotateAngleY = 0.017453292F * entityarmorstand.getRightLegRotation().getY();
			this.bipedRightLeg.rotateAngleZ = 0.017453292F * entityarmorstand.getRightLegRotation().getZ();
			this.bipedRightLeg.setRotationPoint(-1.9F, 11.0F, 0.0F);
			copyModelAngles(this.bipedHead, this.bipedHeadwear);
        }
		else
		{
			super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);
		}
	}
	
	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		update(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		
		super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);
	}
}
