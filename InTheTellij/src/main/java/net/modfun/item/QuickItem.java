package net.modfun.item;

import net.minecraft.item.Item;
import net.modfun.Reference;

public class QuickItem extends Item {
    public QuickItem(String unl, String regn)
    {
        setUnlocalizedName(unl);
        setRegistryName(regn);
        setCreativeTab(Reference.TAB);
    }
}
