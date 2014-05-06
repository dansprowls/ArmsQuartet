/*
 * NOTE: The key that links this app with the Parse backend has been removed for security reasons. (LINE 48)
 */

package com.srirachagames.armsquartet;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.RefreshCallback;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class AppInstance extends Application {

	private static final String TAG = "AppInstance";

	private ProgressDialog progressDialog;

	// Used as a variable to decide which screen to start the app in.
	private ParseUser currentUser;

	// @true: The app will start in the menu screen with character information
	// displayed.
	// @false: The app will start in the login/create account page.
	private boolean hasCurrentUser;
	
	private Context currentContext;

	@Override
	public void onCreate() {
		super.onCreate();

		// This links the app with the Parse server.
		Parse.initialize(this, "",
				"");

		// Check for a current user on app start.
		currentUser = ParseUser.getCurrentUser();
		if (currentUser == null) {
			Log.d(TAG, "App started with no current ParseUser");
			hasCurrentUser = false;
		} else {
			Log.d(TAG, "App started with a current ParseUser");
			hasCurrentUser = true;
		}

	}

	// Refresh the user's data with the data from Parse.
	public void refreshUser(Context context) {

		currentContext = context;
		// Refresh user values.
		ParseUser.getCurrentUser().refreshInBackground(new RefreshCallback() {
			@Override
			public void done(ParseObject refreshedUser, ParseException e) {
				if (refreshedUser == null) {
					// Failed to refresh the user.
					Log.d(TAG, "Failed to refresh the user");
				} else {
					// Successfully refreshed the user.
					Log.d(TAG, "Successfully refreshed the user");

					// Dismiss the progress bar.
					dismissProgressBar();
					refreshBattleList(currentContext);
				}
			}
		});
	}

	// Fetches all battles the user is in.
	// TODO: Create a variable in Battles in Parse to determine if the user has
	// accepted the battle or not.
	public void refreshBattleList(Context context) {
		showProgressBar("Refreshing Battle List...", context);
		Log.d(TAG, "Searching for battles");

		// Create query for all challenger battles.
		ParseQuery<ParseObject> challengerBattlesQuery = ParseQuery
				.getQuery("Battles");
		challengerBattlesQuery.whereEqualTo("user1", ParseUser.getCurrentUser()
				.getString("characterName"));

		// Create query for all challenged battles.
		ParseQuery<ParseObject> challengedBattlesQuery = ParseQuery
				.getQuery("Battles");
		challengedBattlesQuery.whereEqualTo("user2", ParseUser.getCurrentUser()
				.getString("characterName"));

		// Combine queries into a list.
		List<ParseQuery<ParseObject>> battlesQuery = new ArrayList<ParseQuery<ParseObject>>();
		battlesQuery.add(challengerBattlesQuery);
		battlesQuery.add(challengedBattlesQuery);
		// Create a query from that list.
		ParseQuery<ParseObject> mainQuery = ParseQuery.or(battlesQuery);
		// Call the query.
		mainQuery.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> battles, ParseException e) {
				if (e == null) {
					// Battles found.
					Log.d(TAG, battles.size() + " battles were found");

					// Remove all old battles.
					ParseUser.getCurrentUser().remove("battleList");
					
					// Place all battles into the user's battleList.
					for (int i = 0; i < battles.size(); i++) {
						ParseUser.getCurrentUser().add("battleList",
								battles.get(i).getObjectId().toString());
					}
					// Save the user's data to parse.
					ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
						
						@Override
						public void done(ParseException e) {
							if(e == null) {
								// Save was successful.
								Log.d(TAG, "Successfully saved battleList to Parse");
							} else {
								Log.d(TAG, e.getMessage());
							}
							
						}
					});
				} else {
					// No battles found.
					Log.d(TAG, "No battles were found");
				}
				dismissProgressBar();
			}
		});
	}

	// Progress bar that displays the passed message in the passed context.
	public void showProgressBar(String msg, Context context) {
		progressDialog = ProgressDialog.show(context, "", msg, true);
	}

	// Dismisses a progress bar.
	public void dismissProgressBar() {
		if (progressDialog != null && progressDialog.isShowing())
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
	public void setCurrentUser(ParseUser currentUser) {
		this.currentUser = currentUser;
	}

	public void setHasCurrentUser(boolean hasCurrentUser) {
		this.hasCurrentUser = hasCurrentUser;
	}

	// **********************************************************************
	// Getters
	public ParseUser getCurrentUser() {
		return currentUser;
	}

	public boolean getHasCurrentUser() {
		return hasCurrentUser;
	}

}

// TODO: Look into ProGuard.

/*
 * TODO: BUGS:
 */