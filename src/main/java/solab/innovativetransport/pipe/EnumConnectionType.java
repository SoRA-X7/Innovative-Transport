package solab.innovativetransport.pipe;

import net.minecraft.util.IStringSerializable;

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
        return valueOf(name);
    }
}
