/**
 * 
 */
package com.aliv3nation.bossjobs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author dante-aliv3
 *
 */
public class RestApiRequest {

	/**
	 * 
	 */
	static BufferedReader rd;

	public RestApiRequest()
	{
		
	}
	public static String Request(String urlStr) throws IOException {
		try{
		
			  URL url = new URL(urlStr);
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
			  return sb.toString();
			}
		catch(Exception err)
		{
			throw err;
		}
		finally
		{
			//Closes Buffer Reader
			rd.close();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
