package edu.uz.crawler.view.main.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import edu.uz.crawler.R;
import edu.uz.crawler.db.CrawledData;
import edu.uz.crawler.db.DatabaseHelper;

public class HistoryFragment extends Fragment {
	private static final String MIMIE_TYPE = "text/html";
	private static final String ENCODING = "UTF-8";
	public static final String WEBPAGE_CONTENT = "WEBPAGE_CONTENT";
	private DatabaseHelper databaseHelper;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		databaseHelper = new DatabaseHelper(getActivity());

		View rootView = inflater.inflate(R.layout.fragment_history, container, false);

		final ListView list = (ListView) rootView.findViewById(R.id.historyList);

		int[] rowsInListView = { R.id.mainRow, R.id.secondRow, R.id.thirdRow, R.id.fourthRow, R.id.crawledPageId };
		final SimpleCursorAdapter adapter = databaseHelper.cursorAdapter(getActivity(), rowsInListView);
		list.setAdapter(adapter);

		final Button deleteAllHistory = (Button) rootView.findViewById(R.id.deleteAllHistory);
		deleteAllHistory.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				databaseHelper.deleteAll();
				adapter.notifyDataSetChanged();
			}
		});

		final WebView webView = (WebView) rootView.findViewById(R.id.historyWebView);
		final Button backButton = (Button) rootView.findViewById(R.id.historyWebViewBackButton);
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg) {
				webView.setVisibility(View.INVISIBLE);
				backButton.setVisibility(View.INVISIBLE);
				list.setVisibility(View.VISIBLE);
				deleteAllHistory.setVisibility(View.VISIBLE);
			}
		});

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> a, View v, int position, long id) {
				TextView t = (TextView) v.findViewById(R.id.crawledPageId);
				int crawledPageId = Integer.parseInt(t.getText().toString());
				String pageContent = databaseHelper.getContentFromPageWithId(crawledPageId);

				list.setVisibility(View.INVISIBLE);
				deleteAllHistory.setVisibility(View.INVISIBLE);
				webView.setVisibility(View.VISIBLE);
				backButton.setVisibility(View.VISIBLE);

				webView.loadDataWithBaseURL("", pageContent, MIMIE_TYPE, ENCODING, "");
			}
		});

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

		return rootView;
	}

	@Override
	public void onDestroy() {
		databaseHelper.close();
		super.onDestroy();
	}
}
