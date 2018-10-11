package solab.innovativetransport.card.itemsink;

import net.minecraft.item.Item;
import solab.innovativetransport.InnovativeTransport;

public class ItemItemSinkCard extends Item {
    public ItemItemSinkCard() {
        super();
        setUnlocalizedName(InnovativeTransport.MODID + "_" + "ItemSinkCard");
        setRegistryName("ItemSinkCard");
        setCreativeTab(InnovativeTransport.tab);
    }
}
