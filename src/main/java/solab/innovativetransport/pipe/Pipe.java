package solab.innovativetransport.pipe;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;
import solab.innovativetransport.card.CardSlot;
import solab.innovativetransport.transporter.ItemTransporter;
import solab.innovativetransport.transporter.Transporter;

import java.util.*;

public class Pipe implements IPipe {

    protected final TilePipeHolder holder;
    public EnumMap<EnumFacing,EnumConnectionType> connection = new EnumMap<EnumFacing, EnumConnectionType>(EnumFacing.class) {{
        put(EnumFacing.UP,EnumConnectionType.none);
        put(EnumFacing.DOWN,EnumConnectionType.none);
        put(EnumFacing.NORTH,EnumConnectionType.none);
        put(EnumFacing.SOUTH,EnumConnectionType.none);
        put(EnumFacing.EAST,EnumConnectionType.none);
        put(EnumFacing.WEST,EnumConnectionType.none);
    }};
    protected Map<EnumFacing,CardSlot> cardSlots = new HashMap<>();
    public List<Transporter> transporters = new ArrayList<>();
    public IInventory managedInventory;
    public Pipe(TilePipeHolder holderIn) {
        this.holder = holderIn;
    }

    public void update() {
        boolean changed = false;
        if (!cardSlots.isEmpty() && managedInventory == null) {
            managedInventory = holder.getNeighborInventory();
            changed = managedInventory != null;
        }
        for (CardSlot slot:
             cardSlots.values()) {
            slot.update();
        }

        Iterator<Transporter> i = transporters.iterator();
        while (i.hasNext()) {
            changed = true;
            Transporter tra = i.next();
            tra.progress += tra.speed;
            if (tra.progress >= 1f) {
                System.out.println("next");
                tra.current = tra.next;
                tra.in = tra.out.getOpposite();
                tra.out = tra.current.getPipe().getRandomExit(tra.in);
                if (tra.out == null) {
                    if (tra instanceof ItemTransporter && !tra.current.getWorld().isRemote) {
                        BlockPos pos = tra.current.getPos().offset(tra.in.getOpposite());
                        tra.current.getWorld().spawnEntityInWorld(new EntityItem(tra.current.getWorld(),pos.getX(),pos.getY(),pos.getZ(),((ItemTransporter)tra).item));
                        i.remove();
                        continue;
                    }
                }
                tra.next = tra.current.getNextPipeHolder(tra.out);
                tra.current.inject(tra);
                i.remove();
                tra.progress -= 1f;
            }
        }
        if (changed) {
            holder.markDirty();
            holder.getWorld().notifyBlockUpdate(holder.getPos(),holder.getWorld().getBlockState(holder.getPos()),holder.getWorld().getBlockState(holder.getPos()),2);
        }
    }

    @Override
    public TilePipeHolder getHolder() {
        return holder;
    }

    public EnumConnectionType getConnectionTypeOf(EnumFacing facing) {
        return connection.get(facing);
    }

    public void addCardSlot(EnumFacing facing) {
        cardSlots.put(facing,new CardSlot(this));
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
        for (EnumMap.Entry<EnumFacing,EnumConnectionType> entry:
             connection.entrySet()) {
            if (entry.getValue() == EnumConnectionType.pipe || entry.getValue() == EnumConnectionType.tile) {
                if (entry.getKey() != in) {
                    available.add(entry.getKey());
                }
            }
        }
        if (available.isEmpty()) {
            System.out.println("503");
            return null;
        }
        return available.get(new Random().nextInt(available.size()));
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagList list = new NBTTagList();
        for (int i=0;i<transporters.size();i++) {
            NBTTagCompound childCompound = new NBTTagCompound();
            childCompound = transporters.get(i).writeToNBT(childCompound);
            list.appendTag(childCompound);
        }
        compound.setTag("transporters",list);
        return compound;
    }
    public void readFromNBT(NBTTagCompound compound) {
        NBTTagList list = compound.getTagList("transporters", Constants.NBT.TAG_COMPOUND);
        transporters.clear();
        for (int i=0; i<list.tagCount(); i++) {
            NBTTagCompound childCompound = list.getCompoundTagAt(i);
            transporters.add(new Transporter(holder,childCompound));
        }
    }

}
