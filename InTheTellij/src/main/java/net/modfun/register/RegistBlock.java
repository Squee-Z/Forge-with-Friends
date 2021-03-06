package net.modfun.register;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.modfun.block.BlockTileEntityComp;


public class RegistBlock
{
	 //Contains all registered blocks
    public static List<Block> BLOCKS;
    public static List<ItemBlock> ITEM_BLOCKS;

    public static BlockTileEntityComp blockTileEntityData;  // this holds the unique instance of your block
    public static ItemBlock itemBlockTileEntityData; // this holds the unique instance of the ItemBlock corresponding to your block




    public static void init()
    {
        BLOCKS = new ArrayList<>();
        ITEM_BLOCKS = new ArrayList<>();
        
        
        
    }

    private static void addBlock(Block block)
    {
        addBlock(block, (ItemBlock) new ItemBlock(block));
    }

    private static void addBlock(Block block, ItemBlock itemBlock)
    {
        BLOCKS.add(block);
        itemBlock.setRegistryName(block.getRegistryName());
        ITEM_BLOCKS.add(itemBlock);
    }

    private static void regTE(Class<? extends TileEntity> teClass, Block block)
    {
        GameRegistry.registerTileEntity(teClass, block.getRegistryName().getResourcePath());
    }

    private static <T extends TileEntity> void regTESR(Class<T> teClass, TileEntitySpecialRenderer<? super T> tesr)
    {
        ClientRegistry.bindTileEntitySpecialRenderer(teClass, tesr);
    }

    public static void regTileEntities()
    {
      
        
    }

   
    public static ItemBlock[] getItemBlocks()
    {
        if(ITEM_BLOCKS == null) init();
        return ITEM_BLOCKS.toArray(new ItemBlock[ITEM_BLOCKS.size()]);
    }

    public static Block[] getBlocks()
    {
        if(BLOCKS == null) init();
        return BLOCKS.toArray(new Block[BLOCKS.size()]);
    }
   

    public static void voidLists()
    {
        BLOCKS = null;
        ITEM_BLOCKS = null;
    }
	    }

