package com.rhosoft.aplusphysics;

import java.util.ArrayList;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.nineoldandroids.animation.ArgbEvaluator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.rhosoft.aplusphysics.fragments.AboutFragment;
import com.rhosoft.aplusphysics.fragments.CommunityFragment;
import com.rhosoft.aplusphysics.fragments.RssFragment;
import com.rhosoft.aplusphysics.fragments.VideoFragment;
import com.rhosoft.aplusphysics.items.DrawerAdapter;
import com.rhosoft.aplusphysics.items.DrawerObject;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends SherlockFragmentActivity {

	private ListView mDrawerList;
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private ArrayList<DrawerObject> drawerItems;
	private Fragment fragment;
	private FragmentManager fragmentManager;
	public int selectedItem;
	private int currentColor = Color.rgb(255, 166, 66);
	public static Typeface altFont;
	public static Typeface titleFont;
	public static Typeface subTitleFont;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		titleFont = Typeface.createFromAsset(getAssets(),"Roboto-Regular.ttf");
		subTitleFont = Typeface.createFromAsset(getAssets(),"Roboto-Light.ttf");
		altFont = Typeface.createFromAsset(getAssets(),"Roboto-Thin.ttf");
		
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		drawerItems = new ArrayList<DrawerObject>();
		drawerItems.add(new DrawerObject(R.drawable.ic_rss, "Rss Feed",
				new RssFragment(), Color.rgb(255, 166, 66)));
		
		drawerItems.add(new DrawerObject(R.drawable.ic_web_site, "Community",
				new CommunityFragment(), Color.rgb(26, 169, 216)));
		
		drawerItems.add(new DrawerObject(R.drawable.ic_video, "Videos", new VideoFragment(), Color.rgb(255, 102, 102)));
		
		drawerItems.add(new DrawerObject(R.drawable.ic_action_about, "About",new AboutFragment(), Color.rgb(26, 169, 216)));
		

		mDrawerList.setAdapter(new DrawerAdapter(getLayoutInflater(),
				drawerItems));

		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_navigation_drawer, R.string.drawer_open, R.string.drawer_close);

		fragment = drawerItems.get(0).attachedFragment;

		fragmentManager = this.getSupportFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame, fragment).commit();

		mDrawerLayout.setDrawerListener(mDrawerToggle);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		SpannableString s = new SpannableString(drawerItems.get(0).text);
		s.setSpan(new TypefaceSpan(MainActivity.this, "Roboto-Light.ttf"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		setTitle(s);
	}
	
	public void doFade(int endColor){
	  ObjectAnimator colorFade = ObjectAnimator.ofObject(findViewById(R.id.content_frame), "backgroundColor", new ArgbEvaluator(), currentColor, endColor);
	  colorFade.setDuration(1500);
	  colorFade.start();
	  currentColor = endColor;
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
            if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
                mDrawerLayout.closeDrawer(mDrawerList);
            } else {
                mDrawerLayout.openDrawer(mDrawerList);
            }
        }
        return super.onOptionsItemSelected(item);
    }
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	if(selectedItem == 1){
	    		return ((CommunityFragment)drawerItems.get(1).attachedFragment).onKeyDown(keyCode);
	    	}else if(selectedItem == 2){
	    		return ((VideoFragment)drawerItems.get(2).attachedFragment).onKeyDown(keyCode);
	    	}
	    }else
	    	return super.onKeyDown(keyCode, event);
		return false;
	}

	private class DrawerItemClickListener implements ListView.OnItemClickListener {

		@SuppressWarnings("rawtypes")
		@Override
		public void onItemClick(AdapterView parent, View view, int position, long id) {
			selectItem(position);
			selectedItem = position;
		}
		
		private void selectItem(int position) {
			fragment = drawerItems.get(position).attachedFragment;

			fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
			doFade(drawerItems.get(position).background);

			mDrawerList.setItemChecked(position, true);
			SpannableString s = new SpannableString(drawerItems.get(position).text);
			s.setSpan(new TypefaceSpan(MainActivity.this, "Roboto-Light.ttf"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			setTitle(s);
			mDrawerLayout.closeDrawer(mDrawerList);
		}
	}

}
