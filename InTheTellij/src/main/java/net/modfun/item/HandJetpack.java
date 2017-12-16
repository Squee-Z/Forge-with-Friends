package net.modfun.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.modfun.Reference;

public class HandJetpack extends Item {
    public HandJetpack()
    {
        setCreativeTab(Reference.TAB);
        setRegistryName("HandPack");
        setUnlocalizedName("hand_pack");
        setMaxStackSize(1);
    }
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        RayTraceResult result = playerIn.rayTrace(100, 10);
        BlockPos pos = result.getBlockPos();
        Entity entity = result.entityHit;
        double x = playerIn.motionX;
        double z = playerIn.motionZ;

        playerIn.motionY += 0.5;
        playerIn.motionX *= x * x;
        playerIn.motionZ *= z * z;
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
    
}
