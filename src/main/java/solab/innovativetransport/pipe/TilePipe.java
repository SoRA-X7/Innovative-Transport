package solab.innovativetransport.pipe;

import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import solab.innovativetransport.PipeBlockStateNBTData;
import solab.innovativetransport.transporter.Transporter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TilePipe extends TileEntity implements ITickable {

    public List<Transporter> transporters = new ArrayList<Transporter>();
    public Map<EnumFacing,EnumConnectionType> connection = new HashMap<>();
    static final PropertyEnum<EnumConnectionType> stateU = PropertyEnum.create("up",EnumConnectionType.class);
    static final PropertyEnum<EnumConnectionType> stateD = PropertyEnum.create("down",EnumConnectionType.class);
    static final PropertyEnum<EnumConnectionType> stateN = PropertyEnum.create("north",EnumConnectionType.class);
    static final PropertyEnum<EnumConnectionType> stateS = PropertyEnum.create("south",EnumConnectionType.class);
    static final PropertyEnum<EnumConnectionType> stateE = PropertyEnum.create("east",EnumConnectionType.class);
    static final PropertyEnum<EnumConnectionType> stateW = PropertyEnum.create("west",EnumConnectionType.class);
    public static final Map<EnumFacing,PropertyEnum<EnumConnectionType>> states = new HashMap<EnumFacing, PropertyEnum<EnumConnectionType>>() {
        {
            put(EnumFacing.UP,stateU);
            put(EnumFacing.DOWN,stateD);
            put(EnumFacing.NORTH,stateN);
            put(EnumFacing.SOUTH,stateS);
            put(EnumFacing.EAST,stateE);
            put(EnumFacing.WEST,stateW);
        }
    };
    PipeBlockStateNBTData nbtData;
    boolean first = true;

    public TilePipe() {
        connection.put(EnumFacing.UP,EnumConnectionType.none);
        connection.put(EnumFacing.DOWN,EnumConnectionType.none);
        connection.put(EnumFacing.NORTH,EnumConnectionType.none);
        connection.put(EnumFacing.SOUTH,EnumConnectionType.none);
        connection.put(EnumFacing.WEST,EnumConnectionType.none);
        connection.put(EnumFacing.EAST,EnumConnectionType.none);
    }

    void setBlockStatus(EnumFacing facing,EnumConnectionType v) {
        switch (facing) {
            case UP:
                worldObj.setBlockState(pos,worldObj.getBlockState(pos).withProperty(stateU,v));
                break;
            case DOWN:
                worldObj.setBlockState(pos,worldObj.getBlockState(pos).withProperty(stateD,v));
                break;
            case NORTH:
                worldObj.setBlockState(pos,worldObj.getBlockState(pos).withProperty(stateN,v));
                break;
            case SOUTH:
                worldObj.setBlockState(pos,worldObj.getBlockState(pos).withProperty(stateS,v));
                break;
            case EAST:
                worldObj.setBlockState(pos,worldObj.getBlockState(pos).withProperty(stateE,v));
                break;
            case WEST:
                worldObj.setBlockState(pos,worldObj.getBlockState(pos).withProperty(stateW,v));
                break;
        }
    }

    public void connect(EnumFacing to) {
//        System.out.println("Connect from " + pos.toString() + " to " + to.pos.toString());
        if (to != null) {
            setBlockStatus(to,EnumConnectionType.pipe);
            connection.put(to,EnumConnectionType.pipe);
            markDirty();
            worldObj.notifyBlockUpdate(pos,worldObj.getBlockState(pos),worldObj.getBlockState(pos),2);
        }

    }

    public void disconnect(EnumFacing to) {
        connection.put(to,EnumConnectionType.none);
        setBlockStatus(to,EnumConnectionType.none);

        markDirty();
        worldObj.markBlockRangeForRenderUpdate(pos, pos);
        worldObj.notifyBlockUpdate(pos,worldObj.getBlockState(pos),worldObj.getBlockState(pos),2);
        worldObj.scheduleBlockUpdate(pos,this.getBlockType(),0,0);
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
        nbt.setString("connection_up",connection.get(EnumFacing.UP).getName());
        nbt.setString("connection_down",connection.get(EnumFacing.DOWN).getName());
        nbt.setString("connection_north",connection.get(EnumFacing.NORTH).getName());
        nbt.setString("connection_south",connection.get(EnumFacing.SOUTH).getName());
        nbt.setString("connection_east",connection.get(EnumFacing.EAST).getName());
        nbt.setString("connection_west",connection.get(EnumFacing.WEST).getName());
        return nbt;
    }

    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState == newSate;
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
    public void handleUpdateTag(NBTTagCompound tag)
    {
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

    /**
     * Like the old updateEntity(), except more generic.
     */
    @Override
    public void update() {
        if (first) {
            if (nbtData != null) {
                connectUsingNBT(nbtData);
                first = false;
            }
        }
    }
}
