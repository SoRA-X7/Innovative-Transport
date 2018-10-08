package solab.innovativetransport;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
//これでいいといいんだけどなぁ
public class Debugger extends Item {
    public Debugger() {
        super();
        setUnlocalizedName("debugger");
        setRegistryName("debugger");
        setCreativeTab(InnovativeTransport.tab);
    }
    public EnumActionResult onItemUseFirst(ItemStack itemStack, EntityPlayer entity, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        Minecraft mc = Minecraft.getMinecraft();
        if(entity.isSneaking()){
            if(world.getBlockState(pos).getBlock() == InnovativeTransport.blocks[0] && world.isRemote){
                TilePipe me = (TilePipe) world.getTileEntity(pos);
                mc.thePlayer.addChatMessage(new TextComponentTranslation("§aCLIENT-----------------"));
                if(me.connection.get(EnumFacing.UP) != null){
                    mc.thePlayer.addChatMessage(new TextComponentTranslation("§aUP"));
                }
                if(me.connection.get(EnumFacing.DOWN) != null){
                    mc.thePlayer.addChatMessage(new TextComponentTranslation("§aDOWN"));
                }
                if(me.connection.get(EnumFacing.NORTH) != null){
                    mc.thePlayer.addChatMessage(new TextComponentTranslation("§aNORTH"));
                }
                if(me.connection.get(EnumFacing.SOUTH) != null){
                    mc.thePlayer.addChatMessage(new TextComponentTranslation("§aSOUTH"));
                }
                if(me.connection.get(EnumFacing.EAST) != null){
                    mc.thePlayer.addChatMessage(new TextComponentTranslation("§aEAST"));
                }
                if(me.connection.get(EnumFacing.WEST) != null){
                    mc.thePlayer.addChatMessage(new TextComponentTranslation("§aWEST"));
                }
            }else if(world.getBlockState(pos).getBlock() == InnovativeTransport.blocks[0] &! world.isRemote){
                TilePipe me = (TilePipe) world.getTileEntity(pos);
                mc.thePlayer.addChatMessage(new TextComponentTranslation("§cSERVER-----------------"));
                if(me.connection.get(EnumFacing.UP) != null){
                    mc.thePlayer.addChatMessage(new TextComponentTranslation("§cUP"));
                }
                if(me.connection.get(EnumFacing.DOWN) != null){
                    mc.thePlayer.addChatMessage(new TextComponentTranslation("§cDOWN"));
                }
                if(me.connection.get(EnumFacing.NORTH) != null){
                    mc.thePlayer.addChatMessage(new TextComponentTranslation("§cNORTH"));
                }
                if(me.connection.get(EnumFacing.SOUTH) != null){
                    mc.thePlayer.addChatMessage(new TextComponentTranslation("§cSOUTH"));
                }
                if(me.connection.get(EnumFacing.EAST) != null){
                    mc.thePlayer.addChatMessage(new TextComponentTranslation("§cEAST"));
                }
                if(me.connection.get(EnumFacing.WEST) != null){
                    mc.thePlayer.addChatMessage(new TextComponentTranslation("§cWEST"));
                }
                mc.thePlayer.addChatMessage(new TextComponentTranslation("-----------------------"));
            }
        }else{
            if(world.getBlockState(pos).getBlock() == InnovativeTransport.blocks[0] && world.isRemote){
                TilePipe me = (TilePipe) world.getTileEntity(pos);
                mc.thePlayer.addChatMessage(new TextComponentTranslation("§aCLIENT-----------------"));
                mc.thePlayer.addChatMessage(new TextComponentTranslation("§a"+pos.toString()));
                mc.thePlayer.addChatMessage(new TextComponentTranslation("§a"+me.connection.toString()));

            }else if(world.getBlockState(pos).getBlock() == InnovativeTransport.blocks[0] &! world.isRemote){
                TilePipe me = (TilePipe) world.getTileEntity(pos);
                mc.thePlayer.addChatMessage(new TextComponentTranslation("§cSERVER-----------------"));
                mc.thePlayer.addChatMessage(new TextComponentTranslation("§c"+pos.toString()));
                mc.thePlayer.addChatMessage(new TextComponentTranslation("§c"+me.connection.toString()));
                mc.thePlayer.addChatMessage(new TextComponentTranslation("-----------------------"));
            }
        }
        return EnumActionResult.PASS;
    }

}