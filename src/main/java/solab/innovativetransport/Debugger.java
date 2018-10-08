package solab.innovativetransport;

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
import solab.innovativetransport.pipe.TilePipe;

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
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
    }
    public EnumActionResult onItemUseFirst(ItemStack itemStack, EntityPlayer entity, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        Minecraft mc = Minecraft.getMinecraft();

        if(mode == 0) {
            if (world.getBlockState(pos).getBlock() == InnovativeTransport.blocks[0] && world.isRemote) {
                TilePipe me = (TilePipe) world.getTileEntity(pos);
                mc.thePlayer.addChatMessage(new TextComponentTranslation("§aCLIENT-----------------"));
                mc.thePlayer.addChatMessage(new TextComponentTranslation("§a" + pos.toString()));
                mc.thePlayer.addChatMessage(new TextComponentTranslation("§a" + me.connection.toString()));

            } else if (world.getBlockState(pos).getBlock() == InnovativeTransport.blocks[0] & !world.isRemote) {
                TilePipe me = (TilePipe) world.getTileEntity(pos);
                mc.thePlayer.addChatMessage(new TextComponentTranslation("§cSERVER-----------------"));
                mc.thePlayer.addChatMessage(new TextComponentTranslation("§c" + pos.toString()));
                mc.thePlayer.addChatMessage(new TextComponentTranslation("§c" + me.connection.toString()));
                mc.thePlayer.addChatMessage(new TextComponentTranslation("-----------------------"));
            }
        }
        if (mode == 1) {
            if(world.getBlockState(pos).getBlock() == InnovativeTransport.blocks[0] && world.isRemote){
                TilePipe me = (TilePipe) world.getTileEntity(pos);
                mc.thePlayer.addChatMessage(new TextComponentTranslation("§aCLIENT-----------------"));
                if(me.connection.get(EnumFacing.UP).toString() != "none"){
                    ctex =ctex+"U";
                }else{
                    ctex =ctex+"n";
                }
                if(me.connection.get(EnumFacing.DOWN).toString() != "none"){
                    ctex =ctex+"D";
                }else{
                    ctex =ctex+"n";
                }
                if(me.connection.get(EnumFacing.NORTH).toString() != "none"){
                    ctex =ctex+"N";
                }else{
                    ctex =ctex+"n";
                }
                if(me.connection.get(EnumFacing.SOUTH).toString() != "none"){
                    ctex =ctex+"S";
                }else{
                    ctex =ctex+"n";
                }
                if(me.connection.get(EnumFacing.EAST).toString() != "none"){
                    ctex =ctex+"E";
                }else{
                    ctex =ctex+"n";
                }
                if(me.connection.get(EnumFacing.WEST).toString() != "none"){
                    ctex =ctex+"W";
                }else{
                    ctex =ctex+"n";
                }
                mc.thePlayer.addChatMessage(new TextComponentTranslation(ctex));
            }else if(world.getBlockState(pos).getBlock() == InnovativeTransport.blocks[0] &! world.isRemote){
                TilePipe me = (TilePipe) world.getTileEntity(pos);
                mc.thePlayer.addChatMessage(new TextComponentTranslation("§cSERVER-----------------"));
                if(me.connection.get(EnumFacing.UP).toString() != "none"){
                    stex =stex+"U";
                }else{
                    stex =stex+"n";
                }
                if(me.connection.get(EnumFacing.DOWN).toString() != "none"){
                    stex =stex+"D";
                }else{
                    stex =stex+"n";
                }
                if(me.connection.get(EnumFacing.NORTH).toString() != "none"){
                    stex =stex+"N";
                }else{
                    stex =stex+"n";
                }
                if(me.connection.get(EnumFacing.SOUTH).toString() != "none"){
                    stex =stex+"S";
                }else{
                    stex =stex+"n";
                }
                if(me.connection.get(EnumFacing.EAST).toString() != "none"){
                    stex =stex+"E";
                }else{
                    stex =stex+"n";
                }
                if(me.connection.get(EnumFacing.WEST).toString() != "none"){
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
        if (mode == 2) {
            if(world.getBlockState(pos).getBlock() == InnovativeTransport.blocks[0]){
                mc.thePlayer.addChatMessage(new TextComponentTranslation("x"));
            }

        }
        return EnumActionResult.PASS;
    }

}