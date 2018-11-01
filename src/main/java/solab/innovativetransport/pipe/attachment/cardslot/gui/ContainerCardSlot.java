package solab.innovativetransport.pipe.attachment.cardslot.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class ContainerCardSlot extends Container {
    public static IInventory inve;
    //public InventoryCardSlot cslot = new InventoryCardSlot();
    //スロットの追加、処理
    public ContainerCardSlot(World world, int i, int j, int k, EntityPlayer player) {
            inve = new InventoryBasic("", true, 4);

            this.addSlotToContainer(new Slot(inve, 0, 55, 9) {
                public void onSlotChanged() {
                    super.onSlotChanged();
                    if (getHasStack()) {
                        EntityPlayer entity = Minecraft.getMinecraft().thePlayer;
                        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
                        World world = server.worldServers[0];

                    }
                }
            });
        this.addSlotToContainer(new Slot(inve, 1, 105, 9) {
            public void onSlotChanged() {
                super.onSlotChanged();
                if (getHasStack()) {
                    EntityPlayer entity = Minecraft.getMinecraft().thePlayer;
                    MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
                    World world = server.worldServers[0];

                }
            }
        });
        this.addSlotToContainer(new Slot(inve, 2, 55, 59) {
            public void onSlotChanged() {
                super.onSlotChanged();
                if (getHasStack()) {
                    EntityPlayer entity = Minecraft.getMinecraft().thePlayer;
                    MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
                    World world = server.worldServers[0];

                }
            }
        });
        this.addSlotToContainer(new Slot(inve, 3, 105, 59) {
            public void onSlotChanged() {
                super.onSlotChanged();
                if (getHasStack()) {
                    EntityPlayer entity = Minecraft.getMinecraft().thePlayer;
                    MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
                    World world = server.worldServers[0];
                }
            }
        });

        // スロットを設定する。
        for (int iy = 0; iy < 3; iy++) {
            for (int ix = 0; ix < 9; ix++) {
                addSlotToContainer(new Slot(player.inventory, ix + (iy * 9) + 9, 8 + (ix * 18), 84 + (iy * 18)));
            }
        }
        for (int ix = 0; ix < 9; ix++) {
            addSlotToContainer(new Slot(player.inventory, ix, 8 + (ix * 18), 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
    //shiftクリック時のアイテムの移動処理の奴
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < 4) {
                if (!this.mergeItemStack(itemstack1, 4, (45 - 4), true)) {// fixes
                    // shiftclick
                    // error
                    return null;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, 4, false)) {
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
}
