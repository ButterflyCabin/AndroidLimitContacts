package com.yehu.androidlimitcontacts.utils;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

/**
 * 创建日期：2017/12/20 17:33
 *
 * @author yehu
 *         类说明：
 */
public class ImageLoaderUtils {
    public static DisplayImageOptions getOptions(int resId) {
        DisplayImageOptions options = (new DisplayImageOptions.Builder()).showImageForEmptyUri(resId).showImageOnFail(resId).showImageOnLoading(resId).imageScaleType(ImageScaleType.EXACTLY).cacheInMemory(true).cacheOnDisc(true).build();
        return options;
    }
}
