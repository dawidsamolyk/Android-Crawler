package edu.uz.crawler.view.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import edu.uz.crawler.view.main.fragments.HistoryFragment;
import edu.uz.crawler.view.main.fragments.TopicsAndSettingsFragment;
import edu.uz.crawler.view.main.fragments.WebpageFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {
	private final WebpageFragment webpageFragment = new WebpageFragment();
	private final TopicsAndSettingsFragment topicsFragment = new TopicsAndSettingsFragment();
	private final HistoryFragment historyFragment = new HistoryFragment();
	
	public TabsPagerAdapter(final FragmentManager fragmentManager) {
		super(fragmentManager);
	}

	@Override
	public Fragment getItem(final int position) {
		switch (position) {
		case 0:
			return webpageFragment;
		case 1:
			return topicsFragment;
		case 2:
			return historyFragment;
		}

		return null;
	}

	@Override
	public int getCount() {
		return 3;
	}
}
