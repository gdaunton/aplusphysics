package com.rhosoft.aplusphysics.items;

import android.support.v4.app.Fragment;

public class DrawerObject {

	public int image;
	public String text;
	public Fragment attachedFragment;
	public int background;
	public DrawerObject(int imageRes, String title, Fragment associatedFragment, int backgroundColor){
		image = imageRes;
		text = title;
		attachedFragment = associatedFragment;
		background = backgroundColor;
	}
}
