package edu.uz.crawler.view.main.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import edu.uz.crawler.R;

public class TopicsFragment extends Fragment {

	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_topics, container,
				false);

		configureTopicsArea(rootView);

		return rootView;
	}

	private void configureTopicsArea(final View rootView) {
		final ListView topicsList = (ListView) rootView
				.findViewById(R.id.topicsList);
		final TopicsListAdapter topicsListAdapter = new TopicsListAdapter(
				rootView.getContext());

		topicsList.setAdapter(topicsListAdapter);

		final EditText newTopicName = (EditText) rootView
				.findViewById(R.id.newTopicName);
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
}
