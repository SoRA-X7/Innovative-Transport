package solab.innovativetransport.pipe;

import codechicken.lib.raytracer.IndexedCuboid6;
import codechicken.lib.raytracer.RayTracer;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import solab.innovativetransport.InnovativeTransport;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BlockPipe extends BlockContainer {

    protected static final AxisAlignedBB CENTER_AABB = new AxisAlignedBB(0.25D, 0.25D, 0.25D, 0.75D, 0.75D, 0.75D);
    protected static final AxisAlignedBB UP_AABB = new AxisAlignedBB(0.25D, 0.75D, 0.25D, 0.75D, 1D, 0.75D);
    protected static final AxisAlignedBB DOWN_AABB = new AxisAlignedBB(0.25D, 0D, 0.25D, 0.75D, 0.25D, 0.75D);
    protected static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.25D, 0.25D, 0D, 0.75D, 0.75D, 0.25D);
    protected static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.25D, 0.25D, 0.75D, 0.75D, 0.75D, 1D);
    protected static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.75D, 0.25D, 0.25D, 1D, 0.75D, 0.75D);
    protected static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0D, 0.25D, 0.25D, 0.25D, 0.75D, 0.75D);
    protected static final AxisAlignedBB[] BOX_FACES = {DOWN_AABB , UP_AABB, NORTH_AABB, SOUTH_AABB, WEST_AABB, EAST_AABB };

    public BlockPipe() {
        super(Material.CIRCUITS);
        setRegistryName(InnovativeTransport.MODID,"transportpipe");
        setUnlocalizedName("transportpipe");
        setCreativeTab(InnovativeTransport.tab);
        setDefaultState(blockState.getBaseState()
                .withProperty(TilePipeHolder.states.get(EnumFacing.UP),EnumConnectionType.none)
                .withProperty(TilePipeHolder.states.get(EnumFacing.DOWN),EnumConnectionType.none)
                .withProperty(TilePipeHolder.states.get(EnumFacing.NORTH),EnumConnectionType.none)
                .withProperty(TilePipeHolder.states.get(EnumFacing.SOUTH),EnumConnectionType.none)
                .withProperty(TilePipeHolder.states.get(EnumFacing.EAST),EnumConnectionType.none)
                .withProperty(TilePipeHolder.states.get(EnumFacing.WEST),EnumConnectionType.none)
        );
    }
    @Override
    protected BlockStateContainer createBlockState(){
        return new BlockStateContainer(this,
                TilePipeHolder.states.get(EnumFacing.UP),
                TilePipeHolder.states.get(EnumFacing.DOWN),
                TilePipeHolder.states.get(EnumFacing.NORTH),
                TilePipeHolder.states.get(EnumFacing.SOUTH),
                TilePipeHolder.states.get(EnumFacing.EAST),
                TilePipeHolder.states.get(EnumFacing.WEST)
        );
    }

    public int getMetaFromState(IBlockState state)
    {
        return 0;
    }

    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        TilePipeHolder tilePipe = (TilePipeHolder) worldIn.getTileEntity(pos);
        state.withProperty(TilePipeHolder.states.get(EnumFacing.UP),tilePipe.pipe.connection.get(EnumFacing.UP))
                .withProperty(TilePipeHolder.states.get(EnumFacing.DOWN),tilePipe.pipe.connection.get(EnumFacing.DOWN))
                .withProperty(TilePipeHolder.states.get(EnumFacing.NORTH),tilePipe.pipe.connection.get(EnumFacing.NORTH))
                .withProperty(TilePipeHolder.states.get(EnumFacing.SOUTH),tilePipe.pipe.connection.get(EnumFacing.SOUTH))
                .withProperty(TilePipeHolder.states.get(EnumFacing.EAST),tilePipe.pipe.connection.get(EnumFacing.EAST))
                .withProperty(TilePipeHolder.states.get(EnumFacing.WEST),tilePipe.pipe.connection.get(EnumFacing.WEST));
        return state;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {return true;}

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TilePipeHolder();
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

        for (Map.Entry<EnumFacing,EnumConnectionType> entry:((TilePipeHolder)worldIn.getTileEntity(pos)).pipe.connection.entrySet()) {
            if (entry.getValue() == EnumConnectionType.pipe) {
                TilePipeHolder holder = (TilePipeHolder) worldIn.getTileEntity(pos.offset(entry.getKey()));
                if (holder != null) {
                    holder.disconnect(entry.getKey().getOpposite());
                }
            }
        }

        worldIn.removeTileEntity(pos);
//        worldIn.markBlockRangeForRenderUpdate(pos, pos);
//        worldIn.notifyBlockUpdate(pos,worldIn.getBlockState(pos),worldIn.getBlockState(pos),2);
    }

    public void updateConnection(IBlockAccess world, BlockPos pos, EnumFacing facing) {
        TileEntity tile = world.getTileEntity(pos.offset(facing));
        TilePipeHolder me = (TilePipeHolder) world.getTileEntity(pos);
        if (tile instanceof TilePipeHolder) {
            me.connect(facing,true);
            ((TilePipeHolder)tile).connect(facing.getOpposite(),true);
        }
//        System.out.println(me.connection.toString());
    }

    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        RayTraceResult trace = Minecraft.getMinecraft().objectMouseOver;
        if (trace == null || trace.subHit < 0 || !pos.equals(trace.getBlockPos())) {
            // Perhaps we aren't the object the mouse is over
            return FULL_BLOCK_AABB;
        }
        int part = trace.subHit;
        AxisAlignedBB aabb = CENTER_AABB;
        switch (part) {
            case 0:
                aabb.union(CENTER_AABB);
                break;
            case 1:
                aabb.union(UP_AABB);
                break;
            case 2:
                aabb.union(DOWN_AABB);
                break;
            case 3:
                aabb.union(NORTH_AABB);
                break;
            case 4:
                aabb.union(SOUTH_AABB);
                break;
            case 5:
                aabb.union(EAST_AABB);
                break;
            case 6:
                aabb.union(WEST_AABB);
                break;
        }
        return aabb;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World world, BlockPos pos) {
        TilePipeHolder tile = (TilePipeHolder)world.getTileEntity(pos);
        if (tile == null) {
            return FULL_BLOCK_AABB;
        }
        RayTraceResult trace = Minecraft.getMinecraft().objectMouseOver;
        if (trace == null || trace.subHit < 0 || !pos.equals(trace.getBlockPos())) {
            // Perhaps we aren't the object the mouse is over
            return FULL_BLOCK_AABB;
        }
        int part = trace.subHit;
        AxisAlignedBB aabb = FULL_BLOCK_AABB;
        System.out.println(part);

        if (part == 0) {
            aabb = CENTER_AABB;
        } else if (part < 1 + 6) {
            aabb = BOX_FACES[part - 1];
            Pipe pipe = tile.getPipe();
            if (pipe != null) {
                EnumFacing face = EnumFacing.VALUES[part - 1];
                EnumConnectionType con = pipe.getConnectionTypeOf(face);
            }
        }

        return aabb;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn) {
        state = state.getActualState(worldIn, pos);
        addCollisionBoxToList(pos,entityBox,collidingBoxes,CENTER_AABB);
        if ((state.getValue(TilePipeHolder.states.get(EnumFacing.UP)) == EnumConnectionType.pipe)) addCollisionBoxToList(pos,entityBox,collidingBoxes,UP_AABB);
        if ((state.getValue(TilePipeHolder.states.get(EnumFacing.DOWN)) == EnumConnectionType.pipe)) addCollisionBoxToList(pos,entityBox,collidingBoxes,DOWN_AABB);
        if ((state.getValue(TilePipeHolder.states.get(EnumFacing.NORTH)) == EnumConnectionType.pipe)) addCollisionBoxToList(pos,entityBox,collidingBoxes,NORTH_AABB);
        if ((state.getValue(TilePipeHolder.states.get(EnumFacing.SOUTH)) == EnumConnectionType.pipe)) addCollisionBoxToList(pos,entityBox,collidingBoxes,SOUTH_AABB);
        if ((state.getValue(TilePipeHolder.states.get(EnumFacing.EAST)) == EnumConnectionType.pipe)) addCollisionBoxToList(pos,entityBox,collidingBoxes,EAST_AABB);
        if ((state.getValue(TilePipeHolder.states.get(EnumFacing.WEST)) == EnumConnectionType.pipe)) addCollisionBoxToList(pos,entityBox,collidingBoxes,WEST_AABB);
    }

    @Override
    public RayTraceResult collisionRayTrace(IBlockState blockState, World worldIn, BlockPos pos, Vec3d start, Vec3d end) {
        if (blockState.getBlock() == InnovativeTransport.ITBlocks.PIPE) {
            List<IndexedCuboid6> cuboids = new ArrayList<>();
            cuboids.add(new IndexedCuboid6(0,CENTER_AABB));
            if ((blockState.getValue(TilePipeHolder.states.get(EnumFacing.DOWN)) == EnumConnectionType.pipe)) cuboids.add(new IndexedCuboid6(1,DOWN_AABB));
            if ((blockState.getValue(TilePipeHolder.states.get(EnumFacing.UP)) == EnumConnectionType.pipe)) cuboids.add(new IndexedCuboid6(2,UP_AABB));
            if ((blockState.getValue(TilePipeHolder.states.get(EnumFacing.NORTH)) == EnumConnectionType.pipe)) cuboids.add(new IndexedCuboid6(3,NORTH_AABB));
            if ((blockState.getValue(TilePipeHolder.states.get(EnumFacing.SOUTH)) == EnumConnectionType.pipe)) cuboids.add(new IndexedCuboid6(4,SOUTH_AABB));
            if ((blockState.getValue(TilePipeHolder.states.get(EnumFacing.WEST)) == EnumConnectionType.pipe)) cuboids.add(new IndexedCuboid6(5,WEST_AABB));
            if ((blockState.getValue(TilePipeHolder.states.get(EnumFacing.EAST)) == EnumConnectionType.pipe)) cuboids.add(new IndexedCuboid6(6,EAST_AABB));
            return RayTracer.rayTraceCuboidsClosest(start, end, cuboids, pos);
        }
        return null;
    }
}
