package edu.uz.crawler.view.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import edu.uz.crawler.view.main.fragments.HistoryFragment;
import edu.uz.crawler.view.main.fragments.TopicsFragment;
import edu.uz.crawler.view.main.fragments.WebpageFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {
    public TabsPagerAdapter(FragmentManager fragmentManager) {
	super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
	switch (position) {
	    case 0:
		return new WebpageFragment();
	    case 1:
		return new TopicsFragment();
	    case 2:
		return new HistoryFragment();
	}

	return null;
    }

    @Override
    public int getCount() {
	return 3;
    }
}
