package solab.innovativetransport.routing;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import solab.innovativetransport.InnovativeTransport;

import static solab.innovativetransport.InnovativeTransport.MODID;

public class BlockQuantumCore extends BlockContainer {

    public static final BlockQuantumCore INSTANCE = new BlockQuantumCore();

    public BlockQuantumCore() {
        super(Material.IRON);
        setRegistryName(new ResourceLocation(MODID,"quantumcore"));
        setUnlocalizedName(MODID + "_quantumcore");
        setCreativeTab(InnovativeTransport.tab);
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     *
     * @param worldIn
     * @param meta
     */
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileQuantumCore();
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {return true;}

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.SOLID;
    }

    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
        return false;
    }
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }
}
