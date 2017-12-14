package net.modfun.proxy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.modfun.example.TileEntityCompressor;
import net.modfun.example.TileEntitySpecialRendererCompressor;

public class ClientProxy extends CommonProxy{
	public static void preInitClientOnly()
	  {
	    // This is currently necessary in order to make your block render properly when it is an item (i.e. in the inventory
	    //   or in your hand or thrown on the ground).
	    // It must be done on client only, and must be done after the block has been created in Common.preinit().
	    ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation("minecraftbyexample:mbe21_tesr_block", "inventory");
	    final int DEFAULT_ITEM_SUBTYPE = 0;
	    ModelLoader.setCustomModelResourceLocation(CommonProxy.itemBlockMBE21, DEFAULT_ITEM_SUBTYPE, itemModelResourceLocation);  }

	  public static void initClientOnly()
	  {
	    ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCompressor.class, new TileEntitySpecialRendererCompressor());
	  }

	  public static void postInitClientOnly()
	  {
	  }

}
