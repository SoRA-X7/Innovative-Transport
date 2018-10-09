package solab.innovativetransport;

import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import solab.innovativetransport.pipe.PipeModel;

public class InnovativeTransportCustomModelLoader implements ICustomModelLoader {
    @Override
    public boolean accepts(ResourceLocation modelLocation) {
        return modelLocation.getResourceDomain().equals(InnovativeTransport.MODID.toLowerCase());
    }

    @Override
    public IModel loadModel(ResourceLocation modelLocation) throws Exception {
        return new PipeModel();
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {

    }
}
