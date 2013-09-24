package com.bowstringLLP.bigcloud;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.os.Environment;

public class DownloadVideo {

	private final static int TIMEOUT_CONNECTION = 5000;//5sec
	private final static int TIMEOUT_SOCKET = 30000;//30sec
	
	public static void download(String path, int vidID)
	{
			try{
		            URL url = new URL(path);

		            //Open a connection to that URL.
		            URLConnection ucon = url.openConnection();

		            //this timeout affects how long it takes for the app to realize there's a connection problem
		            ucon.setReadTimeout(TIMEOUT_CONNECTION);
		            ucon.setConnectTimeout(TIMEOUT_SOCKET);


		            //Define InputStreams to read from the URLConnection.
		            // uses 3KB download buffer
		            InputStream is = ucon.getInputStream();
		            BufferedInputStream inStream = new BufferedInputStream(is, 1024 * 5);
		            FileOutputStream outStream = new FileOutputStream(Environment.getExternalStorageDirectory().toString() + "/video/"+ vidID);
		            byte[] buff = new byte[5 * 1024];

		            //Read bytes (and store them) until there is nothing more to read(-1)
		            int len;
		            while ((len = inStream.read(buff)) != -1)
		            {
		                outStream.write(buff,0,len);
		            }

		            //clean up
		            outStream.flush();
		            outStream.close();
		            inStream.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
	}
}
