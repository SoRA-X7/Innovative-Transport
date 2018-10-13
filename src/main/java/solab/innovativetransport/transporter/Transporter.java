package solab.innovativetransport.transporter;

public class Transporter {
    private static int nextFreeId = 0;
    public int id;

    public Transporter() {
        this.id = getNextFreeId();
    }

    public static int getNextFreeId() {
        return ++Transporter.nextFreeId;
    }
}
