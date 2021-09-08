package com.cwj.we.module.activity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.cwj.we.R;
import com.cwj.we.base.BaseActivity;
import com.cwj.we.base.BasePresenter;
import com.cwj.we.view.ChaosCompassView;
import com.gyf.immersionbar.ImmersionBar;

import butterknife.BindView;

/**
 * 指南针
 */
public class CompassActivity extends BaseActivity {

    @BindView(R.id.cl_view)
    CoordinatorLayout clView;
    private SensorManager mSensorManager;
    private SensorEventListener mSensorEventListener;
    private ChaosCompassView chaosCompassView;
    private float val;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_compass;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .init();
        chaosCompassView = (ChaosCompassView) findViewById(R.id.ccv);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        mSensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                val = event.values[0];
                chaosCompassView.setVal(val);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        mSensorManager.registerListener(mSensorEventListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSensorManager.unregisterListener(mSensorEventListener);
    }
}