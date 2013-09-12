package com.bowstringLLP.bigcloud;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		test();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void test()
	{
		try{
		String SERVER_IP = "192.168.0.1";
		String APPLICATION_ID = Base64.encodeToString("1005".getBytes(), Base64.NO_WRAP);
		String androidId = Base64.encodeToString(Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID).getBytes(), Base64.NO_WRAP);
		TelephonyManager telephonyManager = (TelephonyManager)getSystemService( Context.TELEPHONY_SERVICE );
	                      
	    /*
	     * getDeviceId() function Returns the unique device ID.
	     * for example,the IMEI for GSM and the MEID or ESN for CDMA phones.  
	     */                                                                
	    androidId = Base64.encodeToString(telephonyManager.getDeviceId().getBytes(), Base64.NO_WRAP);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		URL url = null;

		url = new URL("http://https://github.com/rjain90/BigCloud.git"+SERVER_IP+"/index.php/app/requestVideoCategories");
		
		
		HttpClient httpclient = new DefaultHttpClient();
		    HttpPost httppost = new HttpPost(url.toString());

		    try {
		        // Add your data
		        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		        nameValuePairs.add(new BasicNameValuePair("DEVICE_ID", androidId));
		        nameValuePairs.add(new BasicNameValuePair("APPLICATION_ID", APPLICATION_ID));
		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		        // Execute HTTP Post Request
		        HttpResponse response = httpclient.execute(httppost);
		        System.out.println(response.toString());
		    } catch (ClientProtocolException e) {
		        // TODO Auto-generated catch block
		    	e.printStackTrace();
		    } catch (IOException e) {
		        // TODO Auto-generated catch block
		    	e.printStackTrace();
		    }catch (Exception e)
		    {
		    	e.printStackTrace();
		    }
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
}
}
