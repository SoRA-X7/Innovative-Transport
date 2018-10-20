package solab.innovativetransport.pipe;

import codechicken.lib.render.block.CCBlockRendererDispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ModelManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import org.lwjgl.opengl.GL11;
import solab.innovativetransport.InnovativeTransport;
import solab.innovativetransport.card.BlockDummyCardSlot;

import java.util.Map;

public class PipeTESR extends TileEntitySpecialRenderer<TilePipeHolder> {
    @Override
    public void renderTileEntityAt(TilePipeHolder te, double x, double y, double z, float partialTicks, int destroyStage) {
        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();

        // Translate to the location of our tile entity
        GlStateManager.translate(x, y, z);
        GlStateManager.disableRescaleNormal();

        renderCardSlots(te, x, y, z);
        renderTransporters(te, x, y, z);

        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }

    private void renderCardSlots(TilePipeHolder te, double x, double y, double z) {
        GlStateManager.enableLighting();
        GlStateManager.pushMatrix();
        GlStateManager.translate(-te.getPos().getX(), -te.getPos().getY(), -te.getPos().getZ());
        if (Minecraft.isAmbientOcclusionEnabled()) {
            GlStateManager.shadeModel(GL11.GL_SMOOTH);
        } else {
            GlStateManager.shadeModel(GL11.GL_FLAT);
        }

        for (Map.Entry<EnumFacing,EnumConnectionType> entry:
             te.getPipe().connection.entrySet()) {
            if (entry.getValue() == EnumConnectionType.slot) {
                Tessellator tessellator = Tessellator.getInstance();
                VertexBuffer buffer = tessellator.getBuffer();
                buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);

                BlockRendererDispatcher dispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
                CCBlockRendererDispatcher renderer = new CCBlockRendererDispatcher(dispatcher,BlockColors.init());
                renderer.renderBlock(InnovativeTransport.dummyCardSlot.getDefaultState().withProperty(BlockDummyCardSlot.propertyFacing,entry.getKey()),te.getPos(),te.getWorld(),buffer);
                tessellator.draw();
            }
        }

        RenderHelper.enableStandardItemLighting();
        GlStateManager.popMatrix();
    }

    private void renderTransporters(TilePipeHolder te, double x, double y, double z) {
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enableLighting();
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.5d,0.4d,0.5d);
        GlStateManager.scale(0.75f,0.75f,0.75f);
        Minecraft.getMinecraft().getRenderItem().renderItem(new ItemStack(Blocks.DIAMOND_BLOCK), ItemCameraTransforms.TransformType.GROUND);
        GlStateManager.popMatrix();
    }
}
