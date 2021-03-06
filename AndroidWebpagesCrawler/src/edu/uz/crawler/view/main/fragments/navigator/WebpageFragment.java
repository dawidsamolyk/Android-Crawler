package edu.uz.crawler.view.main.fragments.navigator;

import java.io.Serializable;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import edu.uz.crawler.R;

public class WebpageFragment extends Fragment implements Serializable {
	private static final long serialVersionUID = 5990770478494895878L;
	private WebView webpageView;

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_webpage, container, false);

		webpageView = (WebView) rootView.findViewById(R.id.webpageView);
		final EditText webpageAddress = (EditText) rootView.findViewById(R.id.webpageAddressInput);
		final Button backButton = (Button) rootView.findViewById(R.id.backButton);

		WebpagesNavigatorHandler webpagesNavigatorHandler = new WebpagesNavigatorHandler(webpageView);

		webpageView.setWebViewClient(webpagesNavigatorHandler);
		webpageAddress.setOnKeyListener(webpagesNavigatorHandler);
		backButton.setOnClickListener(webpagesNavigatorHandler);

		final ProgressBar progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);

		webpageView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int progress) {
				if (progress < 100 && progressBar.getVisibility() == ProgressBar.GONE) {
					progressBar.setVisibility(ProgressBar.VISIBLE);
				}
				progressBar.setProgress(progress);
				if (progress == 100) {
					progressBar.setVisibility(ProgressBar.GONE);
				}
			}

		});
		
		webpageView.setWebViewClient(new WebViewClient() {

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				webpageAddress.setText(url);
				super.onPageStarted(view, url, favicon);
			}
			
		});

		return rootView;
	}

	public String getWebpageUrl() {
		return webpageView.getUrl();
	}
}
