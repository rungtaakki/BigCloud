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
	ImageView favourite = null;
	ImageView download = null;
	ImageView arrow = null;
	
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
	
	ImageView getFavouriteImage() {
		if (favourite==null) {
			favourite=(ImageView)base.findViewById(R.id.list_favourite);
		}

		return(favourite);
	}
	
	ImageView getDownloadImage() {
		if (download==null) {
			download=(ImageView)base.findViewById(R.id.list_download);
		}

		return(download);
	}
	
	ImageView getArrowImage() {
		if (arrow==null) {
			arrow=(ImageView)base.findViewById(R.id.arrow);
		}

		return(arrow);
	}
}
