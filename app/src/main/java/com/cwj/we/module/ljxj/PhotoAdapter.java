package com.cwj.we.module.ljxj;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cwj.we.R;

import java.util.List;

public class PhotoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private Context context;

    public PhotoAdapter(Context context, int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        ImageView iv = helper.getView(R.id.iv);
        Glide.with(context).load(item).into(iv);
        helper.addOnClickListener(R.id.iv);
    }
}
