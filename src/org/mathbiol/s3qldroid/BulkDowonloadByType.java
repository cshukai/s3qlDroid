package org.mathbiol.s3qldroid;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class BulkDowonloadByType extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bulk_dowonload_by_type);
		
		final Button downsButton = (Button) findViewById(R.id.downloads_button);
		final EditText fileTypeField = (EditText) findViewById(R.id.fileType_input);
		
		downsButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				 S3DBC.downloadBinaryFilesByFileTypeAndRuleID("jpg","99");
				  
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_bulk_dowonload_by_type, menu);
		return true;
	}

}
