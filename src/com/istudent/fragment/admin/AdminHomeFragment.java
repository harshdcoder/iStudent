package com.istudent.fragment.admin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.istudent.R;

public class AdminHomeFragment extends Fragment {

	public AdminHomeFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_home, container,
				false);

		String ID = getArguments().getString("ID");
		String FIRST_NAME = getArguments().getString("FIRST_NAME");
		String LAST_NAME = getArguments().getString("LAST_NAME");
		String USERNAME = getArguments().getString("USERNAME");
		String MOBILE = getArguments().getString("MOBILE");
		String ROLE = getArguments().getString("ROLE");

		TextView text = (TextView) rootView.findViewById(R.id.txtLabel);
		text.setText("Welcome " + FIRST_NAME);

		return rootView;
	}
}
