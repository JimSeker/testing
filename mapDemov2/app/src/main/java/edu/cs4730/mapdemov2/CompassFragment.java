package edu.cs4730.mapdemov2;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class CompassFragment extends Fragment {

    private GoogleMap map;
    public CompassFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.compass_fragment, container, false);
        //in a fragment
        map = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map2)).getMap();
        //in an activity
        //map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

        // Move the camera instantly to laramie and zoom in .
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(MainActivity.LARAMIE, 15));

        // Zoom in, animating the camera.
        //map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

        // Sets the map type to be "hybrid"
        //map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        return myView;
    }


}
