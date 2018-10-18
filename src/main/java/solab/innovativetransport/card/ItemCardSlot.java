package solab.innovativetransport.card;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import solab.innovativetransport.InnovativeTransport;
import solab.innovativetransport.pipe.TilePipeHolder;

public class ItemCardSlot extends Item {
    public ItemCardSlot() {
        super();
        setCreativeTab(InnovativeTransport.tab);
        setRegistryName("itemcardslot");
        setUnlocalizedName("itemcardslot");
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        IBlockState blockState = worldIn.getBlockState(pos);
        if (blockState.getBlock() == InnovativeTransport.ITBlocks.PIPE) {
            TilePipeHolder holder = (TilePipeHolder)worldIn.getTileEntity(pos);
            if (holder != null) {
                holder.attachCardSlot(facing);
            }
        }
        return EnumActionResult.FAIL;
    }
}
