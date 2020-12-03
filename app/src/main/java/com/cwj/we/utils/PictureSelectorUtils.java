package com.cwj.we.utils;

import android.app.Activity;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.cwj.we.R;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

/**
 * @author CuiZhen
 * @date 2019/6/4
 * QQ: 302833254
 * E-mail: goweii@163.com
 * GitHub: https://github.com/goweii
 */
public class PictureSelectorUtils {

    public static void ofImage(Fragment fragment, int requestCode) {
        PictureSelector.create(fragment)
                .openGallery(PictureMimeType.ofImage())
                .theme(R.style.PictureSelectorStyle)
                .selectionMode(PictureConfig.SINGLE)
                .enableCrop(false)//是否裁剪
                .isDragFrame(false)// 是否可拖动裁剪框(固定)
                .withAspectRatio(3, 4)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .isCamera(true)//是否显示拍照按钮 true or false
                .isGif(true)//是否显示gif图片 true or false
                .previewImage(true)// 是否可预览图片 true or false
                .forResult(requestCode);
    }

    public static String forResult(int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            // 图片、视频、音频选择结果回调
            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
            if (selectList != null && selectList.size() > 0) {
                return selectList.get(0).getPath();
            }
        }
        return null;
    }
}
