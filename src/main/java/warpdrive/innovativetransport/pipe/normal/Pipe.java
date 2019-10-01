/*
 * Copyright (c) 2019. Made by SoRA_X7. CC BY-NC-SA 4.0
 */

package warpdrive.innovativetransport.pipe.normal;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;
import warpdrive.innovativetransport.pipe.attachment.cardslot.CardSlot;
import warpdrive.innovativetransport.pipe.base.IPipe;
import warpdrive.innovativetransport.transporter.ItemTransporter;

import java.util.*;

/**
 * アイテム輸送管としてのパイプの機能
 * （まだ分離しきれていない）
 */
public class Pipe implements IPipe {

    private final TilePipeHolder holder;
    public EnumMap<EnumFacing, warpdrive.innovativetransport.utils.Constants.EnumConnectionType> connection = new EnumMap<EnumFacing, warpdrive.innovativetransport.utils.Constants.EnumConnectionType>(EnumFacing.class) {{
        put(EnumFacing.UP, warpdrive.innovativetransport.utils.Constants.EnumConnectionType.none);
        put(EnumFacing.DOWN, warpdrive.innovativetransport.utils.Constants.EnumConnectionType.none);
        put(EnumFacing.NORTH, warpdrive.innovativetransport.utils.Constants.EnumConnectionType.none);
        put(EnumFacing.SOUTH, warpdrive.innovativetransport.utils.Constants.EnumConnectionType.none);
        put(EnumFacing.EAST, warpdrive.innovativetransport.utils.Constants.EnumConnectionType.none);
        put(EnumFacing.WEST, warpdrive.innovativetransport.utils.Constants.EnumConnectionType.none);
    }};
    private Map<EnumFacing,CardSlot> cardSlots = new HashMap<>();
    List<ItemTransporter> items = new ArrayList<>();
    public IInventory managedInventory;

    public Pipe(TilePipeHolder holderIn) {
        this.holder = holderIn;
    }

    void update() {

        boolean changed = false;
        if (!cardSlots.isEmpty() && managedInventory == null) {
            managedInventory = holder.getNeighborInventory();
            changed = managedInventory != null;
        }
        for (CardSlot slot:
                cardSlots.values()) {
            slot.update();
        }

        Iterator<ItemTransporter> i = items.iterator();
        while (i.hasNext()) {
            ItemTransporter tra = i.next();
            if (tra.next == null) {
                tra.next = tra.current.getNextPipeHolder(tra.out);
            }

            tra.progress += tra.speed;
            if (!holder.getWorld().isRemote) {
                if (tra.next == null && tra.progress > 0.7f) {
                    changed = true;
                    if (tra.current.hasWorldObj() && !tra.current.getWorld().isRemote) {
                        BlockPos pos = tra.current.getPos().offset(tra.in.getOpposite());
                        tra.current.getWorld().spawnEntityInWorld(new EntityItem(tra.current.getWorld(),pos.getX(),pos.getY(),pos.getZ(), tra.item));
                        i.remove();
                        continue;
                    }
                }

                if (tra.progress >= 1f) {
                    changed = true;
                    tra.in = tra.out.getOpposite();
                    EnumFacing rndOut = tra.next != null ? tra.next.getPipe().getRandomExit(tra.in) : null;
                    if (rndOut != null) {
                        tra.out = rndOut;
                    } else {
                        tra.out = tra.in.getOpposite();
                    }
                    assert tra.next != null;
                    tra.next.inject(tra);
                    i.remove();
                    tra.progress -= 1f;
                    tra.current = tra.next;
                    tra.next = tra.next.getNextPipeHolder(tra.out);
                }
            }
        }
        if (changed) {
            holder.markDirty();
            holder.getWorld().notifyBlockUpdate(holder.getPos(),holder.getWorld().getBlockState(holder.getPos()),holder.getWorld().getBlockState(holder.getPos()),2);
        }
    }

    public List<ItemTransporter> getItems() {return items;}

    @Override
    public TilePipeHolder getHolder() {
        return holder;
    }

    @Override
    public warpdrive.innovativetransport.utils.Constants.EnumConnectionType getConnectionTypeOf(EnumFacing facing) {
        return connection.get(facing);
    }

    /**
     * スロットが存在しない場合、インスタンス化してそれを返します。
     * スロットがすでにある場合、そのスロットを返します。
     * @param facing スロットを設置する方向
     * @return 最終的にfacingにあるスロット
     */
    CardSlot addCardSlotNonOverride(EnumFacing facing) {
        if (cardSlots.get(facing) == null) {
            CardSlot slot = new CardSlot(this);
            cardSlots.put(facing,slot);
            connection.put(facing, warpdrive.innovativetransport.utils.Constants.EnumConnectionType.slot);
            return slot;
        } else {
            return cardSlots.get(facing);
        }
    }

    public CardSlot getCardSlot(EnumFacing facing) {
        return cardSlots.get(facing);
    }

    public void removeCardSlot(EnumFacing facing) {
        cardSlots.remove(facing);
        if (cardSlots.isEmpty()) {
            managedInventory = null;
        }
    }

    public EnumFacing getRandomExit(EnumFacing in) {
        List<EnumFacing> available = new ArrayList<>();
        for (EnumMap.Entry<EnumFacing, warpdrive.innovativetransport.utils.Constants.EnumConnectionType> entry :
                connection.entrySet()) {
            if (entry.getValue() == warpdrive.innovativetransport.utils.Constants.EnumConnectionType.pipe || entry.getValue() == warpdrive.innovativetransport.utils.Constants.EnumConnectionType.tile) {
                if (entry.getKey() != in) {
                    available.add(entry.getKey());
                }
            }
        }
        if (available.isEmpty()) {
            return null;
        }
        return available.get(new Random().nextInt(available.size()));
    }

    NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagList list = new NBTTagList();
        for (ItemTransporter transporter : items) {
            NBTTagCompound childCompound = new NBTTagCompound();
            childCompound = transporter.writeToNBT(childCompound);
            list.appendTag(childCompound);
        }
        compound.setTag("items",list);
        return compound;
    }

    void readFromNBT(NBTTagCompound compound) {
        NBTTagList list = compound.getTagList("items", Constants.NBT.TAG_COMPOUND);
        items.clear();
        for (int i = 0; i<list.tagCount(); i++) {
            NBTTagCompound childCompound = list.getCompoundTagAt(i);
            items.add(new ItemTransporter(holder,childCompound));
        }
    }

}
