package solab.innovativetransport.pipe;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.util.vector.Vector3f;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class PipeBakedModel implements IBakedModel {

    FaceBakery faceBakery = new FaceBakery();
    TextureAtlasSprite stone;

    public PipeBakedModel(Function<ResourceLocation,TextureAtlasSprite> bakedTextureGetter) {
        stone = bakedTextureGetter.apply(new ResourceLocation("blocks/stone"));
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
        if (side == null || state == null) return ImmutableList.of();

        List<BakedQuad> quads = new ArrayList<>();
//        if (state.getValue(TilePipe.states.get(side)) == EnumConnectionType.pipe) {
//            quads.add();
//        }
        //面の始点
        Vector3f from = new Vector3f(0, 0, 0);

        //面の終点
        Vector3f to = new Vector3f(16, 16, 16);

        //TextureのUVの指定
        BlockFaceUV uv = new BlockFaceUV(new float[]{0.0F, 0.0F, 16.0F, 16.0F}, 0);

        //面の描画の設定、ほぼ使用されないと思われる。
        //第一引数:cullface(使用されない)
        //第二引数:tintindex兼layer兼renderPass
        //第三引数:テクスチャの場所(使用されない)
        //第四引数:TextureのUVの指定
        BlockPartFace partFace = new BlockPartFace(side, side.getIndex(), new ResourceLocation("blocks/stone").toString(), uv);

        //Quadの設定
        //第一引数:面の始点
        //第二引数:面の終点
        //第三引数:面の描画の設定
        //第四引数:テクスチャ
        //第五引数:面の方向
        //第六引数:モデルの回転
        //第七引数:面の回転(nullで自動)
        //第八引数:モデルの回転に合わせてテクスチャを回転させるか
        //第九引数:陰らせるかどうか
        BakedQuad bakedQuad = faceBakery.makeBakedQuad(from, to, partFace, stone, side, ModelRotation.X0_Y0, null, true, true);

        quads.add(bakedQuad);
        return quads;
    }

    @Override
    public boolean isAmbientOcclusion() {
        return false;
    }

    @Override
    public boolean isGui3d() {
        return false;
    }

    @Override
    public boolean isBuiltInRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return null;
    }

    @Override
    public ItemCameraTransforms getItemCameraTransforms() {
        return null;
    }

    @Override
    public ItemOverrideList getOverrides() {
        return null;
    }
}
