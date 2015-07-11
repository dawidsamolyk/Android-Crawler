package edu.uz.crawler.view.main.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import edu.uz.crawler.CrawlingJob;
import edu.uz.crawler.CrawlingResultProvider;
import edu.uz.crawler.CrawlingSettings;
import edu.uz.crawler.R;
import edu.uz.crawler.db.DatabaseHelper;
import edu.uz.crawler.view.main.fragments.settings.CrawlingOption;

public class TopicsAndSettingsFragment extends Fragment {
	public static final String WEBPAGE_FRAGMENT_BUNDLE_NAME = "WebpageFragment";
	private WebpageFragment webpageFragment;
	private DatabaseHelper databaseHelper;
	private View rootView;
	private TopicsListAdapter topicsListAdapter;
	private Map<CrawlingOption, Boolean> crawlingOptions = new HashMap<CrawlingOption, Boolean>();
	private Button startCrawler;
	private Button stopCrawler;
	private TextView crawlerStatus;

	public void setArguments(Bundle args) {
		webpageFragment = (WebpageFragment) args.get(WEBPAGE_FRAGMENT_BUNDLE_NAME);
	}

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
		this.databaseHelper = new DatabaseHelper(getActivity());

		rootView = inflater.inflate(R.layout.fragment_topics_settings, container, false);

		configureTopicsArea();
		configureSettingsArea();
		configureStartButton();

		registerCrawlingResultReceiverIn(getActivity());

		return rootView;
	}

	private void registerCrawlingResultReceiverIn(final FragmentActivity fragmentActivity) {
		final IntentFilter intentFilter = new IntentFilter(CrawlingResultProvider.ACTION);
		intentFilter.addCategory(Intent.CATEGORY_DEFAULT);

		final BroadcastReceiver receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				int crawledPages = intent.getIntExtra(CrawlingResultProvider.PAGES_NUMBER, 0);
				showCrawlingEnd(crawledPages);
			}
		};
		fragmentActivity.registerReceiver(receiver, intentFilter);
	}

	private void configureTopicsArea() {
		final ListView topicsList = (ListView) rootView.findViewById(R.id.topicsList);
		topicsListAdapter = new TopicsListAdapter(rootView.getContext());

		topicsList.setAdapter(topicsListAdapter);

		final EditText newTopicName = (EditText) rootView.findViewById(R.id.newTopicName);
		final Button addTopic = (Button) rootView.findViewById(R.id.addTopic);
		addTopic.setOnClickListener(new OnClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onClick(final View view) {
				String newTopic = newTopicName.getText().toString();
				topicsListAdapter.add(newTopic);
			}
		});
	}

	private void configureSettingsArea() {
		observeState(R.id.searchAlsoInSubpages, CrawlingOption.searchAlsoInSubpages);
		observeState(R.id.contentSearch, CrawlingOption.contentSearch);
		observeState(R.id.requireAllTopicsOnOnePage, CrawlingOption.requireAllTopicsOnOnePage);
	}

	private void observeState(final int checkBoxID, final CrawlingOption option) {
		final CheckBox searchAlsoInSubpages = (CheckBox) rootView.findViewById(checkBoxID);
		this.crawlingOptions.put(option, searchAlsoInSubpages.isChecked());
		searchAlsoInSubpages.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
				TopicsAndSettingsFragment.this.crawlingOptions.put(option, isChecked);
			}
		});
	}

	private void configureStartButton() {
		final CrawlingJob crawlingJob = new CrawlingJob(getActivity(), databaseHelper);
		crawlerStatus = (TextView) rootView.findViewById(R.id.crawlerStatus);
		crawlerStatus.setText("Not working");
		startCrawler = (Button) rootView.findViewById(R.id.startCrawler);
		stopCrawler = (Button) rootView.findViewById(R.id.stopCrawler);
		stopCrawler.setEnabled(false);

		startCrawler.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String webpageUrl = webpageFragment.getWebpageUrl();
				ArrayList<String> topics = topicsListAdapter.getAllTopics();

				try {
					CrawlingSettings settings = new CrawlingSettings(webpageUrl, topics, crawlingOptions);
					crawlingJob.start(settings);
					crawlerStatus.setText("Working...");

					startCrawler.setEnabled(false);
					stopCrawler.setEnabled(true);

				} catch (IllegalArgumentException e) {
					Log.e("EXCEPTION", e.getMessage());
					crawlerStatus.setText(e.getMessage());
				}
			}
		});

		stopCrawler.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				CrawlingResultProvider.stopCrawler = true;
				crawlerStatus.setText("Stopping...");
				startCrawler.setEnabled(false);
				stopCrawler.setEnabled(false);
			}
		});
	}

	public void showCrawlingEnd(int crawledPages) {
		if (startCrawler != null) {
			startCrawler.setEnabled(true);
		}
		if (stopCrawler != null) {
			stopCrawler.setEnabled(false);
		}
		if (crawlerStatus != null) {
			crawlerStatus.setText("Downloaded pages: " + crawledPages);
		}
	}

	@Override
	public void onDestroy() {
		databaseHelper.close();
		super.onDestroy();
	}
}
