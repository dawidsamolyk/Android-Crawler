package edu.uz.crawler.view.main.fragments;

import java.util.ArrayList;

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
	private ArrayList<String> list = new ArrayList<String>();
	private Context context;

	public TopicsListAdapter(ArrayList<String> list, Context context) {
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int pos) {
		return list.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		// return list.get(pos).getId();
		return 0;
		// just return 0 if your list items do not have an Id variable.
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.topic_list_item, null);
		}

		// Handle TextView and display string from your list
		TextView listItemText = (TextView) view.findViewById(R.id.topicText);
		listItemText.setText(list.get(position));

		// Handle buttons and add onClickListeners
		Button deleteBtn = (Button) view.findViewById(R.id.deleteTopicButton);

		deleteBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// do something
				list.remove(position); // or some other task
				notifyDataSetChanged();
			}
		});

		return view;
	}
}
