package com.bowstringLLP.bigcloud;

import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SearchView;

import com.bowstringLLP.bigcloud.LoadPlaylistTask.PlaylistRecordsUpdateListener;

public class PlaylistActivity extends Activity implements PlaylistRecordsUpdateListener{

	ListView list;
	CustomListAdapter<PlaylistItem> adapter;
	List<PlaylistItem> records;
	LoadPlaylistTask task;
	static DataManipulator manipulator;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		adapter = new CustomListAdapter<PlaylistItem> (this);
		manipulator = new DataManipulator(this);
		
		try
		{
			records = (List<PlaylistItem>) getLastNonConfigurationInstance();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		if(records==null)
			task = (LoadPlaylistTask) new LoadPlaylistTask(this).execute(null,null,null);
		
		list = ((ListView) this.findViewById(R.id.list));
		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			startIntent(records.get(arg2));
		}
	});
	}
	
	@Override
	protected void onStart()
	{
		super.onStart();

		if(records!=null)
			recordsUpdated(records);
	}
	
	private void startIntent(PlaylistItem item) {
		Intent intent = new Intent(this, VideoActivity.class);
		intent.putExtra("PATH", item.link);
		startActivity(intent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		setSearchView(menu);

		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())
		{
		case R.id.favourites:
			startActivity(new Intent(this, FavouritesActivity.class));
		case R.id.search:
			break;		default:
			startActivity(item.getIntent());
		}
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
	public void recordsUpdated(List<PlaylistItem> list) {		
		records = list;
		adapter.setContent(list);
		((ListView) findViewById(R.id.list)).setAdapter(adapter);		
	}
	
	@Override
	public Object onRetainNonConfigurationInstance() {
	    return records;
	}
}
