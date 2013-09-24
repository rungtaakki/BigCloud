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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ListView;

public class LoadVideoListTask extends AsyncTask<String, Void, List<VideoItem>> {

	ListView listView = null;
	ProgressDialog dialog;
	Activity parent;
	VideoRecordsUpdateListener listener;
	
	public interface VideoRecordsUpdateListener
	{
		public void recordsUpdated(List<VideoItem> list);
	}
	
	public LoadVideoListTask(Activity parent) {
		this.parent = parent;
		listener = (VideoRecordsUpdateListener) parent;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		dialog = ProgressDialog.show(parent, null, "Loading...");
	}

	@Override
	protected List<VideoItem> doInBackground(String... path) {

		List<VideoItem> listItem = new ArrayList<VideoItem>();
		try {
			String str = getUrl(path[0]);
			JSONObject json = new JSONObject(str);
			JSONObject items = json.getJSONObject("feed");
			// JSONArray arr = items.names();
			JSONArray obj = items.getJSONArray("entry");
			JSONObject abc;

			InputStream in;
			Bitmap bit;
			String title;
			String Url;
			int duration= 0;
			String thumb;
			
			List<VideoItem> favList = PlaylistActivity.manipulator.readAll();
			
			for (int i = 0; i<obj.length() && obj.getJSONObject(i)!=null; i++) {
				
				abc = obj.getJSONObject(i);
				title =  abc.getJSONObject("title").getString("$t");
				Url = abc.getJSONObject("media$group").getJSONArray("media$content").getJSONObject(2).getString("url");
				duration = Integer.parseInt(abc.getJSONObject("media$group").getJSONArray("media$content").getJSONObject(1).getString("duration"));
				thumb = abc.getJSONObject("media$group").getJSONArray("media$thumbnail").getJSONObject(0).getString("url");
				
				in = new java.net.URL(thumb).openStream();
				bit = BitmapFactory.decodeStream(in);
				
				if(PlaylistActivity.manipulator.contains(i))
					listItem.add(new VideoItem(i, title, duration, Url, bit, thumb, true));
				else
					listItem.add(new VideoItem(i, title, duration, Url, bit, thumb, false));
			}
			
			return listItem;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected void onPostExecute(List<VideoItem> list) {
		if(dialog!=null)
			dialog.dismiss();
		listener.recordsUpdated(list);		
	}
	
	@Override
	protected void onCancelled() {
		super.onCancelled();
		dialog.dismiss();
	}
	
	private String getUrl(String path) throws IOException, JSONException {
		HttpClient client = new DefaultHttpClient();
		HttpGet clientGetMethod = new HttpGet(path);
		HttpResponse clientResponse = null;
		clientResponse = client.execute(clientGetMethod);
		return _convertStreamToString(clientResponse.getEntity().getContent());
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
}
