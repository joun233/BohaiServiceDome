package com.example.bohaiservicedome.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bohaiservicedome.R;
import com.example.bohaiservicedome.entity.NoticeArticle;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author: created by JiangNan
 * @Date: 2020/4/4 12
 */
public class NoticeAdapter  extends RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder>{
    private List<NoticeArticle> mNoticeArticleList;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;
    public NoticeAdapter(List<NoticeArticle> noticeArticleList, Context context) {
        mNoticeArticleList = noticeArticleList;
        mContext = context;
    }

    public interface OnItemClickListener {
        void onClick(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_notice, null);
        return new NoticeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewHolder holder, int position) {
        NoticeArticle noticeArticle = mNoticeArticleList.get(position);
        holder.noticeTime.setText(noticeArticle.getCreatedAt());
        holder.noticeTitle.setText(noticeArticle.getTitle());
        holder.noticeDesc.setText(noticeArticle.getDesc());
        holder.noticeImage.setImageURI(Uri.parse(noticeArticle.getNoticeImage().getFileUrl()));

        holder.noticeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                mOnItemClickListener.onClick(holder.noticeCardView, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNoticeArticleList.size();
    }

    class NoticeViewHolder extends RecyclerView.ViewHolder {
        TextView noticeTime;
        TextView noticeTitle;
        TextView noticeDesc;
        SimpleDraweeView noticeImage;
        CardView noticeCardView;
        public NoticeViewHolder(@NonNull View itemView) {
            super(itemView);
            noticeTime = itemView.findViewById(R.id.notice_time);
            noticeTitle = itemView.findViewById(R.id.notice_title);
            noticeDesc = itemView.findViewById(R.id.notice_des);
            noticeImage = itemView.findViewById(R.id.notice_image);
            noticeCardView = itemView.findViewById(R.id.notice_cardView);
        }
    }
}
