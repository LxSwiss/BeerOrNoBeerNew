package com.celticwolf.alex;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import android.os.AsyncTask;
import android.util.Log;


	  public class DownloadWebPageTask extends AsyncTask<String, Void, String> {
		    @Override
		    protected String doInBackground(String... urls) {
		      String response = "";
		      String DB_PATH = "/data/data/com.celticwolf.alex/databases/";
		      String DB_NAME = "sqlbeerlist.sqlite";
		      String outFileName = DB_PATH + DB_NAME;
		     
		  	try {
		  		URL url = new URL(urls[0]);
	            URLConnection connection = url.openConnection();
	            connection.connect();
	            // this will be useful so that you can show a typical 0-100% progress bar
	            int fileLength = connection.getContentLength();

	            // download the file
	            InputStream input = new BufferedInputStream(url.openStream());
	            OutputStream output = new FileOutputStream("/sdcard/"+DB_NAME);

	            byte data[] = new byte[1024];
	            long total = 0;
	            int count;
	            while ((count = input.read(data)) != -1) {
	                total += count;
	                // publishing the progress....
	                //publishProgress((int) (total * 100 / fileLength));
	                output.write(data, 0, count);
	            }

	            output.flush();
	            output.close();
	            input.close();

      } catch (IOException e) {
              Log.d("ImageManager", "Error: " + e);
      }
		      return response;
		    }
	
	
	  }

