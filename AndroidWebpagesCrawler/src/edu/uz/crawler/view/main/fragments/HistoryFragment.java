package edu.uz.crawler.view.main.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import edu.uz.crawler.R;
import edu.uz.crawler.db.CrawledData;
import edu.uz.crawler.db.CrawledPage;
import edu.uz.crawler.db.DatabaseHelper;
import edu.uz.crawler.view.main.CrawledPageActivity;

public class HistoryFragment extends Fragment {
	public static final String WEBPAGE_CONTENT = "WEBPAGE_CONTENT";
	private DatabaseHelper databaseHelper;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		databaseHelper = new DatabaseHelper(getActivity());

		View rootView = inflater.inflate(R.layout.fragment_history, container, false);

		ListView list = (ListView) rootView.findViewById(R.id.historyList);

		int[] rowsInListView = { R.id.mainRow, R.id.secondRow, R.id.thirdRow, R.id.fourthRow, R.id.crawledPageId };
		final SimpleCursorAdapter adapter = databaseHelper.cursorAdapter(getActivity(), rowsInListView);
		list.setAdapter(adapter);

		CrawledPage page = new CrawledPage("date", "URL", "Tytul", "tematy", "cos tam<br /><br />cos innego");
		databaseHelper.insert(page);

		// Akcja po "dotkniêciu" elementu pobranej strony
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> a, View v, int position, long id) {
				Cursor cursor = (Cursor) adapter.getItem(position);
				int crawledPageId = cursor.getInt(cursor.getColumnIndex(CrawledData.ID));
				cursor.close();

				String pageContent = databaseHelper.getContentFromPageWithId(crawledPageId);

				Intent intent = new Intent(HistoryFragment.this.getActivity(), CrawledPageActivity.class);
				intent.putExtra(WEBPAGE_CONTENT, pageContent);
				startActivity(intent);
			}
		});

		// Akcja po "dotkniêciu" i przytrzymaniu elementu pobranej strony
		list.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> a, View v, int position, long id) {
				Cursor cursor = (Cursor) adapter.getItem(position);
				int crawledPageId = cursor.getInt(cursor.getColumnIndex(CrawledData.ID));
				cursor.close();

				databaseHelper.delete(crawledPageId);
				adapter.notifyDataSetChanged();

				return true;
			}
		});

		final Button deleteAllHistory = (Button) rootView.findViewById(R.id.deleteAllHistory);
		deleteAllHistory.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				databaseHelper.deleteAll();
				adapter.notifyDataSetChanged();
			}
		});

		return rootView;
	}

	@Override
	public void onDestroy() {
		databaseHelper.close();
		super.onDestroy();
	}
}
