package com.bowstringLLP.bigcloud;

import java.io.InputStream;
import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;

public class FavouritesActivity extends Activity {
	
	List<VideoItem> videoList;
	CustomListAdapter<VideoItem> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		adapter = new CustomListAdapter<VideoItem>(this);
	}

	@Override
	protected void onStart() {
		super.onStart();
		
		new FavouritesTask();
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
			onBackPressed();
			break;
		case R.id.favourites:
			break;
		case R.id.search:
			break;		default:
			startActivity(item.getIntent());
		}
		return true;
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
			
			videoList = PlaylistActivity.manipulator.read();
			
			if(videoList == null)
				return null;
			InputStream in;
			
			for(int i=0; i<videoList.size() & videoList.get(i)!=null; i++)
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
