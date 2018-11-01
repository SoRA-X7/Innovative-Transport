package solab.innovativetransport.pipe.attachment.cardslot.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerCardSlot extends Container {

    public ContainerCardSlot(int x, int y, int z, EntityPlayer player) {
        // スロットを設定する。
        for (int iy = 0; iy < 3; iy++) {
            for (int ix = 0; ix < 9; ix++) {
                addSlotToContainer(new Slot(player.inventory, ix + (iy * 9) + 9, 8 + (ix * 18), 84 + (iy * 18)));
            }
        }
        for (int ix = 0; ix < 9; ix++) {
            addSlotToContainer(new Slot(player.inventory, ix, 8 + (ix * 18), 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
}
