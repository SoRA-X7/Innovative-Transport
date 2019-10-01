/*
 * Copyright (c) 2019. Made by SoRA_X7. CC BY-NC-SA 4.0
 */

package warpdrive.innovativetransport.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import warpdrive.innovativetransport.InnovativeTransport;
import warpdrive.innovativetransport.pipe.base.IBlockPipe;
import warpdrive.innovativetransport.pipe.normal.TilePipeHolder;
import warpdrive.innovativetransport.utils.Constants;

public class ItemPanelRemover extends Item {

    public static final ItemPanelRemover INSTANCE = new ItemPanelRemover();

    public ItemPanelRemover() {
        super();
        setCreativeTab(InnovativeTransport.tab);
        setRegistryName(new ResourceLocation(InnovativeTransport.MODID, "itempanelremover"));
        setUnlocalizedName(InnovativeTransport.MODID + ":itempanelremover");
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.getBlockState(pos).getBlock() instanceof IBlockPipe) {
            TilePipeHolder holder = (TilePipeHolder) worldIn.getTileEntity(pos);
            if (holder != null) {
                if (holder.pipe.connection.get(facing) == Constants.EnumConnectionType.slot) {
                    holder.pipe.removeCardSlot(facing);
                }
            }
        }
        return EnumActionResult.FAIL;
    }
}
