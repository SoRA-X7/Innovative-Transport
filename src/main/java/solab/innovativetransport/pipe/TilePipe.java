package solab.innovativetransport.pipe;

import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import solab.innovativetransport.transporter.Transporter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TilePipe extends TileEntity {

    public List<Transporter> transporters = new ArrayList<Transporter>();
    public Map<EnumFacing,TilePipe> connection = new HashMap<>();
    static Map<EnumFacing,PropertyBool> states = new HashMap<>();
    public static final PropertyBool stateU = PropertyBool.create("up");
    public static final PropertyBool stateD = PropertyBool.create("down");
    public static final PropertyBool stateN = PropertyBool.create("north");
    public static final PropertyBool stateS = PropertyBool.create("south");
    public static final PropertyBool stateE = PropertyBool.create("east");
    public static final PropertyBool stateW = PropertyBool.create("west");

    public TilePipe() {
        connection.put(EnumFacing.UP,null);
        connection.put(EnumFacing.DOWN,null);
        connection.put(EnumFacing.NORTH,null);
        connection.put(EnumFacing.SOUTH,null);
        connection.put(EnumFacing.WEST,null);
        connection.put(EnumFacing.EAST,null);
    }

    void setBlockStatus(EnumFacing facing,boolean v) {
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

    public void connect(TilePipe to) {
        System.out.println("Connect from " + pos.toString() + " to " + to.pos.toString());
        BlockPos hispos = to.getPos();
        EnumFacing facing = EnumFacing.getFacingFromVector(
                hispos.getX() - pos.getX(),hispos.getY() - pos.getY(),hispos.getZ() - pos.getZ()
        );
        System.out.println(facing.toString());
        connection.put(facing,to);
        setBlockStatus(facing,true);
        markDirty();
        worldObj.notifyBlockUpdate(pos,worldObj.getBlockState(pos),worldObj.getBlockState(pos),2);
    }

    public void disconnect(TilePipe to) {
        for (Map.Entry<EnumFacing,TilePipe> entry:
             connection.entrySet()) {
            if (entry.getValue() == to) {
                connection.put(entry.getKey(),null);
                setBlockStatus(entry.getKey(),false);
            }
        }
        worldObj.markBlockRangeForRenderUpdate(pos, pos);
        worldObj.notifyBlockUpdate(pos,worldObj.getBlockState(pos),worldObj.getBlockState(pos),2);
        worldObj.scheduleBlockUpdate(pos,this.getBlockType(),0,0);
        markDirty();
    }

    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        connection.put(EnumFacing.UP,null);
        connection.put(EnumFacing.DOWN,null);
        connection.put(EnumFacing.NORTH,null);
        connection.put(EnumFacing.SOUTH,null);
        connection.put(EnumFacing.WEST,null);
        connection.put(EnumFacing.EAST,null);
        if (nbt.getBoolean("connection_up")) {
            connection.put(EnumFacing.UP,(TilePipe)worldObj.getTileEntity(pos.up()));
        }
        if (nbt.getBoolean("connection_down")) {
            connection.put(EnumFacing.DOWN,(TilePipe)worldObj.getTileEntity(pos.down()));
        }
        if (nbt.getBoolean("connection_north")) {
            connection.put(EnumFacing.NORTH,(TilePipe)worldObj.getTileEntity(pos.north()));
        }
        if (nbt.getBoolean("connection_south")) {
            connection.put(EnumFacing.SOUTH,(TilePipe)worldObj.getTileEntity(pos.south()));
        }
        if (nbt.getBoolean("connection_east")) {
            connection.put(EnumFacing.EAST,(TilePipe)worldObj.getTileEntity(pos.east()));
        }
        if (nbt.getBoolean("connection_west")) {
            connection.put(EnumFacing.WEST,(TilePipe)worldObj.getTileEntity(pos.west()));
        }
        markDirty();
        worldObj.notifyBlockUpdate(pos,worldObj.getBlockState(pos),worldObj.getBlockState(pos),2);
    }
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setBoolean("connection_up",connection.get(EnumFacing.UP) != null);
        nbt.setBoolean("connection_down",connection.get(EnumFacing.DOWN) != null);
        nbt.setBoolean("connection_north",connection.get(EnumFacing.NORTH) != null);
        nbt.setBoolean("connection_south",connection.get(EnumFacing.SOUTH) != null);
        nbt.setBoolean("connection_east",connection.get(EnumFacing.EAST) != null);
        nbt.setBoolean("connection_west",connection.get(EnumFacing.WEST) != null);
        return nbt;
    }

    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return true;
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
        readFromNBT(tag);
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket(){
        NBTTagCompound nbtTag = new NBTTagCompound();
        //Write your data into the nbtTag
        nbtTag = writeToNBT(nbtTag);
        return new SPacketUpdateTileEntity(getPos(), 1, nbtTag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt){
        NBTTagCompound tag = pkt.getNbtCompound();
        //Handle your Data
        readFromNBT(tag);
    }

}
