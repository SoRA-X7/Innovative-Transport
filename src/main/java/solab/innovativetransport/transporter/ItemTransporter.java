package solab.innovativetransport.transporter;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import solab.innovativetransport.pipe.normal.TilePipeHolder;

public class ItemTransporter extends Transporter {
    public ItemStack item;

    public ItemTransporter(ItemStack item, TilePipeHolder holder, EnumFacing in, EnumFacing out) {
        super(holder,in,out);
        this.item = item;
    }

    public ItemTransporter(TilePipeHolder current, NBTTagCompound childCompound) {
        super(current,childCompound);
        this.readFromNBT(childCompound);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        return item.writeToNBT(super.writeToNBT(compound));
    }
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        item = new ItemStack(new ItemBlock(Blocks.STONE));
        item.readFromNBT(compound);
    }
}
