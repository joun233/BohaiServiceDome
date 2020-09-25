package com.example.bohaiservicedome.utils;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;

/**
 * @author: created by JiangNan
 * @Date: 2020/4/2 19
 */
public class ImageUtils {
    /**
     * 加载圆角图片
     *
     * @param image
     * @param imageUrl
     */
    public static void setRoundImage(Context context, SimpleDraweeView image, String imageUrl) {
        /**初始化圆角圆形参数对象**/
        RoundingParams rp = new RoundingParams();
        /**设置图像是否为圆形**/
        rp.setRoundAsCircle(true);
        /**设置边框颜色及其宽度**/
        rp.setBorder(Color.WHITE, 2);
        /**设置叠加颜色**/
        rp.setOverlayColor(Color.GRAY);
        /**设置圆形圆角模式**/
        rp.setRoundingMethod(RoundingParams.RoundingMethod.OVERLAY_COLOR);
        /**获取GenericDraweeHierarchy对象**/
        GenericDraweeHierarchy hierarchy = GenericDraweeHierarchyBuilder.newInstance(context.getResources())
                /**设置圆形圆角参数**/
                .setRoundingParams(rp)
                .setRoundingParams(RoundingParams.asCircle())
                /**设置淡入淡出动画持续时间(单位：毫秒ms)**/
                .setFadeDuration(1000)
                .build();
        image.setHierarchy(hierarchy);
        /**构建Controller**/
        DraweeController controller = Fresco.newDraweeControllerBuilder().setUri(imageUrl)
                /**设置需要下载的图片地址**/
                /**设置点击重试是否开启**/
                .setTapToRetryEnabled(true)
                .build();
        /**设置Controller**/
        image.setController(controller);
    }


    public static Uri getImageStreamFromExternal(String imageName) {
        File externalPubPath = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES
        );

        File picPath = new File(externalPubPath, imageName);
        Uri uri = null;
        if(picPath.exists()) {
            uri = Uri.fromFile(picPath);
        }
        return uri;

    }




}
