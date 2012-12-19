package org.mathbiol.s3qldroid;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.keyes.youtube.OpenYouTubePlayerActivity;

public class MultiMediaProceesing extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_multi_media_proceesing);
		
		final Button playVideoBtn = (Button) findViewById(R.id.play_video_button);
		playVideoBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	playVideo();
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_multi_media_proceesing, menu);
		return true;
	}

	public void playVideo(){

  	 /*ref
  	  * http://stackoverflow.com/questions/4864178/how-to-display-youtube-video-in-android-videoview
  	  */
		 //Intent lVideoIntent = new Intent(null, Uri.parse("ytv://"+S3DBC.selected_item_notes), this, OpenYouTubePlayerActivity.class);
		//Intent lVideoIntent = new Intent(null, Uri.parse("ytv://"+"LZOLNT3_KbI"), this, OpenYouTubePlayerActivity.class);
		//Intent lVideoIntent = new Intent(null, Uri.parse("ytv://"+"BGy4CLDytOc"), this, OpenYouTubePlayerActivity.class);
		 //Intent browserIntent = new Intent("android.intent.action.VIEW", Uri.parse(S3DBC.selected_item_notes));
		 String url=S3DBC.selected_item_notes.replace("\"", "");
		 Intent browserIntent = new Intent("android.intent.action.VIEW", Uri.parse(url)); 		 
   	     startActivity(browserIntent);
	}
}
