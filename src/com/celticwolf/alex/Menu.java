package com.celticwolf.alex;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.ByteArrayBuffer;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuInflater;

public class Menu extends SherlockActivity implements View.OnClickListener{
	
	Button start,  about, sendbeer, banner;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.main);
		start = (Button) findViewById(R.id.bStart);
		about = (Button) findViewById(R.id.bAbout);
		sendbeer = (Button) findViewById(R.id.bSendBrand);
		banner = (Button) findViewById(R.id.bBanner);
		
		start.setOnClickListener(this);
		about.setOnClickListener(this);
		sendbeer.setOnClickListener(this);
		banner.setOnClickListener(this);
		AppRater.app_launched(this);
	
	}

	public void onClick(View v) {
		if (v.getId() == R.id.bStart) {
			startGame();
		} else if (v.getId() == R.id.bAbout) {
			about();
		} else if (v.getId() == R.id.bSendBrand) {
			sendbeer();
		} else if (v.getId() == R.id.bBanner) {
			openWebsite();
		}
		
	}

private void openWebsite() {
	String url = "http://www.schuetzengarten.ch/portal/";
	Intent i = new Intent(Intent.ACTION_VIEW);
	i.setData(Uri.parse(url));
	startActivity(i);
		
	}

private void about() {
	/*Intent goabout  = new Intent("com.celticwolf.alex.ABOUT");
	startActivity(goabout);*/
	DownloadWebPageTask task = new DownloadWebPageTask();
	task.execute(new String[] { "http://schmidt-mathias.ch/Alex/sqlbeerlist.sqlite" });
    //task.doInBackground( "http://schmidt-mathias.ch/Alex/sqlbeerlist.sqlite");

		
	}

public void sendbeer(){
	Intent gosendbeer  = new Intent("com.celticwolf.alex.SENDBRAND");
	startActivity(gosendbeer);
}


	
	public void startGame() {

		Intent startGame  = new Intent("com.celticwolf.alex.GAME");
		startActivity(startGame);
	}
	
	 @Override
	  protected void onDestroy() {
	    super.onDestroy();
	  }

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.short_menu, (com.actionbarsherlock.view.Menu) menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item1:
			rateApp();
			return true;
		case R.id.item2:
			shareIt();
			return true;
		}
		return false;
	}
	private void shareIt() {
		String share = getResources().getString(R.string.ac_share);
		Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
		sharingIntent.setType("text/plain");
		String shareBody = "https://play.google.com/store/apps/details?id=com.celticwolf.alex#?t=W251bGwsMSwxLDIxMiwiY29tLmNlbHRpY3dvbGYuYWxleCJd";
		sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Beer or no Beer™ Drinking Game");
		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
		startActivity(Intent.createChooser(sharingIntent, share));
	}

	private void rateApp() {
		String url = "https://play.google.com/store/apps/details?id=com.celticwolf.alex#?t=W251bGwsMSwxLDIxMiwiY29tLmNlbHRpY3dvbGYuYWxleCJd";
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));
		startActivity(i);
	}
	
	  public class DownloadWebPageTask extends AsyncTask<String, Void, String> {
		    @Override
		    protected String doInBackground(String... urls) {
		      String response = "";
		      String DB_PATH = "/data/data/com.celticwolf.alex/databases/";
		      String DB_NAME = "sqlbeerlist.sqlite";
		     
		  	try {
                URL url = new URL("http://schmidt-mathias.ch/Alex/sqlbeerlist.sqlite"); //you can write here any link
                File file = new File("sqlbeerlist.sqlite");

                long startTime = System.currentTimeMillis();
                Log.d("ImageManager", "download begining");
                Log.d("ImageManager", "download url:" + url);
                Log.d("ImageManager", "downloaded file name:" + "sqlbeerlist.sqlite");
                /* Open a connection to that URL. */
                URLConnection ucon = url.openConnection();

                /*
                 * Define InputStreams to read from the URLConnection.
                 */
                InputStream is = ucon.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                /*
                 * Read bytes to the Buffer until there is nothing more to read(-1).
                 */
                ByteArrayBuffer baf = new ByteArrayBuffer(50);
                int current = 0;
                while ((current = bis.read()) != -1) {
                        baf.append((byte) current);
                }

                /* Convert the Bytes read to a String. 
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(baf.toByteArray());
                fos.close();
                Log.d("ImageManager", "download ready in"
                                + ((System.currentTimeMillis() - startTime) / 1000)
                                + " sec");*/

               //FileOutputStream fos = new FileOutputStream(file);
                
                
                String outFileName = DB_PATH + DB_NAME;
        		OutputStream myOutput = new FileOutputStream(outFileName);

        		// transfer bytes from the inputfile to the outputfile

        		byte[] buffer = new byte[1024];

        		int length;

        		while ((length = is.read(buffer)) > 0) {

        			myOutput.write(buffer, 0, length);

        		}

        		// Close the streams

        		myOutput.flush();

        		myOutput.close();

        		is.close();


        } catch (IOException e) {
                Log.d("ImageManager", "Error: " + e);
        }
		      return response;
		    }
	
	
	  }
}
