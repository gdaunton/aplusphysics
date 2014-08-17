package com.rhosoft.aplusphysics.fragments;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rhosoft.aplusphysics.MainActivity;
import com.rhosoft.aplusphysics.R;
import com.rhosoft.aplusphysics.VideoActivity;
import com.rhosoft.aplusphysics.items.ClassObject;
import com.rhosoft.aplusphysics.items.VideoCategoryObject;
import com.rhosoft.aplusphysics.items.VideoObject;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class VideoFragment extends Fragment{

	public ArrayList<ClassObject> Course;
	private LayoutInflater inflater;
	
	public static VideoFragment newInstance() {
		return new VideoFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.inflater = inflater;
		return inflater.inflate(R.layout.videos, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle saved) {
		super.onViewCreated(view, saved);
		((TextView)getView().findViewById(R.id.cources)).setTypeface(MainActivity.subTitleFont);
		((TextView)getView().findViewById(R.id.courceTitle)).setTypeface(MainActivity.subTitleFont);
		if(Course == null){
			new LoadVideos().execute();
		}else{
			initializeList();
		}
	}
	
	public void initializeList(){
		final Animation mSlideOutLeft = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_from_left);
		final Animation mSlideInRight = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_from_right);
		
	     
		final LinearLayout classListLayout = (LinearLayout)getView().findViewById(R.id.classListLayout);
		final LinearLayout categoryLayout = (LinearLayout)getView().findViewById(R.id.catergoryLayout);
		
		final ListView classList = (ListView)getView().findViewById(R.id.classlist);
		final ExpandableListView categoryList = (ExpandableListView)getView().findViewById(R.id.categoryList);
	    		
		categoryLayout.setVisibility(View.INVISIBLE);
		classListLayout.setVisibility(View.VISIBLE);
		
		classList.setAdapter(new ClassListAdapter(getActivity().getLayoutInflater(), Course));
		classList.setOnItemClickListener(new OnItemClickListener(){
			@SuppressLint("DefaultLocale")
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3){
				classListLayout.startAnimation(mSlideOutLeft);
				categoryLayout.startAnimation(mSlideInRight);
				categoryLayout.setVisibility(View.VISIBLE);
				classListLayout.setVisibility(View.INVISIBLE);
				final ClassObject cource = Course.get(arg2);
				
				((TextView)getView().findViewById(R.id.courceTitle)).setText(Course.get(arg2).title.toUpperCase());
				((Button)getView().findViewById(R.id.backButton)).setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View arg0) {
						navigateBack();
					}
				});
				
				categoryList.setAdapter(new CategoryListAdapter(inflater, Course.get(arg2).categories));
				
				categoryList.setOnChildClickListener(new OnChildClickListener(){
					@Override
					public boolean onChildClick(ExpandableListView arg0,
							View arg1, int arg2, int arg3, long arg4) {
						final VideoObject video = cource.categories.get(arg2).videos.get(arg3);
						Intent intent = new Intent();
						intent.setClass(getActivity(), VideoActivity.class);
						intent.putExtra("URL", video.URL);
						intent.putExtra("Title", video.title);
						getActivity().startActivity(intent);
						return false;
					}
				});
			}
		});
	}
	
	public boolean onKeyDown(int keyCode){
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				return navigateBack();
			} else
				return false;
	}
	
	public boolean navigateBack(){
		final LinearLayout categoryList = (LinearLayout)getView().findViewById(R.id.catergoryLayout);
		final LinearLayout classListLayout = (LinearLayout)getView().findViewById(R.id.classListLayout);
			if (categoryList.getVisibility() == View.VISIBLE) {
				final Animation mSlideOutRight = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_from_right);
				final Animation mSlideInLeft = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_from_left);
				classListLayout.startAnimation(mSlideInLeft);
				categoryList.startAnimation(mSlideOutRight);
				categoryList.setVisibility(View.INVISIBLE);
				classListLayout.setVisibility(View.VISIBLE);
				return true;
			} else
				return false;
	}
	
	private class LoadVideos extends AsyncTask<Void, Void, Void> {

		private ProgressDialog dialog;

		protected Void doInBackground(Void...voids) {
			try {
			    URL url = new URL("http://www.rhosoft.biz/aplusphysics/youtubearrays.html");
			    URLConnection tc = url.openConnection();
			    BufferedReader in = new BufferedReader(new InputStreamReader(tc.getInputStream()));
			    String line;
			    String str = "";
			    while ((line = in.readLine()) != null) {   
			        str += line;
			    }
			    
			    Course = new ArrayList<ClassObject>();
		        ArrayList<VideoCategoryObject> categories = new ArrayList<VideoCategoryObject>();
		        ArrayList<VideoObject> videos = new ArrayList<VideoObject>();
		        
			    JSONObject jo = new JSONObject(str);
			    JSONArray ja = jo.getJSONArray("classes");
			    Course = new ArrayList<ClassObject>();
			    for (int i = 0; i < ja.length(); i++) {
			        String classTitle = ja.getJSONObject(i).getString("name");
			        JSONArray ja1 = ja.getJSONObject(i).getJSONArray("categories");
			        categories = new ArrayList<VideoCategoryObject>();
			        for (int j = 0; j < ja1.length(); j++) {
				        String catTitle = ja1.getJSONObject(j).getString("name");
				        JSONArray ja2 = ja1.getJSONObject(j).getJSONArray("videos");
				        videos = new ArrayList<VideoObject>();
				        for (int k = 0; k < ja2.length(); k++) {
					        String title = ja2.getJSONObject(k).getString("name");
					        String URL = ja2.getJSONObject(k).getString("url");
					        videos.add(new VideoObject(title, URL));
					    }
				        categories.add(new VideoCategoryObject(getActivity().getLayoutInflater(), catTitle, videos));
				    }
			        Course.add(new ClassObject(classTitle, categories));
			    }
			} catch (MalformedURLException e) {
			    e.printStackTrace();
			} catch (IOException e) {
			    e.printStackTrace();
			} catch (JSONException e) {
			    e.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(Void paramVoid) {
			dialog.dismiss();
			initializeList();
		}

		protected void onPreExecute() {
			dialog = ProgressDialog.show(getActivity(), "", "Loading", true);
		}
	}
	
	class ClassListAdapter extends BaseAdapter
	{
		private ArrayList<ClassObject> classes;
		private LayoutInflater inflater;

	  public ClassListAdapter(LayoutInflater inflater, ArrayList<ClassObject> classes){
			this.classes = classes;
			this.inflater = inflater;
	  }

	  public int getCount()
	  {
	    return this.classes.size();
	  }

	  public Object getItem(int paramInt)
	  {
	    return this.classes.get(paramInt);
	  }

	  public long getItemId(int paramInt)
	  {
	    return paramInt;
	  }

	  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
	  {
	    View view = inflater.inflate(R.layout.class_resource, null);
	    
	    TextView title = (TextView)view.findViewById(R.id.text);
	    title.setTypeface(MainActivity.subTitleFont);
	    title.setText(classes.get(paramInt).title);
	    return view;
	  }
	}
	
	class CategoryListAdapter extends BaseExpandableListAdapter
	{
		private ArrayList<VideoCategoryObject> categories;
		private LayoutInflater inflater;

	  public CategoryListAdapter(LayoutInflater inflater, ArrayList<VideoCategoryObject> categories){
			this.categories = categories;
			this.inflater = inflater;
	  }

	@Override
	public Object getChild(int arg0, int arg1) {
		return categories.get(arg0).videos.get(arg1);
	}

	@Override
	public long getChildId(int arg0, int arg1) {
		return arg0+arg1;
	}

	@Override
	public View getChildView(int arg0, int arg1, boolean arg2, View arg3,
			ViewGroup arg4) {
		
		View view = inflater.inflate(R.layout.video_resource, null);
		final VideoObject video = categories.get(arg0).videos.get(arg1);
	    TextView title = (TextView)view.findViewById(R.id.text);
	    title.setTypeface(MainActivity.subTitleFont);
	    title.setText(video.title);
	    return view;
	}

	@Override
	public int getChildrenCount(int arg0) {
		return categories.get(arg0).videos.size();
	}

	@Override
	public Object getGroup(int arg0) {
		return categories.get(arg0);
	}

	@Override
	public int getGroupCount() {
		return categories.size();
	}

	@Override
	public long getGroupId(int arg0) {
		return arg0;
	}

	@Override
	public View getGroupView(int arg0, boolean arg1, View arg2, ViewGroup arg3) {
		View v = categories.get(arg0).getView(getActivity());
		int id = (!arg1) ? R.drawable.arrow: R.drawable.arrow_up;
		ImageView arrow = (ImageView)v.findViewById(R.id.arrow);
		arrow.setImageResource(id);
		return v;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		return true;
	}
	}
	
}
