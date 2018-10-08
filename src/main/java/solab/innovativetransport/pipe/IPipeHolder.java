package solab.innovativetransport.pipe;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public interface IPipeHolder {
    World getWorld();
    BlockPos getPos();
    TileEntity getTile();
    IPipe getPipe();

    @Nullable
    TileEntity getNeighborTile(EnumFacing facing);
}
