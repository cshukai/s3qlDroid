package org.mathbiol.s3qldroid;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MultiMediaProceesing extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_multi_media_proceesing);
		
		final Button playVideoBtn = (Button) findViewById(R.id.play_video_button);
		playVideoBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_multi_media_proceesing, menu);
		return true;
	}

}
