package com.cwj.we.game.eluosifangkuai;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cwj.we.R;

import java.util.List;

/**
 * Created by dell on 2018/3/23.
 */

public class BlockAdapter extends CommonAdapter {
    Context context;
    List<Integer> mDatas;

    public BlockAdapter(Context context, List mDatas, int mLayoutId) {
        super(context, mDatas, mLayoutId);
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    public void convert(ViewHolder helper, Object item) {
        ImageView imageView = helper.getView(R.id.adapter_image);
        Integer integer = (Integer) item;
        if (integer > 0) {
            Glide.with(context)
                    .load(StateFang.color[integer - 1])
                    .into(imageView);
        } else {
            imageView.setBackgroundColor(Color.parseColor("#29505B"));
        }
    }
}
