package zairus.megaloot.client.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
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
	public void setRotationAngles(float f, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale, Entity entity)
	{
		super.setRotationAngles(f, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);
	}
	
	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		update(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		
		super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);
	}
}
