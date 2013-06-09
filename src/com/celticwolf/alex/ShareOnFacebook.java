package com.celticwolf.alex;

import com.facebook.android.*;
import com.facebook.android.Facebook.DialogListener;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

public class ShareOnFacebook extends Activity{

	private static final String APP_ID = "352567491507909";
	private static final String[] PERMISSIONS = new String[] {"publish_stream","publish_actions","manage_pages"};
	private static final String PAGE_ID ="118709531601144";

	private static final String TOKEN = "access_token";
        private static final String EXPIRES = "expires_in";
        private static final String KEY = "facebook-credentials";

	private Facebook facebook;
	private String messageToPost;

	public boolean saveCredentials(Facebook facebook) {
        	Editor editor = getApplicationContext().getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
        	editor.putString(TOKEN, facebook.getAccessToken());
        	editor.putLong(EXPIRES, facebook.getAccessExpires());
        	return editor.commit();
    	}

    	public boolean restoreCredentials(Facebook facebook) {
        	SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        	facebook.setAccessToken(sharedPreferences.getString(TOKEN, null));
        	facebook.setAccessExpires(sharedPreferences.getLong(EXPIRES, 0));
        	return facebook.isSessionValid();
    	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		facebook = new Facebook(APP_ID);
		restoreCredentials(facebook);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.facebook_dialog);

		String facebookMessage = getIntent().getStringExtra("facebookMessage");
		if (facebookMessage == null){
			facebookMessage = "Test wall post";
		}
		messageToPost = facebookMessage;
	}

	public void doNotShare(View button){
		finish();
	}
	public void share(View button){
		if (! facebook.isSessionValid()) {
			loginAndPostToWall();
		}
		else {
			postToWall(messageToPost);
		}
	}

	public void loginAndPostToWall(){
		 facebook.authorize(this, PERMISSIONS, Facebook.FORCE_DIALOG_AUTH, new LoginDialogListener());
	}

	public void postToWall(String message){	
		new MyAsyncTask().execute(message);
	} 
	
	


class MyAsyncTask extends AsyncTask<String,Void,Boolean>
{
public Boolean doInBackground(String ...message){

    Bundle parameters = new Bundle();
            parameters.putString("message", message[0]);
            parameters.putString("description", "topic share");
            try {
                facebook.request("me");   
        String response = facebook.request("118709531601144/feed", parameters, "POST");
        Log.d("Tests", "got response: " + response);
        if (response == null || response.equals("") ||
                response.equals("false")) {
            return Boolean.FALSE;
        }
        else {
             return Boolean.TRUE;
        }
    } catch (Exception e) {

        e.printStackTrace();
        return Boolean.FALSE;
    }
} 

public void onPostExecute(Boolean result){
        if(result == Boolean.TRUE){
 showToast("posted successfully");
}else{
 showToast("couldn't post to FB.");
}
        finish();
}
}
	
	
	
	
	

	class LoginDialogListener implements DialogListener {
	    public void onComplete(Bundle values) {
	    	saveCredentials(facebook);
	    	if (messageToPost != null){
			postToWall(messageToPost);
		}
	    }
	    public void onFacebookError(FacebookError error) {
	    	showToast("Authentication with Facebook failed!");
	        finish();
	    }
	    public void onError(DialogError error) {
	    	showToast("Authentication with Facebook failed!");
	        finish();
	    }
	    public void onCancel() {
	    	showToast("Authentication with Facebook cancelled!");
	        finish();
	    }
	}

	private void showToast(String message){
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	}
}