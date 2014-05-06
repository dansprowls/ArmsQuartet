package com.srirachagames.armsquartet;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.List;

public class MenuBattlesFragment extends Fragment implements OnClickListener {
	private AppInstance appInstance;
	private static final String TAG = "MenuBattlesFragment";

	private Button startBattleButton;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		appInstance = (AppInstance) getActivity().getApplication();		
				
		populateBattleList();
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
			Log.d(TAG, "Add battle clicked");
			FragmentManager manager = getFragmentManager();
			AddBattleDialog addBattleDialog = new AddBattleDialog();
			addBattleDialog.show(manager, "addBattleDialog");
			break;
		}
	}

	/*
	 * This method creates a battle button for each battle the user is currently
	 * fighting.
	 */
	private void populateBattleList() {
		// Set LayoutParams for battle buttons to be added.
		LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);

		// Get the appropriate pixel value for 6dp to use as the margin for each button.
		int marginSize = (int) appInstance.convertDpToPixel(6, getActivity());
		// Set the 6dp margin to left, right and bottom of the button.
		buttonParams.setMargins(marginSize, 0, marginSize, marginSize);

		// Get the linear layout to add the buttons to.
		LinearLayout battleListLayout = (LinearLayout) getActivity().findViewById(
				R.id.menu_battles_linear_layout);
		
		// Get the current user's battle list data.
		List<String> battleList = appInstance.getCurrentUser().getList("battleList");

		// Does the user have at least one battle?
		if(battleList != null) {
			// Create a button for each battle.
			for(int i = 0; i < battleList.size(); i++) {
				Button battleButton = new Button(getActivity());
				battleButton.setText(battleList.get(i));
				battleButton.setBackgroundResource(R.drawable.button_colors);
				battleButton.setTextSize(20);
				battleButton.setTextColor(getResources().getColor(R.color.white));
				// Dynamically set on click for each battle button.
				battleButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Button button = (Button) v;
						Log.d(TAG, button.getText() + " clicked");
						
					}
				});
				// Add the button to the layout.
				battleListLayout.addView(battleButton, buttonParams);
			}
		}
		
	}
	
}
