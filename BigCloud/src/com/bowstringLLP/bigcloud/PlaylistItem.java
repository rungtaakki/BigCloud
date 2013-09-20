package com.bowstringLLP.bigcloud;

import android.graphics.Bitmap;

public class PlaylistItem{

	String title;
	Bitmap bitmap;
	int count;
	String link;
	String thumb;
	
	public PlaylistItem(String title, int count, String link, Bitmap bitmap, String thumb)
	{
		this.title = title;
		this.link = link;
		this.bitmap = bitmap;
		this.count = count;
		this.thumb  = thumb;
	}
}
