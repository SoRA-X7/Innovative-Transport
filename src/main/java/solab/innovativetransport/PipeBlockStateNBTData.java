package solab.innovativetransport;

import net.minecraft.nbt.NBTTagCompound;

public class PipeBlockStateNBTData {
    public String u;
    public String d;
    public String n;
    public String s;
    public String e;
    public String w;

    public PipeBlockStateNBTData(NBTTagCompound nbtTagCompound) {
        this.u = nbtTagCompound.getString("connection_up");
        this.d = nbtTagCompound.getString("connection_down");
        this.n = nbtTagCompound.getString("connection_north");
        this.s = nbtTagCompound.getString("connection_south");
        this.e = nbtTagCompound.getString("connection_east");
        this.w = nbtTagCompound.getString("connection_west");
    }
}
