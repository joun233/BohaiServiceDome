package com.example.bohaiservicedome.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bohaiservicedome.R;
import com.example.bohaiservicedome.entity.CampusTalk;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.bohaiservicedome.adapter.CampusTalkAdapter.CampusTalkView;

/**
 * @author: created by JiangNan
 * @Date: 2020/4/12 17
 */
public class CampusTalkAdapter extends RecyclerView.Adapter<CampusTalkView>{
    private List<CampusTalk> mCampusTalks;

    public CampusTalkAdapter(List<CampusTalk> campusTalks) {
        mCampusTalks = campusTalks;
    }

    @NonNull
    @Override
    public CampusTalkView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_campus_talk, parent, false);
        return new CampusTalkView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CampusTalkView holder, int position) {
        holder.imgTalk.setImageURI(mCampusTalks.get(position).getImage().getFileUrl());
        holder.contentTalk.setText(mCampusTalks.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return mCampusTalks.size();
    }


    public static class CampusTalkView extends RecyclerView.ViewHolder {
        SimpleDraweeView imgTalk;
        TextView contentTalk;

        public CampusTalkView(@NonNull View itemView) {
            super(itemView);
            imgTalk = itemView.findViewById(R.id.campus_img);
            contentTalk = itemView.findViewById(R.id.campus_content);
        }
    }
}
