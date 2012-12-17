package org.mathbiol.s3qldroid;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DbViewer extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_db_viewer);
		final Button ls_proj_info = (Button) findViewById(R.id.ls_project_info);
		final EditText proj_id=(EditText)findViewById(R.id.project_id_input);
		//final String url="http://204.232.200.16/uabs3db/xmlproject.php?project_id="+proj_id.getText().toString()+"key="+S3DBC.api_key;
		final String url="http://sandbox2.mathbiol.org/Kinomics.s3db.xml";
		new XmlReader().execute(url);
		
		ls_proj_info.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            
            	new XmlReader().execute(url);
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_db_viewer, menu);
		return true;
	}
    
	
private class XmlReader extends AsyncTask<String, String, String> {
	
		
		@Override
		protected String doInBackground(String... urls) {
		     	
        	String xml = getXmlFromUrl(urls[0]);
        	//Log.v("show_raw_xml",xml);
        	Document doc = getDomElement(xml);
        	NodeList nl = doc.getElementsByTagName("ENTITY");
        	//Toast.makeText(DbViewer.this,"you have following entites:" , Toast.LENGTH_LONG).show();
        	for (int i = 0; i < nl.getLength(); i++) {
				
				Element e = (Element) nl.item(i);
				//Toast.makeText(DbViewer.this,e.getTextContent(), Toast.LENGTH_LONG).show();
				 Log.v("async",  e.getTextContent());

			}
        	
        	
		
			
			return "test";
		}

		@Override
		protected void onProgressUpdate(String... progress) {
	         
	     }
		
		@Override
	     protected void onPostExecute(String result) {
	         
	     }
		
	}
	
	public String getXmlFromUrl(String url) {
		String xml = null;

		try {

			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			xml = EntityUtils.toString(httpEntity);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// return XML
		return xml;
	}

	public Document getDomElement(String xml) {
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {

			DocumentBuilder db = dbf.newDocumentBuilder();

			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));
			doc = db.parse(is);

		} catch (ParserConfigurationException e) {
			Log.e("Error: ", e.getMessage());
			return null;
		} catch (SAXException e) {
			Log.e("Error: ", e.getMessage());
			return null;
		} catch (IOException e) {
			Log.e("Error: ", e.getMessage());
			return null;
		}
		// return DOM
		return doc;
	}

	public String getValue(Element item, String str) {
		NodeList n = item.getElementsByTagName(str);
		return this.getElementValue(n.item(0));
	}

	public final String getElementValue(Node elem) {
		Node child;
		if (elem != null) {
			if (elem.hasChildNodes()) {
				for (child = elem.getFirstChild(); child != null; child = child
						.getNextSibling()) {
					if (child.getNodeType() == Node.TEXT_NODE) {
						return child.getNodeValue();
					}
				}
			}
		}
		return "";
	}
}
