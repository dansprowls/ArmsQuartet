package com.srirachagames.armsquartet;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class ChallengeFriendDialog extends DialogFragment implements
		OnClickListener {

	private static final String TAG = "ChallengeFriendDialog";
	private AppInstance appInstance;
	private Button yes, no;
	private String friendName;
	private ParseObject newBattle;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		appInstance = (AppInstance) getActivity().getApplication();
		friendName = getArguments().getString("friendName");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getDialog().setTitle("Challenge " + friendName + "?");
		View v = inflater.inflate(R.layout.challenge_friend_dialog, null);
		
		yes = (Button) v.findViewById(R.id.button_yes_challenge);
		yes.setOnClickListener(this);
		
		no = (Button) v. findViewById(R.id.button_no_challenge);
		no.setOnClickListener(this);
		
		return v;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_yes_challenge:
			challengeFriend();
			break;
		case R.id.button_no_challenge:
			dismiss();
			break;
		}
	}
	
	private void challengeFriend() {
		appInstance.showProgressBar("Challenging " + friendName, getActivity());
		newBattle = new ParseObject("Battles");
		newBattle.put("turn", true);
		newBattle.put("accepted", false);
		newBattle.put("user1", ParseUser.getCurrentUser().get("characterName"));
		newBattle.put("user2", friendName);
		// TODO: Put each user's starting stats for a battle.
		
		// Save the new battle in a background thread.
		newBattle.saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException e) {
				if(e == null) {
					// Successfully created new battle.
					Log.d(TAG, "A new battle was created");
					appInstance.dismissProgressBar();
					appInstance.refreshBattleList(getActivity());
					dismiss();
					// TODO: Launch the battle screen here.
				} else {
					// Failed to create new battle.
					Log.d(TAG, "ERROR: " + e.getMessage());
				}
				
			}
		});
		
	}
}
