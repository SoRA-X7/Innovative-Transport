package solab.innovativetransport.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import solab.innovativetransport.InnovativeTransport;
import solab.innovativetransport.pipe.base.IBlockPipe;
import solab.innovativetransport.pipe.base.IPipeHolder;

import static solab.innovativetransport.InnovativeTransport.MODID;

public class ItemPanelRemover extends Item {

    public static final ItemPanelRemover INSTANCE = new ItemPanelRemover();

    private ItemPanelRemover() {
        super();
        setCreativeTab(InnovativeTransport.tab);
        setRegistryName(new ResourceLocation(MODID,"itempanelremover"));
        setUnlocalizedName(MODID + "_itempanelremover");
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.getBlockState(pos).getBlock() instanceof IBlockPipe) {
            IPipeHolder holder = (IPipeHolder) worldIn.getTileEntity(pos);
            if (holder != null) {

            }
        }
        return EnumActionResult.FAIL;
    }
}
