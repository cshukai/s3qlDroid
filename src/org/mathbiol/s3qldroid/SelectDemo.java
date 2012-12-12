package org.mathbiol.s3qldroid;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SelectDemo extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_demo);
		
		final Button submit_itemID_button = (Button) findViewById(R.id.submit4selection);
		final EditText itemIdFiled=(EditText)findViewById(R.id.item_id_input);
		
		submit_itemID_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                   S3DBC.selectItem(itemIdFiled.getText().toString());
              }
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_select_demo, menu);
		return true;
	}

}
