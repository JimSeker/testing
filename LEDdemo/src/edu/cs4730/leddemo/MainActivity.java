package edu.cs4730.leddemo;

import android.os.Bundle;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {
	int LED_NOTIFICATION_ID = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				RedFlashLight();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void RedFlashLight()
	{
	NotificationManager nm = ( NotificationManager ) getSystemService( NOTIFICATION_SERVICE );
	Notification notif = new Notification();
	notif.ledARGB = 0xFFff0000;
	notif.flags = Notification.FLAG_SHOW_LIGHTS;
	notif.ledOnMS = 100;
	notif.ledOffMS = 100;
	nm.notify(LED_NOTIFICATION_ID, notif);
	}
	
}
