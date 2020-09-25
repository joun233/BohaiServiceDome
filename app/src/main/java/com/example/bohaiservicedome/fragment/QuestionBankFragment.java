package com.example.bohaiservicedome.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.bohaiservicedome.R;
import com.example.bohaiservicedome.activity.PdfViewActivity;
import com.example.bohaiservicedome.adapter.SubjectAdapter;
import com.example.bohaiservicedome.base.BaseFragment;
import com.example.bohaiservicedome.entity.Subject;
import com.example.bohaiservicedome.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * @author: JiangNan
 * @Date:2020/4/6
 */
public class QuestionBankFragment extends BaseFragment {
    @BindView(R.id.list_object)
    ListView sujectListView;
    List<Subject> mSubjectList = new ArrayList<>();
    SubjectAdapter mSubjectAdapter;

    public static Fragment getInstance() {
        Fragment fragment = new QuestionBankFragment();
        return fragment;
    }


    @Override
    protected int contentViewID() {
        return R.layout.fragment_question_bank;
    }

    @Override
    protected void initialize() {
        querySubject();
        OnItemClick();
    }

    private void OnItemClick() {
        sujectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mSubjectList == null){
                    return;
                }
                Subject subject = mSubjectList.get(i);
                Bundle bundle = new Bundle();
                bundle.putSerializable("subject",subject);
                startActivity(PdfViewActivity.class, bundle);
            }
        });
    }

    private void querySubject() {
        BmobQuery<Subject> subjectBmobQuery = new BmobQuery<Subject>();
        subjectBmobQuery.findObjects(new FindListener<Subject>() {
            @Override
            public void done(List<Subject> object, BmobException e) {
                if (e == null) {
                    mSubjectList = object;
                    mSubjectAdapter = new SubjectAdapter(getContext(),R.layout.item_subject, mSubjectList);
                    sujectListView.setAdapter(mSubjectAdapter);
                } else {
                    ToastUtils.showShort(getContext(), getContext().getString(R.string.query_failure));
                }
            }
        });
    }

}
