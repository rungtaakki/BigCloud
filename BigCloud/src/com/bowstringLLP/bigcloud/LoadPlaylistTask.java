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

public class LoadPlaylistTask extends AsyncTask<Void, Void, List<PlaylistItem>> {

	ListView listView = null;
	ProgressDialog dialog;
	Activity parent;
	PlaylistRecordsUpdateListener listener;

	public interface PlaylistRecordsUpdateListener
	{
		public void recordsUpdated(List<PlaylistItem> list);
	}
	
	public LoadPlaylistTask(Activity parent) {
		this.parent = parent;
		listener = (PlaylistRecordsUpdateListener) parent;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		dialog = ProgressDialog.show(parent, null, "Loading...");
	}

	@Override
	protected List<PlaylistItem> doInBackground(Void... Voids) {

		List<PlaylistItem> listItem = new ArrayList<PlaylistItem>();
		try {
			String str = getUrl();
			JSONObject json = new JSONObject(str);
			JSONObject items = json.getJSONObject("feed");
			// JSONArray arr = items.names();
			JSONArray obj = items.getJSONArray("entry");
			JSONObject abc;

			for (int i = 0; i < 20; i++) {
				InputStream in;
				Bitmap bit;
				String title;
				String Url;
				int count= 0;
				String thumb;
				
				abc = obj.getJSONObject(i);
				title = abc.getJSONObject("title").getString("$t");
				Url = abc.getJSONObject("content").getString("src") + "&alt=json";
				count = Integer.parseInt(abc.getJSONObject("yt$countHint").getString("$t"));
				thumb = abc.getJSONObject("media$group").getJSONArray("media$thumbnail").getJSONObject(0).getString("url");
				
				in = new java.net.URL(thumb).openStream();
				bit = BitmapFactory.decodeStream(in);
				
				listItem.add(new PlaylistItem(title, count, Url, bit, thumb));
			}
			
			return listItem;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected void onPostExecute(List<PlaylistItem> list) {
		dialog.dismiss();
		listener.recordsUpdated(list);
	}

	private String getUrl() throws IOException, JSONException {
		HttpClient client = new DefaultHttpClient();
		HttpGet clientGetMethod = new HttpGet(
				"https://gdata.youtube.com/feeds/api/users/UC7Zbrf7JzzMCx58Yk612SIA/playlists?v=2&alt=json");
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
