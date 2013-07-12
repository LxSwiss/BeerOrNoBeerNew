package com.celticwolf.alex;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;

public class Splash extends Activity{
	Boolean DB_isCreated = false;
	MediaPlayer ourSong = new MediaPlayer();
	@Override
	protected void onCreate(Bundle TravisLoveBacon) {
		
		super.onCreate(TravisLoveBacon);
		setContentView(R.layout.splash);
		ourSong = MediaPlayer.create(Splash.this, R.raw.bottleopen);
		ourSong.start();

		Thread timer = new Thread(){
			public void run(){
				try{
					sleep(1500);
				}catch(InterruptedException e){
					e.printStackTrace();
				}finally{
					Intent openStartingPoint  = new Intent("com.celticwolf.alex.MENU");
					startActivity(openStartingPoint);
					
				}
			}
		};
		timer.start();
		
	}


	@Override
	protected void onPause() {
		super.onPause();
		ourSong.release();
		finish();
	}
}
