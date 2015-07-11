package edu.uz.crawler.view.main.fragments.settings;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import edu.uz.crawler.R;

public class TopicsListAdapter extends BaseAdapter implements ListAdapter {
	private final ArrayList<String> list = new ArrayList<String>();
	private final Context context;

	public TopicsListAdapter(final Context context) throws IllegalArgumentException {
		if (context != null) {
			this.context = context;
		} else {
			throw new IllegalArgumentException();
		}
	}

	@SuppressLint("NewApi")
	public void add(final String item) {
		if (item != null && !item.isEmpty() && !list.contains(item)) {
			list.add(item);
			notifyDataSetChanged();
		}
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public String getItem(final int pos) {
		return list.get(pos);
	}

	@Override
	public long getItemId(final int pos) {
		return list.get(pos).hashCode();
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent) {
		View view = convertView;

		if (view == null) {
			final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.topic_list_item, null);
		}

		final TextView listItemText = (TextView) view.findViewById(R.id.topicText);
		listItemText.setText(list.get(position));

		final Button deleteBtn = (Button) view.findViewById(R.id.deleteTopicButton);
		deleteBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
				list.remove(position);
				notifyDataSetChanged();
			}
		});

		return view;
	}

	public ArrayList<String> getAllTopics() {
		final int topicsCount = getCount();
		final ArrayList<String> result = new ArrayList<String>(topicsCount);

		for (int topicPosition = 0; topicPosition < topicsCount; topicPosition++) {
			result.add(getItem(topicPosition));
		}

		return result;
	}
}
