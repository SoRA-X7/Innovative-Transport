/*
 * Copyright (c) 2019. Made by SoRA_X7. CC BY-NC-SA 4.0
 */

package warpdrive.innovativetransport.utils;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ModuleInventoryAccessHelper {

    public static ItemStack pullNextItemInInventory(IInventory inventory, int max) {
        for (int i=0; i<inventory.getSizeInventory(); i++) {
            ItemStack stack = inventory.decrStackSize(i,max);
            if (stack != null) {
                return stack;
            }
        }
        return null;
    }

    public static ItemStack pullNextItemInInventoryFiltered(IInventory inventory, Item[] filter, boolean whitelist, int max) {
        for (int i=0; i<inventory.getSizeInventory(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (stack != null) {
                for (Item item:
                     filter) {
                    if (whitelist) {
                        if (stack.getItem() == item) {
                            return inventory.decrStackSize(i,max);
                        }
                    } else {
                        if (stack.getItem() != item) {
                            return inventory.decrStackSize(i,max);
                        }
                    }
                }
            }
        }
        return null;
    }

}
