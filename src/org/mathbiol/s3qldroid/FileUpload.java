package org.mathbiol.s3qldroid;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;


public class FileUpload extends SherlockActivity {
	//setting of camera intent
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
	private Uri fileUri;
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;
    	
	//setting of gallery access
	//ourgoodies
	private static final int SELECT_PICTURE = 1;
	private String selectedImagePath;
	
	// setting of actionbarsherlock
	ActionMode mMode;
	public static int THEME = R.style.Theme_Sherlock;
	
	
	
	/** Create a file Uri for saving an image or video */
	private Uri getOutputMediaFileUri(int type){
	      return Uri.fromFile(getOutputMediaFile(type));
	}

	/** Create a File for saving an image or video */
	private  File getOutputMediaFile(int type){
	    // To be safe, you should check that the SDCard is mounted
	    // using Environment.getExternalStorageState() before doing this.

	    File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
	              Environment.DIRECTORY_PICTURES), "s3qldroid");
	    // This location works best if you want the created images to be shared
	    // between applications and persist after your app has been uninstalled.

	    // Create the storage directory if it does not exist
	    if (! mediaStorageDir.exists()){
	        if (! mediaStorageDir.mkdirs()){
	            Log.d("s3qldroid", "failed to create directory");
	            return null;
	        }
	    }

	    // Create a media file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    File mediaFile;
	    if (type == MEDIA_TYPE_IMAGE){
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	        "IMG_"+ timeStamp + ".jpg");
	    } else if(type == MEDIA_TYPE_VIDEO) {
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	        "VID_"+ timeStamp + ".mp4");
	    } else {
	        return null;
	    }

	 
	    //updateMediaStore
	    MediaScannerWrapper mediaWrapper=new MediaScannerWrapper(FileUpload.this,mediaFile.getAbsolutePath(),null);
	    mediaWrapper.scan();
	    return mediaFile;
	}
   
	
	

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
	        if (resultCode == RESULT_OK) {
	        	if(data!=null){
	        		
	        		  
	        		
	        		 // Image captured and saved to fileUri specified in the Intent
		            Toast.makeText(FileUpload.this, "Image saved to:\n" +
		                     data.getData(), Toast.LENGTH_LONG).show();
		            
		          
	        	}
	        	
	        	else{
	        		//MediaStore.Images.Media.insertImage
	        	}
	        	
	           
	        } else if (resultCode == RESULT_CANCELED) {
	            // User cancelled the image capture
	        } else {
	            // Image capture failed, advise user
	        }
	    }

	    if (requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE) {
	        if (resultCode == RESULT_OK) {
	            // Video captured and saved to fileUri specified in the Intent
	            Toast.makeText(FileUpload.this, "Video saved to:\n" +
	                     data.getData(), Toast.LENGTH_LONG).show();
	        } else if (resultCode == RESULT_CANCELED) {
	            // User cancelled the video capture
	        } else {
	            // Video capture failed, advise user
	        }
	    }
	    
	    
	    // ourgoodies
	    if (requestCode == SELECT_PICTURE) {
	    	if (resultCode == RESULT_OK) {
	            Uri selectedImageUri = data.getData();
	            selectedImagePath = selectedImageUri.getPath();
	            Toast.makeText(FileUpload.this, selectedImagePath, Toast.LENGTH_LONG).show();
	    		
	    	}

        }
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_file_upload);
	
	    

	}

	@Override
	public void onResume() {
		super.onResume();
        
		setTheme(THEME); 
		mMode = startActionMode(new AnActionModeOfEpicProportions());

	}

	private final class AnActionModeOfEpicProportions implements
			ActionMode.Callback {
		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			// Used to put dark icons on light action bar
			boolean isLight = THEME == R.style.Theme_Sherlock_Light;

			menu.add("Save")
					.setIcon(
							isLight ? R.drawable.ic_compose_inverse
									: R.drawable.ic_compose)
					.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

			menu.add("Search")
					.setIcon(
							isLight ? R.drawable.ic_search_inverse
									: R.drawable.ic_search)
					.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

			menu.add("Refresh")
					.setIcon(
							isLight ? R.drawable.ic_refresh_inverse
									: R.drawable.ic_refresh)
					.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

			menu.add("Save")
					.setIcon(
							isLight ? R.drawable.ic_compose_inverse
									: R.drawable.ic_compose)
					.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

			menu.add("Search")
					.setIcon(
							isLight ? R.drawable.ic_search_inverse
									: R.drawable.ic_search)
					.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

			menu.add("Refresh")
					.setIcon(
							isLight ? R.drawable.ic_refresh_inverse
									: R.drawable.ic_refresh)
					.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

			return true;
		}

		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return false;
		}

		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			String itemTitle = item.getTitle().toString();
			if (itemTitle.equals("Save")) {

				 Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				  fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
				  intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
				  //intent.putExtra("org.mathbiol.s3qldroid.FileUpload", fileUri); // set the image file name
				    // start the image capture Intent
				    startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
				    
			}
		

			if (itemTitle.equals("Refresh")) {
				//ourgoodies
				Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTURE);				 
				    
			}
			
			
			return true;
		}

		//ourgoodies
		/* getting url of files in media store */
		public String getPath(Uri uri) {
		    String[] projection = { MediaStore.Images.Media.DATA };
		    Cursor cursor = managedQuery(uri, projection, null, null, null);
		    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		    cursor.moveToFirst();
		    return cursor.getString(column_index);
		}
		
		
		@Override
		public void onDestroyActionMode(ActionMode mode) {
			
		}
	}
	
	
	
	class MediaScannerWrapper implements  MediaScannerConnection.MediaScannerConnectionClient{
		 private MediaScannerConnection mConnection;
		    private String mPath;
		    private String mMimeType;

		    // filePath - where to scan; 
		    // mime type of media to scan i.e. "image/jpeg". 
		    // use "*/*" for any media
		    public MediaScannerWrapper(Context ctx, String filePath, String mime){
		        mPath = filePath;
		        mMimeType = mime;
		        mConnection = new MediaScannerConnection(ctx, this);
		    }

		    // do the scanning
		    public void scan() {
		        mConnection.connect();
		    }

		    // start the scan when scanner is ready
		    public void onMediaScannerConnected() {
		        mConnection.scanFile(mPath, mMimeType);
		        Log.w("MediaScannerWrapper", "media file scanned: " + mPath);
		    }

		    public void onScanCompleted(String path, Uri uri) {
		        // when scan is completes, update media file tags
		    }
	}
}
