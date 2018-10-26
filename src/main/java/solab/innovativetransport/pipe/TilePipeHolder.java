package solab.innovativetransport.pipe;

import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import solab.innovativetransport.transporter.ItemTransporter;
import solab.innovativetransport.utils.PipeBlockStateNBTData;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * パイプ、カードスロット等のTileEntity
 */
public class TilePipeHolder extends TileEntity implements IPipeHolder, ITickable {

    /**
     * 自身のPipe
     */
    protected final Pipe pipe;

    private PipeBlockStateNBTData nbtData;
    private boolean first = true;

    public static final Map<EnumFacing,PropertyBool> states = new HashMap<EnumFacing, PropertyBool>() {
        {
            put(EnumFacing.UP,PropertyBool.create("up"));
            put(EnumFacing.DOWN,PropertyBool.create("down"));
            put(EnumFacing.NORTH,PropertyBool.create("north"));
            put(EnumFacing.SOUTH,PropertyBool.create("south"));
            put(EnumFacing.EAST,PropertyBool.create("east"));
            put(EnumFacing.WEST,PropertyBool.create("west"));
        }
    };

    public TilePipeHolder() {
        this.pipe = new Pipe(this);
        pipe.connection.put(EnumFacing.UP,EnumConnectionType.none);
        pipe.connection.put(EnumFacing.DOWN,EnumConnectionType.none);
        pipe.connection.put(EnumFacing.NORTH,EnumConnectionType.none);
        pipe.connection.put(EnumFacing.SOUTH,EnumConnectionType.none);
        pipe.connection.put(EnumFacing.WEST,EnumConnectionType.none);
        pipe.connection.put(EnumFacing.EAST,EnumConnectionType.none);
    }

    @Override
    public Pipe getPipe() {
        return pipe;
    }

    /**
     * 隣接したTilePipeHolderと接続します。
     *
     * @param to        　接続する方向（自分から見て）
     * @param checkNext falseの場合、接続先がTilePipeHolderでなくてもその方角を接続状態にします。
     * @return 接続に成功したらtrue
     */
    boolean connect(EnumFacing to, boolean checkNext) {
        TilePipeHolder next = getNextPipeHolder(to);
        if (!checkNext || next != null) {
            if (!checkNext || ((next.pipe.connection.get(to.getOpposite()) == EnumConnectionType.none || next.pipe.connection.get(to.getOpposite()) == EnumConnectionType.pipe)) && pipe.connection.get(to) == EnumConnectionType.none) {
                IBlockState oldState = worldObj.getBlockState(pos);
                worldObj.setBlockState(pos,oldState.withProperty(states.get(to),true));
                pipe.connection.put(to,EnumConnectionType.pipe);
                markDirty();
                worldObj.notifyBlockUpdate(pos,oldState,worldObj.getBlockState(pos),2);
                return true;
            }
        }
        return false;
    }

    /**
     * 指定した方向のパイプと切断します。
     * 自分にしか効果を及ぼさないため、相手先でも同じメソッドを呼んでください。
     *
     * @param from 切断するパイプの方向
     */
    void disconnect(EnumFacing from) {
        if (pipe.connection.get(from) == EnumConnectionType.pipe) {
            pipe.connection.put(from,EnumConnectionType.none);
            worldObj.setBlockState(pos,worldObj.getBlockState(pos).withProperty(states.get(from),false));
            markDirty();
            worldObj.notifyBlockUpdate(pos, worldObj.getBlockState(pos), worldObj.getBlockState(pos), 2);
        }
    }

    /**
     * カードスロットを装着します。
     *
     * @param facing 装着する方向
     * @return 装着に成功したらtrue
     */
    public boolean attachCardSlot(EnumFacing facing) {
        if (pipe.connection.get(facing) == EnumConnectionType.none) {
            pipe.addCardSlot(facing);
            IBlockState oldState = worldObj.getBlockState(pos);
//            worldObj.setBlockState(pos,oldState.withProperty(states.get(facing), EnumConnectionType.slot));
            pipe.connection.put(facing,EnumConnectionType.slot);
            markDirty();
            worldObj.notifyBlockUpdate(pos, oldState, worldObj.getBlockState(pos), 2);
            return true;
        }
        return false;
    }

    /**
     * ItemTransporterをこのパイプに挿入します。
     *
     * @param transporter 挿入するItemTransporter
     * @see ItemTransporter
     */
    public void inject(ItemTransporter transporter) {
        pipe.items.add(transporter);
        markDirty();
        worldObj.notifyBlockUpdate(pos,worldObj.getBlockState(pos),worldObj.getBlockState(pos),2);
    }

    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        pipe.readFromNBT(nbtTagCompound);
        if (hasWorldObj()) {
            connectUsingNBT(new PipeBlockStateNBTData(nbtTagCompound));
        } else {
            nbtData = new PipeBlockStateNBTData(nbtTagCompound);
        }
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt = super.writeToNBT(nbt);
        nbt = pipe.writeToNBT(nbt);
        nbt.setString("connection_up",pipe.connection.get(EnumFacing.UP).getName());
        nbt.setString("connection_down",pipe.connection.get(EnumFacing.DOWN).getName());
        nbt.setString("connection_north",pipe.connection.get(EnumFacing.NORTH).getName());
        nbt.setString("connection_south",pipe.connection.get(EnumFacing.SOUTH).getName());
        nbt.setString("connection_east",pipe.connection.get(EnumFacing.EAST).getName());
        nbt.setString("connection_west",pipe.connection.get(EnumFacing.WEST).getName());
        return nbt;
    }

    private void connectUsingNBT(PipeBlockStateNBTData nbtData) {
        IBlockState oldBlockState = worldObj.getBlockState(pos);
        if (EnumConnectionType.valueOf(nbtData.u) == EnumConnectionType.pipe) {
            connect(EnumFacing.UP,false);
        } else if (EnumConnectionType.valueOf(nbtData.u) == EnumConnectionType.slot) {
            attachCardSlot(EnumFacing.UP);
        } else if (EnumConnectionType.valueOf(nbtData.u) == EnumConnectionType.none) {
            disconnect(EnumFacing.UP);
        }
        if (EnumConnectionType.valueOf(nbtData.d) == EnumConnectionType.pipe) {
            connect(EnumFacing.DOWN,false);
        } else if (EnumConnectionType.valueOf(nbtData.d) == EnumConnectionType.slot) {
            attachCardSlot(EnumFacing.DOWN);
        } else if (EnumConnectionType.valueOf(nbtData.d) == EnumConnectionType.none) {
            disconnect(EnumFacing.DOWN);
        }
        if (EnumConnectionType.valueOf(nbtData.n) == EnumConnectionType.pipe) {
            connect(EnumFacing.NORTH,false);
        } else if (EnumConnectionType.valueOf(nbtData.n) == EnumConnectionType.slot) {
            attachCardSlot(EnumFacing.NORTH);
        } else if (EnumConnectionType.valueOf(nbtData.n) == EnumConnectionType.none) {
            disconnect(EnumFacing.NORTH);
        }
        if (EnumConnectionType.valueOf(nbtData.s) == EnumConnectionType.pipe) {
            connect(EnumFacing.SOUTH,false);
        } else if (EnumConnectionType.valueOf(nbtData.s) == EnumConnectionType.slot) {
            attachCardSlot(EnumFacing.SOUTH);
        } else if (EnumConnectionType.valueOf(nbtData.s) == EnumConnectionType.none) {
            disconnect(EnumFacing.SOUTH);
        }
        if (EnumConnectionType.valueOf(nbtData.e) == EnumConnectionType.pipe) {
            connect(EnumFacing.EAST,false);
        } else if (EnumConnectionType.valueOf(nbtData.e) == EnumConnectionType.slot) {
            attachCardSlot(EnumFacing.EAST);
        } else if (EnumConnectionType.valueOf(nbtData.e) == EnumConnectionType.none) {
            disconnect(EnumFacing.EAST);
        }
        if (EnumConnectionType.valueOf(nbtData.w) == EnumConnectionType.pipe) {
            connect(EnumFacing.WEST,false);
        } else if (EnumConnectionType.valueOf(nbtData.w) == EnumConnectionType.slot) {
            attachCardSlot(EnumFacing.WEST);
        } else if (EnumConnectionType.valueOf(nbtData.w) == EnumConnectionType.none) {
            disconnect(EnumFacing.WEST);
        }
        worldObj.notifyBlockUpdate(pos,oldBlockState,worldObj.getBlockState(pos),2);
        markDirty();
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

    /** 隣接TileEntityを取得します。
     * @param facing TileEntityの方角
     * @return 指定した方向のTileEntity、存在しなければnull
     */
    @Nullable
    public TileEntity getNeighborTile(EnumFacing facing) {
        if (hasWorldObj() && facing != null) {
            return getWorld().getTileEntity(getPos().offset(facing));
        } else return null;
    }

    /** 指定した方角のTilePipeHolderを取得します。
     * @param facing TilePipeHolderの方角
     * @return 指定した方向のTilePipeHolder、存在しなければnull
     */
    @Nullable
    TilePipeHolder getNextPipeHolder(EnumFacing facing) {
        TileEntity tile = getNeighborTile(facing);
        if (tile instanceof TilePipeHolder) {
            return (TilePipeHolder) tile;
        }
        return null;
    }

    /** 指定した方角のIInventoryを取得します。
     * @return 指定した方角のIInventory、存在しなければnull
     */
    @Nullable
    IInventory getNeighborInventory() {
        for (EnumFacing facing :
                EnumFacing.VALUES) {
            if (pipe.connection.get(facing) == EnumConnectionType.tile) {
                return (IInventory) getNeighborTile(facing);
            }
        }
        for (EnumFacing facing:
                EnumFacing.VALUES) {
            if (pipe.connection.get(facing) == EnumConnectionType.none) {
                TileEntity tile = getNeighborTile(facing);
                if (tile instanceof IInventory) {
                    return (IInventory) tile;
                }

            }
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
