package solab.innovativetransport;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import solab.innovativetransport.card.ItemBlankCard;
import solab.innovativetransport.card.ItemCardSlot;
import solab.innovativetransport.card.extractor.ItemExtractorCard;
import solab.innovativetransport.card.provider.ItemProviderCard;
import solab.innovativetransport.card.request.ItemSupplierCard;
import solab.innovativetransport.pipe.BlockPipe;
import solab.innovativetransport.pipe.TilePipeHolder;

@Mod(modid = InnovativeTransport.MODID)
public class InnovativeTransport {
    public static final String MODID = "innovativetransport";

    public static final CreativeTabs tab = new CreativeTabs("Innovative Transport") {
        @Override
        public Item getTabIconItem() {
            return Items.ARROW;
        }
    };
    public static final Block[] blocks = {
            new BlockPipe()
    };
    public static final Item[] items = {
            new Debugger(),
            new ItemCardSlot(),
            new ItemBlankCard(),
            new ItemExtractorCard(),
            new ItemProviderCard(),
            new ItemSupplierCard()
    };

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        InnovativeTransportRegisterBlocks.registerBlocks(blocks,event.getSide().isClient());
        GameRegistry.registerTileEntity(TilePipeHolder.class,MODID + ":transportpipe");
        InnovativeTransportRegisterItems.registerItems(items,event.getSide().isClient());
    }
}
