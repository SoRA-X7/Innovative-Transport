package solab.innovativetransport.pipe;

import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class TilePipeHolder extends TileEntity implements IPipeHolder, ITickable {

    protected Pipe pipe;

    public static final Map<EnumFacing,PropertyEnum<EnumConnectionType>> states = new HashMap<EnumFacing, PropertyEnum<EnumConnectionType>>() {
        {
            put(EnumFacing.UP,PropertyEnum.create("up",EnumConnectionType.class));
            put(EnumFacing.DOWN,PropertyEnum.create("down",EnumConnectionType.class));
            put(EnumFacing.NORTH,PropertyEnum.create("north",EnumConnectionType.class));
            put(EnumFacing.SOUTH,PropertyEnum.create("south",EnumConnectionType.class));
            put(EnumFacing.EAST,PropertyEnum.create("east",EnumConnectionType.class));
            put(EnumFacing.WEST,PropertyEnum.create("west",EnumConnectionType.class));
        }
    };

    public TilePipeHolder() {
        this.pipe = new Pipe(this);
    }

    @Override
    public IPipe getPipe() {
        return pipe;
    }

    public boolean connect(EnumFacing to) {
        TilePipeHolder next = getNextPipeHolder(to);
        if (next != null) {
            if (pipe.connection.get(to) == EnumConnectionType.none) {
                worldObj.setBlockState(pos,worldObj.getBlockState(pos).withProperty(states.get(to),EnumConnectionType.pipe));
                pipe.connection.put(to,EnumConnectionType.pipe);
                markDirty();
                worldObj.notifyBlockUpdate(pos,worldObj.getBlockState(pos),worldObj.getBlockState(pos),2);
            }
        }
        return false;
    }

    @Nullable
    @Override
    public TileEntity getNeighborTile(EnumFacing facing) {
        return getWorld().getTileEntity(getPos().offset(facing));
    }

    public TilePipeHolder getNextPipeHolder(EnumFacing facing) {
        TileEntity tile = getNeighborTile(facing);
        if (tile instanceof TilePipeHolder) {
            return (TilePipeHolder)tile;
        }
        return null;
    }

    /**
     * Like the old updateEntity(), except more generic.
     */
    @Override
    public void update() {
        pipe.update();
    }
}
