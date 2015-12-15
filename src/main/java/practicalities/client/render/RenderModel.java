package practicalities.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.obj.Face;
import net.minecraftforge.client.model.obj.GroupObject;
import net.minecraftforge.client.model.obj.TextureCoordinate;
import net.minecraftforge.client.model.obj.Vertex;
import net.minecraftforge.client.model.obj.WavefrontObject;

import org.lwjgl.opengl.GL11;

import practicalities.PracticalitiesMod;
import scala.actors.threadpool.Arrays;
import codechicken.lib.vec.Vector3;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public abstract class RenderModel implements ISimpleBlockRenderingHandler {
	ResourceLocation objFile;
	WavefrontObject model;
	IconTessellator t;
	private int id;
	
	public abstract IIcon getIcon();
	
	public Vector3 handOffset() { return new Vector3(); }
	public double handScale() { return 1; }
	public Vector3 blockOffset() { return new Vector3(); }
	
	
	public RenderModel(String name) {
		id = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(this);
		
		objFile = new ResourceLocation(PracticalitiesMod.MODID, "model/" + name + ".obj");
		t = new IconTessellator();
		loadModel();
	}
	
	public void loadModel() {
		model = new WavefrontObject(objFile);
		for(Vertex v : model.vertices) {
			v.z = -v.z;
		}
		
		for(GroupObject g : model.groupObjects) {
			for(Face f : g.faces) {
				f.faceNormal.z = -f.faceNormal.z;
				Vertex[] verts;
				TextureCoordinate[] texs;
				
				if(f.textureCoordinates != null) {
					texs = new TextureCoordinate[f.textureCoordinates.length];
					for(int i = 0; i < texs.length; i++) {
						texs[i] = f.textureCoordinates[( texs.length-1 )-i];
					}
					f.textureCoordinates = texs;
				}
				if(f.vertices != null) {
					verts = new Vertex[f.vertices.length];
					for(int i = 0; i < verts.length; i++) {
						verts[i] = f.vertices[( verts.length-1 )-i];
					}
					f.vertices = verts;
				}
				
				if(f.vertexNormals != null) {
					verts = new Vertex[f.vertexNormals.length];
					for(int i = 0; i < verts.length; i++) {
						verts[i] = f.vertexNormals[( verts.length-1 )-i];
					}
					f.vertexNormals = verts;
				}
			}
		}
	}
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId,
			RenderBlocks renderer) {
		GL11.glPushMatrix();
		t.icon = getIcon();
	    GL11.glScaled(handScale(), handScale(), handScale());
	    GL11.glTranslatef(-0.5F +((float)handOffset().x), -0.5F +((float)handOffset().y), -0.5F +((float)handOffset().z));
	    t.startDrawingQuads();
		model.tessellateAll(t);
		t.draw();
		GL11.glPopMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		int l = block.getMixedBrightnessForBlock(world, x, y, z);
		t.setBrightness(l);
		t.setColorOpaque_F(1, 1, 1);
		float oX = x +((float)blockOffset().x),
			  oY = y +((float)blockOffset().y),
			  oZ = z +((float)blockOffset().z);
		t.addTranslation(oX, oY, oZ);
		t.icon = getIcon();
		model.tessellateAll(t);
		t.addTranslation(-oX, -oY, -oZ);
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return id;
	}
	
}
