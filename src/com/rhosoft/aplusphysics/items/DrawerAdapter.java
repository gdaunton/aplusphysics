package com.rhosoft.aplusphysics.items;

import java.util.ArrayList;

import com.rhosoft.aplusphysics.MainActivity;
import com.rhosoft.aplusphysics.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DrawerAdapter extends BaseAdapter{

	private ArrayList<DrawerObject> objects;
	private LayoutInflater inflater;
	
	public DrawerAdapter(LayoutInflater inflater, ArrayList<DrawerObject> objects){
		this.objects = objects;
		this.inflater = inflater;
	}
	
	@Override
	public int getCount() {
		return objects.size();
	}

	@Override
	public Object getItem(int position) {
		return objects.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View vi=convertView;
		
        if(convertView == null)
            vi = inflater.inflate(R.layout.drawer_resource, null);
        
        DrawerObject item = objects.get(position);
        
        TextView title = (TextView)vi.findViewById(R.id.text);
        ImageView icon = (ImageView)vi.findViewById(R.id.icon);
        
        title.setText(item.text);
        title.setTypeface(MainActivity.subTitleFont);
        icon.setImageResource(item.image);
        
		return vi;
	}
	
}
