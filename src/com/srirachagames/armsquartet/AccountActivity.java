package com.srirachagames.armsquartet;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.parse.ParseAnalytics;

public class AccountActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_activity);
		
		// Start AccountActivity with the AccountStartFragment.
		AccountStartFragment startFragment = new AccountStartFragment();
		FragmentManager manager = getFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.add(R.id.account_activity_layout, startFragment);
		transaction.addToBackStack(null);
		transaction.commit();
		
		// Track how often the app is opened utilizing Parse Analytics.
        ParseAnalytics.trackAppOpened(getIntent());
   
	}
}
