package org.mathbiol.s3qldroid;

import java.io.FileNotFoundException;
import java.io.InputStream;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class S3DBC extends Activity {

	private static String BASE_URL="http://204.232.200.16/uabs3db";
    private static AsyncHttpClient client = new AsyncHttpClient();
	static RequestParams params;
	public static AsyncHttpResponseHandler responseHandler;
	public static String api_key;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_s3_dbc);
		
		//setting up login environment
		final Button login_button = (Button) findViewById(R.id.login_button);
		final EditText usrnameFiled=(EditText)findViewById(R.id.usrname_field);
		final EditText password_field=(EditText)findViewById(R.id.password_field);
		
		login_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              //  S3DBC.parseApiKey("<TABLE><TR><TD>key_id</TD><TD>expires</TD><TD>notes</TD><TD>account_id</TD></TR><TR><TD>3BGJ30JJvSWGW4z</TD><TD>2012-11-21 22:14:27</TD><TD>Key generated automatically via API</TD><TD>106</TD></TR></TABLE>");
            	S3DBC.s3dbc_login(usrnameFiled.getText().toString(), password_field.getText().toString());
                Intent intent = new Intent(S3DBC.this,FileUpload.class);
				startActivity(intent);
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_s3_dbc, menu);
		return true;
	}
	
	
	
	
	 public static void s3dbc_login(String usrname,String password){
		  params = new RequestParams();
		  params.put("username",usrname);
		  params.put("password", password);
		  params.put("format", "html");
		  
		  responseHandler=new AsyncHttpResponseHandler();
		  S3DBC.get("/apilogin.php", params, responseHandler);
	 }
	
	
	 @TargetApi(Build.VERSION_CODES.JELLY_BEAN) 
	 public static String parseApiKey(String htmlText){
		 
		 String result=null;
		 String [] temp= Html.escapeHtml(htmlText).split(";");
 
		 String temp_2=temp[26];
		 String []temp_3=temp_2.split("&");
		 result=temp_3[0];		 
		 
		 return result;
	 }
	 
	 
	 public static void fileUploadByCollectionIdAndRuleId(ContentResolver cr,Uri fileUrl,String api_key,String collection_id, String rule_id,String fileName){
		 
		 params = new RequestParams();
		 params.put("key",api_key);
         params.put("collection_id",collection_id);
	     params.put("rule_id", rule_id);
	     params.put("format", "json");
       
		 try {
			InputStream input_stream = cr.openInputStream(fileUrl);
			params.put("filename",input_stream,fileName);
		 } 
		 
		 catch (FileNotFoundException e) {
				Log.v("s3dbc","file not found");
				e.printStackTrace();
			    e.printStackTrace();
		}
		 
	 }
	 
	 public static void insertItem(String collectionId, String notes){
		 responseHandler=new AsyncHttpResponseHandler();
	 }
	 
	 

	  public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {		 
	      client.get(getAbsoluteUrl(url), params, new AsyncHttpResponseHandler() {
	    	     @Override
	    	     public void onStart() {
	    	         // Initiated the request
	    	     }

	    	     @Override
	    	     public void onSuccess(String response) {
	    	         // Successfully got a response
	    	    	 Log.v("s3dbc",response);
	    	    	 S3DBC.api_key= S3DBC.parseApiKey(response);
	    	     }
	    	 
	    	     @Override
	    	     public void onFailure(Throwable e, String response) {
	    	         // Response failed :(
	    	    	 Log.e("s3dbc",response);
	    	     }

	    	     @Override
	    	     public void onFinish() {
	    	         // Completed the request (either success or failure)
	    	     }
	      });
	     
	  }

	  public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
	      client.post(getAbsoluteUrl(url), params,new AsyncHttpResponseHandler() {
	    	     @Override
	    	     public void onStart() {
	    	         // Initiated the request
	    	     }

	    	     @Override
	    	     public void onSuccess(String response) {
	    	         // Successfully got a response
	    	    	 Log.v("s3dbc",response);
	    	     }
	    	 
	    	     @Override
	    	     public void onFailure(Throwable e, String response) {
	    	         // Response failed :(
	    	    	 Log.e("s3dbc",response);
	    	     }

	    	     @Override
	    	     public void onFinish() {
	    	         // Completed the request (either success or failure)
	    	     }
	      });
	  }

	  private static String getAbsoluteUrl(String relativeUrl) {
	      return BASE_URL + relativeUrl;
	  }

}
