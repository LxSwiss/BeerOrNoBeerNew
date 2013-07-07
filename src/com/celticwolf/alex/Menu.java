package com.celticwolf.alex;


import java.io.IOException;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

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
		createDatabase();
	
	}

	private void createDatabase() {
		DataBaseHelper myDbHelper = new DataBaseHelper(null);
		myDbHelper = new DataBaseHelper(this);
		try {
			myDbHelper.createDataBase();
		} catch (IOException ioe) {
			throw new Error("Unable to create database");
		}
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
	Intent goabout  = new Intent("com.celticwolf.alex.ABOUT");
	startActivity(goabout);

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
	
}
