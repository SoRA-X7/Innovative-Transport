package solab.innovativetransport;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import solab.innovativetransport.card.ItemCard;
import solab.innovativetransport.card.ItemCardSlot;
import solab.innovativetransport.item.Debugger;
import solab.innovativetransport.pipe.BlockPipe;
import solab.innovativetransport.pipe.TilePipeHolder;

@Mod(modid = InnovativeTransport.MODID,dependencies = InnovativeTransport.DEPENDENCIES)
public class InnovativeTransport {
    public static final String MODID = "innovativetransport";
    public static final String DEPENDENCIES = "required-after:CodeChickenLib";

    public static final CreativeTabs tab = new CreativeTabs("Innovative Transport") {
        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(BlockPipe.INSTANCE);
        }
    };

    private static Block blocks[] = {
            BlockPipe.INSTANCE
    };
    private static Item items[] = {
            Debugger.INSTANCE,
            ItemCard.INSTANCE,
            ItemCardSlot.INSTANCE
    };

    public static Block[] getBlocks() {
        return blocks;
    }
    public static Item[] getItems() {
        return items;
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        InnovativeTransportRegisterBlocks.registerBlocks(getBlocks(),event.getSide().isClient());
        GameRegistry.registerTileEntity(TilePipeHolder.class,MODID + ":transportpipe");
        InnovativeTransportRegisterItems.registerItems(getItems(),event.getSide().isClient());
    }
}
