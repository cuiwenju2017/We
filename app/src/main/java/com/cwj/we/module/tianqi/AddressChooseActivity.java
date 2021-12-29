package com.cwj.we.module.tianqi;

import android.app.Activity;
import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.cwj.we.R;
import com.cwj.we.base.BaseActivity;
import com.cwj.we.base.BasePresenter;
import com.cwj.we.bean.ProvinceBean;
import com.cwj.we.common.GlobalConstant;
import com.cwj.we.http.API;
import com.cwj.we.module.adapter.ReadTextAdapter;
import com.cwj.we.module.adapter.ReadTextAdapter2;
import com.cwj.we.module.adapter.ReadTextAdapter3;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gyf.immersionbar.ImmersionBar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import butterknife.BindView;

/**
 * 地址选择
 */
public class AddressChooseActivity extends BaseActivity {

    @BindView(R.id.rv)
    RecyclerView rv;

    private List<ProvinceBean> data;
    private ReadTextAdapter adapter;
    private ReadTextAdapter2 adapter2;
    private ReadTextAdapter3 adapter3;
    private String province, city, county;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_address_choose;
    }

    @Override
    public void initData() {
        readTextFromSDcard();

        adapter = new ReadTextAdapter(this, data, (v, position) -> {

            adapter2 = new ReadTextAdapter2(AddressChooseActivity.this, data.get(position).getCity(), (v1, position1) -> {

                adapter3 = new ReadTextAdapter3(AddressChooseActivity.this, data.get(position).getCity().get(position1).getArea(), (v11, position2) -> {
                    province = data.get(position).getName();
                    city = data.get(position).getCity().get(position1).getName();
                    county = data.get(position).getCity().get(position1).getArea().get(position2);
                    API.kv.encode(GlobalConstant.province, province);
                    API.kv.encode(GlobalConstant.city, city);
                    API.kv.encode(GlobalConstant.county, county);
                    Intent intent = new Intent();
                    intent.putExtra(GlobalConstant.province, province);
                    intent.putExtra(GlobalConstant.city, city);
                    intent.putExtra(GlobalConstant.county, county);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                });
                rv.setAdapter(adapter3);
                rv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));

            });
            rv.setAdapter(adapter2);
            rv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));

        });
        rv.setAdapter(adapter);
        rv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
    }

    private void readTextFromSDcard() {
        InputStreamReader inputStreamReader;
        try {
            inputStreamReader = new InputStreamReader(getAssets().open("province.json"), "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(
                    inputStreamReader);
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            inputStreamReader.close();
            bufferedReader.close();
            String resultString = stringBuilder.toString();
            Gson gson = new Gson();
            data = gson.fromJson(resultString, new TypeToken<List<ProvinceBean>>() {
            }.getType());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initView() {
        ImmersionBar.with(this)
                .statusBarDarkFont(true)  //状态栏字体是深色，不写默认为亮色
                .init();
    }
}