package com.rhosoft.aplusphysics;


import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class VideoActivity extends SherlockFragmentActivity implements YouTubePlayer.OnFullscreenListener{
	
	public static final String DEVELOPER_KEY = "YOUR_DEV_KEY_HERE";
	private ActionBarPaddedFrameLayout viewContainer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_video);

		final String URL = getIntent().getStringExtra("URL");
		String title = getIntent().getStringExtra("Title");
		
		FragmentManager fragmentManager = getSupportFragmentManager();
	    FragmentTransaction fragmentTransaction = fragmentManager
	            .beginTransaction();
	    
	    YouTubePlayerSupportFragment fragment = new YouTubePlayerSupportFragment();
	    fragmentTransaction.add(R.id.fragmentz, fragment);
	    fragmentTransaction.commit();

	    fragment.initialize(DEVELOPER_KEY,
	            new OnInitializedListener() {

					@Override
					public void onInitializationFailure(Provider arg0,
							YouTubeInitializationResult arg1) {
					}

					@Override
					public void onInitializationSuccess(Provider arg0,
							YouTubePlayer arg1, boolean arg2) {
	                    if (!arg2) {
	                    	arg1.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
	                    	arg1.setOnFullscreenListener(VideoActivity.this);
	                        arg1.loadVideo(URL);
	                        arg1.setFullscreen(true);
	                    }
					}

	            });
		getSupportActionBar().setTitle(title);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xAA000000));
	    
		viewContainer = (ActionBarPaddedFrameLayout)findViewById(R.id.view_container);
		viewContainer.setActionBar(getSupportActionBar());
	}
	
	  @Override
	  public void onFullscreen(boolean fullscreen) {
	    viewContainer.setEnablePadding(!fullscreen);

	    ViewGroup.LayoutParams playerParams = findViewById(R.id.fragmentz).getLayoutParams();
	    if (fullscreen) {
	      playerParams.width = MATCH_PARENT;
	      playerParams.height = MATCH_PARENT;
	    } else {
	      playerParams.width = 0;
	      playerParams.height = WRAP_CONTENT;
	    }
	  }
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	finish();
	    	return true;
	    }else
	    	return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
		switch (paramMenuItem.getItemId()){
		default:
			return super.onOptionsItemSelected(paramMenuItem);
		case android.R.id.home:
			finish();
			return true;
		}
	}

	  public static final class ActionBarPaddedFrameLayout extends FrameLayout {

	    private ActionBar actionBar;
	    private boolean paddingEnabled;

	    public ActionBarPaddedFrameLayout(Context context) {
	      this(context, null);
	    }

	    public ActionBarPaddedFrameLayout(Context context, AttributeSet attrs) {
	      this(context, attrs, 0);
	    }

	    public ActionBarPaddedFrameLayout(Context context, AttributeSet attrs, int defStyle) {
	      super(context, attrs, defStyle);
	      paddingEnabled = true;
	    }

	    public void setActionBar(ActionBar actionBar) {
	      this.actionBar = actionBar;
	      requestLayout();
	    }

	    public void setEnablePadding(boolean enable) {
	      paddingEnabled = enable;
	      requestLayout();
	    }

	    @Override
	    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	      int topPadding =
	          paddingEnabled && actionBar != null && actionBar.isShowing() ? actionBar.getHeight() : 0;
	      setPadding(0, topPadding, 0, 0);

	      super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	    }

	  }
}
