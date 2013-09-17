package com.bowstringLLP.bigcloud;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ListView;

public class LoadListTask extends AsyncTask<Void, Void, List<ListItem>> {
	
	public interface RecordsUpdateListener
	{
		public void onRecordsUpdated(List<ListItem> records);
	}

	CustomListAdapter adapter;
	ListView listView = null;
		ProgressDialog dialog;
		Activity parent;
		
		public LoadListTask(Activity parent)
		{
			this.parent = parent;
			adapter = new CustomListAdapter(parent);
			
		}
		
	@Override
	protected void onPreExecute()
	{
		super.onPreExecute();
		dialog = ProgressDialog.show(parent, null, "Loading...");		
	}
	
		  @Override
		  protected List<ListItem> doInBackground(Void... Voids) {
			  List<ListItem>listItem = res();
		      InputStream in;
		      try {
		        for(int i=0;i<listItem.size() && listItem.get(i)!=null;i++)
		        	{
		        	in = new java.net.URL(listItem.get(i).thumb).openStream();
		        	listItem.get(i).setBitmap(BitmapFactory.decodeStream(in));
		        	}
		        
		        return listItem;
		      } catch (Exception e) {
		          e.printStackTrace();
		          return null;
		      }
		  }

		  @Override
		  protected void onPostExecute(List<ListItem> list) {
		     // bmImage.setImageBitmap(result);
			  dialog.dismiss();
				adapter.setContent(list);
				((ListView) parent.findViewById(R.id.list)).setAdapter(adapter);
		}


private String getUrl() throws IOException, JSONException {
	HttpClient client = new DefaultHttpClient();
	HttpGet clientGetMethod = new HttpGet(
			"https://gdata.youtube.com/feeds/api/users/UC7Zbrf7JzzMCx58Yk612SIA/playlists?v=2&alt=json");
	HttpResponse clientResponse = null;
	clientResponse = client.execute(clientGetMethod);
	return _convertStreamToString(clientResponse.getEntity()
			.getContent());
}

private String _convertStreamToString(InputStream iS) {
	BufferedReader reader = new BufferedReader(new InputStreamReader(iS));
	StringBuilder sB = new StringBuilder();
	String line = null;
	try {
		while ((line = reader.readLine()) != null) {
			sB.append(line).append("\n");
		}
	} catch (IOException e) {
		e.printStackTrace();
	} finally {
		try {
			iS.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	return sB.toString();
}

public List<ListItem> res() {
	try {
		String str = getUrl();
		JSONObject json = new JSONObject(str);
		//JSONObject dataObject = json.getJSONObject("data"); // this is the "data": { } part
		JSONObject items = json.getJSONObject("feed");
		//JSONArray arr = items.names();
		JSONArray obj = items.getJSONArray("entry");
		JSONObject abc;
		List<ListItem> listItem = new ArrayList<ListItem>();
		//List<String> thumb = new ArrayList<String>();
		
		for(int i=0; i<20; i++)
		{
			abc = obj.getJSONObject(i);
			//JSONArray a = abc.names();
			listItem.add(new ListItem(abc.getJSONObject("title").getString("$t"), abc.getJSONObject("media$group").getJSONArray("media$thumbnail").getJSONObject(0).getString("url")));
		}
		
		return listItem;
	} catch (Exception e) {
		e.printStackTrace();
		return null;
	}

}
}
