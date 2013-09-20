package com.bowstringLLP.bigcloud;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewWrapper {
	View base;
	TextView name = null;
	ImageView thumb = null;
	ImageView play = null;
	TextView count_duration = null;
	
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
	
	ImageView getPlayImage() {
		if (play==null) {
			play=(ImageView)base.findViewById(R.id.PlayView);
		}

		return(play);
	}
	
	TextView getCountDuration() {
		if (count_duration==null) {
			count_duration=(TextView) base.findViewById(R.id.listItemCount_Duration);
		}

		return(count_duration);
	}
}
