package com.srirachagames.armsquartet;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class AddBattleDialog extends DialogFragment implements OnClickListener {

	Button startBattle;
	EditText userInput;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Set the title of the dialog.
		getDialog().setTitle("Wage War");

		View v = inflater.inflate(R.layout.add_battle_dialog, null);

		// Set the onClickListener for the startBattle button.
		startBattle = (Button) v.findViewById(R.id.add_battle_button);
		startBattle.setOnClickListener(this);

		userInput = (EditText) v.findViewById(R.id.add_battle_edittext);

		return v;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.add_battle_button:
			findUser(userInput.getText().toString());
			break;
		}

	}

	// Searches for the user with the character name characterName
	private void findUser(String characterName) {

	}
}
