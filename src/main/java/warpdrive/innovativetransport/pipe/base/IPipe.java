/*
 * Copyright (c) 2019. Made by SoRA_X7. CC BY-NC-SA 4.0
 */

package warpdrive.innovativetransport.pipe.base;

import net.minecraft.util.EnumFacing;
import warpdrive.innovativetransport.utils.Constants;

public interface IPipe {

    /**
     * 自身のTileEntity(PipeHolder)を返します。
     * @return 自身のIPipeHolder
     */
    IPipeHolder getHolder();

    /**
     * 指定した方向の接続状態を返します。
     * @param facing 接続状態を取得する方向
     * @return 接続状態
     */
    Constants.EnumConnectionType getConnectionTypeOf(EnumFacing facing);

}
