package solab.innovativetransport.card.extractor;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import solab.innovativetransport.card.EnumCards;
import solab.innovativetransport.card.cardbase.CardBase;
import solab.innovativetransport.card.cardbase.ICardTickable;
import solab.innovativetransport.transporter.ItemTransporter;
import solab.innovativetransport.utils.ModuleInventoryAccessHelper;

public class ExtractorCardMk1 extends CardBase implements ICardTickable {

    private int timer = 0;
    private int interval = 20;
    /**
     * 毎Tick呼び出されます。
     */
    @Override
    public void update() {
        if (pipe.managedInventory != null && !pipe.getHolder().getWorld().isRemote) {
            if (++timer >= interval) {
                timer = 0;
                ItemStack stack = ModuleInventoryAccessHelper.pullNextItemInInventory(pipe.managedInventory,1);
                if (stack != null) {
                    ItemTransporter transporter = new ItemTransporter(stack,pipe.getHolder(), EnumFacing.DOWN, pipe.getRandomExit(EnumFacing.DOWN));
                    pipe.getHolder().inject(transporter);
                }
            }
        }
    }

    @Override
    public EnumCards getCardType() {
        return EnumCards.Extractor;
    }
}
