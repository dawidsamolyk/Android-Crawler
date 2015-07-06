package edu.uz.crawler.view.main;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import edu.uz.crawler.R;
import edu.uz.crawler.view.main.fragments.HistoryFragment;

public class CrawledPageActivity extends Activity {
	private static final String MIMIE_TYPE = "text/html";
	private static final String ENCODING = "UTF-8";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history_page);
		
		final String webpageContentToView = getIntent().getStringExtra(HistoryFragment.WEBPAGE_CONTENT);
		
		final WebView webView = (WebView) findViewById(R.id.historyWebView);
		webView.loadDataWithBaseURL("", webpageContentToView, MIMIE_TYPE, ENCODING, "");
	}
}
