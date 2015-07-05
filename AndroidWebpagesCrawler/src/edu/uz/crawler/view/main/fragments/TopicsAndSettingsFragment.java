package edu.uz.crawler.view.main.fragments;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import edu.uz.crawler.CrawlingJob;
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

	public TopicsAndSettingsFragment() {
		super();
	}

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

		return rootView;
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

		final Button addTopic = (Button) rootView.findViewById(R.id.startButton);
		addTopic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CrawlingSettings settings = null;

				try {
					settings = new CrawlingSettings(webpageFragment.getWebpageUrl(), topicsListAdapter.getAllTopics(),
							crawlingOptions);
				} catch (IllegalArgumentException e) {
					Log.e("EXCEPTION", e.getMessage());
				}

				try {
					crawlingJob.start(settings);
				} catch (IllegalArgumentException e) {
					Log.e("EXCEPTION", e.getMessage());
				}
			}

		});
	}

	@Override
	public void onDestroy() {
		databaseHelper.close();
		super.onDestroy();
	}
}
