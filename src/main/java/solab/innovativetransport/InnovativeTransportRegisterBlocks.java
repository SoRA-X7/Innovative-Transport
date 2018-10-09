package solab.innovativetransport;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class InnovativeTransportRegisterBlocks {
    public static void registerBlocks(Block[] blocks,boolean isClient) {
        for (Block block:blocks) {
            GameRegistry.register(block);
            Item item = new ItemBlock(block).setRegistryName(block.getRegistryName());
            GameRegistry.register(item);
            if (isClient) {
//                ModelLoader.setCustomModelResourceLocation(item,0,new ModelResourceLocation(block.getRegistryName(),"inventory"));
                ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(block), new ItemMeshDefinition(){
                    public ModelResourceLocation getModelLocation(ItemStack stack){
                        return new ModelResourceLocation(new ResourceLocation(InnovativeTransport.MODID, "transportpipe"), "inventory");
                    }
                });
            }
        }
    }
}
