package edu.cs4730.barcodedemo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.android.gms.vision.face.LargestFaceFocusingProcessor;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    String TAG = "MainActivity";
    CameraSource mCameraSource;
    SurfaceView mPreview;
    TextView mLogger;
    private boolean mSurfaceAvailable;
    //for getting permissions to use the camara in API 23+
    final String[] permissions = new String[]{Manifest.permission.CAMERA};
    private static final int RC_HANDLE_CAMERA_PERM = 2;
    //handler, since the facetracker is on another thread.
    protected Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get the views first.
        mPreview = (SurfaceView) findViewById(R.id.CameraView);
        //finally, setup the preview pieces
        mPreview.getHolder().addCallback(this);

        mLogger = (TextView) findViewById(R.id.mylogger);



        // Check for the camera permission before accessing the camera.  If the
        // permission is not granted yet, request permission.
        //this is the quick and dirty version and it doesn't explain why we want permission.  Which is not how google wants us to do it.
        int rc = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (rc == PackageManager.PERMISSION_GRANTED) {
            createCameraSource();
        } else {
            ActivityCompat.requestPermissions(this, permissions,
                    RC_HANDLE_CAMERA_PERM);
        }
    }

    public void createCameraSource() {
        //Setup the BarCodeDetector
        Context context = getApplicationContext();
        BarcodeDetector detector = new BarcodeDetector.Builder(context)
                .build();

        detector.setProcessor(
                new LargestFaceFocusingProcessor(detector,new BarCodeTracker()));


        if (!detector.isOperational()) {
            // Note: The first time that an app using face API is installed on a device, GMS will
            // download a native library to the device in order to do detection.  Usually this
            // completes before the app is run for the first time.  But if that download has not yet
            // completed, then the above call will not detect any faces.
            //
            // isOperational() can be used to check if the required native library is currently
            // available.  The detector will automatically become operational once the library
            // download completes on device.
            Log.w(TAG, "Face detector dependencies are not yet available.");
        }

        // Creates and starts the camera.  Note that this uses a higher resolution in comparison
        // to other detection examples to enable the barcode detector to detect small barcodes
        // at long distances.
        CameraSource.Builder builder = new CameraSource.Builder(getApplicationContext(), detector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(1600, 1024)
                .setRequestedFps(15.0f);

    }

    void startPreview() {
        if (mSurfaceAvailable  && mCameraSource != null) {
            try {
                mCameraSource.start(mPreview.getHolder());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.v(TAG, "preview failed.");
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        startPreview();
    }

    /**
     * Stops the camera.
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (mCameraSource != null)
            mCameraSource.stop();
    }

    /**
     * Releases the resources associated with the camera source, the associated detector, and the
     * rest of the processing pipeline.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCameraSource.release();
    }

    class BarCodeTracker extends Tracker<Barcode> {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Camera permission granted - initialize the camera source");
            // we have permission, so create the camerasource
            createCameraSource();
            return;
        }
        Log.e(TAG, "Permission not granted: results len = " + grantResults.length +
                " Result code = " + (grantResults.length > 0 ? grantResults[0] : "(empty)"));

    }
    /*
    *  methods needed for the surfaceView callback methods.
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mSurfaceAvailable = true;
        startPreview();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //should not be called, app is locked in portrait mode.
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mSurfaceAvailable = false;
    }
}
