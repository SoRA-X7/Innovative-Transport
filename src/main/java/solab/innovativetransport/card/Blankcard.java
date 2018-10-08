package solab.innovativetransport.card;

import net.minecraft.item.Item;
import solab.innovativetransport.InnovativeTransport;

/**
 * Created by kirihi on 2018/10/08.
 */
public class Blankcard extends Item {
    public Blankcard() {
        super();
        setUnlocalizedName(InnovativeTransport.MODID + "_" + "Blankcard");
        setRegistryName("Blankcard");
        setCreativeTab(InnovativeTransport.tab);
    }

}