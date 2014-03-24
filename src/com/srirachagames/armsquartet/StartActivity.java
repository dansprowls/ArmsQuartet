package com.srirachagames.armsquartet;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/*		This activity acts as a decider on which activity to launch when the 
 *  app is opened.  
 *  	If there is no current user recognized the app will
 *  start in AccountActivity where the user can create an account or 
 *  sign in with an existing account.
 *  	If there is a current user recognized the app will open in MenuActivity.
 */
public class StartActivity extends Activity {

	private static final String TAG = "StartActivity";
	private AppInstance appInstance;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		appInstance = (AppInstance)getApplication();
		
		// Hide the action bar to prevent it from loading any visuals.
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		
		if(appInstance.getHasCurrentUser()) {
			startMenuActivity();
		}
		else {
			startAccountActivity();
		}
		
		finish(); // To ensure the user cannot back-press to this activity.
	}

	/*
	 * 
	 */
	private void startMenuActivity() {
		Intent mainMenu = new Intent(this, MenuActivity.class);
		startActivity(mainMenu);
		Log.d(TAG, TAG + " -> MenuActivity");
	}
	
	/*
	 * 
	 */
	private void startAccountActivity() {
		Intent account = new Intent(this, AccountActivity.class);
		startActivity(account);
		Log.d(TAG, TAG + " -> AccountActivity");
	}
}
