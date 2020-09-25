package com.example.bohaiservicedome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.bohaiservicedome.R;
import com.example.bohaiservicedome.entity.MsgLove;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author: created by JiangNan
 * @Date: 2020/4/5 13
 */
public class MsgLoveAdapter extends ArrayAdapter<MsgLove> {
    public static final String TAG = "MsgLoveAdapter";
    List<MsgLove> mMsgLoveList;
    private int resourceId;

    public MsgLoveAdapter(@NonNull Context context, int resource, @NonNull List<MsgLove> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        MsgLove msgLove = getItem(position);
        ViewHolder viewHolder;
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.loveTo = view.findViewById(R.id.love_to);
            viewHolder.loveFrom = view.findViewById(R.id.love_from);
            viewHolder.loveContent = view.findViewById(R.id.love_content);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.loveTo.setText(msgLove.getTo());
        viewHolder.loveContent.setText(msgLove.getContent());
        viewHolder.loveFrom.setText(msgLove.getFrom());
        return view;

    }

    class ViewHolder {
        TextView loveTo;
        TextView loveFrom;
        TextView loveContent;
        SimpleDraweeView loveImg;
    }
}
