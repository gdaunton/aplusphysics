package com.rhosoft.aplusphysics.items;

import java.util.ArrayList;

import com.rhosoft.aplusphysics.MainActivity;
import com.rhosoft.aplusphysics.R;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class VideoCategoryObject {

	public String title;
	public ArrayList<VideoObject> videos;
	private LayoutInflater inflater;
	private View view;
	
	public VideoCategoryObject(LayoutInflater inflater, String title, ArrayList<VideoObject> videos){
		this.title = title;
		this.videos = videos;
		this.inflater = inflater;
	}
	
	public View getView(final Activity parent){
		view = inflater.inflate(R.layout.section_resource, null);
		TextView sectionHeader = ((TextView)view.findViewById(R.id.section_text));
		sectionHeader.setText(title);
		sectionHeader.setTypeface(MainActivity.titleFont);
		return view;
	}
	
	class VideoListAdapter extends BaseAdapter
	{
		private ArrayList<VideoObject> videos;
		private LayoutInflater inflater;

	  public VideoListAdapter(LayoutInflater inflater, ArrayList<VideoObject> videos){
			this.videos = videos;
			this.inflater = inflater;
	  }

	  public int getCount()
	  {
	    return this.videos.size();
	  }

	  public Object getItem(int paramInt)
	  {
	    return videos.get(paramInt);
	  }

	  public long getItemId(int paramInt)
	  {
	    return paramInt;
	  }

	  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
	  {
	    View localView = paramView;
	    if (paramView == null)
	      localView = inflater.inflate(R.layout.video_resource, null);
	    TextView title = (TextView)localView.findViewById(R.id.text);
	    title.setText(videos.get(paramInt).title);
	    return localView;
	  }
	}
}
