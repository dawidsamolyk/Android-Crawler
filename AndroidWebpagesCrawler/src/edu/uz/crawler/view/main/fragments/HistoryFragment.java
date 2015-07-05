package edu.uz.crawler.view.main.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import edu.uz.crawler.R;
import edu.uz.crawler.db.DatabaseHelper;

public class HistoryFragment extends Fragment {
	private DatabaseHelper databaseHelper;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		databaseHelper = new DatabaseHelper(getActivity());
		databaseHelper.deleteAll();

		View rootView = inflater.inflate(R.layout.fragment_history, container, false);

		ListView list = (ListView) rootView.findViewById(R.id.historyList);

		int[] rowsInListView = { R.id.mainRow, R.id.secondRow, R.id.thirdRow, R.id.fourthRow };
		SimpleCursorAdapter adapter = databaseHelper.cursorAdapter(getActivity(), rowsInListView);
		list.setAdapter(adapter);

		return rootView;
	}

	@Override
	public void onDestroy() {
		databaseHelper.close();
		super.onDestroy();
	}
}
