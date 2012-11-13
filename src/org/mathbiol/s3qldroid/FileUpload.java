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
			c = Camera.open(0); 
			
		} catch (Exception e) {
	
			//Log.e("camera", e.getMessage());
			throw new RuntimeException(e);

		}
		return c;
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

					Toast.makeText(FileUpload.this,"here",
							 Toast.LENGTH_SHORT).show();
					camera.release();
					
				
				}

				else {
					 Toast.makeText(FileUpload.this, "not",
					 Toast.LENGTH_SHORT).show();
				}
				
				

			}
		

			return true;
		}

		@Override
		public void onDestroyActionMode(ActionMode mode) {
		}
	}
}
