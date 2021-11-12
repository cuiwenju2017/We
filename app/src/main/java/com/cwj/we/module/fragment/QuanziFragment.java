package com.cwj.we.module.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.cwj.we.R;
import com.cwj.we.base.BaseFragment;
import com.cwj.we.base.BasePresenter;
import com.cwj.we.bean.EventBG;
import com.cwj.we.module.activity.AddTieZiActivity;
import com.cwj.we.module.adapter.MyAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.gyf.immersionbar.ImmersionBar;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 圈子
 */
public class QuanziFragment extends BaseFragment {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.vp2)
    ViewPager2 vp2;
    @BindView(R.id.fab_add)
    FloatingActionButton fabAdd;

    private ViewHolder holder = null;
    private Intent intent;
    List<String> titles = new ArrayList<>();
    List<Fragment> fragments = new ArrayList<>();
    private int ADD_TIEZI = 201;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_quanzi;
    }

    @Override
    protected void initView() {
        //添加标题
        titles.add("段子");
        titles.add("树洞");
        titles.add("百科");
        titles.add("其他");
        //添加Fragment进去
        fragments.add(new QuanziChildFragment(0));
        fragments.add(new QuanziChildFragment(1));
        fragments.add(new QuanziChildFragment(2));
        fragments.add(new QuanziChildFragment(3));
        //实例化适配器
        MyAdapter myAdapter = new MyAdapter(getFragmentManager(), getLifecycle(), fragments);
        //设置适配器
        vp2.setAdapter(myAdapter);
        //设置abLayout点击时的水波效果
        tabLayout.setTabRippleColor(ColorStateList.valueOf(getResources().getColor(R.color.colorTransparent)));
        //TabLayout和Viewpager2进行关联
        new TabLayoutMediator(tabLayout, vp2, (tab, position) -> tab.setText(titles.get(position))).attach();

        /*
          字体变化设置
         */
        holder = null;
        for (int i = 0; i < titles.size(); i++) {
            //获取tab
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            //给tab设置自定义布局
            tab.setCustomView(R.layout.tab_item);
            holder = new ViewHolder(Objects.requireNonNull(tab.getCustomView()));
            //填充数据
            holder.tab_item_title.setText(titles.get(i));

            //默认选择第一项
            if (i == 0) {
                holder.tab_item_title.setSelected(true);
                holder.tab_item_title.setTextSize(20);

                /*常用的字体类型名称有：
                Typeface.DEFAULT //常规字体类型
                Typeface.DEFAULT_BOLD //黑体字体类型
                Typeface.MONOSPACE //等宽字体类型
                Typeface.SANS_SERIF //无衬线字体类型
                Typeface.SERIF //衬线字体字体类型
                常用的字体风格名称还有：
                Typeface.BOLD //粗体
                Typeface.BOLD_ITALIC //粗斜体
                Typeface.ITALIC //斜体
                Typeface.NORMAL //常规*/
                holder.tab_item_title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                //字体颜色
                holder.tab_item_title.setTextColor(getResources().getColor(R.color.colorAccent));
            } else {
                holder.tab_item_title.setSelected(false);
                holder.tab_item_title.setTextSize(15);
                holder.tab_item_title.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
                //字体颜色
                holder.tab_item_title.setTextColor(getResources().getColor(R.color.white));
            }
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                holder = new ViewHolder(tab.getCustomView());
                holder.tab_item_title.setSelected(true);
                //设置选中后的字体大小
                holder.tab_item_title.setTextSize(20);
                //字体类型、字体风格
                holder.tab_item_title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                //关联Viewpager
                vp2.setCurrentItem(tab.getPosition());
                //字体颜色
                holder.tab_item_title.setTextColor(getResources().getColor(R.color.colorAccent));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                holder = new ViewHolder(tab.getCustomView());
                holder.tab_item_title.setSelected(false);
                //恢复默认字体大小
                holder.tab_item_title.setTextSize(15);
                //字体类型、字体风格
                holder.tab_item_title.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
                //字体颜色
                holder.tab_item_title.setTextColor(getResources().getColor(R.color.white));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    protected void initData() {

    }

    @OnClick({R.id.fab_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fab_add://发布帖子
                intent = new Intent(getActivity(), AddTieZiActivity.class);
                startActivityForResult(intent, ADD_TIEZI);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_TIEZI && resultCode == Activity.RESULT_OK) {
            EventBG eventBG = new EventBG("ADD_SUCCESS", "");
            EventBus.getDefault().post(eventBG);
        }
    }

    static class ViewHolder {
        TextView tab_item_title;

        ViewHolder(View tabView) {
            tab_item_title = tabView.findViewById(R.id.tab_item_title);
        }
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .statusBarDarkFont(false) //状态栏字体是深色，不写默认为亮色
                .init();
    }
}