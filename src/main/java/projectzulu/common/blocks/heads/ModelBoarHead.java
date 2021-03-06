package projectzulu.common.blocks.heads;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBoarHead extends ModelBase{

	ModelRenderer CENTERROT;
	private ModelRenderer HEADROT;

	public ModelBoarHead()
	{
		textureWidth = 64;
		textureHeight = 32;
		setTextureOffset("HEADROT.head", 0, 0);
		setTextureOffset("HEADROT.nose2", 28, 0);
		setTextureOffset("HEADROT.nose1", 42, 0);
		setTextureOffset("HEADROT.tusklef1", 0, 29);
		setTextureOffset("HEADROT.tusklef2", 0, 27);
		setTextureOffset("HEADROT.tuskrig1", 0, 29);
		setTextureOffset("HEADROT.tuskrig2", 0, 27);
		setTextureOffset("HEADROT.tusklef3", 0, 29);
		setTextureOffset("HEADROT.tusklef4", 0, 27);
		setTextureOffset("HEADROT.tuskrig3", 0, 29);
		setTextureOffset("HEADROT.tuskrig4", 0, 27);

		CENTERROT = new ModelRenderer(this, "CENTERROT");
		CENTERROT.setRotationPoint(0F, 21F, 0F);
		setRotation(CENTERROT, 0F, 0F, 0F);
		CENTERROT.mirror = true;
		HEADROT = new ModelRenderer(this, "HEADROT");
		HEADROT.setRotationPoint(0F, 0F, 4F);
		setRotation(HEADROT, 0F, 0F, 0F);
		HEADROT.mirror = true;
		HEADROT.addBox("head", -4F, -3.5F, -5F, 8, 7, 6);
		HEADROT.addBox("nose2", -2.5F, -0.5F, -7F, 5, 4, 2);
		HEADROT.addBox("nose1", -2F, 0.5F, -9F, 4, 3, 2);
		HEADROT.addBox("tusklef1", -4F, 2.5F, -9F, 2, 1, 1);
		HEADROT.addBox("tusklef2", -4F, 1.5F, -9F, 1, 1, 1);
		HEADROT.addBox("tuskrig1", 2F, 2.5F, -9F, 2, 1, 1);
		HEADROT.addBox("tuskrig2", 3F, 1.5F, -9F, 1, 1, 1);
		HEADROT.addBox("tusklef3", -4.5F, 2.5F, -7F, 2, 1, 1);
		HEADROT.addBox("tusklef4", -4.5F, 1.5F, -7F, 1, 1, 1);
		HEADROT.addBox("tuskrig3", 2.5F, 2.5F, -7F, 2, 1, 1);
		HEADROT.addBox("tuskrig4", 3.5F, 1.5F, -7F, 1, 1, 1);
		CENTERROT.addChild(HEADROT);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		CENTERROT.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}


	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity par7Entity){
		super.setRotationAngles(f, f1, f2, f3, f4, f5, par7Entity);
		CENTERROT.rotateAngleY = f3 / (180F / (float)Math.PI);
	}


}
