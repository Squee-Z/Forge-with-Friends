package net.modfun.item;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
@Mod.EventBusSubscriber
public class ItemRegistry {

    public static Map<String, Item> ITEMS = new HashMap<String, Item>();

    public static final Item copper = new ItemCopper();
    public static final Item copperWrench = new ItemCopperWrench();
    public static final Item pack = new HandJetpack();

    /**
     * Register all the items into the event handler before the pre-init
     *
     * @param event
     */

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        Item kat = new QuickItem("modfun:kat", "kat");
        quickReg(event, copperWrench, copper, pack, kat);


    }

    /*
     * @param Registers Items quickly
     */
    public static void quickReg(RegistryEvent.Register<Item> event, Item... item) {
        for (Item itemI : item) {
            IForgeRegistry<Item> registry = event.getRegistry();
            registry.registerAll(itemI);
            ModelLoader.setCustomModelResourceLocation(itemI, 0, new ModelResourceLocation(itemI.getRegistryName(), "inventory"));
        }
    }
    /*
     * Render the models for the items
     */

}
