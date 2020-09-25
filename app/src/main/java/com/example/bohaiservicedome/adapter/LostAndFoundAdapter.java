package com.example.bohaiservicedome.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.bohaiservicedome.R;
import com.example.bohaiservicedome.entity.LostInformation;
import com.example.bohaiservicedome.entity.MyUser;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cn.bmob.v3.BmobUser;

/**
 * @author: created by JiangNan
 * @Date: 2020/4/4 20
 */
public class LostAndFoundAdapter extends RecyclerView.Adapter<LostAndFoundAdapter.LostAndFoundHolder> {
    private Context mContext;
    private List<LostInformation> mLostInformationList;
    private ItemClickListener mItemClickListener;
    public final static int EDIT_CODE = 998;
    public final static int DELETE_CODE = 997;

    public LostAndFoundAdapter(Context context) {
        mContext = context;
    }

    public void setLostInformationList(List<LostInformation> lostInformationList) {
        mLostInformationList = lostInformationList;
    }

    @NonNull
    @Override
    public LostAndFoundHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_lost, parent, false);
        LostAndFoundHolder lostAndFoundHolder = new LostAndFoundHolder(view);
        return lostAndFoundHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LostAndFoundHolder holder, int position) {
        if (!mLostInformationList.isEmpty()) {
            LostInformation lostInformation = mLostInformationList.get(position);
            holder.title.setText(lostInformation.getTitle());
            holder.desc.setText(lostInformation.getDesc());
            holder.telephone.setText(lostInformation.getPhoneNum());
            holder.time.setText(lostInformation.getCreatedAt());
            holder.llItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
                    if (myUser.getUsername().equals(lostInformation.getUsername())){
                        showWindow(holder, position);
                        return true;
                    }
                    return false;
                }
            });
        }

    }

    private void showWindow(LostAndFoundHolder holder, int position) {
        //加载布局文件
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.pop_window_view, null);
        final PopupWindow popupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        //设置焦点
        popupWindow.setFocusable(true);
        //触摸框外
        popupWindow.setOutsideTouchable(true);
        //点击空白处的时候让PopupWindow消失
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        //设置偏移量
        popupWindow.showAsDropDown(holder.time, 300, -100);
        //点击编辑按钮
        contentView.findViewById(R.id.edit_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //回调给主界面，进行数据操作
                mItemClickListener.onEditOrDeleteClick(position, EDIT_CODE);
                //销毁弹出框
                popupWindow.dismiss();
            }
        });

        //点击删除按钮
        contentView.findViewById(R.id.delete_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //回调给主界面，进行数据操作
                mItemClickListener.onEditOrDeleteClick(position, DELETE_CODE);
                //销毁弹出框
                popupWindow.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mLostInformationList.size() == 0 ? 0 : mLostInformationList.size();
    }

    //设置长按事件
    public void setLongClickListener(ItemClickListener clickListener) {
        this.mItemClickListener = clickListener;
    }

    public interface ItemClickListener {
        void onEditOrDeleteClick(int position, int code);
    }

    public class LostAndFoundHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView desc;
        private TextView time;
        private TextView telephone;
        private LinearLayout llItem;

        public LostAndFoundHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.lost_title);
            desc = (TextView) itemView.findViewById(R.id.lost_desc);
            time = (TextView) itemView.findViewById(R.id.lost_time);
            telephone = (TextView) itemView.findViewById(R.id.lost_telephone);
            llItem = (LinearLayout) itemView.findViewById(R.id.ll_item);
        }
    }

}
