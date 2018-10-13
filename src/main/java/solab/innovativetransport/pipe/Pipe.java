package solab.innovativetransport.pipe;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import solab.innovativetransport.card.CardSlot;
import solab.innovativetransport.card.cardbase.ICardBehaviour;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pipe implements IPipe {

    protected IPipeHolder holder;
    public Map<EnumFacing,EnumConnectionType> connection = new HashMap<EnumFacing, EnumConnectionType>() {{
        put(EnumFacing.UP,EnumConnectionType.none);
        put(EnumFacing.DOWN,EnumConnectionType.none);
        put(EnumFacing.NORTH,EnumConnectionType.none);
        put(EnumFacing.SOUTH,EnumConnectionType.none);
        put(EnumFacing.EAST,EnumConnectionType.none);
        put(EnumFacing.WEST,EnumConnectionType.none);
    }};
    protected Map<EnumFacing,CardSlot> cardSlots = new HashMap<>();

    public Pipe(IPipeHolder holderIn) {
        this.holder = holderIn;
    }
    /**
     * Determines if this object has support for the capability in question on the specific side.
     * The return value of this MIGHT change during runtime if this object gains or looses support
     * for a capability.
     * <p>
     * Example:
     * A Pipe getting a cover placed on one side causing it loose the Inventory attachment function for that side.
     * <p>
     * This is a light weight version of getCapability, intended for metadata uses.
     *
     * @param capability The capability to check
     * @param facing     The Side to check from:
     *                   CAN BE NULL. Null is defined to represent 'internal' or 'self'
     * @return True if this object supports the capability.
     */
    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return false;
    }

    /**
     * Retrieves the handler for the capability requested on the specific side.
     * The return value CAN be null if the object does not support the capability.
     * The return value CAN be the same for multiple faces.
     *
     * @param capability The capability to check
     * @param facing     The Side to check from:
     *                   CAN BE NULL. Null is defined to represent 'internal' or 'self'
     * @return True if this object supports the capability.
     */
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        return null;
    }

    public void update() {
        for (CardSlot slot:
             cardSlots.values()) {
            slot.update();
        }
    }

    @Override
    public IPipeHolder getHolder() {
        return holder;
    }

    public void addCardSlot(EnumFacing facing) {
        cardSlots.put(facing,new CardSlot());
    }

    public CardSlot getCardSlot(EnumFacing facing) {
        return cardSlots.get(facing);
    }

    public void removeCardSlot(EnumFacing facing) {
        cardSlots.remove(facing);
    }

}
