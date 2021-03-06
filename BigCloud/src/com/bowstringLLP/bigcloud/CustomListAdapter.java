package com.bowstringLLP.bigcloud;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class CustomListAdapter<T> extends ArrayAdapter<T> {

	Activity context;
	List<T> rec;
	List<PlaylistItem> playlist;
	List<VideoItem> videoList;

	public CustomListAdapter(Activity context) {
		super(context, R.layout.list_item_layout);
		this.context = context;
	}

	@SuppressWarnings("unchecked")
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		ViewWrapper wrapper = null;

		if (row == null) {
			LayoutInflater inflater = context.getLayoutInflater();

			row = inflater.inflate(R.layout.list_item_layout, null);
			wrapper = new ViewWrapper(row);
			row.setTag(wrapper);
		} else {
			wrapper = (ViewWrapper) row.getTag();
		}

		try {
			if (rec.get(0).getClass().equals(PlaylistItem.class)) {
				
				wrapper.getArrowImage().setVisibility(View.VISIBLE);
				wrapper.getFavouriteImage().setVisibility(View.INVISIBLE);
				wrapper.getDownloadImage().setVisibility(View.INVISIBLE);
				
				playlist = (List<PlaylistItem>) rec;
				if (playlist.get(position).title != null)
					wrapper.getName().setText(playlist.get(position).title);
				else
					wrapper.getName().setText("Unknown");

				if (playlist.get(position).bitmap != null) {
					wrapper.getThumb().setImageBitmap(
							playlist.get(position).bitmap);
				} else
					wrapper.getName().setText("Unknown");

				wrapper.getCountDuration().setText(
						"No. of Videos: "
								+ String.valueOf(playlist.get(position).count));
			} else if(rec.get(0).getClass().equals(VideoItem.class)) {
				
				wrapper.getArrowImage().setVisibility(View.GONE);
				wrapper.getFavouriteImage().setVisibility(View.VISIBLE);
				wrapper.getDownloadImage().setVisibility(View.VISIBLE);
				
				videoList = (List<VideoItem>) rec;
				if (videoList.get(position).title != null)
					wrapper.getName().setText(videoList.get(position).title);
				else
					wrapper.getName().setText("Unknown");

				if (videoList.get(position).bitmap != null) {
					wrapper.getThumb().setImageBitmap(
							videoList.get(position).bitmap);
				} else
					wrapper.getName().setText("Unknown");

				wrapper.getPlayImage().setPadding(
						videoList.get(position).bitmap.getWidth() / 3,
						videoList.get(position).bitmap.getHeight() / 4, 0, 0);
				wrapper.getPlayImage().setVisibility(View.VISIBLE);

				wrapper.getCountDuration()
						.setText(
								"Duration: "
										+ String.valueOf(videoList
												.get(position).duration)
										+ " seconds");
				
				setupFavouriteButton(position, wrapper.getFavouriteImage());
				
				final int i = position;
				wrapper.getFavouriteImage().setOnClickListener(new OnClickListener() {
				      @Override
				      public void onClick(View v) {
				    	  VideoItem vid = videoList.get(i);
				    	  vid.isFavourite = !vid.isFavourite;
				    	  if(videoList.get(i).isFavourite)
				    		  PlaylistActivity.manipulator.insert(vid);
				    	  
					  	  else
					  		  PlaylistActivity.manipulator.delete(vid);
				    	  
				    	  setupFavouriteButton(i, (ImageView) v);
				      }
				});
				
				wrapper.getDownloadImage().setOnClickListener(new OnClickListener() {
				      @Override
				      public void onClick(View v) {
				    	  VideoItem vid = videoList.get(i);
				    	  DownloadVideo.download(vid.link, vid.id);
				      }
				});
			}
		} catch (ClassCastException e) {
			e.printStackTrace();
		}
		return row;
	}

	private void setupFavouriteButton(int position, ImageView view) {
		      
		      if(videoList.get(position).isFavourite)
		    	  view.setImageDrawable(getContext().getResources().getDrawable(R.drawable.rating_important));
		  	  else
		  		  view.setImageDrawable(getContext().getResources().getDrawable(R.drawable.rating_not_important));
	}

	public void setContent(List<T> list) {
		rec = list;
	}

	@Override
	public int getCount() {
		if (rec == null)
			return 0;
		else
			return rec.size();
	}
}
