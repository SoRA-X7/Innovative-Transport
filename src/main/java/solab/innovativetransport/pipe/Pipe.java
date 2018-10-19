package solab.innovativetransport.pipe;

import net.minecraft.inventory.IInventory;
import net.minecraft.util.EnumFacing;
import solab.innovativetransport.card.CardSlot;
import solab.innovativetransport.transporter.Transporter;

import java.util.*;

public class Pipe implements IPipe {

    protected TilePipeHolder holder;
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
        if (!cardSlots.isEmpty() && managedInventory != null) {

        }
        for (CardSlot slot:
             cardSlots.values()) {
            slot.update();
        }
        for (Transporter tra:
             transporters) {

        }
    }

    @Override
    public IPipeHolder getHolder() {
        return holder;
    }

    public EnumConnectionType getConnectionTypeOf(EnumFacing facing) {
        return connection.get(facing);
    }

    public void addCardSlot(EnumFacing facing) {
        cardSlots.put(facing,new CardSlot());
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
            return null;
        }
        return available.get(new Random().nextInt(available.size()));
    }

}
