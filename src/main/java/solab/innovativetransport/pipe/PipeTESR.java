package solab.innovativetransport.pipe;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class PipeTESR extends TileEntitySpecialRenderer<TilePipeHolder> {
    @Override
    public void renderTileEntityAt(TilePipeHolder te, double x, double y, double z, float partialTicks, int destroyStage) {
        super.renderTileEntityAt(te,x,y,z,partialTicks,destroyStage);
        GlStateManager.pushMatrix();
        Minecraft mc = Minecraft.getMinecraft();

        GlStateManager.translate(x, y, z);
//        BlockRendererDispatcher dispatcher = mc.getBlockRendererDispatcher();
//        IBakedModel model = dispatcher.getModelForState(BlockPipe.INSTANCE.getDefaultState())
//        dispatcher.getBlockModelRenderer().renderModel(te.getWorld());


        GlStateManager.translate(x + 0.5d, y + 0.4d, z + 0.5d);
        GlStateManager.scale(0.75f,0.75f,0.75f);
        mc.getRenderItem().renderItem(new ItemStack(Blocks.DIAMOND_BLOCK), ItemCameraTransforms.TransformType.GROUND);

        GlStateManager.popMatrix();
    }
}
