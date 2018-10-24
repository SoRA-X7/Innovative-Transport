package solab.innovativetransport.card;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;

import static solab.innovativetransport.InnovativeTransport.MODID;

public class BlockDummyCardSlot extends Block {
    public static final PropertyEnum<EnumFacing> propertyFacing = PropertyEnum.create("facing",EnumFacing.class);
    public BlockDummyCardSlot() {
        super(Material.ROCK);
        setRegistryName(MODID,"partcardslot");
        setUnlocalizedName(MODID + "_dummycardslot");
        setDefaultState(blockState.getBaseState().withProperty(propertyFacing,EnumFacing.DOWN));
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, propertyFacing);
    }
    @Override
    public int getMetaFromState(IBlockState state) { return 0; }
    @Override
    public IBlockState getStateFromMeta(int meta) { return this.getDefaultState(); }
}
