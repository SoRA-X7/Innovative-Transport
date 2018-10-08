package solab.innovativetransport;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class InnovativeTransportRegisterItems {

    public static void registerItems(Item[] items, boolean isClient) {
        for (Item item:items) {
            GameRegistry.register(item);
            if (isClient) {
                ModelLoader.setCustomModelResourceLocation(item,0,new ModelResourceLocation(item.getRegistryName(),"tile"));
            }
        }
    }

}
