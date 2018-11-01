package solab.innovativetransport.pipe.attachment.cardslot.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.lwjgl.input.Keyboard;
import solab.innovativetransport.InnovativeTransport;

public class GuiContainerCardSlot extends GuiContainer {
    public static IInventory inve;
    private static final ResourceLocation background = new ResourceLocation(InnovativeTransport.MODID,"textures/gui/guicardslot.png");

    public GuiContainerCardSlot(World world, int x, int y, int z, EntityPlayer player) {
        super(new ContainerCardSlot(world,x, y, z, player));
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.fontRendererObj.drawString("CardSlot", 8, 5, 0x000000);//white 0xffffff
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
    //ボタン追加その他もろもろ
    public void initGui() {
        super.initGui();
        this.guiLeft = (this.width - 176) / 2;
        this.guiTop = (this.height - 166) / 2;
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        int posX = (this.width) / 2;
        int posY = (this.height) / 2;
        this.buttonList.add(new GuiButton(0, this.guiLeft + 10, this.guiTop + 10, 20, 20, "A"));
    }
    //ボタン管理
    protected void actionPerformed(GuiButton button) {
        //int i = (int) entity.posX;
        //int j = (int) entity.posY;
        //int k = (int) entity.posZ;
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        World world = server.worldServers[0];
        //EntityItem redball = new EntityItem(world, (double) (i), (double) (j + 2), (double) (k), new ItemStack(ExampleMod.redball, 1, 0));
        if (button.id == 0) {
        }

    }
}
