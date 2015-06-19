package edu.uz.crawler.view.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import edu.uz.crawler.R;
import edu.uz.crawler.db.CrawledData;
import edu.uz.crawler.db.DatabaseHelper;

@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity {
	private DatabaseHelper databaseHelper;
	private final String[] tabs = { "Webpage", "Topics", "History" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
		final TabsPagerAdapter tabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(tabsPagerAdapter);
		viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener());

		final ActionBarManager manager = new ActionBarManager(getActionBar());
		final CrawlerTabListener crawlerTabListener = new CrawlerTabListener(viewPager);
		manager.createTabs(tabs, crawlerTabListener);

		databaseHelper = new DatabaseHelper(this, new CrawledData());
	}

	@Override
	protected void onDestroy() {
		databaseHelper.close();
		super.onDestroy();
	}

	public void onSave(final View view) {
		databaseHelper.insert(null); // TODO
	}

}
