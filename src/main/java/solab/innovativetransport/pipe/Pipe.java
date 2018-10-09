package solab.innovativetransport.pipe;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import solab.innovativetransport.transporter.ItemTransporter;
import solab.innovativetransport.transporter.Transporter;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class Pipe implements IPipe {

    public List<ItemTransporter> items = new ArrayList<>();
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
}
