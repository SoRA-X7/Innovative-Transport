package solab.innovativetransport.transporter;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import solab.innovativetransport.pipe.Pipe;
import solab.innovativetransport.pipe.TilePipe;

public class Transporter {

    public TilePipe current;

    public Pipe getNextPipe(EnumFacing output) {
        TileEntity tile = current.getTile(output);
        if (tile instanceof TilePipe) {
            return ((TilePipe) tile).pipe;
        }
        return null;
    }
}
