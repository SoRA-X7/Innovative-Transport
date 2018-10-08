package solab.innovativetransport.card;

import net.minecraft.item.Item;
import solab.innovativetransport.InnovativeTransport;

/**
 * Created by kirihi on 2018/10/08.
 */
public class Suppliercard extends Item {
    public Suppliercard() {
        super();
        setUnlocalizedName(InnovativeTransport.MODID + "_" + "Suppliercard");
        setRegistryName("Suppliercard");
        setCreativeTab(InnovativeTransport.tab);
    }

}