package edu.cs4730.guidemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	ActionBar actionBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		
		
		
		//The following is setup for Navigation Menu on the actionbar.
		
		actionBar = getSupportActionBar();
		actionBar.setNavigationMode( ActionBar.NAVIGATION_MODE_LIST );

		final List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		//add an item to the list.
		map.put( "title", getString( R.string.text_frag ) );  
		map.put( "fragment", Fragment.instantiate( this, Text_frag.class.getName() ) );
		data.add( map );

		//add next item to the list.
		map = new HashMap<String, Object>();
		map.put( "title", getString( R.string.Edittext_frag  ));
		map.put( "fragment", Fragment.instantiate( this, Edittext_frag.class.getName() ) );
		data.add( map );
		
		//add next item to the list.
		map = new HashMap<String, Object>();
		map.put( "title", "Button Example");
		map.put( "fragment", Fragment.instantiate( this, Button_frag.class.getName() ) );
		data.add( map );

		//add next item to the list.
		map = new HashMap<String, Object>();
		map.put( "title", "Layout1 Example");
		map.put( "fragment", Fragment.instantiate( this, Layout1_frag.class.getName() ) );
		data.add( map );
		
		//add next item to the list.
		map = new HashMap<String, Object>();
		map.put( "title", "Layout2 Example");
		map.put( "fragment", Fragment.instantiate( this, Layout2_frag.class.getName() ) );
		data.add( map );
		
		//setup the drop down list and action to be performed when the item is selected.
		SimpleAdapter adapter = new SimpleAdapter( getSupportActionBar().getThemedContext(), data,
				R.layout.support_simple_spinner_dropdown_item,
				new String[] { "title" }, new int[] { android.R.id.text1 } );
		actionBar.setListNavigationCallbacks( adapter,
				new ActionBar.OnNavigationListener()
				{
					@Override
					public boolean onNavigationItemSelected( int itemPosition,	long itemId )
					{
						Map<String, Object> map = data.get( itemPosition );
						Object o = map.get( "fragment" );
						if (o instanceof Fragment)
						{
							Log.w("Jim", "Switch to next fragment.");
							FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
							//different animations, only uncomment one.
							//tx.setCustomAnimations(R.anim.abc_slide_out_bottom, R.anim.abc_slide_in_bottom);
							tx.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.popenter, R.anim.popexit);
							
							//note, this one will not work on in the support library, because of android:propertyName is only for ObjectAnimator (Honeycomb+) animations
							//tx.setCustomAnimations(R.animator.card_flip_right_in, R.animator.card_flip_right_out,
			                //                       R.animator.card_flip_left_in, R.animator.card_flip_left_out);
							tx.replace( android.R.id.content, (Fragment) o );
							//tx.replace( R.id.frameLayout1, (Fragment) o );
							tx.commit();
						}
						return true;
					} 
				} );
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
