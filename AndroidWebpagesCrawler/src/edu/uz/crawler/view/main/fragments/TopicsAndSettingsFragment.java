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
import edu.uz.crawler.R;
import edu.uz.crawler.view.main.fragments.settings.CrawlingOption;

public class TopicsAndSettingsFragment extends Fragment {
	private View rootView;
	private final WebpageFragment webpageFragment;
	private TopicsListAdapter topicsListAdapter;
	private Map<CrawlingOption, Boolean> crawlingOptions = new HashMap<CrawlingOption, Boolean>();

	public TopicsAndSettingsFragment(final WebpageFragment webpageFragment) {
		this.webpageFragment = webpageFragment;
	}

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
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
		final Button addTopic = (Button) rootView.findViewById(R.id.startButton);
		addTopic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					CrawlingJob job = new CrawlingJob(webpageFragment.getWebpageUrl(),
							topicsListAdapter.getAllTopics(), crawlingOptions);
					job.start();
				} catch (IllegalArgumentException e) {
					Log.e("EXCEPTION", e.getMessage());
				}
			}

		});
	}
}
