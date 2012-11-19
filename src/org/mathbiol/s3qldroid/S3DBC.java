package org.mathbiol.s3qldroid;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class S3DBC extends Activity {

	private static String BASE_URL="http://204.232.200.16/uabs3db";
    private static AsyncHttpClient client = new AsyncHttpClient();
	private static RequestParams params;
	private static AsyncHttpResponseHandler responseHandler;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_s3_dbc);
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
	      client.post(getAbsoluteUrl(url), params, responseHandler);
	  }

	  private static String getAbsoluteUrl(String relativeUrl) {
	      return BASE_URL + relativeUrl;
	  }

}
