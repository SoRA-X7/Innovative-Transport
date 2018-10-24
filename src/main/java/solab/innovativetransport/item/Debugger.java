package solab.innovativetransport.item;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import solab.innovativetransport.InnovativeTransport;
import solab.innovativetransport.card.cardbase.CardBase;
import solab.innovativetransport.pipe.BlockPipe;
import solab.innovativetransport.pipe.TilePipeHolder;

/**
 * Created by kirihi on 2018/10/07.
 */
public class Debugger extends Item {
    private int mode = 0;
    private String ctex="§a";
    private String stex="§c";
    public Debugger() {
        super();
        setUnlocalizedName("debugger");
        setRegistryName("debugger");
        setCreativeTab(InnovativeTransport.tab);
    }
    public static final Debugger INSTANCE = new Debugger();

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand){
        Minecraft mc = Minecraft.getMinecraft();
        if (!worldIn.isRemote) {
            if (playerIn.isSneaking()) {
                if (mode == 0) {
                    mode = 1;
                    mc.thePlayer.addChatMessage(new TextComponentTranslation("§6simplemode"));
                }else if (mode == 1) {
                    mode = 2;
                    mc.thePlayer.addChatMessage(new TextComponentTranslation("§6modulemode"));
                }else if (mode == 2) {
                    mode = 0;
                    mc.thePlayer.addChatMessage(new TextComponentTranslation("§6normalmode"));
                }
            }
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, itemStackIn);
    }
    public EnumActionResult onItemUseFirst(ItemStack itemStack, EntityPlayer entity, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        Minecraft mc = Minecraft.getMinecraft();

        if(mode == 0) {
            if (world.getBlockState(pos).getBlock() == BlockPipe.INSTANCE && world.isRemote) {
                TilePipeHolder me = (TilePipeHolder) world.getTileEntity(pos);
                mc.thePlayer.addChatMessage(new TextComponentTranslation("§aCLIENT-----------------"));
                mc.thePlayer.addChatMessage(new TextComponentTranslation("§a" + pos.toString()));
                mc.thePlayer.addChatMessage(new TextComponentTranslation("§a" + me.getPipe().connection.toString()));

            } else if (world.getBlockState(pos).getBlock() == BlockPipe.INSTANCE & !world.isRemote) {
                TilePipeHolder me = (TilePipeHolder) world.getTileEntity(pos);
                mc.thePlayer.addChatMessage(new TextComponentTranslation("§cSERVER-----------------"));
                mc.thePlayer.addChatMessage(new TextComponentTranslation("§c" + pos.toString()));
                mc.thePlayer.addChatMessage(new TextComponentTranslation("§c" + me.getPipe().connection.toString()));
                mc.thePlayer.addChatMessage(new TextComponentTranslation("-----------------------"));
            }
        }
        if (mode == 1) {
            if(world.getBlockState(pos).getBlock() == BlockPipe.INSTANCE && world.isRemote){
                TilePipeHolder me = (TilePipeHolder) world.getTileEntity(pos);
                mc.thePlayer.addChatMessage(new TextComponentTranslation("§aCLIENT-----------------"));
                    if(me.getPipe().connection.get(EnumFacing.UP).toString() != "none"){
                        ctex =ctex+"U";
                    }else{
                        ctex =ctex+"_";
                    }
                    if(me.getPipe().connection.get(EnumFacing.DOWN).toString() != "none"){
                        ctex =ctex+"D";
                    }else{
                        ctex =ctex+"_";
                    }
                    if(me.getPipe().connection.get(EnumFacing.NORTH).toString() != "none"){
                        ctex =ctex+"N";
                    }else{
                        ctex =ctex+"_";
                    }
                    if(me.getPipe().connection.get(EnumFacing.SOUTH).toString() != "none"){
                        ctex =ctex+"S";
                    }else{
                        ctex =ctex+"_";
                    }
                    if(me.getPipe().connection.get(EnumFacing.EAST).toString() != "none"){
                        ctex =ctex+"E";
                    }else{
                        ctex =ctex+"_";
                    }
                    if(me.getPipe().connection.get(EnumFacing.WEST).toString() != "none"){
                        ctex =ctex+"W";
                    }else{
                        ctex =ctex+"_";
                    }
                mc.thePlayer.addChatMessage(new TextComponentTranslation(ctex));
            }else if(world.getBlockState(pos).getBlock() == BlockPipe.INSTANCE &! world.isRemote){
                TilePipeHolder me = (TilePipeHolder) world.getTileEntity(pos);
                mc.thePlayer.addChatMessage(new TextComponentTranslation("§cSERVER-----------------"));
                if(me.getPipe().connection.get(EnumFacing.UP).toString() != "none"){
                    stex =stex+"U";
                }else{
                    stex =stex+"n";
                }
                if(me.getPipe().connection.get(EnumFacing.DOWN).toString() != "none"){
                    stex =stex+"D";
                }else{
                    stex =stex+"n";
                }
                if(me.getPipe().connection.get(EnumFacing.NORTH).toString() != "none"){
                    stex =stex+"N";
                }else{
                    stex =stex+"n";
                }
                if(me.getPipe().connection.get(EnumFacing.SOUTH).toString() != "none"){
                    stex =stex+"S";
                }else{
                    stex =stex+"n";
                }
                if(me.getPipe().connection.get(EnumFacing.EAST).toString() != "none"){
                    stex =stex+"E";
                }else{
                    stex =stex+"n";
                }
                if(me.getPipe().connection.get(EnumFacing.WEST).toString() != "none"){
                    stex =stex+"W";
                }else{
                    stex =stex+"n";
                }
                mc.thePlayer.addChatMessage(new TextComponentTranslation(stex));
                mc.thePlayer.addChatMessage(new TextComponentTranslation("-----------------------"));
                stex="§c";
                ctex="§a";
            }
        }
        return EnumActionResult.PASS;
    }
    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        IBlockState blockState = worldIn.getBlockState(pos);
        if (mode == 2) {
            if (blockState.getBlock() == BlockPipe.INSTANCE) {
                if (!blockState.getValue(TilePipeHolder.states.get(facing)) && ((TilePipeHolder)worldIn.getTileEntity(pos)).getPipe().getCardSlot(facing) != null) {
                    Minecraft mc = Minecraft.getMinecraft();
                    if (worldIn.isRemote) {
                        mc.thePlayer.addChatMessage(new TextComponentTranslation("§aCLIENT-----------------"));
                        for (CardBase card: ((TilePipeHolder)worldIn.getTileEntity(pos)).getPipe().getCardSlot(facing).getCards()) {
                            mc.thePlayer.addChatMessage(new TextComponentTranslation("§a"+"1"));
                        }
                    } else {
                        mc.thePlayer.addChatMessage(new TextComponentTranslation("§cSERVER-----------------"));
                        for (CardBase card: ((TilePipeHolder)worldIn.getTileEntity(pos)).getPipe().getCardSlot(facing).getCards()) {
                            mc.thePlayer.addChatMessage(new TextComponentTranslation("§a"+"1"));
                        }
                        mc.thePlayer.addChatMessage(new TextComponentTranslation("-----------------------"));
                    }
                    return EnumActionResult.SUCCESS;
                }
            }
        }
        return EnumActionResult.FAIL;
    }

}