package com.srirachagames.armsquartet;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;


public class MenuActivity extends Activity {

	private final static String TAG = "MenuActivity";
	private AppInstance appInstance;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_activity);
		
		appInstance = (AppInstance) getApplication();
		
		initTabNavigation(); 	// Initialize the navigation tabs.
	}	
	
	@Override
	protected void onStart() {
		super.onStart();
		// Refresh all user and character data.
		appInstance.showProgressBar("Loading user data...", this);
		// Refresh ParseUser data in currentUser.
		appInstance.refreshUser();
	}

	/*
	 * initTabNavigation() puts the Action Bar in navigation tab mode and creates
	 * 	three tabs:
	 * -Left: Character
	 * 		 	This tab navigates to the Character Activity.
	 * 			The user can see their character's stats and portrait at first and 
	 * 				navigate to the Abilities Fragment and the Equipment Fragment.
	 * -Middle: Battles
	 * 		   	This is the default selected tab.
	 * 			This tab loads the Battles Fragment where users can see what
	 * 				battles they are currently in and can create new battles. 
	 * -Right: Friends
	 * 			This tab goes to the Friends List Fragment.
	 * 			This fragment allows the user to search for and add friends via
	 * 				character name or Facebook.
	 */
	private void initTabNavigation() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setTitle(R.string.app_name);
		
		ActionBar.Tab characterTab = actionBar.newTab().setText(R.string.menu_tab_character);
		MenuCharacterFragment menuCharacterFragment = new MenuCharacterFragment();
		characterTab.setTabListener(new MenuTabsListener(menuCharacterFragment));
		actionBar.addTab(characterTab, 0, false);
		
		ActionBar.Tab battlesTab = actionBar.newTab().setText(R.string.menu_tab_battles);
		MenuBattlesFragment menuBattlesFragment = new MenuBattlesFragment();
		battlesTab.setTabListener(new MenuTabsListener(menuBattlesFragment));
		actionBar.addTab(battlesTab, 1, true);
		
		ActionBar.Tab friendsTab = actionBar.newTab().setText(R.string.menu_tab_friends);
		MenuFriendsListFragment menuFriendsListFragment = new MenuFriendsListFragment();
		friendsTab.setTabListener(new MenuTabsListener(menuFriendsListFragment));
		actionBar.addTab(friendsTab, 2, false);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actions, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	class MenuTabsListener implements ActionBar.TabListener {
		private Fragment fragment;
		
		public MenuTabsListener(Fragment fragment) {
			this.fragment = fragment;
		}
		
		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			
		}
		
		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			ft.replace(R.id.fragment_container, fragment);
			Log.d(TAG, "Tab navigation occured.");
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			ft.remove(fragment);
			
		}
		
	}
}
