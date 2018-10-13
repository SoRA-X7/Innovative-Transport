package solab.innovativetransport.card.provider;

import net.minecraft.item.Item;
import solab.innovativetransport.InnovativeTransport;

/**
 * Created by kirihi on 2018/10/08.
 */
public class ItemProviderCard extends Item {
    public ItemProviderCard() {
        super();
        setUnlocalizedName(InnovativeTransport.MODID + "_" + "ProviderCard");
        setRegistryName("ProviderCard");
        setCreativeTab(InnovativeTransport.tab);
    }

}