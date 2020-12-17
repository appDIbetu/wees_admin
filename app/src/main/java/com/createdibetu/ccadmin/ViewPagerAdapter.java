package com.createdibetu.ccadmin;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    FragmentManager fa;
    //Constructor to the class

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        fa = fm;
        //Initializing tab count
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {

            case 0:
                return new WebViewFragment();
            case 1:
                return new PostFeed();
            case 2:
                return new RecyclerList();
            case 3:
                return new ImageLinkFragment();
            default:
                return new WebViewFragment();

        }
    }

    @Override
    public int getCount() {
        return 4;
    }

}
