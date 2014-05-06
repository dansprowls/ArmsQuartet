package com.srirachagames.armsquartet;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;


public class AccountSignInFragment extends Fragment implements OnClickListener {

	private static final String TAG = "AccountSignInFragment";
	private AppInstance appInstance;

	private Button buttonSignIn, buttonForgotPassword;
	private EditText editTextEmail, editTextPassword;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		appInstance = (AppInstance) getActivity().getApplication();

		View v = inflater.inflate(R.layout.account_signin_fragment, container,
				false);

		// Initialize Buttons
		buttonSignIn = (Button) v.findViewById(R.id.signin_button_signin);
		buttonSignIn.setOnClickListener(this);
		buttonForgotPassword = (Button) v
				.findViewById(R.id.signin_button_forgotpassword);
		buttonForgotPassword.setOnClickListener(this);

		editTextEmail = (EditText) v
				.findViewById(R.id.signin_edittext_email);
		editTextPassword = (EditText) v
				.findViewById(R.id.signin_edittext_password);

		return v;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.signin_button_signin:
			appInstance.showProgressBar("Attemping to sign in...", getActivity());
			signIn();
			break;
		case R.id.signin_button_forgotpassword:
			// TODO: Set up a password retrieval fragment.
			break;
		}
	}

	/*
	 * signIn() is called when the sign in button is clicked. onClick() method.
	 * Attempts to log the user in with the editable text in the
	 * editTextUsername and editTextPassword EditTexts. If there was an error
	 * signing in it toasts the error to the user. If the user was successfully
	 * logged in it links to the main menu activity.
	 */
	private void signIn() {
		// Make sure the user has entered information.
		if (!inputIsEmpty()) {
			ParseUser.logInInBackground(editTextEmail.getEditableText()
				.toString(), editTextPassword.getEditableText().toString(),
				new LogInCallback() {
					public void done(ParseUser user, ParseException e) {
						if (user != null) {
							// Sign in was a success!
							Log.d(TAG, "Sign in was a success!");

							// Set the user in the appInstance.
							appInstance.setCurrentUser(user);
							appInstance.dismissProgressBar();
							// Launch the Menu Activity
							startMainMenuActivity();
						} else {
							// Sign in has failed.
							Log.d(TAG,
									"Error signing in: " + e.getMessage());
							appInstance.dismissProgressBar();
							appInstance.toastUser(e.getMessage(), getActivity());
						}
					}
				});
		}
		else {
			Log.d(TAG, "Failed to sign in: An input was empty.");
		}
	}

	/*
	 * inputIsEmpty() is called to check the EditTexts have input from the user.
	 * in signIn(). Returns true if one of the inputs is empty. Returns false
	 * otherwise. Toasts the user which input is empty.
	 */
	// TODO: Set focuses on EditTexts that need editing.
	// Clear user inputs as needed.
	private boolean inputIsEmpty() {
		// Username is empty.
		if (editTextEmail.getEditableText().toString().isEmpty()) {
			appInstance.toastUser("please enter your email address", getActivity());
			return true;
		}
		// Password is empty.
		else if (editTextPassword.getEditableText().toString().isEmpty()) {
			appInstance.toastUser("please enter your password", getActivity());
			return true;
		}
		// No empty EditTextinputs.
		else {
			return false;
		}
	}

	private void startMainMenuActivity() {
		Log.d(TAG, TAG + " -> MenuActivity");
		Intent mainMenu = new Intent(getActivity(), MenuActivity.class);
		startActivity(mainMenu);
	}
}
