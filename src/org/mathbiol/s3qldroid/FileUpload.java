package org.mathbiol.s3qldroid;

import android.hardware.Camera;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import android.util.Log;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class FileUpload extends SherlockActivity {
	ActionMode mMode;
	public static int THEME = R.style.Theme_Sherlock;
	private static Camera camera;

	public static Camera getCameraInstance() {
		Camera c = null;
		try {
			// Toast.makeText(Index.this,"here", Toast.LENGTH_SHORT).show();
			c = Camera.open(0); // attempt to get a Camera instance
		} catch (Exception e) {
			// Toast.makeText(Index.this,"test"+ e.getMessage(),
			// Toast.LENGTH_SHORT).show();
			Log.e("camera", e.getMessage());
			throw new RuntimeException(e);

		}
		return c; // returns null if camera is unavailable
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_file_upload);

	}

	@Override
	public void onResume() {
		super.onResume();

		WebView myWebView = (WebView) findViewById(R.id.webview);
		myWebView.setWebViewClient(new WebViewClient());

		WebSettings webSettings = myWebView.getSettings();
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setUseWideViewPort(true);
		webSettings.setJavaScriptEnabled(true);
		myWebView.loadUrl("http://sandbox2.mathbiol.org/blueimp/ex4/");
		setTheme(THEME); // Used for theme switching in samples
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

				camera = FileUpload.getCameraInstance();
				if (camera != null) {
					Camera.Parameters cam_params = camera.getParameters();
					Camera.CameraInfo cam_info = null;
					camera.getCameraInfo(0, cam_info);
					Toast.makeText(FileUpload.this,"here",
							 Toast.LENGTH_SHORT).show();
					camera.release();
					
					//error message: fata signal 11 (SIGSEGV)  code=1
					
					/*
					 * 1.seems like the current code has conflict with the
					 * setting of coexistence of back and front end cameras
					 * 
					 * whether webcam+emu or emu+emu
					 * 
					 * proof as follows:
					 */
					// Toast.makeText(Index.this, cam_info.facing,
					// Toast.LENGTH_SHORT).show();
					// Toast.makeText(Index.this, camera.getNumberOfCameras(),
					// Toast.LENGTH_SHORT).show();
					
					// Toast.makeText(Index.this, "here",
					// Toast.LENGTH_SHORT).show();

					/*
					 * reset the setting to single one camera , still has the
					 * same problem with the following line
					 * 
					 * 
					 * Toast.makeText(Index.this, "here",
					 * Toast.LENGTH_SHORT).show();
					 */

					// Toast.makeText(Index.this, "here",
					// Toast.LENGTH_SHORT).show();
				}

				else {
					// Toast.makeText(Index.this, "not",
					// Toast.LENGTH_SHORT).show();
				}
				
				

			}
			// Toast.makeText(Index.this, "Got click: " + item.getTitle(),
			// Toast.LENGTH_SHORT).show();

			return true;
		}

		@Override
		public void onDestroyActionMode(ActionMode mode) {
		}
	}
}
