package org.mathbiol.s3qldroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InsertDemo extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_insert_demo);
		final Button insert_button = (Button) findViewById(R.id.button_for_insertion);		
		final EditText collectionIdField=(EditText)findViewById(R.id.collection_id_input_for_insertion);
		
		
		
		
		insert_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
          
            }
        });
		
		
    	LocationManager locationManager =(LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        final boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
        	AlertDialog.Builder builder = new AlertDialog.Builder(InsertDemo.this);
         	
         	builder.setMessage("click ok to enable gps..").setTitle("gps required");
         	
         	builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int id) {
                 	enableLocationSettings();
                 }
              });
         	
             builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int id) {
                     dialog.dismiss();
                 }
             });
         	
             AlertDialog dialog = builder.create();
             dialog.show();
        }
        
        else{          	
           	 LocationProvider provider =locationManager.getProvider(LocationManager.GPS_PROVIDER);          	 
        }
        
		
	}

	
	private void enableLocationSettings() {
	    Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	    startActivity(settingsIntent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_insert_demo, menu);
		return true;
	}

}
