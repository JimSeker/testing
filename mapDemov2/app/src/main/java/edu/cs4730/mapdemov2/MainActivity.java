package edu.cs4730.mapdemov2;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;

import com.google.android.gms.maps.model.LatLng;


//note, very little happens in here.  see the other activities.

//Second note, this example needs to be updated to use fragments (a view pager?) instead of 3 activities.

public class MainActivity extends AppCompatActivity {

    static final LatLng CHEYENNE = new LatLng(41.1400, -104.8197);  //Note, West is a negative, East is positive
    static final LatLng KIEL = new LatLng(53.551, 9.993);
    static final LatLng LARAMIE = new LatLng(41.312928, -105.587253);

    ViewPager viewPager;
//    BasicMapFragment BasicMapFrag;
//    CompassFragment CompassFrag;
//    DrawMapFragment DrawMapFrag;
    String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        BasicMapFrag = new BasicMapFragment();
//        CompassFrag = new CompassFragment();
//        DrawMapFrag = new DrawMapFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new ThreeFragmentPagerAdapter(fragmentManager));
    }

    /*
     * We need to extend a FragmentPagerAdapter to add our three fragments.
     *   We need to override getCount and getItem.  Also getPageTitle since we are
     *   using a PageStripe.
      */
    public class ThreeFragmentPagerAdapter extends FragmentPagerAdapter {
        int PAGE_COUNT = 3;

        //required constructor that simply supers.
        public ThreeFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        // return the correct fragment based on where in pager we are.
        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0: return new BasicMapFragment();
                case 1: return new CompassFragment();
                case 2: return new DrawMapFragment();
                default:
                    return null;
            }
        }

        //how many total pages in the viewpager there are.  3 in this case.
        @Override
        public int getCount() {

            return PAGE_COUNT;
        }

        //getPageTitle required for the PageStripe to work and have a value.
        @Override
        public CharSequence getPageTitle(int position) {

            //return String.valueOf(position);  //returns string of position for title
            switch (position) {
                case 0: return "Basic Map";
                case 1: return "Compass Map";
                case 2: return "Drawn on Map";
                default:
                    return null;
            }

        }
    }
}
