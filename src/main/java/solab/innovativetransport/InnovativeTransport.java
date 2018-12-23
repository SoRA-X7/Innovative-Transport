package solab.innovativetransport;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import solab.innovativetransport.card.BlockDummyCardSlot;
import solab.innovativetransport.card.ItemCard;
import solab.innovativetransport.item.ItemDebugger;
import solab.innovativetransport.pipe.attachment.cardslot.ItemCardSlot;
import solab.innovativetransport.pipe.normal.BlockPipe;
import solab.innovativetransport.pipe.normal.TilePipeHolder;
import solab.innovativetransport.pipe.render.PipeTESR;
import solab.innovativetransport.routing.BlockQuantumCore;
import solab.innovativetransport.routing.TileQuantumCore;

import java.util.ArrayList;
import java.util.List;

@Mod(modid = InnovativeTransport.MODID,dependencies = InnovativeTransport.DEPENDENCIES)
public class InnovativeTransport {
    public static final String MODID = "innovativetransport";
    static final String DEPENDENCIES = "required-after:CodeChickenLib";

    @Mod.Instance(MODID)
    public static InnovativeTransport INSTANCE;

    public static final CreativeTabs tab = new CreativeTabs("Innovative Transport") {
        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(BlockPipe.INSTANCE);
        }
    };

    private static List<Block> blocks = new ArrayList<>();
    private static List<Item> items = new ArrayList<>();
    public static final BlockDummyCardSlot dummyCardSlot = new BlockDummyCardSlot();

    public static Block[] getBlocks() {
        return blocks.toArray(new Block[0]);
    }

    private static void registerBlocks(boolean isClient) {
        blocks.add(new BlockPipe());
        blocks.add(new BlockQuantumCore());

        for (Block block:blocks) {
            System.out.println("Registering " + block.getRegistryName().toString());
            GameRegistry.register(block);
            Item item = new ItemBlock(block).setRegistryName(block.getRegistryName());
            GameRegistry.register(item);
            if (isClient) {
                ModelLoader.setCustomModelResourceLocation(item,0,new ModelResourceLocation(block.getRegistryName(),"inventory"));
            }
        }
    }

    public static Item[] getItems() {
        return items.toArray(new Item[0]);
    }

    private static void registerItems(boolean isClient) {
        items.add(new ItemDebugger());
        items.add(new ItemCardSlot());
        items.add(new ItemCard());

        for (Item item:items) {
            System.out.println("Registering " + item.getRegistryName().toString());
            GameRegistry.register(item);
            if (isClient) {
                ModelLoader.setCustomModelResourceLocation(item,0,new ModelResourceLocation(item.getRegistryName(),"inventory"));
            }
        }
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        GameRegistry.register(dummyCardSlot);
        registerBlocks(event.getSide().isClient());
        GameRegistry.registerTileEntity(TilePipeHolder.class,MODID + ":transportpipe");
        if (event.getSide().isClient()) {
            ClientRegistry.bindTileEntitySpecialRenderer(TilePipeHolder.class,new PipeTESR());
        }
        GameRegistry.registerTileEntity(TileQuantumCore.class,MODID + ":quantumcore");
        registerItems(event.getSide().isClient());
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(this,new ITGuiHandler());
    }
}
