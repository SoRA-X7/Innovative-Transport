package solab.innovativetransport;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import solab.innovativetransport.pipe.attachment.cardslot.gui.ContainerCardSlot;
import solab.innovativetransport.pipe.attachment.cardslot.gui.GuiContainerCardSlot;
import solab.innovativetransport.utils.Constants.GuiTypes;

public class ITGuiHandler implements IGuiHandler {
    /**
     * Returns a Server side Container to be displayed to the user.
     *
     * @param ID     The Gui ID Number
     * @param player The player viewing the Gui
     * @param world  The current world
     * @param x      X Position
     * @param y      Y Position
     * @param z      Z Position
     * @return A GuiScreen/Container to be displayed to the user, null if none.
     */
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (GuiTypes.values()[ID]) {
            case CardSlot:
                return new ContainerCardSlot(x, y, z, player);
        }
        return null;
    }

    /**
     * Returns a Container to be displayed to the user. On the client side, this
     * needs to return a instance of GuiScreen On the server side, this needs to
     * return a instance of Container
     *
     * @param ID     The Gui ID Number
     * @param player The player viewing the Gui
     * @param world  The current world
     * @param x      X Position
     * @param y      Y Position
     * @param z      Z Position
     * @return A GuiScreen/Container to be displayed to the user, null if none.
     */
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (GuiTypes.values()[ID]) {
            case CardSlot:
                return new GuiContainerCardSlot(x, y, z, player);
        }
        return null;
    }
}
