package com.srirachagames.armsquartet;

import java.util.List;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class PlayerCharacter {
	
	private static final String TAG = "PlayerCharacter";
	
	private ParseObject parseCharacterObject;	

	// BASIC INFORMATION
	private String 		objectId,					// The key for the character object (Parse).
						name, 						// Character name.
						portraitName, 				// File name of portrait.
						createdAt,					// The date the character was created in the database.
						updatedAt,					// The date the character was last updated in the database.
						user;						// The user tied to the character.

	private int 		wins,						// The number of game wins.
						losses,						// The number of game losses.
						draws;						// The number of game draws.
	 						
	// CHARACTER ATTRIBUTES
	private int 		level,						// The character's level.
						expTotal,					// The total experience gained.
						expNeeded,					// The experience needed to obtain a level.
						hpTotal,					// The character's total health.
						strTotal,					// The character's total strength stat.
						agiTotal,					// The character's total agility stat.
						intelTotal,					// The character's total intelligence stat.
						statPoints;					// The character's stat points.
	
	private Abilities 	abilities;					// The abilities the character has obtained.
	
	private Equipment 	equipment;					// The character's equipment.
	
	// CHARACTER COMBAT ATTRIBUTES
	private int			hpCurrent,					// The character's current health in a battle.
						strCurrent,					// The character's current strength in a battle.
						agiCurrent,					// The character's current agility in a battle.
						intelCurrent;				// The character's current intelligence in a battle.
	
	// CHARACTER CONSTANTS
	private final int 	STARTINGLEVEL = 1,
					  	STARTINGHP = 35,
					  	STARTINGSTR = 10,
					  	STARTINGAGI = 10,
					  	STARTINGINTEL = 10,
					  	STARTINGSTATPOINTS = 20;
	
	
	// Creates a new PlayerCharacter.
	public PlayerCharacter(String name, String userId) {
		setName(name);
		setUser(userId);
		setLevel(STARTINGLEVEL);
		setHpTotal(STARTINGHP);
		setStrTotal(STARTINGSTR);
		setAgiTotal(STARTINGAGI);
		setIntelTotal(STARTINGINTEL);
		setStatPoints(STARTINGSTATPOINTS);
		saveNewCharacter();
		
	}
	
	// Creates an already existing PlayerCharacter.
	// @param: The objectId of the Character in Parse.
	public PlayerCharacter(ParseObject parseCharacter) {
		setParseCharacterObject(parseCharacter);
        setName(parseCharacter.getString("name"));
        setLevel(parseCharacter.getInt("level"));
        setExpTotal(parseCharacter.getInt("expTotal"));
        setExpNeeded(parseCharacter.getInt("expNeeded"));
        setHpTotal(parseCharacter.getInt("healthPointsTotal"));
        setHpCurrent(parseCharacter.getInt("healthPointsCurrent"));
        setStrTotal(parseCharacter.getInt("strTotal"));
        setStrCurrent(parseCharacter.getInt("strCurrent"));
        setAgiTotal(parseCharacter.getInt("agiTotal"));
        setAgiCurrent(parseCharacter.getInt("agiCurrent"));
        setIntelTotal(parseCharacter.getInt("intelTotal"));
        setIntelCurrent(parseCharacter.getInt("intelCurrent"));
        setCreatedAt(parseCharacter.getString("createdAt"));
        setUpdatedAt(parseCharacter.getString("updatedAt"));
        
        //TODO: setPortraitName();
        //TODO: setAbilities();
        //TODO: setEquipment();
        
        Log.d(TAG, "Created new PlayerCharacter object: " + getName());
	}
	
	private void saveNewCharacter() {
		ParseObject newCharacter = new ParseObject("Character");
		newCharacter.put("name", getName());
		newCharacter.put("user", getUser());
		newCharacter.put("level", getLevel());
		newCharacter.put("healthPointsTotal", getHpTotal());
		newCharacter.put("strTotal", getStrTotal());
		newCharacter.put("agiTotal", getAgiTotal());
		newCharacter.put("intelTotal", getIntelTotal());
		newCharacter.put("statPoints", getStatPoints());
		newCharacter.saveInBackground();
		Log.d(TAG, "Successfully created Character: " + getName());
	}
	
	//  Getters.
	public String getUser() {
		return user;
	}
	
	public ParseObject getParseCharacterObject() {
		return parseCharacterObject;
	}
	
	public int getStatPoints() {
		return statPoints;
	}

	public String getObjectId() {
		return objectId;
	}

	public String getName() {
		return name;
	}

	public String getPortraitName() {
		return portraitName;
	}

	public int getWins() {
		return wins;
	}

	public int getLosses() {
		return losses;
	}

	public int getDraws() {
		return draws;
	}

	public int getLevel() {
		return level;
	}

	public int getExpTotal() {
		return expTotal;
	}

	public int getExpNeeded() {
		return expNeeded;
	}

	public int getHpTotal() {
		return hpTotal;
	}

	public int getStrTotal() {
		return strTotal;
	}

	public int getAgiTotal() {
		return agiTotal;
	}

	public int getIntelTotal() {
		return intelTotal;
	}

	public Abilities getAbilities() {
		return abilities;
	}

	public Equipment getEquipment() {
		return equipment;
	}

	public int getHpCurrent() {
		return hpCurrent;
	}

	public int getStrCurrent() {
		return strCurrent;
	}

	public int getAgiCurrent() {
		return agiCurrent;
	}

	public int getIntelCurrent() {
		return intelCurrent;
	}
	
	public String getCreatedAt() {
		return createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}
	
	// Setters.
	public void setUser(String user) {
		this.user = user;
	}
	
	public void setParseCharacterObject(ParseObject parseCharacterObject) {
		this.parseCharacterObject = parseCharacterObject;
	}
	
	public void setStatPoints(int statPoints) {
		this.statPoints = statPoints;
	}

	
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPortraitName(String portraitName) {
		this.portraitName = portraitName;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public void setLosses(int losses) {
		this.losses = losses;
	}

	public void setDraws(int draws) {
		this.draws = draws;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setExpTotal(int expTotal) {
		this.expTotal = expTotal;
	}

	public void setExpNeeded(int expNeeded) {
		this.expNeeded = expNeeded;
	}

	public void setHpTotal(int hpTotal) {
		this.hpTotal = hpTotal;
	}

	public void setStrTotal(int strTotal) {
		this.strTotal = strTotal;
	}

	public void setAgiTotal(int agiTotal) {
		this.agiTotal = agiTotal;
	}

	public void setIntelTotal(int intelTotal) {
		this.intelTotal = intelTotal;
	}

	public void setAbilities(Abilities abilities) {
		this.abilities = abilities;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

	public void setHpCurrent(int hpCurrent) {
		this.hpCurrent = hpCurrent;
	}

	public void setStrCurrent(int strCurrent) {
		this.strCurrent = strCurrent;
	}

	public void setAgiCurrent(int agiCurrent) {
		this.agiCurrent = agiCurrent;
	}

	public void setIntelCurrent(int intelCurrent) {
		this.intelCurrent = intelCurrent;
	}
	
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
}
