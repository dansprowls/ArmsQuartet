package com.srirachagames.armsquartet;

import android.util.Log;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.RefreshCallback;

public class User {

	private static final String TAG = "User";

	private ParseUser parseUser;
	private PlayerCharacter playerCharacter; // The user's character.

	private String objectId, // The objectId key stored in the Parse database.
			username, // The user's username.
			password, // The user's password.
			email, // The user's email address.
			characterName, // The user's character's name.
			createdAt, // The date and time the user was created.
			updatedAt; // The date and time the user was last updated.

	private boolean adsRemoved,   // If the user has made the in-app purchase to
								  // remove ads.
					// If the user has a character in the database.
					// This is used to see if the app should draw from the database or 
					// Create a new character.
					hasCharacter; 

	private int gold, // How much gold the user has.
			wins, // The user's total wins.
			loses, // The user's total loses.
			draws; // The user's total draws.

	// Constructors
	public User(ParseUser user) {
		// Set user values.
		setParseUser(user);
		setObjectId(user.getObjectId());
		setUsername(user.getString("username"));
		setPassword(user.getString("password"));
		setEmail(user.getString("email"));
		setCharacterName(user.getString("characterName"));
		setGold(user.getInt("gold"));
		setWins(user.getInt("wins"));
		setLoses(user.getInt("loses"));
		setDraws(user.getInt("draws"));
		setCreatedAt(user.getString("createdAt"));
		setUpdatedAt(user.getString("updatedAt"));
		setAdsRemoved(user.getBoolean("adsRemoved"));
		setHasCharacter(user.getBoolean("hasCharacter"));
		updateCharacter();
		// TODO: setFriendsList();
		// setBattleList();

		Log.d(TAG, "Successfully created User: " + getUsername());
	}

	public void updateCharacter() {
		// Grab Character from Parse.
		if (hasCharacter()) {
			loadCharacter(getParseUser());
		} else { // Create a new character.
			playerCharacter = new PlayerCharacter(getCharacterName(), getObjectId());
			setHasCharacter(true);
			parseUser.put("hasCharacter", true);
			parseUser.saveInBackground();
			Log.d(TAG, "parseUser has updated hasCharacter to true");
			// TODO:set hasCharacter in the user object to true 
		}

		// Grab the user's character from the pointer object.
		// characterObject = new ParseObject("Character");
		// characterObject = user.getParseObject("character");
		// setCharacter(characterObject);
	}
	
	public void saveUser() {
		parseUser.put("gold", getGold());
		parseUser.put("wins", getWins());
		parseUser.put("loses", getLoses());
		parseUser.put("draws", getDraws());
		parseUser.saveInBackground();
	}

	/*
	 * Loads in the character from Parse with the attached objectId of the user.
	 * 
	 * @param: The user.
	 */
	private void loadCharacter(ParseUser user) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Character");
		query.whereEqualTo("user", user.getObjectId());
		query.getFirstInBackground(new GetCallback<ParseObject>() {
			@Override
			public void done(ParseObject parseCharacter, ParseException e) {
				if (e == null) {
					// Successfully loaded the character.
					Log.d(TAG, "Successfully retrieved Character: "
							+ parseCharacter.getString("name") + " from Parse.");
					setCharacter(parseCharacter);
				} else {
					// The character failed to load.
					Log.d(TAG, "Error: " + e.getMessage());
				}
			}
		});
	}

	// ********************************************************************************************
	// Getters:
	public boolean hasCharacter() {
		return hasCharacter;
	}
	
	public String getCharacterName() {
		return characterName;
	}
	
	public String getObjectId() {
		return objectId;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public boolean adsRemoved() {
		return adsRemoved;
	}

	public int getGold() {
		return gold;
	}

	public PlayerCharacter getCharacter() {
		return playerCharacter;
	}

	public ParseUser getParseUser() {
		return parseUser;
	}

	public int getWins() {
		return wins;
	}

	public int getLoses() {
		return loses;
	}

	public int getDraws() {
		return draws;
	}

	// ********************************************************************************************
	// Setters:
	public void setHasCharacter(boolean hasCharacter) {
		this.hasCharacter = hasCharacter;
	}
	
	public void setCharacterName(String characterName) {
		this.characterName = characterName;
	}
	
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public void setAdsRemoved(boolean adsRemoved) {
		this.adsRemoved = adsRemoved;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public void setLoses(int loses) {
		this.loses = loses;
	}

	public void setDraws(int draws) {
		this.draws = draws;
	}

	/*
	 * This method is called first when the user signs in and the character is
	 * loaded. method: loadCharacter(User user){} in AccountSignInFragment.
	 */
	public void setCharacter(ParseObject parseCharacter) {
		this.playerCharacter = new PlayerCharacter(parseCharacter);
	}

	public void setParseUser(ParseUser parseUser) {
		this.parseUser = parseUser;
	}
}
