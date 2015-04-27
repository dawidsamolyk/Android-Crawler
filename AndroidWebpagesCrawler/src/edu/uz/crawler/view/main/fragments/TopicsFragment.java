package edu.uz.crawler.view.main.fragments;

import java.util.ArrayList;

import edu.uz.crawler.R;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class TopicsFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_topics, container,
				false);

		ListView list = (ListView) rootView.findViewById(R.id.listView);
		final ArrayList<String> sampleTopics = new ArrayList<String>();
		// XXX Sample data
		sampleTopics.add("Topic 1");
		sampleTopics.add("Topic 2");
		sampleTopics.add("Topic 3");
		list.setAdapter(new TopicsListAdapter(sampleTopics, rootView
				.getContext()));

		return rootView;
	}
}
