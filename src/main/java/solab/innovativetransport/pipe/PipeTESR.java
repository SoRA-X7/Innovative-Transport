package solab.innovativetransport.pipe;

import codechicken.lib.render.block.CCBlockRendererDispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import org.lwjgl.opengl.GL11;
import solab.innovativetransport.InnovativeTransport;
import solab.innovativetransport.card.BlockDummyCardSlot;
import solab.innovativetransport.transporter.ItemTransporter;
import solab.innovativetransport.transporter.Transporter;

import java.util.Map;

public class PipeTESR extends TileEntitySpecialRenderer<TilePipeHolder> {
    @Override
    public void renderTileEntityAt(TilePipeHolder te, double x, double y, double z, float partialTicks, int destroyStage) {
        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();

        // Translate to the location of our tile entity
        GlStateManager.translate(x, y, z);
        GlStateManager.disableRescaleNormal();
        this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

        renderCardSlots(te, x, y, z);
        renderTransporters(te, x, y, z);

        GlStateManager.enableRescaleNormal();
        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }

    private void renderCardSlots(TilePipeHolder te, double x, double y, double z) {
//        System.out.println("Render Card Slots");
        GlStateManager.pushMatrix();
        GlStateManager.enableLighting();
        RenderHelper.disableStandardItemLighting();
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
//        System.out.println("Render Transporters");
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enableLighting();
        System.out.println(te.pipe.transporters);

        for (Transporter tra:
             te.pipe.transporters) {
            GlStateManager.pushMatrix();
//            GlStateManager.translate(te.getPos().getX(), te.getPos().getY(), te.getPos().getZ());
            GlStateManager.translate(0.5f,0.4f,0.5f);
            System.out.println(tra.progress);
            if (tra.progress < 0.5f) {
                switch (tra.in) {
                    case DOWN:
                        GlStateManager.translate(0,-0.5+tra.progress,0);
                        break;
                    case UP:
                        GlStateManager.translate(0,0.5-tra.progress,0);
                        break;
                    case NORTH:
                        GlStateManager.translate(0,0,0.5-tra.progress);
                        break;
                    case SOUTH:
                        GlStateManager.translate(0,0,-0.5+tra.progress);
                        break;
                    case WEST:
                        GlStateManager.translate(-0.5+tra.progress,0,0);
                        break;
                    case EAST:
                        GlStateManager.translate(0.5-tra.progress,0,0);
                        break;
                }
            } else {
                switch (tra.in) {
                    case DOWN:
                        GlStateManager.translate(0,0.5-tra.progress,0);
                        break;
                    case UP:
                        GlStateManager.translate(0,-0.5+tra.progress,0);
                        break;
                    case NORTH:
                        GlStateManager.translate(0,0,-0.5+tra.progress);
                        break;
                    case SOUTH:
                        GlStateManager.translate(0,0,0.5-tra.progress);
                        break;
                    case WEST:
                        GlStateManager.translate(0.5-tra.progress,0,0);
                        break;
                    case EAST:
                        GlStateManager.translate(-0.5+tra.progress,0,0);
                        break;
                }
            }
            GlStateManager.scale(0.75f,0.75f,0.75f);
            if (tra instanceof ItemTransporter) {
                ItemTransporter itemTransporter = (ItemTransporter)tra;
                Minecraft.getMinecraft().getRenderItem().renderItem(itemTransporter.item, ItemCameraTransforms.TransformType.NONE);
            }
            GlStateManager.popMatrix();
        }
    }
}
