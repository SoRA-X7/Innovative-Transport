package solab.innovativetransport.pipe;

import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import solab.innovativetransport.PipeBlockStateNBTData;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class TilePipeHolder extends TileEntity implements IPipeHolder, ITickable {

    protected Pipe pipe;
    PipeBlockStateNBTData nbtData;
    boolean first = true;

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
                return true;
            }
        }
        return false;
    }

    public void disconnect(EnumFacing from) {
        pipe.connection.put(from,EnumConnectionType.none);
        worldObj.setBlockState(pos,worldObj.getBlockState(pos).withProperty(states.get(from),EnumConnectionType.none));
        markDirty();
        worldObj.notifyBlockUpdate(pos,worldObj.getBlockState(pos),worldObj.getBlockState(pos),2);
    }

    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        if (hasWorldObj()) {
            connectUsingNBT(new PipeBlockStateNBTData(nbtTagCompound));
        } else {
            nbtData = new PipeBlockStateNBTData(nbtTagCompound);
        }
//        markDirty();
//        worldObj.notifyBlockUpdate(pos,worldObj.getBlockState(pos),worldObj.getBlockState(pos),2);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt = super.writeToNBT(nbt);
        nbt.setString("connection_up",pipe.connection.get(EnumFacing.UP).getName());
        nbt.setString("connection_down",pipe.connection.get(EnumFacing.DOWN).getName());
        nbt.setString("connection_north",pipe.connection.get(EnumFacing.NORTH).getName());
        nbt.setString("connection_south",pipe.connection.get(EnumFacing.SOUTH).getName());
        nbt.setString("connection_east",pipe.connection.get(EnumFacing.EAST).getName());
        nbt.setString("connection_west",pipe.connection.get(EnumFacing.WEST).getName());
        return nbt;
    }

    void connectUsingNBT(PipeBlockStateNBTData nbtData) {
        IBlockState oldBlockState = worldObj.getBlockState(pos);
        if (EnumConnectionType.getTypeFromName(nbtData.u) == EnumConnectionType.pipe) {
            connect(EnumFacing.UP);
        }
        if (EnumConnectionType.getTypeFromName(nbtData.d) == EnumConnectionType.pipe) {
            connect(EnumFacing.DOWN);
        }
        if (EnumConnectionType.getTypeFromName(nbtData.n) == EnumConnectionType.pipe) {
            connect(EnumFacing.NORTH);
        }
        if (EnumConnectionType.getTypeFromName(nbtData.s) == EnumConnectionType.pipe) {
            connect(EnumFacing.SOUTH);
        }
        if (EnumConnectionType.getTypeFromName(nbtData.e) == EnumConnectionType.pipe) {
            connect(EnumFacing.EAST);
        }
        if (EnumConnectionType.getTypeFromName(nbtData.w) == EnumConnectionType.pipe) {
            connect(EnumFacing.WEST);
        }
        worldObj.notifyBlockUpdate(pos,oldBlockState,worldObj.getBlockState(pos),2);
        markDirty();
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound nbt = super.getUpdateTag();
        nbt = writeToNBT(nbt);
        return nbt;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        super.handleUpdateTag(tag);
        readFromNBT(tag);
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket(){
        NBTTagCompound nbtTag = new NBTTagCompound();
        //Write your data into the nbtTag
        nbtTag = writeToNBT(nbtTag);
        return new SPacketUpdateTileEntity(pos, 1, nbtTag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt){
        NBTTagCompound tag = pkt.getNbtCompound();
        //Handle your Data
        super.readFromNBT(tag);
        readFromNBT(tag);
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
        if (first && nbtData != null) {
            connectUsingNBT(nbtData);
            first = false;
        }
    }
}
