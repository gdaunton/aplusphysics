package com.rhosoft.aplusphysics;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

public class FeedDetail extends SherlockActivity {
	
	private WebView webView;

	public void onBackPressed() {
		finish();
	}

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.community);
		String title = getIntent().getStringExtra("Title");
		String subTitle = getIntent().getStringExtra("SubTitle");
		String URL = getIntent().getStringExtra("URL");
		webView = (WebView)findViewById(R.id.webView);
		webView.loadUrl(URL);
		webView.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				((ProgressBar)findViewById(R.id.progressBar)).setProgress(progress);
			}
		});
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.setWebViewClient(new WebViewClient());
		getSupportActionBar().setTitle(title);
		getSupportActionBar().setSubtitle(subTitle);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
		switch (paramMenuItem.getItemId()) {
		default:
			return super.onOptionsItemSelected(paramMenuItem);
		case android.R.id.home:
			finish();
			return true;
		}

	}
}