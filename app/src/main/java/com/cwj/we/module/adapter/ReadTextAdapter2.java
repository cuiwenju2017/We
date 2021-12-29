package com.cwj.we.module.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cwj.we.R;
import com.cwj.we.bean.ProvinceBean;

import java.util.List;

public class ReadTextAdapter2 extends RecyclerView.Adapter<ReadTextAdapter2.ViewHolder> {

    private List<ProvinceBean.CityBean> list;
    private Context context;
    private ItemClickListener itemClickListener;

    public ReadTextAdapter2(Context context, List<ProvinceBean.CityBean> list, ItemClickListener itemClickListener) {
        this.context = context;
        this.list = list;
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onClickName(View v, int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_address, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_name.setText(list.get(position).getName());
        holder.tv_name.setOnClickListener(v -> itemClickListener.onClickName(v, position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
        }
    }
}
