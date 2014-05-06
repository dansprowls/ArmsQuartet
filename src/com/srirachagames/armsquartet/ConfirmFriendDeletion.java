package com.srirachagames.armsquartet;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.parse.ParseUser;

import java.util.List;

public class ConfirmFriendDeletion extends DialogFragment implements
		OnClickListener {

	private static final String TAG = "DeleteFriendDialog";
	private AppInstance appInstance;
	private Button yes, no;
	private String friendName;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		appInstance = (AppInstance) getActivity().getApplication();
		friendName = getArguments().getString("friendName");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getDialog().setTitle("Remove " + friendName + "?");
		View v = inflater.inflate(R.layout.delete_friend_dialog, null);
		
		yes = (Button) v.findViewById(R.id.button_yes);
		yes.setOnClickListener(this);
		
		no = (Button) v.findViewById(R.id.button_no);
		no.setOnClickListener(this);
		
		return v;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_yes:
			deleteFriend();
			break;
		case R.id.button_no:
			dismiss();
			break;
		}

	}

	private void deleteFriend() {
		// Begin the progress bar while the data loads.
		appInstance.showProgressBar("Deleting...", getActivity());
		// Fetch the friend's name to delete.
		String friendName = getArguments().getString("friendName");
		// Get the friend list.
		List<String> friendsList = ParseUser.getCurrentUser().getList("friendsList");
		// Remove the friend from the list.
		friendsList.remove(friendName);
		// Save the new list to the ParseUser.
		ParseUser.getCurrentUser().put("friendsList", friendsList);
		// Sync the user.
		appInstance.refreshUser(getActivity());
		dismiss();
	}
	
}
