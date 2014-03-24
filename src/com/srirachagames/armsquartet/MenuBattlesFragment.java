package com.srirachagames.armsquartet;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.RefreshCallback;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MenuBattlesFragment extends Fragment implements OnClickListener {
	private AppInstance appInstance;
	private User user;
	private static final String TAG = "MenuBattlesFragment";

	private Button startBattleButton;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		appInstance = (AppInstance) getActivity().getApplication();
		user = appInstance.getUser();
		// TODO: user.fetchBattleList;
		// TODO: user.fetchFriendsList;		
		
		/*
		 * Since this is the default tab to open when the menu activity starts this method
		 * only needs to be called in this fragment.  Populates battle and friend lists,
		 * as well as character data into each appropriate tab for a seamless transition
		 * when each tab is clicked.  AKA a slow initial load at first, but avoids many
		 * tiny load pauses.
		 */
		populateBattleList();
		//populateFriendList();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.menu_battles_fragment, container,
				false);

		// Set on click listener for start battle button.
		startBattleButton = (Button) v
				.findViewById(R.id.menu_battles_button_start_battle);
		startBattleButton.setOnClickListener(this);

		return v;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.menu_battles_button_start_battle:
		//	startBattle();
			//refreshCharacterData();
			showDialog(v);
			Log.d(TAG, "Add battle clicked");
			break;
		}
	}

	//TODO:  BROKEN!
	private void refreshCharacterData() {
		user.getCharacter().getParseCharacterObject().
							refreshInBackground(new RefreshCallback() {
			
			@Override
			public void done(ParseObject refreshedCharacter, ParseException e) {
				if(refreshedCharacter == null) {
					
				}
				else {
					// Successfully refreshed character object.
					user.setCharacter(refreshedCharacter);
					Log.d(TAG, "Refreshed character data.");
				}
				
			}
		});
	}

	public void showDialog(View v) {
		FragmentManager manager = getFragmentManager();
		AddBattleDialog addBattleDialog = new AddBattleDialog();
		addBattleDialog.show(manager, "addBattleDialog");
	}

	/*
	 * This method creates a battle button for each battle the user is currently
	 * fighting.
	 */
	private void populateBattleList() {
		// TODO: This is a temporary population for the battle list.
		// Fill this with data drawn from Parse.

		// Set LayoutParams for battle buttons to be added.
		LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);

		// Get the appropriate pixel value for 6dp to use as the margin for each
		// button.
		int marginSize = (int) appInstance.convertDpToPixel(6, getActivity());
		// Set the 6dp margin to left, right and bottom of the button.
		buttonParams.setMargins(marginSize, 0, marginSize, marginSize);

		// Get the linear layout to add the buttons to.
		LinearLayout battleList = (LinearLayout) getActivity().findViewById(
				R.id.menu_battles_linear_layout);

		// Add a button to the layout for each battle the user is in.
		Button battleButton = new Button(getActivity());
		battleButton.setText("Battle 1");
		battleButton.setBackgroundResource(R.drawable.button_colors);
		battleButton.setTextSize(20);
		battleButton.setTextColor(getResources().getColor(R.color.white));

		battleList.addView(battleButton, buttonParams);

		Button battleButton2 = new Button(getActivity());
		battleButton2.setText("Battle 2");
		battleButton2.setBackgroundResource(R.drawable.button_colors);
		battleButton2.setTextSize(20);
		battleButton2.setTextColor(getResources().getColor(R.color.white));

		battleList.addView(battleButton2, buttonParams);
	}
	
}
// TODO: The back button goes to a blank screen from this fragment.
// Exit the app when the back button is pressed here.
// Probably needs to be worked through the activity's onBackPressed() function.
