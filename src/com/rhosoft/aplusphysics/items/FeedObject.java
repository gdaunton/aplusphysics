package com.rhosoft.aplusphysics.items;

import java.io.Serializable;
import nl.matshofman.saxrssreader.RssItem;

public class FeedObject implements Serializable {
	private static final long serialVersionUID = 1L;
	public String Time;
	public String Title;
	public RssItem rssItem;
	public String url = "";
	public String link;

	public FeedObject(String title, String time, String link) {
		this.Title = title;
		this.Time = time;
		this.link = link;
	}
}
