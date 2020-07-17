package com.cwj.love_lhh.module.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cwj.love_lhh.R;
import com.cwj.love_lhh.bean.GameBean;

import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {

    private List<GameBean> mgameBeans;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name;
        LinearLayout ll;

        public ViewHolder(View view) {
            super(view);
            img = view.findViewById(R.id.civ_img);
            name = view.findViewById(R.id.tv_name);
            ll = view.findViewById(R.id.ll);
        }
    }

    public GameAdapter(List<GameBean> gameBeans) {
        mgameBeans = gameBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        GameBean gameBean = mgameBeans.get(position);
        holder.img.setImageResource(gameBean.getImg());
        holder.name.setText(gameBean.getName());
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mgameBeans.size();
    }
}
