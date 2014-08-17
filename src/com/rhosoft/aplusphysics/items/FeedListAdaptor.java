package com.rhosoft.aplusphysics.items;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import com.rhosoft.aplusphysics.MainActivity;
import com.rhosoft.aplusphysics.R;

public class FeedListAdaptor extends BaseAdapter
{
  private LayoutInflater inflater = null;
  private ArrayList<FeedObject> objects = null;
  private ArrayList<View> views = null;

  public FeedListAdaptor(LayoutInflater paramLayoutInflater, ArrayList<FeedObject> paramArrayList)
  {
    this.objects = paramArrayList;
    this.inflater = paramLayoutInflater;
    this.views = new ArrayList<View>();
  }

  public int getCount()
  {
    return this.objects.size();
  }

  public Object getItem(int paramInt)
  {
    return Integer.valueOf(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public View getView(int paramInt)
  {
    return (View)this.views.get(paramInt);
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    View localView = paramView;
    if (paramView == null)
      localView = this.inflater.inflate(R.layout.feed_object, null);
    TextView title = (TextView)localView.findViewById(R.id.title);
    TextView time = (TextView)localView.findViewById(R.id.time);
    FeedObject localFeedObject = (FeedObject)this.objects.get(paramInt);
    
    title.setTypeface(MainActivity.titleFont);
    title.setText(localFeedObject.Title);
    
    time.setTypeface(MainActivity.subTitleFont);
    time.setText(localFeedObject.Time);
    return localView;
  }
}
