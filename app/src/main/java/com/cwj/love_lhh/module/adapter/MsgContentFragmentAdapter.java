package com.cwj.love_lhh.module.adapter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cwj.love_lhh.bean.TypesBean;
import com.cwj.love_lhh.module.news.MsgContentFragment;

import java.util.ArrayList;
import java.util.List;

public class MsgContentFragmentAdapter extends FragmentPagerAdapter {

    private List<TypesBean.DataBean> datas;

    public MsgContentFragmentAdapter(FragmentManager fm) {
        super(fm);
        this.datas = new ArrayList<>();
    }

    /**
     * 数据列表
     */
    public void setList(List<TypesBean.DataBean> datas) {
        this.datas.clear();
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        MsgContentFragment fragment = new MsgContentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("typeId", datas.get(position).getTypeId());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String plateName = datas.get(position).getTypeName();
        if (plateName == null) {
            plateName = "";
        } else if (plateName.length() > 15) {
            plateName = plateName.substring(0, 15) + "...";
        }
        return plateName;
    }
}