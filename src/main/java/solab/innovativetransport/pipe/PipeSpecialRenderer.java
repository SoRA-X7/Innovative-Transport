package solab.innovativetransport.pipe;

import net.minecraft.client.renderer.VertexBuffer;
import net.minecraftforge.client.model.animation.FastTESR;
import org.lwjgl.opengl.GL11;
import solab.innovativetransport.transporter.ItemTransporter;

public class PipeSpecialRenderer extends FastTESR<TilePipe> {

    @Override
    public void renderTileEntityFast(TilePipe te, double x, double y, double z, float partialTicks, int destroyStage, VertexBuffer VertexBuffer) {
        if (te instanceof TilePipe) {
            TilePipe pipe = (TilePipe)te;
            if (pipe.pipe == null) return;

        }
    }

    void renderSolids(Pipe pipe, double x, double y, double z, float partialTicks) {
        GL11.glPushMatrix();

        for (ItemTransporter transporter:pipe.items) {

        }
    }
}
