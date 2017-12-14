package net.modfun;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.modfun.item.ItemRegistry;
import net.modfun.CommonProxy;

@Mod(modid = Reference.MOD_ID, version = ExampleMod.VERSION)
public class ExampleMod
{
    public static final String MODID = "examplemod";
    public static final String VERSION = "1.0";
    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
    public static CommonProxy proxy;
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

    }
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code

    }
public static final CreativeTabs modTab = new CreativeTabs("modtabs"){

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(ItemRegistry.copperWrench);
    }
};
}
