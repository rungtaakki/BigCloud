package com.bowstringLLP.bigcloud;

import android.graphics.Bitmap;

public class VideoItem{

	String title;
	String thumb;
	Bitmap bitmap;
	int duration;
	String link;
	
	public VideoItem(String title, int duration, String link, Bitmap bitmap, String thumb)
	{
		this.title = title;
		this.link = link;
		this.bitmap = bitmap;
		this.duration = duration;
		this.thumb  = thumb;
	}	
}
