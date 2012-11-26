package org.mathbiol.s3qldroid;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
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
	private static AsyncHttpResponseHandler responseHandler;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_s3_dbc);
		
		//setting up login environment
		final Button login_button = (Button) findViewById(R.id.login_button);
		final EditText usrnameFiled=(EditText)findViewById(R.id.usrname_field);
		final EditText password_field=(EditText)findViewById(R.id.password_field);
		
		login_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              S3DBC.parseApiKey("<TABLE><TR><TD>key_id</TD><TD>expires</TD><TD>notes</TD><TD>account_id</TD></TR><TR><TD>3BGJ30JJvSWGW4z</TD><TD>2012-11-21 22:14:27</TD><TD>Key generated automatically via API</TD><TD>106</TD></TR></TABLE>");
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
		 //example
		 // 1. using Html.fromHtml(htmlText).toString
		 //key_idexpiresnotesaccount_id3BGJ30JJvSWGW4z2012-11-21
		 //2.using Html.escapeHtml
		// &lt;TABLE&gt;&lt;TR&gt;&lt;TD&gt;key_id&lt;/TD&gt;&lt;TD&gt;expires&lt;/TD&gt;&lt;TD&gt;notes&lt;/TD&gt;&lt;TD&gt;account_id&lt;/TD&gt;&lt;/TR&gt;&lt;TR&gt;&lt;TD&gt;3BGJ30JJvSWGW4z&lt;/TD&gt;&lt;TD&gt;2012-11-21 22:14:27&lt;/TD&gt;&lt;TD&gt;Key generated automatically via API&lt;/TD&gt;&lt;TD&gt;106&lt;/TD&gt;&lt;/TR&gt;&lt;/TABLE&gt;
		 
		 String result=null;
		// String [] temp= Html.escapeHtml(htmlText).split("&gt;*&lt");
		 String [] temp= Html.escapeHtml(htmlText).split(";");
		 
//		 for(int i=0; i <temp.length;i++){
//			 Log.v("s3dbc","index:"+i+"is"+temp[i]);	 
//		 }
		 
		 String temp_2=temp[26];
		 String []temp_3=temp_2.split("&");
		 result=temp_3[0];		 
		// Log.v("s3dbc",result);
		 
		 return result;
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
