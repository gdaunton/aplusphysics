package com.rhosoft.aplusphysics.fragments;

import java.lang.reflect.Field;

import com.actionbarsherlock.app.SherlockFragment;
import com.rhosoft.aplusphysics.R;
import com.rhosoft.aplusphysics.subfragment.BlogFragment;
import com.rhosoft.aplusphysics.subfragment.DownloadFragment;
import com.rhosoft.aplusphysics.subfragment.ForumFragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RssFragment extends SherlockFragment {
	
	public static RssFragment newInstance() {
		return new RssFragment();
	}

	private ViewPager mPager;
	private ScreenSlidePagerAdapter mPagerAdapter;
	private static final int NUM_PAGES = 3;
	private static final Field sChildFragmentManagerField;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.rss, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle saved) {
		super.onViewCreated(view, saved);
		mPager = (ViewPager) getView().findViewById(R.id.pager);
		mPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());
		mPager.setAdapter(mPagerAdapter);
	}

    static {
        Field f = null;
        try {
            f = Fragment.class.getDeclaredField("mChildFragmentManager");
            f.setAccessible(true);
        } catch (NoSuchFieldException e) {
        }
        sChildFragmentManagerField = f;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if (sChildFragmentManagerField != null) {
            try {
                sChildFragmentManagerField.set(this, null);
            } catch (Exception e) {
            }
        }
    }
    
	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
		public ScreenSlidePagerAdapter(FragmentManager fm) {
			super(fm);
		}
		
		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
			// do nothing here! no call to super.restoreState(arg0, arg1);
		}

		@Override
		public Fragment getItem(int position) {
			if (position == 0) {
				return new ForumFragment();
			} else if (position == 1) {
				return new BlogFragment();
			} else if (position == 2) {
				return new DownloadFragment();
			} else
				return null;
		}

		@Override
		public int getCount() {
			return NUM_PAGES;
		}
	}
}
