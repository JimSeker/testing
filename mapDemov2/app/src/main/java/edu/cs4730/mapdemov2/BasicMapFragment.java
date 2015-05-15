package edu.cs4730.mapdemov2;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A simple {@link Fragment} subclass.
 */
public class BasicMapFragment extends Fragment {

    private GoogleMap map;

    public BasicMapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View myView = inflater.inflate(R.layout.basicmap_fragment, container, false);
        View myView = null;
        try {
            myView = inflater.inflate(R.layout.basicmap_fragment, container, false);
        } catch (InflateException e) {
            //failed, why?  not sure.
            //container.removeView(myView);  //it already exists I think...
            return container;
        }
        //in a fragment
        map = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMap();
        //in an activity
        //map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

        Marker kiel = map.addMarker(new MarkerOptions()
                        .position(MainActivity.KIEL)
                        .title("Kiel")
                                //change and use a blue "default" marker, instead of read.
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
        );

        Marker laramie = map.addMarker(new MarkerOptions()
                        .position(MainActivity.LARAMIE)
                        .title("Laramie")
                        .snippet("I'm in Laramie!")
                        .icon(BitmapDescriptorFactory
                                .fromResource(R.drawable.ic_launcher))
        );
        Marker cheyenne = map.addMarker(new MarkerOptions().position(MainActivity.CHEYENNE)
                .title("Cheyenne"));

        // Move the camera instantly to hamburg with a zoom of 15.
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(MainActivity.LARAMIE, 15));

        // Zoom in, animating the camera.
        map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

        // Sets the map type to be "hybrid"
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL); //normal map
        //map.setMapType(GoogleMap.MAP_TYPE_HYBRID);


        //add a marker click event.
        map.setOnMarkerClickListener( new GoogleMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker myMarker) {
                Toast.makeText(getActivity().getApplicationContext(), "Clicked the " + myMarker.getTitle() + " Marker", Toast.LENGTH_SHORT).show();

                //return true;  //yes we consumed the event.
                return false; //so the default action is shown as well.
            }

        });


        //add map click listener.
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                // TODO Auto-generated method stub
                Toast.makeText(getActivity().getApplicationContext(), "Lat: " + point.latitude+ " Long:" +point.longitude,  Toast.LENGTH_SHORT).show();
            }

        });
        return myView;
    }


}
