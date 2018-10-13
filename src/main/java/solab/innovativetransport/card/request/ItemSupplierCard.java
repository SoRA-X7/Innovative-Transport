package solab.innovativetransport.card.request;

import net.minecraft.item.Item;
import solab.innovativetransport.InnovativeTransport;

/**
 * Created by kirihi on 2018/10/08.
 */
public class ItemSupplierCard extends Item {
    public ItemSupplierCard() {
        super();
        setUnlocalizedName(InnovativeTransport.MODID + "_" + "SupplierCard");
        setRegistryName("SupplierCard");
        setCreativeTab(InnovativeTransport.tab);
    }

}