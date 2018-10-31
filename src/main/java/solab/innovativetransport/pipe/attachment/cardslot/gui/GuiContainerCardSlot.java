package solab.innovativetransport.pipe.attachment.cardslot.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import solab.innovativetransport.InnovativeTransport;

public class GuiContainerCardSlot extends GuiContainer {

    private static final ResourceLocation background = new ResourceLocation(InnovativeTransport.MODID,"textures/gui/guicardslot.png");

    public GuiContainerCardSlot(int x, int y, int z, EntityPlayer player) {
        super(new ContainerCardSlot(x, y, z, player));
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {

    }

    /**
     * Draws the background layer of this container (behind the items).
     *
     * @param partialTicks
     * @param mouseX
     * @param mouseY
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.renderEngine.bindTexture(background);
        drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, xSize, ySize);
    }
}
