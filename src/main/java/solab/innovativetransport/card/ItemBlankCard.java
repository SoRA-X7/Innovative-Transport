package solab.innovativetransport.card;

import net.minecraft.item.Item;
import solab.innovativetransport.InnovativeTransport;

/**
 * Created by kirihi on 2018/10/08.
 */
public class ItemBlankCard extends Item {
    public ItemBlankCard() {
        super();
        setUnlocalizedName(InnovativeTransport.MODID + "_" + "BlankCard");
        setRegistryName("BlankCard");
        setCreativeTab(InnovativeTransport.tab);
    }

}