package com.dongyuwuye.component_net;


import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.dongyuwuye.compontent_sdk.utils.DensityUtils;


import java.io.File;

/**
 * create by：mc on 2018/9/10 13:53
 * email:
 * 图片加载
 * picasso 是单例模式，所以要传入application context 防止内存泄漏
 */
public class ImageLoader {

    private static ImageLoader Instance = null;

    private ImageLoader() {
    }

    public static ImageLoader getInstance() {
        if (Instance == null) {
            Instance = new ImageLoader();
        }
        return Instance;
    }


    /**
     * 加载图片
     *
     * @param URL
     * @param context
     * @param imageView
     */
    public void LoadImage(String URL, Context context, ImageView imageView) {
        Glide.with(context)
                .load(URL)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.shape_placeholder))
                .override(DensityUtils.dpTopx(50, context))
                .into(imageView);
    }

    /**
     * 加载图片
     *
     * @param URL
     * @param context
     * @param imageView
     */
    public void LoadImage(String URL, Context context, ImageView imageView, int placeholder) {
        Glide.with(context)
                .load(URL)
                .apply(new RequestOptions()
                        .placeholder(placeholder))
                .override(DensityUtils.dpTopx(50, context))
                .into(imageView);
    }

    /**
     * 加载图片
     *
     * @param URL
     * @param context
     * @param imageView
     */
    public void LoadLocalImage(String URL, Context context, ImageView imageView, int placeholder) {
        Glide.with(context)
                .load(Uri.fromFile(new File(URL)))
                .apply(new RequestOptions()
                        .placeholder(placeholder))
                .override(DensityUtils.dpTopx(50, context))
                .into(imageView);
    }

    /**
     * 加载图片
     *
     * @param URL
     * @param context
     * @param imageView
     */
    public void LoadLocalImage(String URL, Context context, ImageView imageView) {
        Glide.with(context)
                .load(Uri.fromFile(new File(URL)))
                .skipMemoryCache(true) // 不使用内存缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE) // 不使用磁盘缓存
                .override(DensityUtils.dpTopx(50, context))
                .apply(new RequestOptions())
                .into(imageView);
    }

    /**
     * 加载本地或网络图片
     *
     * @param URL
     * @param context
     * @param imageView
     */
    public void LoadLocalOrOriginImage(String URL, Context context, ImageView imageView) {
        if (URL.startsWith("http")) {
            Glide.with(context)
                    .load(URL)
                    .apply(new RequestOptions())
                    .override(DensityUtils.dpTopx(50, context))
                    .into(imageView);
        } else {
            Glide.with(context)
                    .load(Uri.fromFile(new File(URL)))
                    .apply(new RequestOptions())
                    .override(DensityUtils.dpTopx(50, context))
                    .into(imageView);
        }
    }
}
