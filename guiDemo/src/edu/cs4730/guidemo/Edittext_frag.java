package edu.cs4730.guidemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class Edittext_frag extends Fragment implements TextWatcher
{
	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )	{
		View myView;
		 myView = inflater.inflate( R.layout.edittext_frag, container, false );
		 
		 EditText ET_dec = (EditText) myView.findViewById(R.id.editText3);
		 ET_dec.addTextChangedListener(this);
		 return myView;
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		Log.w("EditText_frag", s.toString());
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		Log.w("EditText_frag2", s.toString());
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		Log.w("EditText_frag3", s.toString());
		
	}
}
