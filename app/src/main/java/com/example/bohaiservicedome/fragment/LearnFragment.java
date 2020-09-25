package com.example.bohaiservicedome.fragment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bohaiservicedome.R;
import com.example.bohaiservicedome.base.BaseFragment;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.OnClick;

/**
* @author: JiangNan
* @Date:2020/4/1
*/
public class LearnFragment extends BaseFragment {
    @BindView(R.id.tv_everyday_english)
    TextView tvEverydayEnglish;
    @BindView(R.id.tv_question_bank)
    TextView tvQuestionBank;
    @BindView(R.id.ll_everyday_english)
    LinearLayout llEveryDayEnglish;
    @BindView(R.id.ll_question_bank)
    LinearLayout llQuestionBank;
    private FragmentManager fm;
    public static Fragment getInstance() {
        Fragment fragment = new LearnFragment();
        return fragment;
    }

    @Override
    protected int contentViewID() {
        return R.layout.fragment_learn;
    }

    @Override
    protected void initialize() {
        tvEverydayEnglish.performLongClick();
        llEveryDayEnglish.performClick();
    }

    //重置所有文本的选中状态
    private void setSelected(){
        tvEverydayEnglish.setSelected(false);
        tvQuestionBank.setSelected(false);

        llEveryDayEnglish.setSelected(false);
        llQuestionBank.setSelected(false);
    }

    @OnClick({R.id.ll_everyday_english, R.id.ll_question_bank})
    public void onClick(View view){
        fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        switch (view.getId()){
            case R.id.ll_everyday_english:
                setSelected();
                tvEverydayEnglish.setSelected(true);
                llEveryDayEnglish.setSelected(true);
                transaction.add(R.id.fragment_learn, EverydayEnglishFragment.getInstance());
                break;
            case R.id.ll_question_bank:
                setSelected();
                tvQuestionBank.setSelected(true);
                llQuestionBank.setSelected(true);
                transaction.add(R.id.fragment_learn, QuestionBankFragment.getInstance());
                break;
            default:
        }
        List<Fragment> fragments = fm.getFragments();
        for (Fragment fragment: fragments){
            transaction.remove(fragment);
        }
        transaction.commit();
    }
}
