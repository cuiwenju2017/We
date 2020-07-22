package com.cwj.love_lhh.module.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cwj.love_lhh.R;
import com.cwj.love_lhh.bean.GameBean;
import com.cwj.love_lhh.module.activity.CalculatorActivity;
import com.cwj.love_lhh.module.activity.ClockActivity;
import com.cwj.love_lhh.module.activity.CompassActivity;
import com.cwj.love_lhh.module.adapter.GameAdapter;
import com.cwj.love_lhh.module.ljxj.OpenCameraActivity;
import com.cwj.love_lhh.module.lpclock.LPClockActivity;
import com.cwj.love_lhh.utils.ToastUtil;
import com.permissionx.guolindev.PermissionX;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 工具
 */
public class ToolFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.rv_game)
    RecyclerView rvGame;

    private Intent intent;
    private Uri content_url;
    private List<GameBean> gameBeans = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tool, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvGame.setLayoutManager(layoutManager);
        GameAdapter adapter = new GameAdapter(gameBeans);
        rvGame.setAdapter(adapter);

        adapter.setOnclick((view1, position) -> {
            if (position == 0) {//计算器
                startActivity(new Intent(getActivity(), CalculatorActivity.class));
            } else if (position == 1) {//指南针
                startActivity(new Intent(getActivity(), CompassActivity.class));
            } else if (position == 2) {//时钟
                startActivity(new Intent(getActivity(), ClockActivity.class));
            } else if (position == 3) {//扒一剧
                intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                content_url = Uri.parse("http://www.81ju.cn/");
                intent.setData(content_url);
                startActivity(intent);
            } else if (position == 4) {//轮盘时中
                startActivity(new Intent(getActivity(), LPClockActivity.class));
            } else if (position == 5) {//滤镜相机
                PermissionX.init(this)
                        .permissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
                        .request((allGranted, grantedList, deniedList) -> {
                            if (allGranted) {
                                startActivity(new Intent(getActivity(), OpenCameraActivity.class));
                            } else {
                                ToastUtil.showTextToast(getActivity(), "同意权限后才能操作哦");
                            }
                        });
            }
        });

        return view;
    }

    private void initData() {
        GameBean jsq = new GameBean("计算器", R.drawable.counter_logo);
        gameBeans.add(jsq);
        GameBean znz = new GameBean("指南针", R.drawable.icon_compass);
        gameBeans.add(znz);
        GameBean sz = new GameBean("时钟", R.drawable.clock);
        gameBeans.add(sz);
        GameBean byj = new GameBean("扒一剧", R.drawable.icon_byj);
        gameBeans.add(byj);
        GameBean lpsz = new GameBean("轮盘时钟", R.drawable.icon_lp_shizhong);
        gameBeans.add(lpsz);
        GameBean ljxj = new GameBean("滤镜相机", R.drawable.filter_thumb_original);
        gameBeans.add(ljxj);
    }
}
