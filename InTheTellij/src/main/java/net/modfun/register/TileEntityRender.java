package net.modfun.register;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.modfun.tileentity.TileEntityComp;

public class TileEntityRender	 extends TileEntitySpecialRenderer<TileEntityComp> {
	

	  /**
	   * render the tile entity - called every frame while the tileentity is in view of the player
	   * @param tileEntity the associated tile entity
	   * @param relativeX the x distance from the player's eye to the tileentity
	   * @param relativeY the y distance from the player's eye to the tileentity
	   * @param relativeZ the z distance from the player's eye to the tileentity
	   * @param partialTicks the fraction of a tick that this frame is being rendered at - used to interpolate frames between
	   *                     ticks, to make animations smoother.  For example - if the frame rate is steady at 80 frames per second,
	   *                     this method will be called four times per tick, with partialTicks spaced 0.25 apart, (eg) 0, 0.25, 0.5, 0.75
	   * @param blockDamageProgress the progress of the block being damaged (0 - 10), if relevant.  -1 if not relevant.
	   * @param alpha Transparencey for the block
	   */
	  public void render(TileEntityComp tileEntity, double relativeX, double relativeY, double relativeZ,
	                     float partialTicks, int blockDamageProgress, float alpha) {

	    if (!(tileEntity instanceof TileEntityComp)) return;
	    TileEntityComp tileEntityMBE21 = tileEntity;



	    final double pedestalCentreOffsetX = 0.5;
	    final double pedestalCentreOffsetY = 0.8;
	    final double pedestalCentreOffsetZ = 0.5;
	    Vec3d playerEye = new Vec3d(0.0, 0.0, 0.0);
	    Vec3d pedestalCentre = new Vec3d(relativeX + pedestalCentreOffsetX, relativeY + pedestalCentreOffsetY, relativeZ + pedestalCentreOffsetZ);
	    double playerDistance = playerEye.distanceTo(pedestalCentre);

	    final double DISTANCE_FOR_MIN_SPIN = 8.0;
	    final double DISTANCE_FOR_MAX_SPIN = 4.0;
	    final double DISTANCE_FOR_MIN_GLOW = 16.0;
	    final double DISTANCE_FOR_MAX_GLOW = 4.0;
	    final double DISTANCE_FOR_MIN_LEVITATE = 4.0;
	    final double DISTANCE_FOR_MAX_LEVITATE = 2.0;

	    final double MIN_LEVITATE_HEIGHT = 0.0;
	    final double MAX_LEVITATE_HEIGHT = 0.5;
	    double gemCentreOffsetX = pedestalCentreOffsetX;
	    double gemCentreOffsetY = pedestalCentreOffsetY + UsefulFunctions.interpolate(playerDistance, DISTANCE_FOR_MIN_LEVITATE, DISTANCE_FOR_MAX_LEVITATE,
	            MIN_LEVITATE_HEIGHT, MAX_LEVITATE_HEIGHT);
	    double gemCentreOffsetZ = pedestalCentreOffsetZ;

	    final double MIN_GLOW = 0.0;
	    final double MAX_GLOW = 1.0;
	    double glowMultiplier = UsefulFunctions.interpolate(playerDistance, DISTANCE_FOR_MIN_GLOW, DISTANCE_FOR_MAX_GLOW,
	            MIN_GLOW, MAX_GLOW);

	    final double MIN_REV_PER_SEC = 0.0;
	    final double MAX_REV_PER_SEC = 0.5;
	    double revsPerSecond = UsefulFunctions.interpolate(playerDistance, DISTANCE_FOR_MIN_SPIN, DISTANCE_FOR_MAX_SPIN,
	            MIN_REV_PER_SEC, MAX_REV_PER_SEC);

	    try {

	      GL11.glPushMatrix();
	      GL11.glPushAttrib(GL11.GL_ENABLE_BIT);


	      GlStateManager.translate(relativeX + gemCentreOffsetX, relativeY + gemCentreOffsetY, relativeZ + gemCentreOffsetZ);
	      float s = 0;
	      Random r = new Random();
	      s = r.nextFloat();
	      GlStateManager.rotate(s, 0, 1, 0);   // rotate around the vertical axis

	      final double GEM_HEIGHT = 0.5;        // desired render height of the gem
	      final double MODEL_HEIGHT = 1.0;      // actual height of the gem in the vertexTable
	      final double SCALE_FACTOR = GEM_HEIGHT / MODEL_HEIGHT;
	      GlStateManager.scale(SCALE_FACTOR, SCALE_FACTOR, SCALE_FACTOR);

	      Tessellator tessellator = Tessellator.getInstance();
	      BufferBuilder bufferBuilder = tessellator.getBuffer();
	      this.bindTexture(gemTexture);         // texture for the gem appearance

	      // set the key rendering flags appropriately...
	      GL11.glDisable(GL11.GL_LIGHTING);     // turn off "item" lighting (face brightness depends on which direction it is facing)
	      GL11.glDisable(GL11.GL_BLEND);        // turn off "alpha" transparency blending
	      GL11.glDepthMask(true);               // gem is hidden behind other objects

	      // set the rendering colour as the gem base colour
	      
	       

	        // change the "multitexturing" lighting value (default value is the brightness of the tile entity's block)
	        // - this will make the gem "glow" brighter than the surroundings if it is dark.
	      final int SKY_LIGHT_VALUE = (int)(15 * glowMultiplier);
	      final int BLOCK_LIGHT_VALUE = 0;
	      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, SKY_LIGHT_VALUE * 16.0F, BLOCK_LIGHT_VALUE * 16.0F);

	      bufferBuilder.begin(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_TEX);
	      addGemVertices(bufferBuilder);
	      tessellator.draw();

	    } finally {
	      GL11.glPopAttrib();
	      GL11.glPopMatrix();
	    }
	  }

	  // this should be true for tileentities which render globally (no render bounding box), such as beacons.
	  @Override
	  public boolean isGlobalRenderer(TileEntityComp tileEntityMBE21)
	  {
	    return false;
	  }

	  // add the vertices for drawing the gem.  Generated using a model builder and pasted manually because the object model
	  //   loader wasn't implemented at the time I wrote this example...
	  private void addGemVertices(BufferBuilder bufferBuilder) {
	    final double[][] vertexTable = {
	            {0.000,1.000,0.000,0.000,0.118},          //1
	            {-0.354,0.500,-0.354,0.000,0.354},
	            {-0.354,0.500,0.354,0.236,0.236},
	            {-0.354,0.500,0.354,0.236,0.236},         //2
	            {-0.354,0.500,-0.354,0.000,0.354},
	            {0.000,0.000,0.000,0.236,0.471},
	            {-0.354,0.500,0.354,0.236,0.236},         //3
	            {0.000,0.000,0.000,0.236,0.471},
	            {0.354,0.500,0.354,0.471,0.354},
	            {-0.354,0.500,0.354,0.236,0.236},         //4
	            {0.354,0.500,0.354,0.471,0.354},
	            {0.000,1.000,0.000,0.471,0.118},
	            {0.000,1.000,0.000,0.471,0.118},          //5
	            {0.354,0.500,0.354,0.471,0.354},
	            {0.354,0.500,-0.354,0.707,0.236},
	            {0.354,0.500,-0.354,0.707,0.236},         //6
	            {0.354,0.500,0.354,0.471,0.354},
	            {0.000,0.000,0.000,0.707,0.471},
	            {0.354,0.500,-0.354,0.707,0.236},         //7
	            {0.000,0.000,0.000,0.707,0.471},
	            {-0.354,0.500,-0.354,0.943,0.354},
	            {0.000,1.000,0.000,0.943,0.118},          //8
	            {0.354,0.500,-0.354,0.707,0.236},
	            {-0.354,0.500,-0.354,0.943,0.354}
	              };

	    for (double [] vertex : vertexTable) {
	      bufferBuilder.pos(vertex[0], vertex[1], vertex[2])
	                   .tex(vertex[3], vertex[4])
	                   .endVertex();
	    }
	  }

	   private static final ResourceLocation gemTexture = new ResourceLocation("modfun:textures/item/wrench_orange.png");
	}


