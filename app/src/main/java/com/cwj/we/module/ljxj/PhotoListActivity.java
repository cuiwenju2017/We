package com.cwj.we.module.ljxj;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cwj.we.R;
import com.cwj.we.base.BaseActivity;
import com.cwj.we.base.BasePresenter;
import com.gyf.immersionbar.ImmersionBar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 相册列表
 */
public class PhotoListActivity extends BaseActivity {

    @BindView(R.id.rv_photo_list)
    RecyclerView rvPhotoList;
    @BindView(R.id.my_toolbar)
    Toolbar toolbar;

    private PhotoAdapter photoAdapter;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_photo_list;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarDarkFont(true)  //状态栏字体是深色，不写默认为亮色
                .fitsSystemWindows(true) //解决状态栏和布局重叠问题，任选其一，默认为false，当为true时一定要指定statusBarColor()，不然状态栏为透明色，还有一些重载方法
                .statusBarColor(R.color.colorPrimary)     //状态栏颜色，不写默认透明色
                .init();
        toolbar = findViewById(R.id.my_toolbar);
        toolbar.setTitle("相册");
        // 显示导航按钮
        toolbar.setNavigationIcon(R.drawable.icon_back);
        setSupportActionBar(toolbar);
        // 显示标题和子标题
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    @Override
    public void initData() {
        rvPhotoList.setLayoutManager(new GridLayoutManager(this, 4));//列数设置
        photoAdapter = new PhotoAdapter(PhotoListActivity.this, R.layout.item_photo, getSystemPhotoList(this));
        rvPhotoList.setAdapter(photoAdapter);
        photoAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            Intent intent = new Intent(this, PhotoViewActivity.class);
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("dataBean", (ArrayList<String>) getSystemPhotoList(this));
            intent.putExtras(bundle);
            intent.putExtra("currentPosition", position);
            startActivity(intent);
        });
    }

    /**
     * 获取本地相册路径列表
     *
     * @param context
     * @return
     */
    public static List<String> getSystemPhotoList(Context context) {
        List<String> result = new ArrayList<String>();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor == null || cursor.getCount() <= 0) return null; // 没有图片
        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            String path = cursor.getString(index); // 文件地址
            File file = new File(path);
            if (file.exists()) {
                result.add(path);
            }
        }
        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}