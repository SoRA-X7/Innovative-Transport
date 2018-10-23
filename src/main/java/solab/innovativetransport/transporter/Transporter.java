package solab.innovativetransport.transporter;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import solab.innovativetransport.pipe.TilePipeHolder;

public class Transporter {
    private static int nextFreeId = 0;
    public int id;
    public float progress = 0;
    public EnumFacing in;
    public EnumFacing out;
    public TilePipeHolder current;
    public TilePipeHolder next;
    public float speed = 0.01f;

    public Transporter(TilePipeHolder current, EnumFacing in, EnumFacing out) {
        this.id = getNextFreeId();
        this.current = current;
        this.in = in;
        this.out = out;
        this.next = this.current.getNextPipeHolder(this.out);
    }

    public Transporter(TilePipeHolder current, NBTTagCompound childCompound) {
        this.readFromNBT(childCompound);
        this.id = getNextFreeId();
        this.current = current;
        this.next = this.current.getNextPipeHolder(this.out);
    }

    public static int getNextFreeId() {
        return ++Transporter.nextFreeId;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setFloat("IT_Tra_progress",progress);
        compound.setFloat("IT_Tra_speed",speed);
        compound.setByte("IT_Tra_in",(byte)in.ordinal());
        compound.setByte("IT_Tra_out",(byte)out.ordinal());
        return compound;
    }
    public void readFromNBT(NBTTagCompound compound) {
        progress = compound.getFloat("IT_Tra_progress");
        speed = compound.getFloat("IT_Tra_speed");
        in = EnumFacing.values()[compound.getByte("IT_Tra_in")];
        out = EnumFacing.values()[compound.getByte("IT_Tra_out")];
    }
}
