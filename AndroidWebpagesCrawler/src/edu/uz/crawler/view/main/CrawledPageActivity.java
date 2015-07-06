package edu.uz.crawler.view.main;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import edu.uz.crawler.R;

public class CrawledPageActivity extends Activity {
	public static final String WEBPAGE_CONTENT = "WEBPAGE_CONTENT";
	private static final String MIMIE_TYPE = "text/html";
	private static final String ENCODING = "UTF-8";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history_page);
		
		final String webpageContentToView = savedInstanceState.getString(WEBPAGE_CONTENT);
		
		final WebView webView = (WebView) findViewById(R.id.historyWebView);
		webView.loadDataWithBaseURL("", webpageContentToView, MIMIE_TYPE, ENCODING, "");
	}
}
