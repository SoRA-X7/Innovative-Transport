package solab.innovativetransport;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import solab.innovativetransport.card.BlockDummyCardSlot;
import solab.innovativetransport.card.ItemCard;
import solab.innovativetransport.item.ItemDebugger;
import solab.innovativetransport.pipe.attachment.cardslot.ItemCardSlot;
import solab.innovativetransport.pipe.normal.BlockPipe;
import solab.innovativetransport.pipe.normal.TilePipeHolder;
import solab.innovativetransport.pipe.render.PipeTESR;

@Mod(modid = InnovativeTransport.MODID,dependencies = InnovativeTransport.DEPENDENCIES)
public class InnovativeTransport {
    public static final String MODID = "innovativetransport";
    static final String DEPENDENCIES = "required-after:CodeChickenLib";

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
            ItemDebugger.INSTANCE,
            ItemCard.INSTANCE,
            ItemCardSlot.INSTANCE
    };
    public static final BlockDummyCardSlot dummyCardSlot = new BlockDummyCardSlot();

    private static Block[] getBlocks() {
        return blocks;
    }
    private static void registerBlocks(Block[] blocks, boolean isClient) {
        for (Block block:blocks) {
            GameRegistry.register(block);
            Item item = new ItemBlock(block).setRegistryName(block.getRegistryName());
            GameRegistry.register(item);
            if (isClient) {
                ModelLoader.setCustomModelResourceLocation(item,0,new ModelResourceLocation(block.getRegistryName(),"inventory"));
            }
        }
    }

    private static Item[] getItems() {
        return items;
    }
    private static void registerItems(Item[] items, boolean isClient) {
        for (Item item:items) {
            GameRegistry.register(item);
            if (isClient) {
                ModelLoader.setCustomModelResourceLocation(item,0,new ModelResourceLocation(item.getRegistryName(),"inventory"));
            }
        }
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        GameRegistry.register(dummyCardSlot);
        registerBlocks(getBlocks(),event.getSide().isClient());
        GameRegistry.registerTileEntity(TilePipeHolder.class,MODID + ":transportpipe");
        if (event.getSide().isClient()) {
            ClientRegistry.bindTileEntitySpecialRenderer(TilePipeHolder.class,new PipeTESR());
        }
        registerItems(getItems(),event.getSide().isClient());
    }

    public enum EnumCards implements IStringSerializable {
        ItemSink,
        Extractor,
        Provider,
        Supplier;

        public static int maxLength() {
            return 4;
        }

        @Override
        public String getName() {
            return this.name();
        }
    }
}
