package solab.innovativetransport.pipe;

import com.google.common.base.Function;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.animation.FastTESR;
import org.lwjgl.opengl.GL11;
import solab.innovativetransport.InnovativeTransport;
import solab.innovativetransport.transporter.ItemTransporter;

import javax.annotation.Nullable;

public class PipeSpecialRenderer extends FastTESR<TilePipe> {

    @Override
    public void renderTileEntityFast(TilePipe te, double x, double y, double z, float partialTicks, int destroyStage, VertexBuffer VertexBuffer) {
        if (te instanceof TilePipe) {
            TilePipe pipe = (TilePipe)te;
            if (pipe.pipe == null) return;

            //Render Pipe
            IBakedModel bakedModel;
            try {
                bakedModel = ModelLoaderRegistry.getModel(new ResourceLocation(InnovativeTransport.MODID,"pipe_side_off")).bake(null, new VertexFormat(), new Function<ResourceLocation, TextureAtlasSprite>() {
                    @Nullable
                    @Override
                    public TextureAtlasSprite apply(@Nullable ResourceLocation input) {
                        return null;
                    }
                });

                BlockModelRenderer renderer = new BlockModelRenderer(BlockColors.init());
                renderer.renderModel(te.getWorld(),bakedModel,te.getWorld().getBlockState(te.getPos()),te.getPos(),VertexBuffer,false);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //Render Items/Fluids
            renderSolids(pipe.pipe,x,y,z,partialTicks);
        }
    }

    void renderSolids(Pipe pipe, double x, double y, double z, float partialTicks) {
        GL11.glPushMatrix();

        for (ItemTransporter transporter:pipe.items) {

        }
    }
}
