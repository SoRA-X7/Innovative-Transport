package solab.innovativetransport.pipe;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

import javax.annotation.Nullable;

public class TilePipeHolder extends TileEntity implements IPipeHolder {

    public Pipe pipe;

    @Override
    public IPipe getPipe() {
        return pipe;
    }

    @Nullable
    @Override
    public TileEntity getNeighborTile(EnumFacing facing) {
        return getWorld().getTileEntity(getPos().offset(facing));
    }
}
