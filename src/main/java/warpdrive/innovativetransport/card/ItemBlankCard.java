/*
 * Copyright (c) 2019. Made by SoRA_X7. CC BY-NC-SA 4.0
 */

package warpdrive.innovativetransport.card;

import net.minecraft.item.Item;
import warpdrive.innovativetransport.InnovativeTransport;

/**
 * Created by kirihi on 2018/10/08.
 */
public class ItemBlankCard extends Item {
    public ItemBlankCard() {
        super();
        setUnlocalizedName(InnovativeTransport.MODID + ":BlankCard");
        setRegistryName("BlankCard");
        setCreativeTab(InnovativeTransport.tab);
    }

}