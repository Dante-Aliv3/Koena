package com.aliv3nation.bossjobs;


import java.io.InputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import com.aliv3nation.beingboss.R;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;


public class JobActivity extends ActionBarActivity implements OnClickListener {
/** Called when the activity is first created. */

	private  static final String PUBLISHER_ID = "5692149126888232";
	private  DrawerLayout drawerLayout;
	private  ListView listView;
	private  Context current;
	private  String[ ] [ ] results;
	private  ArrayList<ArrayList<String>> records = new ArrayList<ArrayList<String>>();
	private  ArrayList <String> searchItems;
	private  String outputString, query, state, city, jobType = "", fromage = "";
	private  String[ ] allJobNames;
	public   int DATABASE_VERSION_NUMBER = 1, count, length = 0, 
			 numberedResults = 25;
	public   int next; // control variable for submit button
	private static String urlStr = "http://api.indeed.com/ads/apisearch?publisher=5692149126888232&q=java"
			+ "&l=atlanta%2C+ga&sort=&radius=&st=&jt=&start=&limit=100&fromage=&highlight=0&"
			+ "filter=1&latlong=1&co=us&chnl=&userip=1.2.3.4&useragent=Mozilla/%2F4.0%28Firefox%29&v=2";
	SQLiteDatabase apiDatabase;
	SQLiteHelper sqlHelper;
	Cursor cursor;
	
	TextView title, jobLabel, company, positionDescription, 
		it_positionDescription, jobLocation,Education_level, 
		MoneySource, jobSource, it_jobType, Hyperlink,
		jobOutlook, mainLink, secondaryLink, usefulLink;
	Button submit;
	
	String TABLENAME = "Job_Records",
			KEY_ROWID = "Row_ID",
			KEY_JOBID = "Job_Title",
			KEY_COMPANY = "Company",
			KEY_POSITION_DESC = "Position_Description",
			KEY_JOBLOCATION = "Job_Location",
			KEY_EDUCATION_LEVEL = "Education_Level",
			KEY_CREATION_DATE = "Date_Created",
			KEY_JOB_SOURCE = "JobFeed_Source",
			KEY_HYPERLINK = "Hyperlink",
			KEY_PUBLISH_DATE = "Date_Published";
	
@SuppressLint("SetJavaScriptEnabled")
@Override


public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_job_search);
	getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	current = getApplicationContext();
	String [] temp = Database.allRecords();
	searchItems = new ArrayList<String>(temp.length);
	for(int i = 0; i < temp.length; i++)
		searchItems.add(temp[i]);
	try{
		new ReadApi().execute(urlStr);
	}
	catch(Exception error)
	{
		throw error;
	}
	listView = (ListView) findViewById(R.id.drawerList);
	drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
	title = (TextView) findViewById(R.id.title);
	company = (TextView) findViewById(R.id.jobField);
	positionDescription = (TextView) findViewById(R.id.positionDescription);
	jobLocation = (TextView) findViewById(R.id.Skill);
	Education_level = (TextView) findViewById(R.id.expectedCost);
	MoneySource = (TextView) findViewById(R.id.MoneySource);
	jobSource = (TextView) findViewById(R.id.JobLocation);
	Hyperlink = (TextView) findViewById(R.id.jobType);
	jobOutlook = (TextView) findViewById(R.id.jobOutlook);
	mainLink = (TextView) findViewById(R.id.mainLink);
	secondaryLink = (TextView) findViewById(R.id.secondaryLink);
	usefulLink = (TextView) findViewById(R.id.usefulLink);
	it_positionDescription = (TextView) findViewById(R.id.it_positionDescription);
	it_jobType = (TextView) findViewById(R.id.it_jobType);
	drawerLayout.openDrawer(listView);
	Hyperlink.setTextColor(Color.parseColor("#2ECCFA"));
	submit = (Button) findViewById(R.id.submit);

	it_positionDescription.setOnClickListener(this);
	it_jobType.setOnClickListener(this);
	submit.setOnClickListener(this);
	
	}

	private void update() {
		// between 0 - 8
		SQLSetUp();
		next = next + (numberedResults - 25);
		title.setText(records.get(next).get(0));//job name
		company.setText(records.get(next).get(1));//job company
		positionDescription.setText(records.get(next).get(2));//job description
		jobLocation.setText(records.get(next).get(3));//city, state
		Education_level.setText(records.get(next).get(4));//Education Required
		jobSource.setText("Job Source: " + records.get(next).get(5));//website source (Indeed)
		MoneySource.setText(records.get(next).get(5));//date published (Specific)
		String link = "<a href='" + records.get(next).get(7) + "'> " + "Job Details </a>";
		Hyperlink.setText(Html.fromHtml(link));//Hyperlink
		jobOutlook.setText(records.get(next).get(8));//date published (General)
	    next++;

		//sets text for Layout Drawer
	    listView.setAdapter(new ArrayAdapter<>(current, android.R.layout.simple_list_item_1, allJobNames));
	    listView.setOnItemClickListener(new OnItemClickListener() 
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) 
			{	
				if(position < records.size())
				{
					title.setText(records.get(position).get(0));
			    	company.setText(records.get(position).get(1));
			    	positionDescription.setText(records.get(position).get(2));
			    	jobLocation.setText(records.get(position).get(3));
			    	Education_level.setText(records.get(position).get(4) + " Required");
			    	MoneySource.setText(records.get(position).get(5));
			    	jobSource.setText(records.get(position).get(6));
			    	String link = "<a href='" + records.get(position).get(7) + "'> " + "Job Details </a>";
			    	Hyperlink.setText(Html.fromHtml(link));
			    	jobOutlook.setText(records.get(position).get(3));
				}
				else
					Toast.makeText(current, "Invalid Selection, Try Again", Toast.LENGTH_SHORT).show();
				drawerLayout.closeDrawers();
			}
	    });
	}

	private void SQLSetUp() {
		apiDatabase = openOrCreateDatabase("Boss_Jobs.db", MODE_PRIVATE,null);
		
		String CREATE_JOBS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLENAME + " ( "
				+ KEY_ROWID + " INTEGER PRIMARY KEY, "
				+ KEY_JOBID + " TEXT NOT NULL, " 
				+ KEY_COMPANY + " TEXT NOT NULL, "
				+ KEY_POSITION_DESC + " TEXT NOT NULL, "
				+ KEY_JOBLOCATION + " TEXT, " // city, state
				+ KEY_EDUCATION_LEVEL + " TEXT, "
				+ KEY_JOB_SOURCE + " TEXT, "
				+ KEY_CREATION_DATE + " TEXT, " //convert to Date, Data Type
				+ KEY_HYPERLINK + " TEXT, "
				+ KEY_PUBLISH_DATE + " TEXT, "
				+ "UNIQUE (" + KEY_JOBID + ", " + KEY_COMPANY + ") );";

		apiDatabase.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
		apiDatabase.execSQL(CREATE_JOBS_TABLE);
		
		/**mydatabase.execSQL("INSERT INTO TutorialsPoint VALUES('admin','admin');");
		sqlHelper = new SQLiteHelper(current, "Boss_Jobs.db", null, DATABASE_VERSION_NUMBER);
		db = sqlHelper.getWritableDatabase();**/
		
		String INSERT_NEW_RECORD = "";
		for(int i = 0; i < results.length; i++)
		{
			results[i][7] = results[i][7].replace("'", "^");
			INSERT_NEW_RECORD = "INSERT OR IGNORE INTO " + TABLENAME 
			+ " (" + KEY_ROWID + ", "
			+ KEY_JOBID + ", " 
			+ KEY_COMPANY + ", "
			+ KEY_POSITION_DESC + ", "
			+ KEY_JOBLOCATION + ", " // city, state
			+ KEY_EDUCATION_LEVEL + ", "
			+ KEY_JOB_SOURCE + ", "
			+ KEY_CREATION_DATE + ", " //convert to Date, Data Type
			+ KEY_HYPERLINK + ", "
			+ KEY_PUBLISH_DATE + ")"
			+"  VALUES ( "
			+ (i + 1) + ", "
			+ "'"+(results[i][0])+"', "
	    	+ "'"+(results[i][1])+"', "
	    	+ "'"+(results[i][7].replace(",", "*")) +"', "
	    	+ "'"+(results[i][2] + "* " + results[i][3])+"', "
	    	+ "'"+(results[i][10].replace("'", "*"))+"', "
	    	+ "'"+(results[i][6].replace(",", "*"))+"', "
	    	+ "'"+(results[i][5])+"', "
	    	+ "'"+(results[i][8])+"', "
	    	+ "'"+(results[i][12].replace(",", "*"))+"');";
			apiDatabase.execSQL(INSERT_NEW_RECORD);
			//sqlHelper.insert(INSERT_NEW_RECORD);
		}
		results = null;
		queryResults();
	}

	private void queryResults() {
		
		String whereClause = "Row_ID <= " + numberedResults
				+ " AND Row_ID >= " + (numberedResults - 25);
		String orderBy = KEY_JOBID;
		cursor = apiDatabase.query(
				TABLENAME, null, whereClause, null, null, null, orderBy);
		int i = (numberedResults - 25);
		if (cursor != null && i < numberedResults)  {
		    if  (cursor.moveToFirst()) {
		    	
		    	if(records.size() > numberedResults)
		    		records.clear();//used to detect an job search reset by looking for bigger array size than results
		    	
		    	do {
		    		
		    		if(records.size() < numberedResults)//controls the number of records
		    			records.add(new ArrayList<String>());
		    		
		    		if(records.get(i).isEmpty())
		    		{//checks if database is empty
		    			records.get(i).add(cursor.getString(cursor.getColumnIndex(KEY_JOBID)));
		    			records.get(i).add(cursor.getString(cursor.getColumnIndex(KEY_COMPANY)));
			        	records.get(i).add(cursor.getString(cursor.getColumnIndex(KEY_POSITION_DESC)));
			        	records.get(i).add(cursor.getString(cursor.getColumnIndex(KEY_JOBLOCATION)));
			        	records.get(i).add(cursor.getString(cursor.getColumnIndex(KEY_EDUCATION_LEVEL)));
			        	records.get(i).add(cursor.getString(cursor.getColumnIndex(KEY_CREATION_DATE)));
			        	records.get(i).add(cursor.getString(cursor.getColumnIndex(KEY_JOB_SOURCE)));
			        	records.get(i).add(cursor.getString(cursor.getColumnIndex(KEY_HYPERLINK)));
			        	records.get(i).add(cursor.getString(cursor.getColumnIndex(KEY_PUBLISH_DATE)));
		    		}
		    		else
		    		{// repopulates database if search parameters have changed
		    			records.get(i).set(0, cursor.getString(cursor.getColumnIndex(KEY_JOBID)));
			        	records.get(i).set(1, cursor.getString(cursor.getColumnIndex(KEY_COMPANY)));
			        	records.get(i).set(2, cursor.getString(cursor.getColumnIndex(KEY_POSITION_DESC)));
			        	records.get(i).set(3, cursor.getString(cursor.getColumnIndex(KEY_JOBLOCATION)));
			        	records.get(i).set(4, cursor.getString(cursor.getColumnIndex(KEY_EDUCATION_LEVEL)));
			        	records.get(i).set(5, cursor.getString(cursor.getColumnIndex(KEY_CREATION_DATE)));
			        	records.get(i).set(6, cursor.getString(cursor.getColumnIndex(KEY_JOB_SOURCE)));
			        	records.get(i).set(7, cursor.getString(cursor.getColumnIndex(KEY_HYPERLINK)));
			        	records.get(i).set(8, cursor.getString(cursor.getColumnIndex(KEY_PUBLISH_DATE)));
		        	}
		    		i++;
		        }while (cursor.moveToNext());
		    }
		}
		cursor.close();
		
		allJobNames = new String[records.size()];
        for(int r = 0; r < records.size(); r++)
        {
        	allJobNames[r] =  records.get(r).get(0);
        }
        listView.setAdapter(new ArrayAdapter<>(current, android.R.layout.simple_list_item_1, allJobNames));
	}

	public void createUrlStream(String query, String city,
		String state, String jobType, String country, String start, String fromage )
	{
	  urlStr = "http://api.indeed.com/ads/apisearch?publisher="
			+ PUBLISHER_ID + "&q=" + query + "&l=" + city.toLowerCase() + "," + state.toLowerCase()
			+ "&sort=&radius=&st=&jt=" + jobType + "&start=" + start 
			+ "&limit=100&fromage="+ fromage +"&highlight=0&filter=1&latlong=1&co="
			+ country + "&chnl=&userip=1.2.3.4&useragent=Mozilla/%2F4.0%28Firefox%29&v=2";
	}

	public void onClick(View v) {
		switch(v.getId())
		{
			case (R.id.submit): 
				
				try{
					numberedResults = (numberedResults + 25);
					new ReadApi().execute(urlStr);
				}
				catch(Exception error)
				{
					throw error;
				}
				break;
				
			case (R.id.it_positionDescription): 
				if(positionDescription.getVisibility() == View.VISIBLE)
					positionDescription.setVisibility(View.GONE);
				else
					positionDescription.setVisibility(View.VISIBLE);
				break;
				
			case (R.id.it_jobType): 
				if(Hyperlink.getVisibility() == View.VISIBLE)
					Hyperlink.setVisibility(View.GONE);
				else
					Hyperlink.setVisibility(View.VISIBLE);
				break;
				
				
			case (R.id.it_links): 
				if(mainLink.getVisibility() == View.VISIBLE)
				{
					mainLink.setVisibility(View.GONE);
					secondaryLink.setVisibility(View.GONE);
					usefulLink.setVisibility(View.GONE);
				}
				else
				{
					mainLink.setVisibility(View.VISIBLE);
					secondaryLink.setVisibility(View.VISIBLE);
					usefulLink.setVisibility(View.VISIBLE);
				}
				break;
			}
	}

	private class ReadApi extends AsyncTask<String, Void, String> {//runs network connection in concurrent thread
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
	        StringWriter sw = new StringWriter();
	        TransformerFactory tf = TransformerFactory.newInstance();
	        Transformer transformer = tf.newTransformer();
	        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
	        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	
			transformer.transform(new DOMSource(doc), new StreamResult(sw));
			     String s=sw.toString();
			     outputString = s;
			     
        		XmlParser parsing = new XmlParser(outputString);
        		results = parsing.getResults();

        		int linkLocation = 8;
    			String hyperlink = "";
    			String [] descriptArray = null;
    			
    			for(int i= 0; i < results.length; i++)
    			{// downloads job webpage - parses data
    				hyperlink = results[i][linkLocation];
    				org.jsoup.nodes.Document document;
					document = Jsoup.connect(hyperlink).get();
					Elements tags = document.select("#desc");
					String stringData = tags.text();
    					     
    					//Splits string to find pertinent detail
    					if (stringData.contains("Qualifications")) {
    						descriptArray = stringData.split("Qualifications");
    					} else if (stringData.contains("Minimum")) {
    						descriptArray = stringData.split("Minimum");
    						} else if (stringData.contains("Requirements")) {
    							descriptArray = stringData.split("Requirements");
    							} else if (stringData.contains("Preferences")) {
    								descriptArray = stringData.split("Preferences");
    								} else if (stringData.contains("Required education")) {
    									descriptArray = stringData.split("Required education");
    									} else if (stringData.contains("Mandatory Skills")) {
    										descriptArray = stringData.split("Mandatory Skills");
    										} else if (stringData.contains("Qualification")) {
    											descriptArray = stringData.split("Qualifications");
    										}else {
        										
        										}
    						
    					
    					if(descriptArray != null && descriptArray.length > 0)
    					{ //determines Education Needed

    						if(descriptArray[1].contains("Associate"))
    						{
    							results[i][10] = "Associate's";
    						}
    						else if(descriptArray[1].contains("Bachelor"))
    						{
    							results[i][10] = "Bachelor's";
    						}
    						else if(descriptArray[1].contains("Master"))
    						{
    							results[i][10] = "Master's";
    						}
    						else if(descriptArray[1].contains("Doctorate"))
    						{
    							results[i][10] = "Doctorate's";
    						}
    						else if(descriptArray[1].contains("training"))
    						{
    							results[i][10] = "Training Provided";
    						}
    						else
    						{
    							results[i][10] = "Unspecified";
    						}
    						//	keyword and abilities search: java, c++, python
    						

    						results[i][7] = descriptArray[0];
    					}
    					
        		allJobNames = new String[results.length];
                for(int r = 0; r < results.length; r++)
                {
                	allJobNames[r] =  results[r][0];
                }
    		}
    	}
    	catch(Exception err)
		{
    		String temp = "Request error ==> "+ err;
    		Toast.makeText(current, temp, Toast.LENGTH_LONG).show();
		}
			return null;
    }
    			
    			
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
        	
    		update();
    		}
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		// Handle action bar item clicks here. The action bar will
				// automatically handle clicks on the Home/Up button, so long
				// as you specify a parent activity in AndroidManifest.xml.
				switch (item.getItemId()) 
				{
		        case android.R.id.home:
		            // back icon in action bar clicked; goto parent activity.
		        	Intent parentIntent1 = new Intent(this,MainActivity.class);
				      startActivity(parentIntent1);
		            return true;
		        case R.id.action_settings :
		        	return true;
		        case R.id.reset :
		        	
		        	final AlertDialog.Builder builder = new AlertDialog.Builder(this);
					builder.setIcon(R.drawable.ic_launcher);
					builder.setTitle("Job Search Terms");
					builder.setMessage("What Job Title are you searching for?");
					//Set an EditText view to get user input
					final EditText input = new EditText(this);
					builder.setView(input);
					
					builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							
								query = input.getText().toString();
								cityAlertBuilder();
						}
					});
					
					builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which){
						}
					});
					builder.create().show();
					return true;
				}
				return true; 
	}

	
	protected void cityAlertBuilder() {
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setIcon(R.drawable.ic_launcher);
		builder.setTitle("Job Search Terms");
		builder.setMessage("Which City?");
		//Set an EditText view to get user input
		final EditText input = new EditText(this);
		builder.setView(input);
		
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
					city = input.getText().toString();
					String [] selectable = { "Alabama", "Alaska", "Arizona", "Arkansas",
					"California", "Colorado", "Connecticut", "Delaware", "Florida", "Georgia",
					"Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky",
					"Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota",
					"Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire",
					"New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota",
					"Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina",
					"South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington",
					"West Virginia", "Wisconsin", "Wyoming" };
					singleChoiceBuilder(selectable, "Choose State",count);
				
			}
		});
		
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which){
			}
		});
		builder.create().show();
	}

	private void singleChoiceBuilder(final String[] selectableItems, String title, int count) 
	{//creates pop up AlertDialog for User Selection
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setIcon(R.drawable.ic_launcher);
        builder.setItems(selectableItems, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				state = selectableItems[which];

				String country = "us",
						start = "0";
				createUrlStream(query, city, state, jobType,
						country, start,  fromage );
				try{
					numberedResults = 25;
					new ReadApi().execute(urlStr);
				}
				catch(Exception error)
				{
					throw error;
				}
			}} );
        
        AlertDialog dialog = builder.create();//AlertDialog dialog; create like this outside onClick
        dialog.show();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		// Associate searchable configuration with the SearchView
        SearchManager searchManager = 
        		(SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem mSearchMenuItem = menu.findItem(R.id.search);
       SearchView  mSearchView = (SearchView) MenuItemCompat.getActionView(mSearchMenuItem);
       mSearchView.setSearchableInfo(searchManager.getSearchableInfo(
               new ComponentName(getApplicationContext(), SearchResultsActivity.class)));
       // Load data from list to cursor
       String[] columns = new String[] { "_id", "text" };
       Object[] temp = new Object[] { 0, "default" };

       MatrixCursor cursor = new MatrixCursor(columns);

       for(int i = 0; i < searchItems.size(); i++) {

           temp[0] = i;
           temp[1] = searchItems.get(i);

           cursor.addRow(temp);
       
       }
       mSearchView.setSuggestionsAdapter(new MyCursor(this, cursor, searchItems));
		return true;
	}

	}




