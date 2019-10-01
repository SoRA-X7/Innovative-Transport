/*
 * Copyright (c) 2019. Made by SoRA_X7. CC BY-NC-SA 4.0
 */

package warpdrive.innovativetransport.pipe.render;

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
import warpdrive.innovativetransport.InnovativeTransport;
import warpdrive.innovativetransport.card.BlockDummyCardSlot;
import warpdrive.innovativetransport.pipe.normal.TilePipeHolder;
import warpdrive.innovativetransport.transporter.ItemTransporter;
import warpdrive.innovativetransport.transporter.Transporter;
import warpdrive.innovativetransport.utils.Constants;

import java.util.Map;

public class PipeTESR extends TileEntitySpecialRenderer<TilePipeHolder> {


    @Override
    public void renderTileEntityAt(TilePipeHolder te, double x, double y, double z, float partialTicks, int destroyStage) {
        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();

        // Translate to the location of our tile entity
        GlStateManager.disableRescaleNormal();
        this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

        renderCardSlots(te, x, y, z);
        renderTransporters(te, x, y, z, partialTicks);

        GlStateManager.enableRescaleNormal();
        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }

    private void renderCardSlots(TilePipeHolder te, double x, double y, double z) {
        GlStateManager.pushMatrix();
        GlStateManager.enableLighting();
        RenderHelper.disableStandardItemLighting();
        BlockRendererDispatcher dispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
        CCBlockRendererDispatcher renderer = new CCBlockRendererDispatcher(dispatcher, BlockColors.init());
        GlStateManager.translate(x, y, z);
        GlStateManager.translate(-te.getPos().getX(), -te.getPos().getY(), -te.getPos().getZ());
        if (Minecraft.isAmbientOcclusionEnabled()) {
            GlStateManager.shadeModel(GL11.GL_SMOOTH);
        } else {
            GlStateManager.shadeModel(GL11.GL_FLAT);
        }

        for (Map.Entry<EnumFacing, Constants.EnumConnectionType> entry:
             te.getPipe().connection.entrySet()) {
            if (entry.getValue() == Constants.EnumConnectionType.slot) {
                Tessellator tessellator = Tessellator.getInstance();
                VertexBuffer buffer = tessellator.getBuffer();
                buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);

                renderer.renderBlock(InnovativeTransport.dummyCardSlot.getDefaultState().withProperty(BlockDummyCardSlot.propertyFacing,entry.getKey()),te.getPos(),te.getWorld(),buffer);
                tessellator.draw();
            }
        }

        RenderHelper.enableStandardItemLighting();
        GlStateManager.popMatrix();
    }

    private void renderTransporters(TilePipeHolder te, double x, double y, double z, float partialTicks) {
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enableLighting();
        RenderItem renderer = Minecraft.getMinecraft().getRenderItem();

        for (Transporter tra:
             te.pipe.getItems()) {
            // 毎Tickの位置にTime.deltaTime的な奴を足す
            float delta = tra.progress + tra.speed * partialTicks;
            GlStateManager.pushMatrix();

            GlStateManager.translate(x, y, z);
            GlStateManager.translate(0.5f,0.5f,0.5f);
            if (delta < 0.5f) {
                switch (tra.in) {
                    case DOWN:
                        GlStateManager.translate(0, -0.5 + delta, 0);
                        break;
                    case UP:
                        GlStateManager.translate(0, 0.5 - delta, 0);
                        break;
                    case NORTH:
                        GlStateManager.translate(0, 0, -0.5 + delta);
                        break;
                    case SOUTH:
                        GlStateManager.translate(0, 0, 0.5 - delta);
                        break;
                    case WEST:
                        GlStateManager.translate(-0.5 + delta, 0, 0);
                        break;
                    case EAST:
                        GlStateManager.translate(0.5 - delta, 0, 0);
                        break;
                }
            } else {
                switch (tra.out) {
                    case DOWN:
                        GlStateManager.translate(0, 0.5 - delta, 0);
                        break;
                    case UP:
                        GlStateManager.translate(0, -0.5 + delta, 0);
                        break;
                    case NORTH:
                        GlStateManager.translate(0, 0, 0.5 - delta);
                        break;
                    case SOUTH:
                        GlStateManager.translate(0, 0, -0.5 + delta);
                        break;
                    case WEST:
                        GlStateManager.translate(0.5 - delta, 0, 0);
                        break;
                    case EAST:
                        GlStateManager.translate(-0.5 + delta, 0, 0);
                        break;
                }
            }
            GlStateManager.scale(0.25f,0.25f,0.25f);
            if (tra instanceof ItemTransporter) {
                ItemTransporter itemTransporter = (ItemTransporter)tra;
                renderer.renderItem(itemTransporter.item, ItemCameraTransforms.TransformType.NONE);
            }
            GlStateManager.popMatrix();
        }
    }
}
