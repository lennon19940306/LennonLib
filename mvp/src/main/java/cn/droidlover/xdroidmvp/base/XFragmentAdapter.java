package cn.droidlover.xdroidmvp.base;


import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Created by wanglei on 2016/12/10.
 */

public class XFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList = new ArrayList<>();
    private String[] titles;

    public XFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList, String[] titles) {
        super(fm);
        this.fragmentList.clear();
        this.fragmentList.addAll(fragmentList);
        this.titles = titles;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titles != null && titles.length > position) {
            return titles[position];
        }
        return "";
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
