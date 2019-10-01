/*
 * Copyright (c) 2019. Made by SoRA_X7. CC BY-NC-SA 4.0
 */

package warpdrive.innovativetransport.pipe.attachment.cardslot.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import warpdrive.innovativetransport.card.cardbase.CardBase;
import warpdrive.innovativetransport.pipe.attachment.cardslot.CardSlot;
import warpdrive.innovativetransport.pipe.attachment.cardslot.InventoryCardSlot;
import warpdrive.innovativetransport.pipe.normal.TilePipeHolder;
import warpdrive.innovativetransport.utils.Constants;

public class ContainerCardSlot extends Container {
    public static IInventory inventoryCardSlot;
    //スロットの追加、処理
    public ContainerCardSlot(World world, int x, int y, int z, EntityPlayer player) {
        TilePipeHolder holder = (TilePipeHolder) world.getTileEntity(new BlockPos(x, y, z));
        CardSlot cardSlot = holder.pipe.getCardSlot(player.rayTrace(4, 0).sideHit);
        inventoryCardSlot = new InventoryCardSlot(cardSlot);

        // カード用スロット
        int[] slotX = {55, 105};
        int[] slotY = {9, 59};
        for (int ix = 0; ix < 2; ix++) {
            for (int iy = 0; iy < 2; iy++) {
                this.addSlotToContainer(new Slot(inventoryCardSlot, ix * 2 + iy, slotX[ix], slotY[iy]) {
                    public void onSlotChanged() {
                        super.onSlotChanged();
                        if (getHasStack()) {
                            EntityPlayer entity = Minecraft.getMinecraft().thePlayer;
                            MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
                            World world = server.worldServers[0];
                            NBTTagCompound compound = getStack().getTagCompound();
                            cardSlot.insert(
                                    CardBase.getCardFromType(
                                            Constants.EnumCards.values()[compound.getInteger("IT_Card_Type")],
                                            compound.getInteger("IT_Card_Tier")));
                        }
                    }
                });
            }
        }

        // サバイバルインベントリ
        for (int iy = 0; iy < 3; iy++) {
            for (int ix = 0; ix < 9; ix++) {
                addSlotToContainer(new Slot(player.inventory, ix + (iy * 9) + 9, 8 + (ix * 18), 84 + (iy * 18)));
            }
        }
        // ホットバー
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
        Slot slot = this.inventorySlots.get(index);

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
                slot.putStack(null);
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
