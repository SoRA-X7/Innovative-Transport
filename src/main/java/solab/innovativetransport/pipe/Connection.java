package solab.innovativetransport.pipe;

public class Connection {
    public EnumConnectionType type;
    public Connection(EnumConnectionType type) {
        this.type = type;
    }

    public void setType(EnumConnectionType type) {
        this.type = type;
    }
    public EnumConnectionType getType() {
        return this.type;
    }
}
