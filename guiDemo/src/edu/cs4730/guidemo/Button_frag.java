package edu.cs4730.guidemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Button_frag extends Fragment implements View.OnClickListener {
	TextView output;
	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container,	Bundle savedInstanceState )	{
		View myView;
		myView = inflater.inflate( R.layout.button_frag, container, false );
	    
		output = (TextView) myView.findViewById(R.id.output);
	    
		//setup the listeners.  Each one setup a different way.
		//"standard" way
		Button btn1 = (Button)myView.findViewById(R.id.btn_1);
        btn1.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				output.setText("Output:\nButton1");
			}
        });
        //using the implements methods, ie this
		Button btn2 = (Button)myView.findViewById(R.id.btn_2);
        btn2.setOnClickListener(this);
        
        //shortest version, no variable created.
		myView.findViewById(R.id.btn_3).setOnClickListener(this);
		
		//note setting the listener in the xml android:onclick= will call the MainActivity, not the fragment!
		
		return myView;
	}

	
	/* 
	 * This on is the for the implements View.OnClickListener
	 * 
	 * (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.btn_2) {  //it's button 2
			Toast.makeText( getActivity().getApplicationContext(), "Button 2 was clicked", Toast.LENGTH_SHORT).show();
		
		} else if (v.getId() == R.id.btn_3) {  //it's button 3
			output.setText("Output:\nButton3");
		}
	}
}
