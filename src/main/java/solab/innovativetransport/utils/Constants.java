package solab.innovativetransport.utils;

import net.minecraft.util.IStringSerializable;

public class Constants {
    public enum EnumConnectionType implements IStringSerializable {
        none,
        pipe,
        tile,
        slot;

        @Override
        public String getName() {
            return this.name();
        }

        public static EnumConnectionType getTypeFromName(String name) {
            return EnumConnectionType.valueOf(name);
        }
    }

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

    public enum GuiTypes {
        CardSlot
    }
}
