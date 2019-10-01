/*
 * Copyright (c) 2019. Made by SoRA_X7. CC BY-NC-SA 4.0
 */

package warpdrive.innovativetransport.utils;

import net.minecraft.item.Item;

public class RequestItemStack {
    public Item item;
    public int stackSize;

    public RequestItemStack(Item itemIn, int number) {
        this.item = itemIn;
        this.stackSize = number;
    }
}
