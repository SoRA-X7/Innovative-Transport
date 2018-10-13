package solab.innovativetransport.transporter;

import net.minecraft.item.ItemStack;

public class ItemTransporter extends Transporter {
    public ItemStack item;

    public ItemTransporter(ItemStack item) {
        super();
        this.item = item;
    }
}
