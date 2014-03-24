package com.srirachagames.armsquartet;

import java.util.List;

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

public class MenuFriendsListFragment extends Fragment implements
		OnClickListener {
	private static final String TAG = "MenuFriendsListFragment";
	private AppInstance appInstance;
	private User user;
	private Button addFriendButton;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		appInstance = (AppInstance) getActivity().getApplication();
		user = appInstance.getUser();
		// TODO: user.fetchBattleList;
		// TODO: user.fetchFriendsList;

		populateFriendList();
	}

	@Override
	public View onCreateView(LayoutInflater infalter, ViewGroup container,
			Bundle savedInstanceState) {
		appInstance = (AppInstance) getActivity().getApplication();
		View v = infalter.inflate(R.layout.menu_friends_list_fragment,
				container, false);
		
		// Set on click listener for add friend button.
		addFriendButton = (Button) v.findViewById(R.id.menu_friends_button_add_friend);
		addFriendButton.setOnClickListener(this);
		
		return v;

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.menu_friends_button_add_friend:
			showDialog(true, null);
			Log.d(TAG, "Add friend button clicked");
			break;
		}

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
		LinearLayout friendList = (LinearLayout) getActivity().findViewById(
				R.id.menu_friends_linear_layout);

		// Add a button to the layout for each Friend the user is has.
		List<String> friendsList = appInstance.getCurrentUser().getList("friendsList");
		// Does the user have at least one friend?
		if(friendsList != null) {
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
						showDialog(false, b);
					}
				});
				friendList.addView(friendButton, buttonParams);
				Log.d(TAG, "Created button for friend: " + friendsList.get(i));
			}
		}
	}

	public void showDialog(boolean addFriend, Bundle b) {
		FragmentManager manager = getFragmentManager();
		if(addFriend == true) {
			AddFriendDialog addFriendDialog = new AddFriendDialog();
			addFriendDialog.show(manager, "addFriendDialog");
		}
		else {
			FriendDialog friendDialog = new FriendDialog();
			friendDialog.setArguments(b);
			friendDialog.show(manager, "friendDialog");
		}
	}

}
