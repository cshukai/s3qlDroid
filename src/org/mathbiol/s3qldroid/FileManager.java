package org.mathbiol.s3qldroid;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class FileManager extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_file_manager);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_file_manager, menu);
		return true;
	}

}
