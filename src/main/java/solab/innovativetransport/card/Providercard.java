package solab.innovativetransport.card;

import net.minecraft.item.Item;
import solab.innovativetransport.InnovativeTransport;

/**
 * Created by kirihi on 2018/10/08.
 */
public class Providercard extends Item {
    public Providercard() {
        super();
        setUnlocalizedName(InnovativeTransport.MODID + "_" + "Providercard");
        setRegistryName("Providercard");
        setCreativeTab(InnovativeTransport.tab);
    }

}