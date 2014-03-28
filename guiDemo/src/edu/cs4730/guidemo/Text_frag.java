package edu.cs4730.guidemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Text_frag extends Fragment
{
	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState )
	{
		return inflater.inflate( R.layout.text_frag, container, false );
	}
}
