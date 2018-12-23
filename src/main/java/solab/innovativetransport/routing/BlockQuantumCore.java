package solab.innovativetransport.routing;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import solab.innovativetransport.InnovativeTransport;

import static solab.innovativetransport.InnovativeTransport.MODID;

public class BlockQuantumCore extends BlockContainer {

    private static final PropertyDirection facings = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    public static BlockQuantumCore INSTANCE;

    public BlockQuantumCore() {
        super(Material.IRON);
        setRegistryName(new ResourceLocation(MODID,"quantumcore"));
        setUnlocalizedName(MODID + "_quantumcore");
        setCreativeTab(InnovativeTransport.tab);
        setDefaultState(blockState.getBaseState().withProperty(facings, EnumFacing.NORTH));

        INSTANCE = this;
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

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, facings);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(facings).getIndex();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        switch (meta) {
            case 2:
            case 3:
            case 4:
            case 5:
                return this.getDefaultState().withProperty(facings, EnumFacing.values()[meta]);
            default:
                return this.getDefaultState();
        }
    }

    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(facings, EnumFacing.fromAngle(placer.rotationYaw).getOpposite());
    }
}
