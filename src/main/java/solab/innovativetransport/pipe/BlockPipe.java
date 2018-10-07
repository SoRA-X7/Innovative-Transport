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

public class BlockPipe extends BlockContainer {

    public BlockPipe() {
        super(Material.CIRCUITS);
        setRegistryName(InnovativeTransport.MODID,"transportPipe");
        setUnlocalizedName("transportpipe");
        setCreativeTab(InnovativeTransport.tab);
        setDefaultState(blockState.getBaseState().withProperty(TilePipe.stateU,false)
                .withProperty(TilePipe.stateD,false)
                .withProperty(TilePipe.stateN,false)
                .withProperty(TilePipe.stateS,false)
                .withProperty(TilePipe.stateE,false)
                .withProperty(TilePipe.stateW,false)
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
        state.withProperty(TilePipe.stateU,tilePipe.connection.get(EnumFacing.UP) != null)
                .withProperty(TilePipe.stateD,tilePipe.connection.get(EnumFacing.DOWN) != null)
                .withProperty(TilePipe.stateN,tilePipe.connection.get(EnumFacing.NORTH) != null)
                .withProperty(TilePipe.stateS,tilePipe.connection.get(EnumFacing.SOUTH) != null)
                .withProperty(TilePipe.stateE,tilePipe.connection.get(EnumFacing.EAST) != null)
                .withProperty(TilePipe.stateW,tilePipe.connection.get(EnumFacing.WEST) != null);
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
        updateConnection(worldIn,pos,pos.up());
        updateConnection(worldIn,pos,pos.down());
        updateConnection(worldIn,pos,pos.north());
        updateConnection(worldIn,pos,pos.south());
        updateConnection(worldIn,pos,pos.west());
        updateConnection(worldIn,pos,pos.east());
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {

        for (TilePipe neighborPipe:((TilePipe)(worldIn.getTileEntity(pos))).connection.values()) {
            if (neighborPipe != null) {
                ((TilePipe) worldIn.getTileEntity(neighborPipe.getPos())).disconnect((TilePipe)worldIn.getTileEntity(pos));
            }
        }
//        super.breakBlock(worldIn,pos,state);

        worldIn.removeTileEntity(pos);
        worldIn.markBlockRangeForRenderUpdate(pos, pos);
        worldIn.notifyBlockUpdate(pos,worldIn.getBlockState(pos),worldIn.getBlockState(pos),2);
    }

    public void updateConnection(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
        TileEntity tile = world.getTileEntity(neighbor);
        TilePipe me = (TilePipe) world.getTileEntity(pos);
        if (tile != null && tile instanceof TilePipe) {
            me.connect((TilePipe)tile);
            ((TilePipe)tile).connect(me);
        }
//        System.out.println(me.connection.toString());
    }

    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }


}
