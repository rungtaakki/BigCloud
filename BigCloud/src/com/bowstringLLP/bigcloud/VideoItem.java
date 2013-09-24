package com.bowstringLLP.bigcloud;

import android.graphics.Bitmap;

public class VideoItem {

	int id;
	String title;
	String thumb;
	Bitmap bitmap;
	int duration;
	String link;
	boolean isFavourite = false;

	public VideoItem(int id, String title, int duration, String link, Bitmap bitmap,
			String thumb, boolean isFavourite) {
		
		this.id = id;
		this.title = title;
		this.link = link;
		this.duration = duration;
		this.thumb = thumb;
		this.isFavourite = isFavourite;
		
		if(bitmap!=null)			
			this.bitmap = bitmap;
	}

	public void setBitmap(Bitmap bit) {
		bitmap = bit;
	}
}
