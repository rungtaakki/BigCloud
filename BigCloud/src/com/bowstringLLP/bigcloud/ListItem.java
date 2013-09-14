package com.bowstringLLP.bigcloud;

import android.graphics.Bitmap;

public class ListItem {

	String title;
	String thumb;
	Bitmap bitmap;
	
	public ListItem(String title, String thumb)
	{
		this.title = title;
		this.thumb = thumb;
	}
	
	public void setBitmap(Bitmap bit)
	{
		this.bitmap = bit;
	}
}
