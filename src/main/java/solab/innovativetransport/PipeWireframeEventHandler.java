package solab.innovativetransport;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PipeWireframeEventHandler {

    @SubscribeEvent
    public void drawSelectionBox(DrawBlockHighlightEvent event) {
        System.out.println("drawSelectionBox");
        RayTraceResult trace = event.getTarget();
        if (trace != null || trace.typeOfHit == RayTraceResult.Type.BLOCK) {
            World world = event.getPlayer().worldObj;
            IBlockState state = world.getBlockState(trace.getBlockPos());
            if (state.getBlock() == InnovativeTransport.ITBlocks.PIPE) {
                event.getContext().drawSelectionBox(event.getPlayer(),trace,0,event.getPartialTicks());
                event.setCanceled(true);
            }
        }
    }
}
