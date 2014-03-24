package com.srirachagames.armsquartet;

import java.util.List;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class AddFriendDialog extends DialogFragment implements OnClickListener {

	private static final String TAG = "AddFriendDialog";
	AppInstance appInstance;
	Button addFriend;
	EditText userInput;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		appInstance = (AppInstance) getActivity().getApplication();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Set the title of the dialog.
		getDialog().setTitle("Add a Friend");

		View v = inflater.inflate(R.layout.add_friend_dialog, null);

		// Set the onClickListener for the addFriend button.
		addFriend = (Button) v.findViewById(R.id.add_friend_button);
		addFriend.setOnClickListener(this);

		userInput = (EditText) v.findViewById(R.id.add_friend_edittext);

		return v;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.add_friend_button:
			addFriend();
			break;
		}
	}

	private void addFriend() {
		// Ensure the user entered a name.
		String input = userInput.getEditableText().toString();
		if (input.isEmpty()) {
			appInstance.toastUser("please enter a name...", getActivity());
			return;
		}

		List<String> friendsList = appInstance.getCurrentUser().getList("friendsList");
		// Check if this is the user's first friend.
		if(friendsList == null) {}
		// Check if the user already has that person friended.
		else if (friendsList.contains(input)) {
			appInstance.toastUser("You are already friends with "
					+ input, getActivity());
			return;
		}
		
		// Make sure the user is not trying to add themselves...
		if(input.equals(appInstance.getCurrentUser().get("characterName"))) {
			appInstance.toastUser("You cannot add yourself...", getActivity());
			return;
		}
		
		// Begin the progress bar while the data loads.
		appInstance.showProgressBar("Searching for "
				+ userInput.getEditableText().toString(), getActivity());

		// Query for a User with the Character name in input.
		ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
		query.whereEqualTo("characterName", input);
		query.getFirstInBackground(new GetCallback<ParseObject>() {
			@Override
			public void done(ParseObject parseUser, ParseException e) {
				if (e == null) {
					// Successfully loaded user data.
					Log.d(TAG, "Successfully found User with Character: "
							+ parseUser.getString("characterName"));
					
					// Add friend to user's friends list 
					ParseUser user = appInstance.getCurrentUser();
					user.add("friendsList",
							parseUser.getString("characterName"));
					user.saveInBackground(new SaveCallback() {
						public void done(ParseException e) {
							if (e == null) {
								// Successfully updated user's friendslist.
								Log.d(TAG, "Updated the user's friendslist.");
								
							} else {
								// Failed to update user's friendslist.
								Log.d(TAG, "ERROR: " + e.getMessage());
							}
							// End the progress bar.
							appInstance.dismissProgressBar();
						}
					});
					// Dismiss the DialogFragment.
					dismiss();

				} else {
					// Failed to find a User with that Character name.
					Log.d(TAG, "Error: " + e.getMessage());
					appInstance.toastUser(e.getMessage(), getActivity());
					// End the progress bar.
					appInstance.dismissProgressBar();
				}
			}
		});
	}
}
