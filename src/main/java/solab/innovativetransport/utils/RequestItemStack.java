package solab.innovativetransport.utils;

import net.minecraft.item.Item;

public class RequestItemStack {
    public Item item;
    public int stackSize;

    public RequestItemStack(Item itemIn, int number) {
        this.item = itemIn;
        this.stackSize = number;
    }
}
