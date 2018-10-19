package solab.innovativetransport.transporter;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import solab.innovativetransport.pipe.TilePipeHolder;

public class ItemTransporter extends Transporter {
    public ItemStack item;

    public ItemTransporter(ItemStack item, TilePipeHolder holder, EnumFacing in, EnumFacing out) {
        super(holder,in,out);
        this.item = item;
    }
}
