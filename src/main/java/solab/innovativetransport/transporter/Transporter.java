package solab.innovativetransport.transporter;

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
    public float speed = 0.1f;

    public Transporter(TilePipeHolder current, EnumFacing in, EnumFacing out) {
        this.id = getNextFreeId();
    }

    public static int getNextFreeId() {
        return ++Transporter.nextFreeId;
    }

    public void move() {
        progress += speed;
        if (progress >= 1f) {
            current = next;
            in = out.getOpposite();
            out = current.getPipe().getRandomExit(in);
            next = current.getNextPipeHolder(out);
            progress -= 1f;
        }
    }
}
