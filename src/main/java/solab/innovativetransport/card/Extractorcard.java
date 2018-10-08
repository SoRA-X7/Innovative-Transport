package solab.innovativetransport.card;

import net.minecraft.item.Item;
import solab.innovativetransport.InnovativeTransport;

/**
 * Created by kirihi on 2018/10/08.
 */
public class Extractorcard extends Item {
    public Extractorcard() {
        super();
        setUnlocalizedName(InnovativeTransport.MODID + "_" + "Extractorcard");
        setRegistryName("Extractorcard");
        setCreativeTab(InnovativeTransport.tab);
    }

}