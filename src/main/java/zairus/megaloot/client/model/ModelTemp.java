package zairus.megaloot.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelTemp extends ModelBase
{
	public ModelRenderer ArmR;
    public ModelRenderer LegL;
    public ModelRenderer Head;
    public ModelRenderer Torso;
    public ModelRenderer ArmL;
    public ModelRenderer LegR;
    public ModelRenderer Helmet;
    public ModelRenderer ShoulderRbase;
    public ModelRenderer shape9;
    public ModelRenderer shape10;
    public ModelRenderer shape11;
    public ModelRenderer ShoulderLbase;
    public ModelRenderer shape9_1;
    public ModelRenderer shape10_1;
    public ModelRenderer shape11_1;

    public ModelTemp()
    {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.Helmet = new ModelRenderer(this, 32, 32);
        this.Helmet.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Helmet.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.5F);
        this.LegR = new ModelRenderer(this, 16, 40);
        this.LegR.mirror = true;
        this.LegR.setRotationPoint(1.9F, 12.0F, 0.1F);
        this.LegR.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.ShoulderRbase = new ModelRenderer(this, 20, 0);
        this.ShoulderRbase.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.ShoulderRbase.addBox(-4.0F, -2.0F, -2.5F, 5, 5, 5, 0.0F);
        this.shape9_1 = new ModelRenderer(this, 40, 4);
        this.shape9_1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape9_1.addBox(0.0F, -3.0F, -1.5F, 5, 1, 3, 0.0F);
        this.shape10_1 = new ModelRenderer(this, 16, 12);
        this.shape10_1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape10_1.addBox(1.0F, -4.0F, -0.5F, 5, 1, 1, 0.0F);
        this.Head = new ModelRenderer(this, 32, 48);
        this.Head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
        this.shape11_1 = new ModelRenderer(this, 30, 12);
        this.shape11_1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape11_1.addBox(2.0F, -5.0F, -0.5F, 5, 1, 1, 0.0F);
        this.Torso = new ModelRenderer(this, 32, 16);
        this.Torso.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Torso.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.0F);
        this.shape11 = new ModelRenderer(this, 30, 10);
        this.shape11.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape11.addBox(-7.0F, -5.0F, -0.5F, 5, 1, 1, 0.0F);
        this.shape9 = new ModelRenderer(this, 40, 0);
        this.shape9.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape9.addBox(-5.0F, -3.0F, -1.5F, 5, 1, 3, 0.0F);
        this.shape10 = new ModelRenderer(this, 16, 10);
        this.shape10.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape10.addBox(-6.0F, -4.0F, -0.5F, 5, 1, 1, 0.0F);
        this.LegL = new ModelRenderer(this, 0, 40);
        this.LegL.setRotationPoint(-1.9F, 12.0F, 0.1F);
        this.LegL.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.ShoulderLbase = new ModelRenderer(this, 0, 0);
        this.ShoulderLbase.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.ShoulderLbase.addBox(-1.0F, -2.0F, -2.5F, 5, 5, 5, 0.0F);
        this.ArmL = new ModelRenderer(this, 0, 16);
        this.ArmL.setRotationPoint(5.0F, 2.0F, 0.0F);
        this.ArmL.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(ArmL, -0.045553093477052F, 0.0F, -0.045553093477052F);
        this.ArmR = new ModelRenderer(this, 16, 16);
        this.ArmR.setRotationPoint(-5.0F, 2.0F, 0.0F);
        this.ArmR.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(ArmR, -0.045553093477052F, 0.0F, 0.08726646259971647F);
        this.ArmR.addChild(this.ShoulderRbase);
        
        this.ShoulderLbase.addChild(this.shape9_1);
        this.ShoulderLbase.addChild(this.shape10_1);
        this.ShoulderLbase.addChild(this.shape11_1);
        
        this.ShoulderRbase.addChild(this.shape11);
        this.ShoulderRbase.addChild(this.shape9);
        this.ShoulderRbase.addChild(this.shape10);
        this.ArmL.addChild(this.ShoulderLbase);
    }
    
    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.Helmet.render(f5);
        this.LegR.render(f5);
        this.Head.render(f5);
        this.Torso.render(f5);
        this.LegL.render(f5);
        this.ArmL.render(f5);
        this.ArmR.render(f5);
    }
    
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
