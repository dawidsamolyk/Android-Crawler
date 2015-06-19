package edu.uz.crawler.view.main.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import edu.uz.crawler.R;
import edu.uz.crawler.view.main.fragments.navigator.WebpagesNavigatorHandler;

public class WebpageFragment extends Fragment {
	private WebView webpageView;

	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState) {

		final View rootView = inflater.inflate(R.layout.fragment_webpage,
				container, false);

		webpageView = (WebView) rootView.findViewById(R.id.webpageView);
		final EditText webpageAddress = (EditText) rootView
				.findViewById(R.id.webpageAddressInput);
		final Button backButton = (Button) rootView
				.findViewById(R.id.backButton);

		WebpagesNavigatorHandler webpagesNavigatorHandler = new WebpagesNavigatorHandler(
				webpageView);

		webpageView.setWebViewClient(webpagesNavigatorHandler);
		webpageAddress.setOnKeyListener(webpagesNavigatorHandler);
		backButton.setOnClickListener(webpagesNavigatorHandler);

		return rootView;
	}

	public String getWebpageUrl() {
		return webpageView.getUrl();
	}
}
