package solab.innovativetransport.card;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import solab.innovativetransport.InnovativeTransport;
import solab.innovativetransport.card.cardbase.ICardBehaviour;

import java.util.List;

public class ItemCard extends Item {

    ICardBehaviour card;

    public ItemCard() {
        super();
        setRegistryName(InnovativeTransport.MODID,"itemcard");
        setUnlocalizedName(InnovativeTransport.MODID + "_itemcard");
        addPropertyOverride(new ResourceLocation("cardtype"), (stack, worldIn, entityIn) -> {
            if (stack.getTagCompound() != null) {
                return stack.getTagCompound().getInteger("cardtype");
            }
            return 0;
        });
        setCreativeTab(InnovativeTransport.tab);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems)
    {
        for (int i=0;i<EnumCards.maxLength();i++) {
            ItemStack stack = new ItemStack(itemIn);
            NBTTagCompound compound1 = new NBTTagCompound();
            compound1.setInteger("cardtype",i);
            stack.setTagCompound(compound1);
            subItems.add(stack);
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        if (stack.getTagCompound() == null) stack.setTagCompound(new NBTTagCompound());
        return super.getUnlocalizedName(stack) + stack.getTagCompound().getInteger("cardtype");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        if (stack.getTagCompound() != null) {
            tooltip.add(stack.getTagCompound().getInteger("cardtype") + "");
        }
    }

}
