package solab.innovativetransport;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import solab.innovativetransport.card.ItemCard;
import solab.innovativetransport.card.ItemCardSlot;
import solab.innovativetransport.pipe.BlockPipe;
import solab.innovativetransport.pipe.TilePipeHolder;

@Mod(modid = InnovativeTransport.MODID,dependencies = InnovativeTransport.DEPENDENCIES)
public class InnovativeTransport {
    public static final String MODID = "innovativetransport";
    public static final String DEPENDENCIES = "required-after:CodeChickenLib";

    public static final CreativeTabs tab = new CreativeTabs("Innovative Transport") {
        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(ITBlocks.PIPE);
        }
    };

    public static class ITBlocks {
        public static BlockPipe PIPE = new BlockPipe();
        public static Block[] getBlocks() {
            Block blocks[] = {
                    PIPE
            };
            return blocks;
        }
    }
    public static class ITItems {
        public static Debugger DEBUGGER = new Debugger();
        public static ItemCard ITEMCARD = new ItemCard();
        public static ItemCardSlot CARDSLOT = new ItemCardSlot();
        public static Item[] getItems() {
            Item items[] = {
                    DEBUGGER,
                    ITEMCARD,
                    CARDSLOT
            };
            return items;
        }
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        InnovativeTransportRegisterBlocks.registerBlocks(ITBlocks.getBlocks(),event.getSide().isClient());
        GameRegistry.registerTileEntity(TilePipeHolder.class,MODID + ":transportpipe");
        InnovativeTransportRegisterItems.registerItems(ITItems.getItems(),event.getSide().isClient());
    }
}
