package solab.innovativetransport.pipe;

import net.minecraftforge.common.capabilities.ICapabilityProvider;
import solab.innovativetransport.transporter.Transporter;

import java.util.ArrayList;
import java.util.List;

public interface IPipe {
    IPipeHolder getHolder();
    List<Transporter> transporters = new ArrayList<>();
}
