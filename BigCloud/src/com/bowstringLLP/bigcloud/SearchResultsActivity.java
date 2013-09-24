package com.bowstringLLP.bigcloud;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

public class SearchResultsActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        handleIntent(getIntent());
        setupActionBar();
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
			startActivity(new Intent(this, FavouritesActivity.class));
			break;
		case R.id.search:
			break;		default:
			startActivity(item.getIntent());
		}
		return true;
	}

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
        }
    }
}
