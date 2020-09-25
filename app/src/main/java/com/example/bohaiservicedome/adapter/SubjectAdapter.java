package com.example.bohaiservicedome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.bohaiservicedome.R;
import com.example.bohaiservicedome.entity.Subject;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author: created by JiangNan
 * @Date: 2020/4/7 16
 */
public class SubjectAdapter extends ArrayAdapter<Subject> {
    private int resourceId;

    public SubjectAdapter(@NonNull Context context, int resource, @NonNull List<Subject> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Subject subject = getItem(position);
        SubjectViewHolder viewHolder;
        View view;
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new SubjectViewHolder();
            viewHolder.titleSuject = view.findViewById(R.id.title_subject);
            viewHolder.rescSuject = view.findViewById(R.id.subject_desc);
            viewHolder.imgSubject = view.findViewById(R.id.img_subject);
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (SubjectViewHolder) view.getTag();
        }
        viewHolder.titleSuject.setText(subject.getTitle());
        viewHolder.rescSuject.setText(subject.getDesc());
        viewHolder.imgSubject.setImageURI(subject.getImages().getFileUrl());
        return view;
    }

    class SubjectViewHolder {
        TextView titleSuject;
        TextView rescSuject;
        SimpleDraweeView imgSubject ;
    }
}
