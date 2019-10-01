/*
 * Copyright (c) 2019. Made by SoRA_X7. CC BY-NC-SA 4.0
 */

package warpdrive.innovativetransport.pipe.normal;

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
import warpdrive.innovativetransport.pipe.attachment.cardslot.CardSlot;
import warpdrive.innovativetransport.pipe.base.IPipeHolder;
import warpdrive.innovativetransport.transporter.ItemTransporter;
import warpdrive.innovativetransport.utils.Constants;
import warpdrive.innovativetransport.utils.PipeBlockStateNBTData;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

import static net.minecraftforge.common.util.Constants.NBT.TAG_COMPOUND;

/**
 * パイプ、カードスロット等のTileEntity
 */
public class TilePipeHolder extends TileEntity implements IPipeHolder, ITickable {

    /**
     * 自身のPipe
     */
    public final Pipe pipe;

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

    @Override
    public boolean hasFastRenderer() {
        return false;
    }

    public TilePipeHolder() {
        this.pipe = new Pipe(this);
        pipe.connection.put(EnumFacing.UP, Constants.EnumConnectionType.none);
        pipe.connection.put(EnumFacing.DOWN, Constants.EnumConnectionType.none);
        pipe.connection.put(EnumFacing.NORTH, Constants.EnumConnectionType.none);
        pipe.connection.put(EnumFacing.SOUTH, Constants.EnumConnectionType.none);
        pipe.connection.put(EnumFacing.WEST, Constants.EnumConnectionType.none);
        pipe.connection.put(EnumFacing.EAST, Constants.EnumConnectionType.none);
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
    public boolean connect(EnumFacing to, boolean checkNext) {
        TilePipeHolder next = getNextPipeHolder(to);
        if (!checkNext || next != null) {
            if (!checkNext || ((next.pipe.connection.get(to.getOpposite()) == Constants.EnumConnectionType.none || next.pipe.connection.get(to.getOpposite()) == Constants.EnumConnectionType.pipe)) && pipe.connection.get(to) == Constants.EnumConnectionType.none) {
                IBlockState oldState = worldObj.getBlockState(pos);
                worldObj.setBlockState(pos,oldState.withProperty(states.get(to),true));
                pipe.connection.put(to, Constants.EnumConnectionType.pipe);
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
    public void disconnect(EnumFacing from) {
        if (pipe.connection.get(from) == Constants.EnumConnectionType.pipe) {
            pipe.connection.put(from, Constants.EnumConnectionType.none);
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
        if (pipe.connection.get(facing) == Constants.EnumConnectionType.none) {
            pipe.addCardSlotNonOverride(facing);
            IBlockState oldState = worldObj.getBlockState(pos);
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

        for (EnumFacing facing : EnumFacing.VALUES) {
            if (nbtTagCompound.hasKey("cardslot_" + facing.getName(), TAG_COMPOUND)) {
                pipe.addCardSlotNonOverride(facing).readFromNBT(nbtTagCompound.getCompoundTag("cardslot_" + facing.getName()));
            }
        }

        if (hasWorldObj()) {
            connectUsingNBT(new PipeBlockStateNBTData(nbtTagCompound));
        } else {
            nbtData = new PipeBlockStateNBTData(nbtTagCompound);
        }
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt = super.writeToNBT(nbt);
        nbt = pipe.writeToNBT(nbt);

        for (EnumFacing facing:
             EnumFacing.VALUES) {
            CardSlot slot = pipe.getCardSlot(facing);
            if (slot != null) {
                nbt.setTag("cardslot_"+facing.getName(),slot.writeToNBT(new NBTTagCompound()));
            }
        }

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
        switch (Constants.EnumConnectionType.valueOf(nbtData.u)) {
            case pipe:
                connect(EnumFacing.UP, false);
                break;
            case none:
                disconnect(EnumFacing.UP);
                break;
        }
        switch (Constants.EnumConnectionType.valueOf(nbtData.d)) {
            case pipe:
                connect(EnumFacing.DOWN, false);
                break;
            case none:
                disconnect(EnumFacing.DOWN);
                break;
        }
        switch (Constants.EnumConnectionType.valueOf(nbtData.n)) {
            case pipe:
                connect(EnumFacing.NORTH, false);
                break;
            case none:
                disconnect(EnumFacing.NORTH);
                break;
        }
        switch (Constants.EnumConnectionType.valueOf(nbtData.s)) {
            case pipe:
                connect(EnumFacing.SOUTH, false);
                break;
            case none:
                disconnect(EnumFacing.SOUTH);
                break;
        }
        switch (Constants.EnumConnectionType.valueOf(nbtData.e)) {
            case pipe:
                connect(EnumFacing.EAST, false);
                break;
            case none:
                disconnect(EnumFacing.EAST);
                break;
        }
        switch (Constants.EnumConnectionType.valueOf(nbtData.w)) {
            case pipe:
                connect(EnumFacing.WEST, false);
                break;
            case none:
                disconnect(EnumFacing.WEST);
                break;
        }
        worldObj.notifyBlockUpdate(pos,oldBlockState,worldObj.getBlockState(pos),2);
        markDirty();
    }

    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return (oldState.getBlock() != newSate.getBlock());
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
        readFromNBT(tag);
    }

    /**
     * 隣接TileEntityを取得します。
     * @param facing TileEntityの方角
     * @return 指定した方向のTileEntity、存在しなければnull
     */
    @Nullable
    public TileEntity getNeighborTile(EnumFacing facing) {
        if (hasWorldObj() && facing != null) {
            return getWorld().getTileEntity(getPos().offset(facing));
        }
        return null;
    }

    /**
     * 指定した方角のTilePipeHolderを取得します。
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

    /**
     * 指定した方角のIInventoryを取得します。
     * @return 指定した方角のIInventory、存在しなければnull
     */
    @Nullable
    IInventory getNeighborInventory() {
        for (EnumFacing facing :
                EnumFacing.VALUES) {
            if (pipe.connection.get(facing) == Constants.EnumConnectionType.tile) {
                return (IInventory) getNeighborTile(facing);
            }
        }
        for (EnumFacing facing:
                EnumFacing.VALUES) {
            if (pipe.connection.get(facing) == Constants.EnumConnectionType.none) {
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
