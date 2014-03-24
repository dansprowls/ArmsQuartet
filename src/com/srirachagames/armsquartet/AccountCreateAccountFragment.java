package com.srirachagames.armsquartet;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AccountCreateAccountFragment extends Fragment implements
		OnClickListener {

	private static final String TAG = "AccountCreateAccountFragment";
	private AppInstance appInstance;

	private Button buttonCreateAccount;
	private EditText editTextCharacterName, editTextPassword,
			editTextRepeatPassword, editTextEmail;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		appInstance = (AppInstance) getActivity().getApplication();
		
		View v = inflater.inflate(R.layout.account_createaccount_fragment,
				container, false);

		// Initialize Widgets
		buttonCreateAccount = (Button) v
				.findViewById(R.id.createaccount_button_createaccount);
		buttonCreateAccount.setOnClickListener(this);

		editTextCharacterName = (EditText) v
				.findViewById(R.id.createaccount_edittext_charactername);
		editTextPassword = (EditText) v
				.findViewById(R.id.createaccount_edittext_password);
		editTextRepeatPassword = (EditText) v
				.findViewById(R.id.createaccount_edittext_repeatpassword);
		editTextEmail = (EditText) v
				.findViewById(R.id.createaccount_edittext_email);

		return v;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.createaccount_button_createaccount:
			appInstance.showProgressBar("Creating new account...", getActivity());
			createAccount();
			break;
		}

	}

	/*
	 * createAccount() is called when the create account button is clicked.
	 * onClick() method. Attempts to create a new user with the ParseUser
	 * object. If there is an error it toasts the error to the user. If the user
	 * was successfully created it links to a new activity that asks the user to
	 * verify the email address.
	 */
	// TODO: Check minimum length requirements for inputs.
	// Check for spaces in user inputs.
	// Check that username != password.
	// Add password constraints. (symbols/numbers/etc.)
	// Check that the character's name is unique.
	private void createAccount() {
		ParseUser user = new ParseUser();

		// Make sure that the user has entered information.
		if (!inputIsEmpty()) {
			// Check if the user's password matches the repeated password.
			if (editTextPassword
					.getEditableText()
					.toString()
					.equals(editTextRepeatPassword.getEditableText().toString())) {

				// Set user's information into the ParseUser object.
				user.setUsername(editTextEmail.getEditableText().toString());
				user.setPassword(editTextPassword.getEditableText().toString());
				user.setEmail(editTextEmail.getEditableText().toString());
				user.put("characterName", editTextCharacterName.getEditableText().toString());
				user.put("gold", 0);
				user.put("wins", 0);
				user.put("loses", 0);
				user.put("draws", 0);
				user.put("adsRemoved", false);
				user.put("hasCharacter", false);
				// TODO:
				// user.put("battleList", );
				// user.put("friendsList", ):

				// Attempt to sign the user up.
				user.signUpInBackground(new SignUpCallback() {
					public void done(ParseException e) {
						if (e == null) {
							// Sign up was a success!
							Log.d(TAG, "Successfully signed up Character: "
									+ editTextCharacterName.getEditableText()
											.toString());
							appInstance.setCurrentUser(ParseUser.getCurrentUser());
							// TODO: Character is created, inform user to spend ability points, etc.
							appInstance.dismissProgressBar();
							startMainMenuActivity();
						} else {
							// Sign up has failed.

							Log.d(TAG,
									"Error: Failed to create account: "
											+ e.getMessage());
							appInstance.dismissProgressBar();
							appInstance.toastUser(e.getMessage(), getActivity());
						}
					}
				});
			} else { // Passwords do not match.
				appInstance.toastUser("the password inputs do not match", getActivity());
				Log.d(TAG,
						"Failed to create account: The password inputs did not match.");
			}
		}
	}

	/*
	 * inputIsEmpty() is called to check the EditTexts have input from the user.
	 * in createAccount(). Returns true if one of the inputs is empty. Returns
	 * false otherwise. Toasts the user which input is empty.
	 */
	// TODO: Set focuses on EditTexts that need editing.
	// Clear user inputs as needed.
	private boolean inputIsEmpty() {
		// Username is empty.
		if (editTextCharacterName.getEditableText().toString().isEmpty()) {
			appInstance.toastUser("please enter a name", getActivity());
			return true;
		}
		// Password is empty.
		else if (editTextPassword.getEditableText().toString().isEmpty()) {
			appInstance.toastUser("please enter a password", getActivity());
			return true;
		}
		// Repeated password is empty.
		else if (editTextRepeatPassword.getEditableText().toString().isEmpty()) {
			appInstance.toastUser("please repeat the password", getActivity());
			return true;
		}
		// Email is empty.
		else if (editTextEmail.getEditableText().toString().isEmpty()) {
			appInstance.toastUser("please enter an email address", getActivity());
			return true;
		}
		// No empty EditText inputs.
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
