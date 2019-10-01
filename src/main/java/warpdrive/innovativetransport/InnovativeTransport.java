/*
 * Copyright (c) 2019. Made by SoRA_X7. CC BY-NC-SA 4.0
 */

package warpdrive.innovativetransport;

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
import warpdrive.innovativetransport.card.BlockDummyCardSlot;
import warpdrive.innovativetransport.card.ItemCard;
import warpdrive.innovativetransport.item.ItemDebugger;
import warpdrive.innovativetransport.item.ItemPanelRemover;
import warpdrive.innovativetransport.pipe.attachment.cardslot.ItemCardSlot;
import warpdrive.innovativetransport.pipe.normal.BlockPipe;
import warpdrive.innovativetransport.pipe.normal.TilePipeHolder;
import warpdrive.innovativetransport.pipe.render.PipeTESR;
import warpdrive.innovativetransport.routing.BlockQuantumCore;
import warpdrive.innovativetransport.routing.TileQuantumCore;

import java.util.ArrayList;
import java.util.List;

@Mod(modid = InnovativeTransport.MODID,dependencies = InnovativeTransport.DEPENDENCIES)
public class InnovativeTransport {
    public static final String MODID = "innovativetransport";
    static final String DEPENDENCIES = "required-after:CodeChickenLib";

    @Mod.Instance(MODID)
    public static InnovativeTransport INSTANCE;

    public InnovativeTransport() {
        System.out.println("Hello Minecraft!");
    }

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
        items.add(new ItemPanelRemover());

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
