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

// TODO: Have the list refresh automatically after a friend is added or deleted.
		// Broadcast Receiver or Listener? 
public class MenuFriendsListFragment extends Fragment implements
		OnClickListener {
	private static final String TAG = "MenuFriendsListFragment";
	private AppInstance appInstance;
	private Button addFriendButton;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		appInstance = (AppInstance) getActivity().getApplication();
		//createAddFriendButton();
		populateFriendList();
	}

	@Override
	public View onCreateView(LayoutInflater infalter, ViewGroup container,
			Bundle savedInstanceState) {
		appInstance = (AppInstance) getActivity().getApplication();
		View v = infalter.inflate(R.layout.menu_friends_list_fragment,
				container, false);
		return v;

	}

	@Override
	public void onClick(View v) { // Dynamically added to each button present.
	}

	private void populateFriendList() {
		// Set LayoutParams for battle buttons to be added.
		LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);

		// Get the appropriate pixel value for 6dp to use as the margin for each button.
		int marginSize = (int) appInstance.convertDpToPixel(6, getActivity());
		// Set the 6dp margin to left, right and bottom of the button.
		buttonParams.setMargins(marginSize, 0, marginSize, marginSize);

		// Get the linear layout to add the buttons to.
		LinearLayout friendListLayout = (LinearLayout) getActivity().findViewById(
				R.id.menu_friends_linear_layout);

		// Get the user's friends list from the current user's data.
		List<String> friendsList = appInstance.getCurrentUser().getList("friendsList");
		
		// Recreate the Add Friend button.
		createAddFriendButton(friendListLayout);
		
		// Does the user have at least one friend?
		if(friendsList != null) {
			
			// Remove friends before reapplying them.
			friendListLayout.removeAllViews();
			
			// Recreate the Add Friend button.
			createAddFriendButton(friendListLayout);
			
			// Create a button for each friend.
			for(int i = 0; i < friendsList.size(); i++) {
				Button friendButton = new Button(getActivity());
				friendButton.setText(friendsList.get(i));
				friendButton.setBackgroundResource(R.drawable.button_colors);
				friendButton.setTextSize(20);
				friendButton.setTextColor(getResources().getColor(R.color.white));
				// Dynamically apply onClick functionality.
				friendButton.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Button button = (Button)v;
						Log.d(TAG, button.getText() + " clicked");
						Bundle b = new Bundle();
						b.putString("friendName", button.getText().toString());
						showFriendDialog(b);
					}
				});
				// Add the button the the layout.
				friendListLayout.addView(friendButton, buttonParams);
			}
		}
	}

	private void showAddFriendDialog() {
		FragmentManager manager = getFragmentManager();
		AddFriendDialog addFriendDialog = new AddFriendDialog();
		addFriendDialog.show(manager, "addFriendDialog");
	}

	private void showFriendDialog(Bundle b) {
		FragmentManager manager = getFragmentManager();
		FriendDialog friendDialog = new FriendDialog();
		friendDialog.setArguments(b);
		friendDialog.show(manager, "friendDialog");
	}
	
	private void createAddFriendButton(LinearLayout friendList) {
		// Set LayoutParams for button.
		LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);

		// Get the appropriate pixel value for 6dp to use as the margin for each button.
		int marginSize = (int) appInstance.convertDpToPixel(6, getActivity());
		// Set the 6dp margin to left, right and bottom of the button.
		buttonParams.setMargins(marginSize, marginSize, marginSize, marginSize);
				
		// Create the add friend button.
		addFriendButton = new Button(getActivity());
		addFriendButton.setText("+ Add a Friend");
		addFriendButton.setBackgroundResource(R.drawable.button_colors);
		addFriendButton.setTextSize(20);
		addFriendButton.setTextColor(getResources().getColor(R.color.white));
		addFriendButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Log.d(TAG, "Add friend button clicked");
				showAddFriendDialog();
			}
		});
		// Add the add friend button to the layout.
		friendList.addView(addFriendButton, buttonParams);
	}
}
