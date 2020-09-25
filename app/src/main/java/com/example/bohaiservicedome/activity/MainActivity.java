package com.example.bohaiservicedome.activity;

import android.view.KeyEvent;
import android.widget.FrameLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.bohaiservicedome.R;
import com.example.bohaiservicedome.base.BaseActivity;
import com.example.bohaiservicedome.fragment.HomeFragment;
import com.example.bohaiservicedome.fragment.LearnFragment;
import com.example.bohaiservicedome.fragment.ProfileFragment;
import com.example.bohaiservicedome.fragment.ServiceFragment;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {

    @BindView(R.id.fragment_result)
    FrameLayout frameLayout;
    @BindView(R.id.bottom_bar)
    BottomNavigationBar bottomBar;
    private HomeFragment mHomeFragment;
    private LearnFragment mLearnFragment;
    private ProfileFragment mProfileFragment;
    private ServiceFragment mServiceFragment;

    @Override
    protected int contentViewID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initialize() {
        setTopTitle(getResources().getString(R.string.t_home), true);
        setLeftBtn(false, 0, null);

        bottomBar.setTabSelectedListener(this);
        bottomBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);
        bottomBar.addItem(new BottomNavigationItem(ContextCompat.getDrawable(this, R.mipmap.icon_home_press),
                getString(R.string.t_home)).setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.icon_home)))
                .addItem(new BottomNavigationItem(ContextCompat.getDrawable(this, R.mipmap.icon_discover_press),
                        getString(R.string.t_serive)).setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.icon_discover)))
                .addItem(new BottomNavigationItem(ContextCompat.getDrawable(this, R.mipmap.icon_hot_press),
                        getString(R.string.t_learn)).setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.icon_hot)))
                .addItem(new BottomNavigationItem(ContextCompat.getDrawable(this, R.mipmap.icon_user_press),
                        getString(R.string.t_person)).setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.icon_user)))
                .setFirstSelectedPosition(0)
                .initialise();
        setDefaultFragment();
    }

    private void setDefaultFragment() {
        mHomeFragment = (HomeFragment) HomeFragment.getInstance();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fragment_result, mHomeFragment);
        transaction.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
        }
        return true;
    }

    @Override
    public void onTabSelected(int position) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideAllFragment(transaction);
        switch (position) {
            case 0:
                setTopTitle(getResources().getString(R.string.t_home), true);
                if (mHomeFragment == null) {
                    mHomeFragment = (HomeFragment) HomeFragment.getInstance();
                    transaction.add(R.id.fragment_result, mHomeFragment);
                } else {
                    transaction.show(mHomeFragment);
                }

                break;
            case 1:
                setTopTitle(getResources().getString(R.string.t_serive), true);
                if (mServiceFragment == null) {
                    mServiceFragment = (ServiceFragment) ServiceFragment.getInstance();
                    transaction.add(R.id.fragment_result, mServiceFragment);
                } else {
                    transaction.show(mServiceFragment);
                }

                break;
            case 2:
                setTopTitle(getResources().getString(R.string.t_learn), true);
                if (mLearnFragment == null) {
                    mLearnFragment = (LearnFragment) LearnFragment.getInstance();
                    transaction.add(R.id.fragment_result, mLearnFragment);
                } else {
                    transaction.show(mLearnFragment);
                }
                break;
            case 3:
                setTopTitle(getResources().getString(R.string.t_person), true);
                if (mProfileFragment == null) {
                    mProfileFragment = (ProfileFragment) ProfileFragment.getInstance();
                    transaction.add(R.id.fragment_result, mProfileFragment);
                } else {
                    transaction.show(mProfileFragment);
                }

                break;
            default:
        }
        transaction.commit();

    }

    private void hideAllFragment(FragmentTransaction transaction) {
        if (mHomeFragment != null) {
            transaction.hide(mHomeFragment);
        }
        if (mServiceFragment != null) {
            transaction.hide(mServiceFragment);
        }
        if (mLearnFragment != null) {
            transaction.hide(mLearnFragment);
        }
        if (mProfileFragment != null) {
            transaction.hide(mProfileFragment);
        }

    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}
