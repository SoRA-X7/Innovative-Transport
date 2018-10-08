package solab.innovativetransport;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class InnovativeTransportRegisterBlocks {
    public static void registerBlocks(Block[] blocks,boolean isClient) {
        for (Block block:blocks) {
            GameRegistry.register(block);
            Item item = new ItemBlock(block).setRegistryName(block.getRegistryName());
            GameRegistry.register(item);
            if (isClient) {
                ModelLoader.setCustomModelResourceLocation(item,0,new ModelResourceLocation(block.getRegistryName(),"inventory"));
            }
        }
    }
}
