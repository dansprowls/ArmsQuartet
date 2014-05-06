package com.srirachagames.armsquartet;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class FriendDialog extends DialogFragment implements OnClickListener {

	private static final String TAG = "AddFriendDialog";
	private AppInstance appInstance;
	private Button startBattle, msgFriend, removeFriend;
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
		// Set the title of the dialog.
		getDialog().setTitle(friendName);
		
		View v = inflater.inflate(R.layout.friend_dialog, null);
		
		// Set the onClickListeners for the three buttons.
		startBattle = (Button)v.findViewById(R.id.button_start_battle);
		startBattle.setOnClickListener(this);
		
		msgFriend = (Button)v.findViewById(R.id.button_msg_friend);
		msgFriend.setOnClickListener(this);
		
		removeFriend = (Button)v.findViewById(R.id.button_remove_friend);
		removeFriend.setOnClickListener(this);
		
		return v;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_start_battle:
			challengeFriend();
			break;
		case R.id.button_msg_friend:
			// TODO: Implement chat functionality...
			break;
		case R.id.button_remove_friend:
			removeFriend();
			break;
		}

	}
	
	private void challengeFriend() {
		Log.d(TAG, "Challenge friend clicked");
		Bundle b = new Bundle();
		b.putString("friendName", friendName);
		FragmentManager manager = getFragmentManager();
		ChallengeFriendDialog challengeFriendDialog = new ChallengeFriendDialog();
		challengeFriendDialog.setArguments(b);
		challengeFriendDialog.show(manager, "challenge_friend_dialog");
		dismiss(); // TODO: Put this elsewhere to avoid removing the FriendDialog always.
	}
	
	private void msgFriend() {
		Log.d(TAG, "Msg friend clicked");
	}

	private void removeFriend() {
		Log.d(TAG, "Remove friend clicked");
		Bundle b = new Bundle();
		b.putString("friendName", friendName);
		FragmentManager manager = getFragmentManager();
		ConfirmFriendDeletion confirmFriendDeletion = new ConfirmFriendDeletion();
		confirmFriendDeletion.setArguments(b);
		confirmFriendDeletion.show(manager, "confirm_friend_deletion");
		dismiss(); // TODO: Put this elsewhere to avoid removing the FriendDialog always.
	}
}
