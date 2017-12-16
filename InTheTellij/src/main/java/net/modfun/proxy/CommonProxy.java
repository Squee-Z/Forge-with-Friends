package net.modfun.proxy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.modfun.ExampleMod;
import net.modfun.block.BlockTileEntityComp;
import net.modfun.item.ItemCopper;
import net.modfun.register.RegistBlock;
import net.modfun.tileentity.TileEntityComp;

public class CommonProxy {
	 public static ItemCopper itemCopper;
	 public static BlockTileEntityComp blockTileEntityData;  // this holds the unique instance of your block
	  public static ItemBlock itemBlockTileEntityData; // this holds the unique instance of the ItemBlock corresponding to your block

		@Mod.EventHandler
	  public static void preInitCommon(FMLPreInitializationEvent event)
	  {
	   // Tile Entities
	    blockTileEntityData = (BlockTileEntityComp)(new BlockTileEntityComp().setUnlocalizedName("comp"));
	    blockTileEntityData.setRegistryName("Compressor");
	    ForgeRegistries.BLOCKS.register(blockTileEntityData);

	    // ItemBlock
	    itemBlockTileEntityData = new ItemBlock(blockTileEntityData);
	    itemBlockTileEntityData.setRegistryName(blockTileEntityData.getRegistryName());
	    ForgeRegistries.ITEMS.register(itemBlockTileEntityData);
		//Items
		  itemCopper = (ItemCopper) (new ItemCopper().setUnlocalizedName("kopper"));
		  itemCopper.setRegistryName("copper");
		  ForgeRegistries.ITEMS.register(itemCopper);
	    // Tile Entity
	    GameRegistry.registerTileEntity(TileEntityComp.class, "komp");
	  }

	  public static void initCommon()
	  {
	  }

	  public static void postInitCommon()
	  {
	  }

	public void registerItemRenderer(Item item, int meta, String id) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(ExampleMod.MODID + ":" + id, "inventory"));
	}

	public void postInit() {
        RegistBlock.voidLists();
    }
}
