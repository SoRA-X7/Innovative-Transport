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
    static final PropertyBool stateU = PropertyBool.create("up");
    static final PropertyBool stateD = PropertyBool.create("down");
    static final PropertyBool stateN = PropertyBool.create("north");
    static final PropertyBool stateS = PropertyBool.create("south");
    static final PropertyBool stateE = PropertyBool.create("east");
    static final PropertyBool stateW = PropertyBool.create("west");

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
//        System.out.println("Connect from " + pos.toString() + " to " + to.pos.toString());
        BlockPos hispos = to.getPos();
        EnumFacing facing = EnumFacing.getFacingFromVector(
                hispos.getX() - pos.getX(),hispos.getY() - pos.getY(),hispos.getZ() - pos.getZ()
        );
        connection.put(facing,to);
        setBlockStatus(facing,true);
//        System.out.println(connection.toString());
//        System.out.println(worldObj.getBlockState(pos));
        markDirty();
        worldObj.notifyBlockUpdate(pos,worldObj.getBlockState(pos),worldObj.getBlockState(pos),2);
    }

    public void disconnect(TilePipe to) {
        System.out.println("Disconnect from " + pos.toString() + " to " + to.pos.toString());
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
//        System.out.println(nbt.toString());
        if (nbt.getBoolean("connection_up")) {
            connect((TilePipe)worldObj.getTileEntity(pos.up()));
//            setBlockStatus(EnumFacing.UP,true);
        } else {
//            connection.put(EnumFacing.UP,null);
//            setBlockStatus(EnumFacing.UP,false);
        }
        if (nbt.getBoolean("connection_down")) {
            connect((TilePipe)worldObj.getTileEntity(pos.up()));
//            setBlockStatus(EnumFacing.DOWN,true);
        } else {
//            connection.put(EnumFacing.DOWN,null);
//            setBlockStatus(EnumFacing.DOWN,false);
        }
        if (nbt.getBoolean("connection_north")) {
            connect((TilePipe)worldObj.getTileEntity(pos.up()));
//            setBlockStatus(EnumFacing.NORTH,true);
        } else {
//            connection.put(EnumFacing.NORTH,null);
//            setBlockStatus(EnumFacing.NORTH,false);
        }
        if (nbt.getBoolean("connection_south")) {
            connect((TilePipe)worldObj.getTileEntity(pos.up()));
//            setBlockStatus(EnumFacing.SOUTH,true);
        } else {
//            connection.put(EnumFacing.SOUTH,null);
//            setBlockStatus(EnumFacing.SOUTH,false);
        }
        if (nbt.getBoolean("connection_east")) {
            connect((TilePipe)worldObj.getTileEntity(pos.up()));
//            setBlockStatus(EnumFacing.EAST,true);
        } else {
//            connection.put(EnumFacing.EAST,null);
//            setBlockStatus(EnumFacing.EAST,false);
        }
        if (nbt.getBoolean("connection_west")) {
            connect((TilePipe)worldObj.getTileEntity(pos.up()));
//            setBlockStatus(EnumFacing.WEST,true);
        } else {
//            connection.put(EnumFacing.WEST,null);
//            setBlockStatus(EnumFacing.WEST,false);
        }
        markDirty();
        worldObj.notifyBlockUpdate(pos,worldObj.getBlockState(pos),worldObj.getBlockState(pos),2);
    }
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt = super.writeToNBT(nbt);
        System.out.println(connection.toString());
//        IBlockState state = worldObj.getBlockState(pos);
        nbt.setBoolean("connection_up",connection.get(EnumFacing.UP) != null);
        nbt.setBoolean("connection_down",connection.get(EnumFacing.DOWN) != null);
        nbt.setBoolean("connection_north",connection.get(EnumFacing.NORTH) != null);
        nbt.setBoolean("connection_south",connection.get(EnumFacing.SOUTH) != null);
        nbt.setBoolean("connection_east",connection.get(EnumFacing.EAST) != null);
        nbt.setBoolean("connection_west",connection.get(EnumFacing.WEST) != null);
        System.out.println(nbt.toString());
        return nbt;
    }

    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState == newSate;
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

}
