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

import com.bowstringLLP.bigcloud.LoadVideoListTask.VideoRecordsUpdateListener;

public class VideoActivity extends Activity implements VideoRecordsUpdateListener{

	ListView list;
	CustomListAdapter adapter;
	List<VideoItem> records;
	LoadVideoListTask task;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		adapter = new CustomListAdapter(this);

		setupActionBar();
		task = (LoadVideoListTask) new LoadVideoListTask(this).execute(getIntent().getExtras().getString("PATH"),null,null);
		
		list = ((ListView) this.findViewById(R.id.list));
		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			startIntent(records.get(arg2));
		}
	});
	}

	@TargetApi(11)
	private void setupActionBar() {
		getActionBar().setHomeButtonEnabled(true);
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		if(records != null)
			recordsUpdated(records);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		task.dialog.dismiss();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(!task.isCancelled())
			task.dialog.show();
	}
	
	private void startIntent(VideoItem item) {
		Intent intent = new Intent(this, MediaPlayerDemo_Video.class);
		intent.putExtra("PATH", item.link);
		startActivity(intent);
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

	@Override
	public void recordsUpdated(List<VideoItem> list) {			
		records = list;	
		adapter.setContent(list);
		((ListView) findViewById(R.id.list)).setAdapter(adapter);		
	}
}

