package edu.uz.crawler.view.main;

import android.annotation.SuppressLint;
import android.app.ActionBar;

@SuppressLint("NewApi")
public class ActionBarManager {
	private final ActionBar actionBar;

	public ActionBarManager(final ActionBar actionBar) {
		this.actionBar = actionBar;
		initialize();
	}

	private void initialize() {
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	}

	public void createTabs(final String[] tabs, final CrawlerTabListener crawlerTabListener) {
		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name).setTag(tab_name).setTabListener(crawlerTabListener));
		}
	}

}
