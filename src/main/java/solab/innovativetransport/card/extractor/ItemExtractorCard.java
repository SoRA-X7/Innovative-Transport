package solab.innovativetransport.card.extractor;

import net.minecraft.item.Item;
import solab.innovativetransport.InnovativeTransport;

/**
 * Created by kirihi on 2018/10/08.
 */
public class ItemExtractorCard extends Item {
    public ItemExtractorCard() {
        super();
        setUnlocalizedName(InnovativeTransport.MODID + "_" + "ExtractorCard");
        setRegistryName("ExtractorCard");
        setCreativeTab(InnovativeTransport.tab);
    }

}