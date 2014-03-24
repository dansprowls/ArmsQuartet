package com.srirachagames.armsquartet;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.RefreshCallback;

public class AppInstance extends Application {

	private static final String TAG = "AppInstance";

	// user is used to store all user information including the character
	// object.
	private User user;
	
	private ProgressDialog progressDialog;

	// Used as a variable to decide which screen to start the app in.
	private ParseUser currentUser;

	// @true: The app will start in the menu screen with character information
	// displayed.
	// @false: The app will start in the login/create account page.
	private boolean hasCurrentUser;

	@Override
	public void onCreate() {
		super.onCreate();
		
		// This links the app with the Parse server.
		Parse.initialize(this, "95mBiD5zHGaJZxBHmagQLtkfmIUU0BXIJkXr6U80",
				"i93s4R5m8ziGEVvcdCmosypxyLx4tghdWKlZJ2xe");
		
		// Check for a current user on app start.
		currentUser = ParseUser.getCurrentUser();
		if(currentUser == null) {
			Log.d(TAG, "App started with no current ParseUser");
			hasCurrentUser = false;
		}
		else {
			Log.d(TAG, "App started with a current ParseUser");
			hasCurrentUser = true;
		}

	}

	// Refresh the ParseUser currentUser.
	public void refreshUser() {
		currentUser = ParseUser.getCurrentUser();
		// Refresh user values.
		currentUser.refreshInBackground(new RefreshCallback() {
			@Override
			public void done(ParseObject refreshedUser, ParseException e) {
				if(refreshedUser == null) {
					// Failed to refresh the user.
					Log.d(TAG, "Failed to refresh the user");
				}
				else {
					// Successfully refreshed the user.
					Log.d(TAG, "Successfully refreshed the user");
					// Create a new User object based off the refreshed currentUser.
					// This also refreshes the character data.
					setUser(new User(getCurrentUser()));
					// Dismiss the progress bar.
					dismissProgressBar();
				}
			}
		});
	}
	
	// Progress bar that displays the passed message in the passed context.
	public void showProgressBar(String msg, Context context){
		  progressDialog = ProgressDialog.show(context, "", msg, true);
		}
	
	// Dismisses a progress bar.
	public void dismissProgressBar(){
	   if(progressDialog != null && progressDialog.isShowing())
	         progressDialog.dismiss();
	}

	// Logs the user out.
	public void logOut() {
		ParseUser.logOut();
		hasCurrentUser = false;
		Log.d(TAG, "The user has successfully logged out.");
	}
		/*
		 * Converts the dp value given to the appropriate pixel value based on the
		 * device in use.
		 */
	public float convertDpToPixel(float dp, Context context) {
		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		float px = dp * (metrics.densityDpi / 160f);
		return px;
	}

	/*
	 * Converts the pixel value given to the appropriate dp value based on the
	 * device in use. This method is not used but is handy to have ready.
	 */
	public float convertPixelsToDp(float px, Context context) {
		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		float dp = px / (metrics.densityDpi / 160f);
		return dp;
	}
	
	/*
	 * toastUser is called when an error has occurred to describe what happened
	 * to the user. Called in signIn() for error reporting.
	 * 
	 * @param: the error message to toast, the context.
	 */
	public void toastUser(String toastString, Context context) {
		context.getApplicationContext();
		int duration = Toast.LENGTH_LONG;

		Toast toast = Toast.makeText(context, toastString, duration);
		toast.show();

		Log.d(TAG, "Toasted user: " + toastString);
	}

	// **********************************************************************
	// Setters
	public void setUser(User user) {
		this.user = user;
	}

	public void setCurrentUser(ParseUser currentUser) {
		this.currentUser = currentUser;
	}

	public void setHasCurrentUser(boolean hasCurrentUser) {
		this.hasCurrentUser = hasCurrentUser;
	}

	// **********************************************************************
	// Getters
	public User getUser() {
		return user;
	}

	public ParseUser getCurrentUser() {
		return currentUser;
	}

	public boolean getHasCurrentUser() {
		return hasCurrentUser;
	}

}

// CODE TO LOAD CHARACTER:
/*
 * if(!appInstance.getHasLoadedCharacter()) { // There is no character currently
 * loaded. // Query Parse for the user's character. ParseQuery<ParseObject>
 * query = ParseQuery.getQuery("Character"); query.whereEqualTo("user",
 * user.getObjectId()); query.getFirstInBackground(new
 * GetCallback<ParseObject>() {
 * 
 * @Override public void done(ParseObject parseCharacter, ParseException e) { if
 * (e == null) { // Successfully loaded the character. Log.d(TAG,
 * "Successfully retrieved Character: " + parseCharacter.getString("name") +
 * " from Parse.");
 * 
 * // Grab the needed objects from the outer class. User user =
 * MenuBattlesFragment.this.user; PlayerCharacter character =
 * MenuBattlesFragment.this.character;
 * 
 * // Set the user's character object. user.setCharacter(parseCharacter);
 * character = user.getCharacter(); // Lower hasLoadedCharacter flag.
 * appInstance.setHasLoadedCharacter(true); } else { // The character failed to
 * load. Log.d(TAG, "Error: " + e.getMessage()); } } }); } else { // The
 * character is already up to date. Log.d(TAG,
 * "Character already loaded, skipped Parse Query."); character =
 * appInstance.getUser().getCharacter(); }
 */

// TODO: Look into ProGuard.

/*
 * TODO: BUGS:
 */