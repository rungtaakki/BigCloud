package com.bowstringLLP.bigcloud;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewWrapper {
	View base;
	TextView name = null;
	ImageView thumb = null;
	
	ViewWrapper(View base) {
		this.base=base;
	}

	TextView getName() {
		if (name==null) {
			name=(TextView)base.findViewById(R.id.listItemName);
		}

		return(name);
	}
	
	ImageView getThumb() {
		if (thumb==null) {
			thumb=(ImageView)base.findViewById(R.id.listItemThumb);
		}

		return(thumb);
	}
}
