package com.bowstringLLP.bigcloud;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// test();
		//res();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/*
	 * private void test() { try{ String SERVER_IP = "192.168.0.1"; String
	 * APPLICATION_ID = Base64.encodeToString("1005".getBytes(),
	 * Base64.NO_WRAP); String androidId =
	 * Base64.encodeToString(Settings.Secure.
	 * getString(getContentResolver(),Settings.Secure.ANDROID_ID).getBytes(),
	 * Base64.NO_WRAP); TelephonyManager telephonyManager =
	 * (TelephonyManager)getSystemService( Context.TELEPHONY_SERVICE );
	 * 
	 * 
	 * getDeviceId() function Returns the unique device ID. for example,the IMEI
	 * for GSM and the MEID or ESN for CDMA phones.
	 * 
	 * androidId =
	 * Base64.encodeToString(telephonyManager.getDeviceId().getBytes(),
	 * Base64.NO_WRAP);
	 * 
	 * StrictMode.ThreadPolicy policy = new
	 * StrictMode.ThreadPolicy.Builder().permitAll().build();
	 * StrictMode.setThreadPolicy(policy);
	 * 
	 * URL url = null;
	 * 
	 * url = new URL(
	 * "https://gdata.youtube.com/feeds/api/users/UC7Zbrf7JzzMCx58Yk612SIA/playlists?v=2&alt=json"
	 * );//
	 * "https://www.googleapis.com/youtube/v3/playlists?part=id%2C+snippet&channelId=UC7Zbrf7JzzMCx58Yk612SIA&key=AIzaSyCJJPTApn6cyTGGC92USWwpBPM-hOcjGxI"
	 * ); //("http://https://github.com/rjain90/BigCloud.git"+SERVER_IP+
	 * "/index.php/app/requestVideoCategories");
	 * 
	 * 
	 * HttpClient httpclient = new DefaultHttpClient(); HttpPost httppost = new
	 * HttpPost(url.toString());
	 * 
	 * try { // Add your data List<NameValuePair> nameValuePairs = new
	 * ArrayList<NameValuePair>(2); nameValuePairs.add(new
	 * BasicNameValuePair("DEVICE_ID", androidId)); nameValuePairs.add(new
	 * BasicNameValuePair("APPLICATION_ID", APPLICATION_ID));
	 * httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	 * 
	 * // Execute HTTP Post Request HttpResponse response =
	 * httpclient.execute(httppost); Header[] hd = response.getAllHeaders(); //
	 * JSONObject object = (JSONObject) new
	 * JSONTokener(response.toString()).nextValue(); String str =
	 * EntityUtils.toString(response.getEntity());
	 * System.out.println(response.toString()); } catch (ClientProtocolException
	 * e) { // TODO Auto-generated catch block e.printStackTrace(); } catch
	 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace();
	 * }catch (Exception e) { e.printStackTrace(); } } catch(Exception e) {
	 * e.printStackTrace(); } }
	 */


}
