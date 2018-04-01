package com.aliv3nation.bossjobs;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * @author dante-aliv3
 *publisher - The Publisher ID assigned during registration.
	•	v - Version. Which version of the API you wish to use. All publishers should be using version 2. Currently available versions are 1 and 2. This parameter is required.
	•	formatFormat. - Which output format of the API you wish to use. The options are "xml" and "json." If omitted or invalid, the XML format is used.
	•	callback - Callback. The name of a javascript function to use as a callback to which the results of the search are passed. This only applies when format=json. For security reasons, the callback name is restricted letters, numbers, and the underscore character.
	•	q - Query. By default terms are ANDed. To see what is possible, use our advanced search page to perform a search and then check the url for the q value.
	•	l - Location. Use a postal code or a "city, state/province/region" combination.
	•	sort - Sort by relevance or date. Default is relevance.radiusDistance from search location ("as the crow flies"). Default is 25.
	•	st - Site type. To show only jobs from job boards use 'jobsite'. For jobs from direct employer websites use 'employer'.
	•	jt - Job type. Allowed values: "fulltime", "parttime", "contract", "internship", "temporary".
	•	start - Start results at this result number, beginning with 0. Default is 0.
	•	limit - Maximum number of results returned per query. Default is 10
	•	fromage - Number of days back to search.
	•	highlight - Setting this value to 1 will bold terms in the snippet that are also present in q. Default is 0.
	•	filter - Filter duplicate results. 0 turns off duplicate job filtering. Default is 1.
	•	latlong - If latlong=1, returns latitude and longitude information for each job result. Default is 0.
	•	co - Search within country specified. Default is us. See below for a complete list of supported countries.
	•	chnl - Channel Name: Group API requests to a specific channel
	•	userip - The IP number of the end-user to whom the job results will be displayed. This field is required.
	•	useragent - The User-Agent (browser) of the end-user to whom the job results will be displayed. This can be obtained from the "User-Agent" HTTP request header from the end-user. This field is required.

 */
public class IndeedApiRequest {

	/**
	 * 
	 */
	static String output = "";
	static BufferedReader rd;
	static final String PUBLISHER_ID = "5692149126888232";
	static boolean isConnected = false;
	static String urlStr = "";
	static Context currt;
	public IndeedApiRequest()
	{
		
	}
	
	public static String Request(String urlAdress, Context current) throws Exception {
		// check if you are connected or not
		/**if(isConnected()){
			isConnected = true;
		}
        else{
        	isConnected = false;
        	}**/
		urlStr = urlAdress;
		currt = current;
		try{
			new HttpAsyncTask().execute(urlStr);
		}
		catch(Exception error)
		{
			throw error;
		}
		return output;
		
			  /**URL url = new URL(urlStr);
			  HttpURLConnection conn =
			      (HttpURLConnection) url.openConnection();
			  //Url Response Time-Out
			  if (conn.getResponseCode() != 200) {
			    throw new IOException(conn.getResponseMessage());
			  }

			  // Buffer the result into a string
			  rd = new BufferedReader(
			      new InputStreamReader(conn.getInputStream()));
			  StringBuilder sb = new StringBuilder();
			  String line;
			  while ((line = rd.readLine()) != null) {
			    sb.append(line);
			  }
			  //Closes Buffer Reader
			  rd.close();

			  conn.disconnect();
			  return sb.toString();**/
			}

	public String createUrlStream(String query, String city,
			String state, String jobType, String start, String country)
	{
		String temp = "http://api.indeed.com/ads/apisearch?publisher="
				+ PUBLISHER_ID + "&q=" + query + "&l+" + city + "%2C" + state
				+ "&radius=&st=&" + jobType + "=&" + start 
				+ "=&limit=100&fromage=&highlight=0&filter=1&latlong=1&co="
				+ country + "&chnl=&userip=1.2.3.4"
						+ "&useragent=Mozilla/%2F4.0%28Firefox%29&v=2";
		return temp;
		
	}
	
	 /**public static boolean isConnected(){
	        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
	            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	            if (networkInfo != null && networkInfo.isConnected()) 
	                return true;
	            else
	                return false;   
	    }**/
	 
	private static class HttpAsyncTask extends AsyncTask<String, Void, String> {//runs network connection in concurrent thread
        @Override
        protected String doInBackground(String... urls) {
        	try{
				URL url = new URL(urlStr);
			HttpURLConnection connection =
			    (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "application/xml");

			InputStream xml = connection.getInputStream();

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(xml);
				try {
			        StringWriter sw = new StringWriter();
			        TransformerFactory tf = TransformerFactory.newInstance();
			        Transformer transformer = tf.newTransformer();
			        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	
			        transformer.transform(new DOMSource(doc), new StreamResult(sw));
			     String s=sw.toString();
			     output = s;
			    } catch (Exception ex) {
			        throw ex;
			    }
        	}
        	catch(Exception err)
			{
        		String s = "Request error ==> "+ err;
        		Toast.makeText(currt, s, Toast.LENGTH_LONG).show();
			}
			finally
			{
				//Closes Buffer Reader
				//rd.close();
			}
        	return output;
		}
        
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
        	String s = "Finally Done...";
    		Toast.makeText(currt, s, Toast.LENGTH_LONG).show();
    		}
    }
	
}
