package solab.innovativetransport;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import solab.innovativetransport.card.*;
import solab.innovativetransport.pipe.BlockPipe;
import solab.innovativetransport.pipe.TilePipe;

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
            new Blankcard(),
            new Extractorcard(),
            new Providercard(),
            new Suppliercard()
    };

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        InnovativeTransportRegisterBlocks.registerBlocks(blocks,event.getSide().isClient());
        GameRegistry.registerTileEntity(TilePipe.class,MODID + ":transportpipe");
        InnovativeTransportRegisterItems.registerItems(items,event.getSide().isClient());
    }
}
