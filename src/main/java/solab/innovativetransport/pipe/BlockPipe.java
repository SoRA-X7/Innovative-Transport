package solab.innovativetransport.pipe;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import solab.innovativetransport.InnovativeTransport;

import java.util.Map;

public class BlockPipe extends BlockContainer {

    public BlockPipe() {
        super(Material.CIRCUITS);
        setRegistryName(InnovativeTransport.MODID,"transportpipe");
        setUnlocalizedName("transportpipe");
        setCreativeTab(InnovativeTransport.tab);
        setDefaultState(blockState.getBaseState().withProperty(TilePipe.stateU,EnumConnectionType.none)
                .withProperty(TilePipe.stateD,EnumConnectionType.none)
                .withProperty(TilePipe.stateN,EnumConnectionType.none)
                .withProperty(TilePipe.stateS,EnumConnectionType.none)
                .withProperty(TilePipe.stateE,EnumConnectionType.none)
                .withProperty(TilePipe.stateW,EnumConnectionType.none)
        );
    }
    @Override
    protected BlockStateContainer createBlockState(){
        return new BlockStateContainer(this,TilePipe.stateU,TilePipe.stateD,TilePipe.stateN,TilePipe.stateS,TilePipe.stateE,TilePipe.stateW);
    }

    public int getMetaFromState(IBlockState state)
    {
        return 0;
    }

    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        TilePipe tilePipe = (TilePipe)worldIn.getTileEntity(pos);
        state.withProperty(TilePipe.stateU,tilePipe.connection.get(EnumFacing.UP))
                .withProperty(TilePipe.stateD,tilePipe.connection.get(EnumFacing.DOWN))
                .withProperty(TilePipe.stateN,tilePipe.connection.get(EnumFacing.NORTH))
                .withProperty(TilePipe.stateS,tilePipe.connection.get(EnumFacing.SOUTH))
                .withProperty(TilePipe.stateE,tilePipe.connection.get(EnumFacing.EAST))
                .withProperty(TilePipe.stateW,tilePipe.connection.get(EnumFacing.WEST));
        return state;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {return true;}

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TilePipe();
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
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

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        updateConnection(worldIn,pos,EnumFacing.UP);
        updateConnection(worldIn,pos,EnumFacing.DOWN);
        updateConnection(worldIn,pos,EnumFacing.NORTH);
        updateConnection(worldIn,pos,EnumFacing.SOUTH);
        updateConnection(worldIn,pos,EnumFacing.EAST);
        updateConnection(worldIn,pos,EnumFacing.WEST);
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {

        for (Map.Entry<EnumFacing,EnumConnectionType> entry:((TilePipe)worldIn.getTileEntity(pos)).connection.entrySet()) {
            if (entry.getValue() != EnumConnectionType.none) {
                TilePipe tilePipe = (TilePipe)worldIn.getTileEntity(pos.offset(entry.getKey()));
                tilePipe.disconnect(entry.getKey().getOpposite());
            }
        }

        worldIn.removeTileEntity(pos);
//        worldIn.markBlockRangeForRenderUpdate(pos, pos);
//        worldIn.notifyBlockUpdate(pos,worldIn.getBlockState(pos),worldIn.getBlockState(pos),2);
    }

    public void updateConnection(IBlockAccess world, BlockPos pos, EnumFacing facing) {
        TileEntity tile = world.getTileEntity(pos.offset(facing));
        TilePipe me = (TilePipe) world.getTileEntity(pos);
        if (tile instanceof TilePipe) {
            me.connect(facing);
            ((TilePipe)tile).connect(facing.getOpposite());
        }
//        System.out.println(me.connection.toString());
    }

    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }


}
