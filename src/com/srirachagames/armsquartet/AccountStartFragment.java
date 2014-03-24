package com.srirachagames.armsquartet;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class AccountStartFragment extends Fragment implements OnClickListener {

	private Button buttonSignIn,
			  	   buttonCreateAccount;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.account_start_fragment, container, false);
	     
        // Initialize buttons.
		buttonSignIn = 
				(Button)v.findViewById(R.id.start_button_signin);
        buttonSignIn.setOnClickListener(this);
        
        buttonCreateAccount = 
        		(Button)v.findViewById(R.id.start_button_createaccount);
        buttonCreateAccount.setOnClickListener(this);
	
		return v;
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.start_button_signin:
			swapSignInFragment();
			break;
		case R.id.start_button_createaccount:
			swapCreateAccountFragment();
			break;
		}
		
	}
	
	private void swapSignInFragment() {
		AccountSignInFragment signInFragment = new AccountSignInFragment();
		FragmentManager manager = getFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.replace(R.id.account_activity_layout, signInFragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}

	private void swapCreateAccountFragment() {
		AccountCreateAccountFragment createAccountFragment = 
				new AccountCreateAccountFragment();
		FragmentManager manager = getFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.replace(R.id.account_activity_layout, createAccountFragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}
}
