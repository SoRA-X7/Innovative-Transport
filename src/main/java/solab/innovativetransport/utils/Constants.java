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
}
