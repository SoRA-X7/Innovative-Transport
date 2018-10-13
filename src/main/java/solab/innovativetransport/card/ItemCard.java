package solab.innovativetransport.card;

import net.minecraft.item.Item;
import solab.innovativetransport.InnovativeTransport;

public class ItemCard extends Item {

    public EnumCards cardType;

    public ItemCard() {
        super();
        setRegistryName(InnovativeTransport.MODID,"itemcard");
        setUnlocalizedName(InnovativeTransport.MODID + "_itemcard");
        setMaxDamage(EnumCards.maxLength() - 1);
    }

}
