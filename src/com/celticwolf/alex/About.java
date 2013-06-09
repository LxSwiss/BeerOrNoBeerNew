package com.celticwolf.alex;


//import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
//import android.view.MenuItem;
//import android.view.Menu;
//import android.view.MenuInflater;
import android.view.View;
import android.support.v4.app.FragmentActivity;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuInflater;

public class About extends SherlockActivity implements View.OnClickListener{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.short_menu, menu);
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item1:
			rateApp();
			return true;
		case R.id.item2:
			openFacebook();
			return true;
		
		case android.R.id.home:
			Intent intent = new Intent(this, Menu.class);
	        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        startActivity(intent);
	        return true;
		}
		return false;
	}
	
	private void openFacebook() {
		String url = "https://www.facebook.com/BeerOrNoBeer";
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));
		startActivity(i);
	}

	private void rateApp() {
		String url = "https://play.google.com/store/apps/details?id=com.celticwolf.alex&feature=search_result#?t=W251bGwsMSwyLDEsImNvbS5jZWx0aWN3b2xmLmFsZXgiXQ..";
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));
		startActivity(i);
	}
	

	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
