package edu.uz.crawler.view.main.fragments.navigator;

import android.annotation.SuppressLint;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

@SuppressLint("NewApi")
public class WebpagesNavigatorHandler extends WebViewClient implements OnClickListener, OnKeyListener {
	private final WebView webpageView;
	private String actualUrl;

	public WebpagesNavigatorHandler(final WebView webpageView) {
		this.webpageView = webpageView;
	}

	@Override
	public void onClick(final View v) {
		webpageView.goBack();
	}

	@Override
	public boolean onKey(final View view, final int keyCode, final KeyEvent event) {
		final EditText webpageAddressInput = (EditText) view;
		final String webpageAddress = webpageAddressInput.getText().toString();

		if (keyCode == KeyEvent.KEYCODE_ENTER && webpageAddress != null && webpageAddress.length() > 0) {
			if (!webpageAddress.startsWith("http://")) {
				actualUrl = "http://" + webpageAddress;
			} else {
				actualUrl = webpageAddress;
			}
			
			webpageView.loadUrl(actualUrl);
			webpageView.requestFocus();
			
			return true;
		}
		return false;
	}

	@Override
	public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
		return (false);
	}

}
