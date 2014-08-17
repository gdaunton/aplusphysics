package com.rhosoft.aplusphysics.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import com.actionbarsherlock.app.SherlockFragment;
import com.rhosoft.aplusphysics.R;

public class AboutFragment extends SherlockFragment{
	public static CommunityFragment newInstance() {
		return new CommunityFragment();
	}

	private WebView webView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.about, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle saved) {
		super.onViewCreated(view, saved);
		
		Button aplus = (Button)getView().findViewById(R.id.aplus);
	    Button rating = (Button)getView().findViewById(R.id.rating);
	    aplus.setOnClickListener(new View.OnClickListener()
	    {
	      public void onClick(View paramAnonymousView)
	      {
	        getActivity().startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://www.aplusphysics.com")));
	      }
	    });
	    rating.setOnClickListener(new View.OnClickListener()
	    {
	      public void onClick(View paramAnonymousView)
	      {
	    	  getActivity().startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + "com.rhosoft.aplusphysics")));
	      }
	    });
	    
	}

	public boolean onKeyDown(int keyCode) {

		if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
			webView.goBack();
			return true;
		} else
			return false;
	}
}
