package org.mathbiol.s3qldroid;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FileDownload extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_file_download);
		final Button download_button = (Button) findViewById(R.id.download_button);
		final EditText fileStatmentIdField = (EditText) findViewById(R.id.statement_id_input);
		
		download_button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				 S3DBC.downloadBinaryFile(fileStatmentIdField.getText().toString());
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_file_download, menu);
		return true;
	}

}
