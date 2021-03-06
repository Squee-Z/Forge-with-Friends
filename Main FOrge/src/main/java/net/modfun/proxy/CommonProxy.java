package net.modfun.proxy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.modfun.ExampleMod;
import net.modfun.block.BlockTileEntityComp;
import net.modfun.register.RegistBlock;
import net.modfun.tileentity.TileEntityComp;

public class CommonProxy {

	 public static BlockTileEntityComp blockTileEntityData;  // this holds the unique instance of your block
	  public static ItemBlock itemBlockTileEntityData; // this holds the unique instance of the ItemBlock corresponding to your block

	  public static void preInitCommon()
	  {
	    // each instance of your block should have two names:
	    // 1) a registry name that is used to uniquely identify this block.  Should be unique within your mod.  use lower case.
	    // 2) an 'unlocalised name' that is used to retrieve the text name of your block in the player's language.  For example-
	    //    the unlocalised name might be "water", which is printed on the user's screen as "Wasser" in German or
	    //    "aqua" in Italian.
	    //
	    //    Multiple blocks can have the same unlocalised name - for example
	    //  +----RegistryName----+---UnlocalisedName----+
	    //  |  flowing_water     +       water          |
	    //  |  stationary_water  +       water          |
	    //  +--------------------+----------------------+
	    //
	    blockTileEntityData = (BlockTileEntityComp)(new BlockTileEntityComp().setUnlocalizedName("comp"));
	    blockTileEntityData.setRegistryName("Comp");
	    ForgeRegistries.BLOCKS.register(blockTileEntityData);

	    // We also need to create and register an ItemBlock for this block otherwise it won't appear in the inventory
	    itemBlockTileEntityData = new ItemBlock(blockTileEntityData);
	    itemBlockTileEntityData.setRegistryName(blockTileEntityData.getRegistryName());
	    ForgeRegistries.ITEMS.register(itemBlockTileEntityData);

	    // Each of your tile entities needs to be registered with a name that is unique to your mod.
	    GameRegistry.registerTileEntity(TileEntityComp.class, "mbe20_tileentity_data_tile_entity");
	  }

	  public static void initCommon()
	  {
	  }

	  public static void postInitCommon()
	  {
	  }
	public void init(FMLInitializationEvent event)
	{
		 RegistBlock.init();
		
	}
	public void registerItemRenderer(Item item, int meta, String id) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(ExampleMod.MODID + ":" + id, "inventory"));
	}

	public void postInit() {
        RegistBlock.voidLists();
    }
}
