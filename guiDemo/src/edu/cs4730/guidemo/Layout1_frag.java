package edu.cs4730.guidemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Layout1_frag extends Fragment {
	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container,	Bundle savedInstanceState )	{
		View myView;
		myView = inflater.inflate( R.layout.layout1_frag, container, false );
		return myView;
	}


}
