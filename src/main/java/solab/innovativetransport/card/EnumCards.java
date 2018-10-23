package solab.innovativetransport.card;

import net.minecraft.util.IStringSerializable;

public enum EnumCards implements IStringSerializable {
    ItemSink,
    Extractor,
    Provider,
    Supplier;

    public static int maxLength() {
        return 4;
    }

    @Override
    public String getName() {
        return this.name();
    }
}
