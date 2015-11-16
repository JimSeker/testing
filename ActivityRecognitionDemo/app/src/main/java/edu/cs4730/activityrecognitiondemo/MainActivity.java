package edu.cs4730.activityrecognitiondemo;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

//doesn't work, change to broadcast receiver.
//https://developers.google.com/android/reference/com/google/android/gms/location/ActivityRecognition
//https://github.com/googlesamples/android-play-location/tree/master/ActivityRecognition


public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, OnConnectionFailedListener, ResultCallback<Status> {

    /**
     * The desired time between activity detections. Larger values result in fewer activity
     * detections while improving battery life. A value of 0 results in activity detections at the
     * fastest possible rate. Getting frequent updates negatively impact battery life and a real
     * app may prefer to request less frequent updates.
     */
    public static final long DETECTION_INTERVAL_IN_MILLISECONDS = 0;
    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;
    boolean gettingupdates = false;
    //widgets
    Button btn;
    TextView logger;
    String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do something...
                setupDemo();
            }
        });
        logger = (TextView) findViewById(R.id.logger);

        // Kick off the request to build GoogleApiClient.
        buildGoogleApiClient();
    }

    /**
     * Builds a GoogleApiClient. Uses the {@code #addApi} method to request the
     * ActivityRecognition API.
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(ActivityRecognition.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }


    @Override
    protected void onResume() {
        super.onResume();
        // Register the broadcast receiver that informs this activity of the DetectedActivity
        // object broadcast sent by the intent service.
        setupDemo();  //likely always starts (even first use), since it should be turned off by onPause()
        //should set a preference in OnPause, that is read here as well, to determine if it should be started.
    }

    @Override
    protected void onPause() {
        // Unregister the broadcast receiver that was registered during onResume().
        if (gettingupdates)  //if turned on, stop them during pause.
          setupDemo();
        super.onPause();
    }
    void setupDemo() {
        if (!mGoogleApiClient.isConnected()) {
            Toast.makeText(this, "Google API is not connected!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (gettingupdates) { //we are already getting updates, so stop
            ActivityRecognition.ActivityRecognitionApi.removeActivityUpdates(
                    mGoogleApiClient,
                    getActivityDetectionPendingIntent()
            ).setResultCallback(this);
            btn.setText("Start Recognition");
        } else {
            ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(
                    mGoogleApiClient,
                    DETECTION_INTERVAL_IN_MILLISECONDS,
                    getActivityDetectionPendingIntent()
            ).setResultCallback(this);
            btn.setText("Stop Recognition");
        }
        gettingupdates = !gettingupdates;
    }

    /**
     * Gets a PendingIntent to be sent for each activity detection.
     */
    private PendingIntent getActivityDetectionPendingIntent() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when calling
        // requestActivityUpdates() and removeActivityUpdates().
        return PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }


    //we want to get the intent back here without starting another instanced
    //so  we get the data here, hopefully.
    @Override
    protected void onNewIntent(Intent intent) {
        Log.v(TAG, "onNewIntent");
        logger.append("newIntent\n");
        ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
   /*     for (DetectedActivity da: detectedActivities) {
            Log.i(TAG, Constants.getActivityString(
                            getApplicationContext(),
                            da.getType()) + " " + da.getConfidence() + "%"
            );
        }
        */
    }

    /**
     * Returns a human readable String corresponding to a detected activity type.
     */

    public static String getActivityString(Context context, int detectedActivityType) {
        switch (detectedActivityType) {
            case DetectedActivity.IN_VEHICLE:
                return "In a Vehicle";
            case DetectedActivity.ON_BICYCLE:
                return "On a bicycle";
            case DetectedActivity.ON_FOOT:
                return "On Foot";
            case DetectedActivity.RUNNING:
                return "Running";
            case DetectedActivity.STILL:
                return "Still (not moving)";
            case DetectedActivity.TILTING:
                return "Tilting";
            case DetectedActivity.UNKNOWN:
                return "Unknown Activity";
            case DetectedActivity.WALKING:
                return "Walking";
            default:
                return "Unknown Type";
        }
    }

    //GoogleAPIClient connection call backs
    @Override
    public void onConnected(Bundle bundle) {
        Log.v(TAG, "Connected");
    }

    @Override
    public void onConnectionSuspended(int i) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    //GoogleAPIClient connection failed
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.v(TAG, "Connected Failed");
    }

    //resultcallback ... not sure if need this or not.
    @Override
    public void onResult(Status status) {
        Log.v(TAG, "onResult");
        if (status.isSuccess()) {
            Log.v(TAG, "onResult success");
        } else {
            Log.v(TAG, "onResult failed. " + status.getStatusMessage());
        }
    }
}
