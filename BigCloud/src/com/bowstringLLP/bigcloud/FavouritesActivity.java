package com.bowstringLLP.bigcloud;

import java.io.InputStream;
import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.AdapterView.OnItemClickListener;

public class FavouritesActivity extends Activity {
	
	ListView listView;
	CustomListAdapter<VideoItem> adapter;
	List<VideoItem> videoList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		setupActionBar();
		adapter = new CustomListAdapter<VideoItem>(this);
		
		listView = ((ListView) this.findViewById(R.id.list));
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			startIntent(videoList.get(arg2));
		}
	});
	}

	@Override
	protected void onStart() {
		super.onStart();
		
		new FavouritesTask().execute(null,null,null);
	}
	
	@TargetApi(11)
	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		setSearchView(menu);

		return true;
	}

	@TargetApi(11)
	private void setSearchView(Menu menu) { 
		// Associate searchable configuration with the SearchView
	    SearchManager searchManager =
		           (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		    SearchView searchView =
		            (SearchView) menu.findItem(R.id.search).getActionView();
		    searchView.setSearchableInfo(
		            searchManager.getSearchableInfo(getComponentName()));
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())
		{
		case android.R.id.home:
	        NavUtils.navigateUpFromSameTask(this);
			break;
		case R.id.favourites:			
			new FavouritesTask().execute(null,null,null);
			break;
		case R.id.search:
			break;		default:
			startActivity(item.getIntent());
		}
		return true;
	}
	
	private void startIntent(VideoItem item) {
		Intent intent = new Intent(this, MediaPlayerDemo_Video.class);
		//intent.putExtra("PATH", item.link);
		intent.putExtra("PATH", Environment.getExternalStorageDirectory().toString()+"/video/wildlife.mp4");
		startActivity(intent);
	}
	
	private class FavouritesTask extends AsyncTask<Void, Void, Void> {
		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = ProgressDialog.show(FavouritesActivity.this, null,
					"Loading...");
		}

		@Override
		protected Void doInBackground(Void... params) {
			
			videoList = PlaylistActivity.manipulator.readAll();
			
			if(videoList == null)
				return null;
			InputStream in;
			
			for(int i=0; i<videoList.size() && videoList.get(i)!=null; i++)
			{
				try{
					in = new java.net.URL(videoList.get(i).thumb).openStream();
					videoList.get(i).setBitmap(BitmapFactory.decodeStream(in));
				}catch(Exception e)
				{
					continue;
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			dialog.dismiss();

			adapter.setContent(videoList);
			((ListView) findViewById(R.id.list)).setAdapter(adapter);	
		}

		@Override
		protected void onCancelled(Void result) {
			super.onCancelled(result);
			dialog.dismiss();
		}
	}
}
