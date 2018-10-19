package solab.innovativetransport.pipe;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class PipeTESR extends TileEntitySpecialRenderer<TilePipeHolder> {
    @Override
    public void renderTileEntityAt(TilePipeHolder te, double x, double y, double z, float partialTicks, int destroyStage) {
        super.renderTileEntityAt(te,x,y,z,partialTicks,destroyStage);
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5d, y + 0.5d, z + 0.5d);
        GlStateManager.scale(0.75f,0.75f,0.75f);
        Minecraft.getMinecraft().getRenderItem().renderItem(new ItemStack(Items.APPLE), ItemCameraTransforms.TransformType.GROUND);
        GlStateManager.popMatrix();
    }
}
