package com.cwj.we.module.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cwj.we.R;
import com.cwj.we.base.BaseFragment;
import com.cwj.we.base.BasePresenter;
import com.cwj.we.bean.GameBean;
import com.cwj.we.module.activity.CalculatorActivity;
import com.cwj.we.module.activity.CompassActivity;
import com.cwj.we.module.activity.VideoWebViewActivity;
import com.cwj.we.module.activity.VipJiexiActivity;
import com.cwj.we.module.adapter.GameAdapter;
import com.cwj.we.module.chenghu.ChengHuJiSuanQiActivity;
import com.cwj.we.module.ljxj.OpenCameraActivity;
import com.cwj.we.module.lpclock.LPClockActivity;
import com.cwj.we.utils.ToastUtil;
import com.gyf.immersionbar.ImmersionBar;
import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions;
import com.permissionx.guolindev.PermissionX;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 工具
 */
public class ToolFragment extends BaseFragment {

    @BindView(R.id.rv_game)
    RecyclerView rvGame;

    private Intent intent;
    private List<GameBean> gameBeans = new ArrayList<>();
    private int CAMERA_REQ_CODE = 201;
    private int REQUEST_CODE_SCAN_ONE = 202;

    private void doCode() {
        //调用扫码接口，构建扫码能力
        HmsScanAnalyzerOptions options = new HmsScanAnalyzerOptions.Creator().create();
        ScanUtil.startScan(getActivity(), REQUEST_CODE_SCAN_ONE, options);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tool;
    }

    @Override
    protected void initView() {
        GameBean jsq = new GameBean("计算器", R.drawable.counter_logo);
        gameBeans.add(jsq);
        GameBean chjsq = new GameBean("称呼计算器", R.drawable.icon_chenghu);
        gameBeans.add(chjsq);
        GameBean znz = new GameBean("指南针", R.drawable.icon_compass);
        gameBeans.add(znz);
        GameBean lpsz = new GameBean("轮盘时钟", R.drawable.icon_lp_shizhong);
        gameBeans.add(lpsz);
        GameBean ljxj = new GameBean("滤镜相机", R.drawable.filter_thumb_original);
        gameBeans.add(ljxj);
        GameBean spjx = new GameBean("视频解析", R.drawable.icon_byj);
        gameBeans.add(spjx);
        GameBean ddt = new GameBean("热影库", R.drawable.icon_byj);
        gameBeans.add(ddt);
        GameBean rrsp = new GameBean("人人视频", R.drawable.icon_byj);
        gameBeans.add(rrsp);
        GameBean dszb = new GameBean("电视直播", R.drawable.icon_byj);
        gameBeans.add(dszb);
        GameBean sys = new GameBean("扫一扫", R.drawable.icon_qr_code);
        gameBeans.add(sys);
    }

    protected void initData() {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvGame.setLayoutManager(layoutManager);
        GameAdapter adapter = new GameAdapter(gameBeans);
        rvGame.setAdapter(adapter);

        adapter.setOnclick((view1, position) -> {
            if (position == 0) {//计算器
                startActivity(new Intent(getActivity(), CalculatorActivity.class));
            } else if (position == 1) {//称呼计算器
                startActivity(new Intent(getActivity(), ChengHuJiSuanQiActivity.class));
            } else if (position == 2) {//指南针
                startActivity(new Intent(getActivity(), CompassActivity.class));
            } else if (position == 3) {//轮盘时中
                startActivity(new Intent(getActivity(), LPClockActivity.class));
            } else if (position == 4) {//滤镜相机
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
            } else if (position == 5) {//视频解析
                intent = new Intent(getActivity(), VipJiexiActivity.class);
                startActivity(intent);
            } else if (position == 6) {//热影库
                intent = new Intent(getActivity(), VideoWebViewActivity.class);
                intent.putExtra("movieUrl", "http://www.reyingku.cc/");
                startActivity(intent);
            } else if (position == 7) {//人人视频
                intent = new Intent(getActivity(), VideoWebViewActivity.class);
                intent.putExtra("movieUrl", "http://m.rr.tv/");
                startActivity(intent);
            } else if (position == 8) {//电视直播
                intent = new Intent(getActivity(), VideoWebViewActivity.class);
                intent.putExtra("movieUrl", "http://m.hao5.net/");
                startActivity(intent);
            } else if (position == 9) {//扫一扫
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        //没有权限则申请权限
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_REQ_CODE);
                    } else {
                        //有权限直接执行,docode()不用做处理
                        doCode();
                    }
                } else {
                    //小于6.0，不用申请权限，直接执行
                    doCode();
                }
            }
        });
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .statusBarDarkFont(false)  //状态栏字体是深色，不写默认为亮色
                .init();
    }
}
