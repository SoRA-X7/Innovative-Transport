package solab.innovativetransport.pipe;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import solab.innovativetransport.InnovativeTransport;

import java.util.Random;

public class BlockPipe extends BlockContainer {

    public BlockPipe() {
        super(Material.CIRCUITS);
        setRegistryName(InnovativeTransport.MODID,"transportPipe");
        setUnlocalizedName("transportpipe");
        setCreativeTab(InnovativeTransport.tab);
    }

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

//    @Override
////    @SideOnly(Side.SERVER)
//    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
//        if (!((World)world).isRemote) {
//            updateConnection(world,pos,neighbor);
//        }
//    }

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
            if (neighborPipe != null && worldIn.getTileEntity(neighborPipe.getPos()) instanceof TilePipe) {
                ((TilePipe) worldIn.getTileEntity(neighborPipe.getPos())).disconnect((TilePipe)worldIn.getTileEntity(pos));
            }
        }
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

    @Override @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        TilePipe me = ((TilePipe)worldIn.getTileEntity(pos));
//        System.out.println(me.connection.toString());
        if (me.connection.get(EnumFacing.UP) != null) {
            System.out.println("up");
            worldIn.spawnParticle(
                    EnumParticleTypes.REDSTONE,
                    pos.getX() + 0.5d,
                    pos.getY() + 1d,
                    pos.getZ() + 0.5d,
                    0,0,0
            );
        }
        if (me.connection.get(EnumFacing.DOWN) != null) {
            System.out.println("down");
            worldIn.spawnParticle(
                    EnumParticleTypes.REDSTONE,
                    pos.getX() + 0.5d,
                    pos.getY(),
                    pos.getZ() + 0.5d,
                    0,0,0
            );
        }
        if (me.connection.get(EnumFacing.NORTH) != null) {
            System.out.println("north");
            worldIn.spawnParticle(
                    EnumParticleTypes.REDSTONE,
                    pos.getX() + 0.5d,
                    pos.getY() + 0.5d,
                    pos.getZ() + 1d,
                    0,0,0
            );
        }
        if (me.connection.get(EnumFacing.SOUTH) != null) {
            System.out.println("south");
            worldIn.spawnParticle(
                    EnumParticleTypes.REDSTONE,
                    pos.getX() + 0.5d,
                    pos.getY() + 0.5d,
                    pos.getZ(),
                    0,0,0
            );
        }
        if (me.connection.get(EnumFacing.EAST) != null) {
            System.out.println("east");
            worldIn.spawnParticle(
                    EnumParticleTypes.REDSTONE,
                    pos.getX() + 1d,
                    pos.getY() + 0.5d,
                    pos.getZ() + 0.5d,
                    0,0,0
            );
        }
        if (me.connection.get(EnumFacing.WEST) != null) {
            System.out.println("west");
            worldIn.spawnParticle(
                    EnumParticleTypes.REDSTONE,
                    pos.getX(),
                    pos.getY() + 0.5d,
                    pos.getZ() + 0.5d,
                    0,0,0
            );
        }
    }

    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }
}
