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
    private String ctex="";
    private String stex="";
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
                        mc.thePlayer.addChatMessage(new TextComponentTranslation("§6verysimplemode"));
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
                        mc.thePlayer.addChatMessage(new TextComponentTranslation("§aUP"));
                    }
                    if(me.connection.get(EnumFacing.DOWN).toString() != "none"){
                        mc.thePlayer.addChatMessage(new TextComponentTranslation("§aDOWN"));
                    }
                    if(me.connection.get(EnumFacing.NORTH).toString() != "none"){
                        mc.thePlayer.addChatMessage(new TextComponentTranslation("§aNORTH"));
                    }
                    if(me.connection.get(EnumFacing.SOUTH).toString() != "none"){
                        mc.thePlayer.addChatMessage(new TextComponentTranslation("§aSOUTH"));
                    }
                    if(me.connection.get(EnumFacing.EAST).toString() != "none"){
                        mc.thePlayer.addChatMessage(new TextComponentTranslation("§aEAST"));
                    }
                    if(me.connection.get(EnumFacing.WEST).toString() != "none"){
                        mc.thePlayer.addChatMessage(new TextComponentTranslation("§aWEST"));
                    }
                }else if(world.getBlockState(pos).getBlock() == InnovativeTransport.blocks[0] &! world.isRemote){
                    TilePipe me = (TilePipe) world.getTileEntity(pos);
                    mc.thePlayer.addChatMessage(new TextComponentTranslation("§cSERVER-----------------"));
                    if(me.connection.get(EnumFacing.UP).toString() != "none"){
                        mc.thePlayer.addChatMessage(new TextComponentTranslation("§cUP"));
                    }
                    if(me.connection.get(EnumFacing.DOWN).toString() != "none"){
                        mc.thePlayer.addChatMessage(new TextComponentTranslation("§cDOWN"));
                    }
                    if(me.connection.get(EnumFacing.NORTH).toString() != "none"){
                        mc.thePlayer.addChatMessage(new TextComponentTranslation("§cNORTH"));
                    }
                    if(me.connection.get(EnumFacing.SOUTH).toString() != "none"){
                        mc.thePlayer.addChatMessage(new TextComponentTranslation("§cSOUTH"));
                    }
                    if(me.connection.get(EnumFacing.EAST).toString() != "none"){
                        mc.thePlayer.addChatMessage(new TextComponentTranslation("§cEAST"));
                    }
                    if(me.connection.get(EnumFacing.WEST).toString() != "none"){
                        mc.thePlayer.addChatMessage(new TextComponentTranslation("§cWEST"));
                    }
                    mc.thePlayer.addChatMessage(new TextComponentTranslation("-----------------------"));
                }
            }
            if (mode == 2) {
                if(world.getBlockState(pos).getBlock() == InnovativeTransport.blocks[0]){
                    TilePipe me = (TilePipe) world.getTileEntity(pos);
                    if(world.isRemote){
                        if(me.connection.get(EnumFacing.UP).toString() != "none"){
                            ctex =ctex+"1";
                        }else{
                            ctex =ctex+"0";
                        }
                        if(me.connection.get(EnumFacing.DOWN).toString() != "none"){
                            ctex =ctex+"1";
                        }else{
                            ctex =ctex+"0";
                        }
                        if(me.connection.get(EnumFacing.NORTH).toString() != "none"){
                            ctex =ctex+"1";
                        }else{
                            ctex =ctex+"0";
                        }
                        if(me.connection.get(EnumFacing.SOUTH).toString() != "none"){
                            ctex =ctex+"1";
                        }else{
                            ctex =ctex+"0";
                        }
                        if(me.connection.get(EnumFacing.EAST).toString() != "none"){
                            ctex =ctex+"1";
                        }else{
                            ctex =ctex+"0";
                        }
                        if(me.connection.get(EnumFacing.WEST).toString() != "none"){
                            ctex =ctex+"1";
                        }else{
                            ctex =ctex+"0";
                        }
                    }
                    if(!world.isRemote){
                        if(me.connection.get(EnumFacing.UP).toString() != "none"){
                            stex =stex+"1";
                        }else{
                            stex =stex+"0";
                        }
                        if(me.connection.get(EnumFacing.DOWN).toString() != "none"){
                            stex =stex+"1";
                        }else{
                            stex =stex+"0";
                        }
                        if(me.connection.get(EnumFacing.NORTH).toString() != "none"){
                            stex =stex+"1";
                        }else{
                            stex =stex+"0";
                        }
                        if(me.connection.get(EnumFacing.SOUTH).toString() != "none"){
                            stex =stex+"1";
                        }else{
                            stex =stex+"0";
                        }
                        if(me.connection.get(EnumFacing.EAST).toString() != "none"){
                            stex =stex+"1";
                        }else{
                            stex =stex+"0";
                        }
                        if(me.connection.get(EnumFacing.WEST).toString() != "none"){
                            stex =stex+"1";
                        }else{
                            stex =stex+"0";
                        }
                        if(world.isRemote&&ctex == stex){
                            mc.thePlayer.addChatMessage(new TextComponentTranslation("§3match"));
                        }else if(world.isRemote&&ctex != stex){
                            mc.thePlayer.addChatMessage(new TextComponentTranslation("§4not match"));
                        }
                        mc.thePlayer.addChatMessage(new TextComponentTranslation(ctex));
                        mc.thePlayer.addChatMessage(new TextComponentTranslation(stex));
                        stex="";
                        ctex="";
                    }
                }

            }
        return EnumActionResult.PASS;
    }

}