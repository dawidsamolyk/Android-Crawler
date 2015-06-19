package edu.uz.crawler.view.main.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import edu.uz.crawler.R;
import edu.uz.crawler.db.CrawledData;
import edu.uz.crawler.db.CrawledPage;
import edu.uz.crawler.db.DatabaseHelper;

public class HistoryFragment extends Fragment {
	private DatabaseHelper databaseHelper;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_history, container, false);

		databaseHelper = new DatabaseHelper(getActivity(), new CrawledData());
		
		ListView list = (ListView) rootView.findViewById(R.id.historyList);
		
		int[] rowsInListView = { R.id.mainRow, R.id.secondRow, R.id.thirdRow, R.id.fourthRow };
		SimpleCursorAdapter adapter = databaseHelper.cursorAdapter(getActivity(), rowsInListView);
		list.setAdapter(adapter);

		// EXAMPLE DATA
		databaseHelper.insert(new CrawledPage("14-01-2015", "wp.pl", "Jakis tytul strony", "Temat 1;Temat 2",
				"Tutaj zawartosc strony..."));
		databaseHelper.insert(new CrawledPage("14-01-2015", "wp.pl", "Tytul jakiejs tam strony", "Pies;Kot",
				"TPsy i koty cos tam costa m..."));
		databaseHelper.insert(new CrawledPage("16-01-2015", "onet.pl", "jadsojdas", "gar 1;dasdas 2",
				"fasda rqwqwfa stsadrony..."));
		databaseHelper.insert(new CrawledPage("18-01-2015", "google.pl", "dziwne ze to sciagnelo", "Temat 1;Temat 2",
				"q dasdf stro123213ny..."));

		return rootView;
	}

	@Override
	public void onDestroy() {
		databaseHelper.close();
		super.onDestroy();
	}
}
