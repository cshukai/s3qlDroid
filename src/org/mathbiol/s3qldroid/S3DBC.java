package org.mathbiol.s3qldroid;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class S3DBC extends Activity {

	private static String BASE_URL = "http://204.232.200.16/uabs3db";
	private static AsyncHttpClient client = new AsyncHttpClient();

	// for processing http response
	static RequestParams params;
	public static AsyncHttpResponseHandler responseHandler;
	public static String api_key;
	private static String action_flag;
    private static String downloaded_file_type;
    
	// for retrieving data
	private static JsonArray json_array;
	private static JsonObject json_obj;
	public static String selected_item_notes;
    public static byte[] downloadedBinaryDataArray;
    
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_s3_dbc);

		
		final Button login_button = (Button) findViewById(R.id.login_button);
		final EditText usrnameFiled = (EditText) findViewById(R.id.usrname_field);
		final EditText password_field = (EditText) findViewById(R.id.password_field);

		login_button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				S3DBC.s3dbc_login(usrnameFiled.getText().toString(),
						password_field.getText().toString());
				Intent intent = new Intent(S3DBC.this, BulkDowonloadByType.class);
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

	public static void downloadBinaryFile(String statementId) {
		params = new RequestParams();
		params.put("key", S3DBC.api_key);
		params.put("statement_id", statementId);
		//params.put("statement_id", dataType);
		action_flag = "download_binary";

		responseHandler = new AsyncHttpResponseHandler();
		S3DBC.get("/download.php", params, responseHandler);
	}

	public static void s3dbc_login(String usrname, String password) {
		params = new RequestParams();
		params.put("username", usrname);
		params.put("password", password);
		params.put("format", "html");

		action_flag = "login";

		responseHandler = new AsyncHttpResponseHandler();
		S3DBC.get("/apilogin.php", params, responseHandler);
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public static String parseApiKey(String htmlText) {

		String result = null;
		String[] temp = Html.escapeHtml(htmlText).split(";");

		String temp_2 = temp[26];
		String[] temp_3 = temp_2.split("&");
		result = temp_3[0];

		return result;
	}

	
	
	//@todo
	public static void fileUploadByCollectionIdAndRuleId(ContentResolver cr,
			Uri fileUrl, String api_key, String collection_id, String rule_id,
			String fileName) {

		params = new RequestParams();
		params.put("key", api_key);
		params.put("collection_id", collection_id);
		params.put("rule_id", rule_id);
		params.put("format", "json");

		try {
			InputStream input_stream = cr.openInputStream(fileUrl);
			params.put("filename", input_stream, fileName);
		}

		catch (FileNotFoundException e) {
			Log.v("s3dbc", "file not found");
			e.printStackTrace();
			e.printStackTrace();
		}

	}

	public static String insertItem(String collectionId, String notes) {
		String query = "<S3QL>" + "<insert>item</insert>" + "<where>"
				+ "<collection_id>" + collectionId + "</collection_id>"
				+ "<notes>" + notes + "</notes>" + "</where>" + "</S3QL>";
		responseHandler = new AsyncHttpResponseHandler();

		sendS3Qlrequest(query, api_key);

		return query;
	}

	public static void selectItem(String itemId) {
		String query = "<S3QL>" + "<select>*</select>" + "<from>items</from>"
				+ "<where>" + "<item_id>" + itemId + "</item_id>" + "</where>"
				+ "</S3QL>";

		action_flag = "select_item_by_itemId";

		S3DBC.sendS3Qlrequest(query, S3DBC.api_key);
	}

	public static void sendS3Qlrequest(String query, String api_key) {
		params = new RequestParams();
		params.put("key", api_key);
		params.put("format", "json");
		params.put("query", query);

		S3DBC.get("/S3QL.php", params, responseHandler);
	}

	
	public static void selectStatmentsByRuleId(String rule_id){
		action_flag = "select_statement";
		String query = "<S3QL>" + "<select>*</select>" + "<from>statements</from>"
				+ "<where>" + "<rule_id>" +rule_id + "</rule_id>" + "</where>"
				+ "</S3QL>";
		
		
		
		S3DBC.sendS3Qlrequest(query,S3DBC.api_key);
	}
	
	
	public static void downloadBinaryFilesByFileTypeAndRuleID(String fileType,String rule_id){
		 S3DBC.selectStatmentsByRuleId(rule_id);
		 S3DBC.downloaded_file_type=fileType;
		 action_flag ="select_statement_for_later_process";
	}
	
	public static void selectStatmentsByFileName(String fileName){
		String query = "<S3QL>" + "<select>*</select>" + "<from>statements</from>"
				+ "<where>" + "<file_name>" +fileName + "</file_name>" + "</where>"
				+ "</S3QL>";
		action_flag = "select_statement";
		S3DBC.sendS3Qlrequest(query,S3DBC.api_key);
	}
	
	public static void get(String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {

		/*
		 * https://github.com/loopj/android-async-http/issues/91#issuecomment-
		 * 10104937 should explains why JsonHttpResponseHandler should not be
		 * used for S3DB response
		 */
		if (action_flag.equals("select_item_by_itemId")) {

			client.get(getAbsoluteUrl(url), params,
					new AsyncHttpResponseHandler() {

						@Override
						public void onStart() {

							Log.v("s3dbc_selecItem", "debugging_start");
						}

						@Override
						public void onSuccess(String response) {
							// Successfully got a response

							Log.v("s3dbc_selecItem", response);

							if (new JsonParser().parse(response).isJsonArray()) {
								json_array = new JsonParser().parse(response)
										.getAsJsonArray();
								json_obj = json_array.get(0).getAsJsonObject();
								selected_item_notes = json_obj.get("notes")
										.toString();

								Log.v("s3dbc_selectItem", selected_item_notes);

							}

							if (new JsonParser().parse(response)
									.isJsonPrimitive()) {
								// Log.v("s3dbc_selecItem","primitive");
							}

							// Log.v("s3dbc_selecItem",json_obj.get("id").toString());
						}

						@Override
						public void onFailure(Throwable e, String response) {
							// Response failed :(
							Log.e("s3dbc_selecItem", response);
						}

						@Override
						public void onFinish() {

							Log.v("s3dbc_selecItem", "process_finished");
						}
					});

		}
		
		
		
		if (action_flag.equals("select_statement")||action_flag.equals("select_statement_for_later_process")) {

			client.get(getAbsoluteUrl(url), params,
					new AsyncHttpResponseHandler() {

						@Override
						public void onStart() {

							Log.v("select_statement", "debugging_start");
						}

						@Override
						public void onSuccess(String response) {
							

							Log.v("select_statement", response);

							if (new JsonParser().parse(response).isJsonArray()) {
								json_array = new JsonParser().parse(response)
										.getAsJsonArray();		
								
								
								if(action_flag.equals("select_statement_for_later_process")){
									
									for(int i=0;i<json_array.size();i++){
										json_obj = json_array.get(i).getAsJsonObject();	
										
										if(json_obj.get("file_name").toString().contains(".jpg")){
											 S3DBC.downloadBinaryFile(json_obj.get("file_name").toString());
											 Log.v("bulk_donwload", "bd"); 
										}										
																			}
									
								}

							}

							if (new JsonParser().parse(response)
									.isJsonPrimitive()) {
								
							}

							
						}

						@Override
						public void onFailure(Throwable e, String response) {
							// Response failed :(
							Log.e("select_statement", response);
						}

						@Override
						public void onFinish() {

							Log.v("select_statement", "process_finished");
						}
					});

		}
		
		
		

		if (action_flag.equals("download_binary")) {
			
			String[] allowedContentTypes = new String[] {"application/download"};
			client.get(getAbsoluteUrl(url), params,new BinaryHttpResponseHandler(allowedContentTypes) {
			    
				@Override
			    public void onSuccess(byte[] fileData) {					
					downloadedBinaryDataArray=fileData;
					Log.v("s3dbc_download_binary","onSuccess Fired");
					
			         // prepare to save data to sd card and present in image slider using animation
					//http://stackoverflow.com/questions/3545493/display-byte-to-imageview-in-android
					//http://stackoverflow.com/questions/12844988/how-to-create-image-slider-in-android
					//http://android-developers.blogspot.in/2011/08/horizontal-view-swiping-with-viewpager.html
					//http://codinglookseasy.blogspot.in/2012/08/image-slide-show.html
					 // consider viewflipper
					 File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "s3qldroid");
					 
					    // Create the storage directory if it does not exist
					    if (! mediaStorageDir.exists()){
					        if (! mediaStorageDir.mkdirs()){
					            Log.d("s3qldroid", "failed to create directory");
					            
					        }
					    }
					    
					   
					 BufferedOutputStream buf=null;
					 try {
						 
						 String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
						 FileOutputStream fileoutput=new FileOutputStream( new File(mediaStorageDir.getPath() + File.separator +"IMG_"+ timeStamp + ".jpg"));
					     buf = new BufferedOutputStream(fileoutput);
						
                         buf.write(fileData);
						
						
					} catch (FileNotFoundException e) {						
						e.printStackTrace();
						Log.e("s3dbc_download_binary","file not found ");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Log.e("s3dbc_download_binary","IO problem ");
					}
					 
					 finally{
						 if(buf!=null){
							 try {
								buf.flush();
								buf.close();
							} 
							 
							 catch (IOException e) {
								 Log.v("s3dbc_download_binary","buffer flushing or closing problem");
								 e.printStackTrace();
							}
							  
						 }
					 }

			    }
				
				
				@Override
				public void onFailure(Throwable e, byte[] responseBody) {
					// Response failed :(					
					Log.e("s3dbc_download_binary", "onfailure fired");
					e.printStackTrace();
				}
			});
		}

		if (action_flag.equals("login")) {

			client.get(getAbsoluteUrl(url), params,
					new AsyncHttpResponseHandler() {
						@Override
						public void onStart() {
							// Initiated the request
						}

						@Override
						public void onSuccess(String response) {
							// Successfully got a response

							S3DBC.api_key = S3DBC.parseApiKey(response);
							Log.v("s3dbc", S3DBC.api_key);

						}

						@Override
						public void onFailure(Throwable e, String response) {
							// Response failed :(
							Log.e("s3dbc", response);
						}

						@Override
						public void onFinish() {
							// Completed the request (either success or failure)
						}
					});
		}

	}

	public static void post(String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		client.post(getAbsoluteUrl(url), params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onStart() {
						// Initiated the request
					}

					@Override
					public void onSuccess(String response) {
						// Successfully got a response
						Log.v("s3dbc", response);
					}

					@Override
					public void onFailure(Throwable e, String response) {
						// Response failed :(
						Log.e("s3dbc", response);
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
