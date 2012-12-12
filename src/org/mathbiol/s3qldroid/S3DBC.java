package org.mathbiol.s3qldroid;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;

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
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class S3DBC extends Activity {

	private static String BASE_URL="http://204.232.200.16/uabs3db";
    private static AsyncHttpClient client = new AsyncHttpClient();
    
	static RequestParams params;
	public static AsyncHttpResponseHandler responseHandler;
	private static JsonHttpResponseHandler jsonResponseHandler;
	public static String api_key;
	private static String action_flag;
	
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
                Intent intent = new Intent(S3DBC.this,SelectDemo.class);
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
		  
		  action_flag="login";
		  
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
	 
	 public static String insertItem(String collectionId, String notes){
		 String query="<S3QL>"+"<insert>item</insert>"+"<where>"+"<collection_id>"+collectionId+"</collection_id>"+ "<notes>" + notes + "</notes>"+"</where>"+"</S3QL>";
		 responseHandler=new AsyncHttpResponseHandler();	 
		 
		 sendS3Qlrequest(query, api_key);
		 
		 return query;
	 }
	 
	 
	 public static void selectItem(String itemId){
		    String query="<S3QL>"+"<select>*</select>"
	                + "<from>items</from>"
	                + "<where>"
	                + "<item_id>" + itemId + "</item_id>"
	                + "</where>"
	                + "</S3QL>";
		    
		    
		    action_flag="select_item_by_itemId";
		    
		    
		   
		    S3DBC.sendS3Qlrequest(query,S3DBC.api_key);
	 }
	 
	 public static void sendS3Qlrequest(String query,String api_key){
		 params = new RequestParams();
		 params.put("key",api_key);
		 params.put("format", "json");
		 params.put("query", query);
		 
		 
		 S3DBC.get("/S3QL.php", params, responseHandler);
	 }
	 
	 
//	 public static void sendS3Qlrequest(String query,String api_key,JsonHttpResponseHandler responseHandler){
//		 params = new RequestParams();
//		 params.put("key",api_key);
//		 params.put("format", "json");
//		 params.put("query", query);
//		 
//		 S3DBC.get("/S3QL.php", params, responseHandler);
//	 }
	 

	  public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		  
		  
		  if(action_flag.equals("select_item_by_itemId")){
			  
			  client.get(getAbsoluteUrl(url), params, new JsonHttpResponseHandler() {
				  
		    	     @Override
		    	     public void onStart() {
		    	    	 
		    	    	 //  called
		    	    	 Log.v("s3dbc_json","debugging_start");
		    	     }

		    	     @Override
		    	     public void onSuccess(JSONObject response) {
                            // not called
		    	    	 Log.v("s3dbc_json","debugging_success");
		    	    	 try {
							Log.v("s3dbc_json",response.getString("notes"));
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		    	    	
		    	    	 
		    	    			    	    	
		    	     }
		    	     
		    	     
//		    	     @Override
//		    	     public void onSuccess(String response) {
//		    	         // not called
//		    	    	 Log.v("s3dbc_json",response);
//		    	    
//		    	    	
//		    	     }
//		    	     
//		    	     
//		    	     @Override
//		    	     public void onFailure(Throwable e, String response) {
//		    	         // not called
//		    	    	 Log.e("s3dbc_json",response);
//		    	     }
		    	     
		    	 
		    	     @Override
		    	     public void onFailure(Throwable e, JSONObject  error) {
		    	         // not called
		    	    	 Log.v("s3dbc_json","debugging_onfail");
		    	    	 
		    	    	 try {
							Log.e("s3dbc_json",error.getString("error"));
						} 
		    	    	 
		    	    	 catch (JSONException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
		    	     }

		    	     @Override
		    	     public void onFinish() {
		    	    	 //called
		    	    	 Log.v("s3dbc_json","process_finished");
		    	     }
		      });	
			 	  
	    	} 
		  
		  
		  if(action_flag.equals("login")){
			  
			  client.get(getAbsoluteUrl(url), params, new AsyncHttpResponseHandler() {
		    	     @Override
		    	     public void onStart() {
		    	         // Initiated the request
		    	     }

		    	     @Override
		    	     public void onSuccess(String response) {
		    	         // Successfully got a response
		    	    	 
		    	    	 
	    	    	
		    	    		 S3DBC.api_key= S3DBC.parseApiKey(response);	
		    	    		 Log.v("s3dbc",S3DBC.api_key);
		    	    	
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
