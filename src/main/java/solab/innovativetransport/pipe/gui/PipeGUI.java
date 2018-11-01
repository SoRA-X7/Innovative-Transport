package solab.innovativetransport.pipe.gui;

/**
 * Created by kirihi on 2018/10/30.
 */

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import solab.innovativetransport.pipe.normal.TilePipeHolder;

//たぶん一からやりなおすわこれ
@SuppressWarnings("unchecked")
public class PipeGUI {


    public static Object instance;

    public static int GUIID = 1;
    public PipeGUI() {
    }


    public static IInventory inve;

    public static class GuiContainerMod extends Container {
        World world = null;
        EntityPlayer entity = null;
        //int i, j, k;

        public GuiContainerMod(World world, int i, int j, int k, EntityPlayer player) {

            this.world = world;
            this.entity = player;
            //this.i = i;
            //this.j = j;
            //this.k = k;

            TileEntity ent = world.getTileEntity(new BlockPos(i, j, k));
            if (ent != null && (ent instanceof TilePipeHolder))
                inve = (IInventory) ent;
            else
                inve = new InventoryBasic("", true, 4);

            /*this.addSlotToContainer(new Slot(inve, 0, 55, 9) {
                public void onSlotChanged() {
                    super.onSlotChanged();
                    if (getHasStack()) {
                        EntityPlayer entity = Minecraft.getMinecraft().thePlayer;
                        int i = (int) entity.posX;
                        int j = (int) entity.posY;
                        int k = (int) entity.posZ;
                        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
                        World world = server.worldServers[0];

                    }
                }
            });*/

            bindPlayerInventory(player.inventory);

        }

        @Override
        public boolean canInteractWith(EntityPlayer player) {
            return true;
        }

        protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
            int i;
            int j;

            for (i = 0; i < 3; ++i) {
                for (j = 0; j < 9; ++j) {
                    this.addSlotToContainer(new Slot(inventoryPlayer, j + (i + 1) * 9, 8 + j * 18, 84 + i * 18));
                }
            }

            for (i = 0; i < 9; ++i) {
                this.addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
            }
        }

        @Override
        public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
            ItemStack itemstack = null;
            Slot slot = (Slot) this.inventorySlots.get(index);

            if (slot != null && slot.getHasStack()) {
                ItemStack itemstack1 = slot.getStack();
                itemstack = itemstack1.copy();

                if (index < 9) {
                    if (!this.mergeItemStack(itemstack1, 9, (45 - 9), true)) {// fixes
                        // shiftclick
                        // error
                        return null;
                    }
                } else if (!this.mergeItemStack(itemstack1, 0, 9, false)) {
                    return null;
                }

                if (itemstack1.stackSize == 0) {
                    slot.putStack((ItemStack) null);
                } else {
                    slot.onSlotChanged();
                }

                if (itemstack1.stackSize == itemstack.stackSize) {
                    return null;
                }

                slot.onPickupFromSlot(playerIn, itemstack1);
            }

            return itemstack;
        }

        public void onContainerClosed(EntityPlayer playerIn) {
            super.onContainerClosed(playerIn);

        }
    }

    public static class GuiWindow extends GuiContainer {

        int i = 0;
        int j = 0;
        int k = 0;
        EntityPlayer entity = null;

        public GuiWindow(World world, int i, int j, int k, EntityPlayer entity) {
            super(new GuiContainerMod(world, i, j, k, entity));
            this.i = i;
            this.j = j;
            this.k = k;
            this.entity = entity;
            this.xSize = 176;
            this.ySize = 166;
        }

        private static final ResourceLocation texture = new ResourceLocation("innovativetransport:textures/gui/guicardslot.png");

        protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
            int posX = (this.width) / 2;
            int posY = (this.height) / 2;
            MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
            World world = server.worldServers[0];
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

            this.mc.renderEngine.bindTexture(texture);
            int k = (this.width - this.xSize) / 2;
            int l = (this.height - this.ySize) / 2;
            this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, xSize, ySize);
            zLevel = 100.0F;

        }

        protected void drawGuiContainerForegroundLayer(int par1, int par2) {
            int posX = (this.width) / 2;
            int posY = (this.height) / 2;
            this.fontRendererObj.drawString("Pipepepepepepepepepepepepepe", 8, 5, 0x000000);//white 0xffffff
        }

        protected void mouseClicked(int par1, int par2, int par3) throws java.io.IOException {
            super.mouseClicked(par1, par2, par3);

        }

        public void updateScreen() {
            super.updateScreen();
            int posX = (this.width) / 2;
            int posY = (this.height) / 2;
        }

        @Override
        protected void keyTyped(char par1, int par2) throws java.io.IOException {

            super.keyTyped(par1, par2);

        }


        public void onGuiClosed() {
            super.onGuiClosed();
            Keyboard.enableRepeatEvents(false);
        }

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
        public boolean doesGuiPauseGame() {
            return false;
        }

    }
}