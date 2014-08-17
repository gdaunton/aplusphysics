package com.rhosoft.aplusphysics.fragments;

import com.actionbarsherlock.app.SherlockFragment;
import com.rhosoft.aplusphysics.R;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class CommunityFragment extends SherlockFragment {
	public static CommunityFragment newInstance() {
		return new CommunityFragment();
	}

	private WebView webView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.community, container, false);
	}

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public void onViewCreated(View view, Bundle saved) {
		super.onViewCreated(view, saved);
		webView = (WebView) getView().findViewById(R.id.webView);
		webView.loadUrl("http://www.aplusphysics.com/community");
		webView.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				try{
					ProgressBar bar = ((ProgressBar)getView().findViewById(R.id.progressBar));
					if(bar != null)
						bar.setProgress(progress);
				}catch(Exception e){}
			}
		});
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.setWebViewClient(new WebViewClient());
	}

	public boolean onKeyDown(int keyCode) {

		if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
			webView.goBack();
			return true;
		} else
			return false;
	}
}
