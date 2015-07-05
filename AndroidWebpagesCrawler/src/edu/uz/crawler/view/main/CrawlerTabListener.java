package edu.uz.crawler.view.main;

import android.annotation.SuppressLint;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

@SuppressLint("NewApi")
public class CrawlerTabListener implements TabListener {
	private final ViewPager viewPager;

	public CrawlerTabListener(final ViewPager viewPager) {
		this.viewPager = viewPager;
	}

	@Override
	public void onTabReselected(final Tab tab, final FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(final Tab tab, final FragmentTransaction ft) {
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(final Tab tab, final FragmentTransaction ft) {
	}
}
