package com.srirachagames.armsquartet;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class MenuCharacterFragment extends Fragment implements OnClickListener {
	private static final String TAG = "MenuCharacterFragment";
	private AppInstance appInstance;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.menu_character_fragment, container, false);
		
		return v;
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
