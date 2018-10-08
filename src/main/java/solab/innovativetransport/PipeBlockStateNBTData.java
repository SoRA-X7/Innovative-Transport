package solab.innovativetransport;

import net.minecraft.nbt.NBTTagCompound;

public class PipeBlockStateNBTData {
    public boolean u;
    public boolean d;
    public boolean n;
    public boolean s;
    public boolean e;
    public boolean w;

    public PipeBlockStateNBTData(NBTTagCompound nbtTagCompound) {
        this.u = nbtTagCompound.getBoolean("connection_up");
        this.d = nbtTagCompound.getBoolean("connection_down");
        this.n = nbtTagCompound.getBoolean("connection_north");
        this.s = nbtTagCompound.getBoolean("connection_south");
        this.e = nbtTagCompound.getBoolean("connection_east");
        this.w = nbtTagCompound.getBoolean("connection_west");
    }
}
