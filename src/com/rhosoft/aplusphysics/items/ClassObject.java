package com.rhosoft.aplusphysics.items;

import java.util.ArrayList;

public class ClassObject {

	public String title = "";
	public ArrayList<VideoCategoryObject> categories;
	
	public ClassObject(String title, ArrayList<VideoCategoryObject> categories){
		this.title = title;
		this.categories = categories;
	}
	
}
