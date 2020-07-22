package com.cwj.love_lhh.module.ljxj;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atech.glcamera.interfaces.FileCallback;
import com.atech.glcamera.interfaces.FilteredBitmapCallback;
import com.atech.glcamera.utils.FileUtils;
import com.atech.glcamera.utils.FilterFactory;
import com.atech.glcamera.views.GLCameraView;
import com.cwj.love_lhh.R;
import com.cwj.love_lhh.utils.ToastUtil;
import com.zackratos.ultimatebarx.library.UltimateBarX;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 滤镜相机
 */
public class OpenCameraActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imgSwitch;
    ImageView imgFilter;
    ImageView imgCapter;
    GLCameraView mCameraView;
    RecyclerView rv;
    private ImageView iv;

    private boolean mRecordingEnabled = false;  // controls button state
    private int mode = 1;     //1.capture 2.record
    private List<FilterFactory.FilterType> filters = new ArrayList<>();
    private List<FilterInfo> infos = new ArrayList<>();
    private boolean isShowing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_camera);

        UltimateBarX.create(UltimateBarX.STATUS_BAR)
                .transparent()
                .apply(this);

        UltimateBarX.create(UltimateBarX.NAVIGATION_BAR)
                .transparent()
                .apply(this);

        imgCapter = findViewById(R.id.img_capture);
        imgSwitch = findViewById(R.id.img_switch);
        imgFilter = findViewById(R.id.img_filter);
        mCameraView = findViewById(R.id.glcamera);
        rv = findViewById(R.id.rv);
        iv = findViewById(R.id.iv);

        imgSwitch.setOnClickListener(this);
        imgFilter.setOnClickListener(this);
        imgCapter.setOnClickListener(this);
        iv.setOnClickListener(this);

        initFilters();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        FilterAdapter adapter = new FilterAdapter(this, infos);
        rv.setAdapter(adapter);
        rv.setLayoutManager(linearLayoutManager);

        adapter.setFilterSeletedListener(pos -> mCameraView.updateFilter(filters.get(pos)));

        mCameraView.setOnClickListener(v -> {
            if (isShowing) {
                rv.setVisibility(View.INVISIBLE);
                isShowing = false;
            }
        });
        mCameraView.enableBeauty(true);
    }

    private void initFilters() {
        filters.add(FilterFactory.FilterType.Original);
        filters.add(FilterFactory.FilterType.Sunrise);
        filters.add(FilterFactory.FilterType.Sunset);
        filters.add(FilterFactory.FilterType.BlackWhite);
        filters.add(FilterFactory.FilterType.WhiteCat);
        filters.add(FilterFactory.FilterType.BlackCat);
        filters.add(FilterFactory.FilterType.Beauty);
        filters.add(FilterFactory.FilterType.Healthy);
        filters.add(FilterFactory.FilterType.Sakura);
        filters.add(FilterFactory.FilterType.Romance);
        filters.add(FilterFactory.FilterType.Latte);
        filters.add(FilterFactory.FilterType.Warm);
        filters.add(FilterFactory.FilterType.Calm);
        filters.add(FilterFactory.FilterType.Cool);
        filters.add(FilterFactory.FilterType.Brooklyn);
        filters.add(FilterFactory.FilterType.Amaro);
        filters.add(FilterFactory.FilterType.Antique);
        filters.add(FilterFactory.FilterType.Brannan);

        infos.add(new FilterInfo(R.drawable.filter_thumb_original, "原图"));
        infos.add(new FilterInfo(R.drawable.filter_thumb_sunrise, "日出"));
        infos.add(new FilterInfo(R.drawable.filter_thumb_sunset, "日落"));
        infos.add(new FilterInfo(R.drawable.filter_thumb_1977, "黑白"));
        infos.add(new FilterInfo(R.drawable.filter_thumb_whitecat, "白猫"));
        infos.add(new FilterInfo(R.drawable.filter_thumb_blackcat, "黑猫"));
        infos.add(new FilterInfo(R.drawable.filter_thumb_beauty, "美白"));
        infos.add(new FilterInfo(R.drawable.filter_thumb_healthy, "健康"));
        infos.add(new FilterInfo(R.drawable.filter_thumb_sakura, "樱花"));
        infos.add(new FilterInfo(R.drawable.filter_thumb_romance, "浪漫"));
        infos.add(new FilterInfo(R.drawable.filter_thumb_latte, "拿铁"));
        infos.add(new FilterInfo(R.drawable.filter_thumb_warm, "温暖"));
        infos.add(new FilterInfo(R.drawable.filter_thumb_calm, "安静"));
        infos.add(new FilterInfo(R.drawable.filter_thumb_cool, "寒冷"));
        infos.add(new FilterInfo(R.drawable.filter_thumb_brooklyn, "纽约"));
        infos.add(new FilterInfo(R.drawable.filter_thumb_amoro, "Amaro"));
        infos.add(new FilterInfo(R.drawable.filter_thumb_antique, "复古"));
        infos.add(new FilterInfo(R.drawable.filter_thumb_brannan, "Brannan"));

        //set your own output file here
        // mCameraView.setOuputMP4File();
        //set record finish listener
        mCameraView.setrecordFinishedListnener(new FileCallback() {
            @Override
            public void onData(File file) {
                //update the gallery
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                        Uri.fromFile(file)));
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv://相册
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setType("image/*");
                this.startActivity(intent);
                break;
            case R.id.img_switch:
                mCameraView.switchCamera();
                break;
            case R.id.img_filter:
                if (isShowing) {
                    rv.setVisibility(View.INVISIBLE);
                } else {
                    rv.setVisibility(View.VISIBLE);
                }
                isShowing = !isShowing;
                break;
            case R.id.img_capture:
                if (mode == 1) {
                    onCapture();
                } else {
                    onRecord();
                }
                break;
        }
    }

    private void onCapture() {
        mCameraView.takePicture(new FilteredBitmapCallback() {
            @Override
            public void onData(Bitmap bitmap) {
                File file = FileUtils.createImageFile();
                //重新写入文件
                try {
                    // 写入文件
                    FileOutputStream fos;
                    fos = new FileOutputStream(file);
                    //默认jpg
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();
                    fos.close();
                    bitmap.recycle();
                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
                    ToastUtil.showTextToast(OpenCameraActivity.this, "已保存");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        mRecordingEnabled = false;
        mCameraView.changeRecordingState(mRecordingEnabled);
    }

    private void onRecord() {
        mRecordingEnabled = !mRecordingEnabled;
        mCameraView.changeRecordingState(mRecordingEnabled);
        if (mRecordingEnabled) {
            ToastUtil.showTextToast(OpenCameraActivity.this, "开始");
        } else {
            ToastUtil.showTextToast(OpenCameraActivity.this, "完成");
        }
    }
}