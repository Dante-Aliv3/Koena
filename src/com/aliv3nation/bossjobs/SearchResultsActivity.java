package com.aliv3nation.bossjobs;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aliv3nation.beingboss.R;

public class SearchResultsActivity extends ActionBarActivity  implements OnClickListener
{
	private DrawerLayout drawerLayout;
	private ListView listView;
	private String[ ] allRecordsNames;
	private static String[ ] allRecordFields, educationOptions, stressSet,
	skillSet, moneyOptions, locationOptions, differentHours;
	private static String[ ] incomeOptions = {"Active", "Passive"};
	static ArrayList <Record> records;
	static int next; // control variable for submit button
	static final int DATABASE_VERSION_NUMBER = 1;
	int recordsSize, salaryRange, length = 0;
	MovementMethod move;
	AlertDialog dialog;
	Locale ENGLISH = new Locale("en", "US");
	Record floatingRecord = new Record();// virtual Record for comparing values
	ArrayList <String> searchItems;
	
	RelativeLayout salary;
	Button fieldIcon, educationIcon, activeIcon,
		   salaryIcon,scalesIcon, skillIcon,
		   stressIcon, timeIcon, groupIcon,
		   submit;
	
	TextView title, jobField , positionDescription, expectedCost, 
			 entrySalary, midCareerSalary, seniorSalary,
			 StressLevel, investedTime, educationLevel,
			 Skill, MoneySource, JobLocation, jobType,
			 pros, cons,jobOutlook, mainLink, secondaryLink, usefulLink,
			 it_positionDescription, it_salary, it_Education,
			 it_jobType, it_prosCons, it_links,
			 activePassive, educationNeeded;
	
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_search);
    	connectXML();
    	getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    	listView = (ListView) findViewById(R.id.drawerList);
        if(Database.records.isEmpty())//loads CSV Database into memory
    	{
    		AssetManager manager = getAssets();
    		try 
    		{
				Database.creation(manager,getApplicationContext());
			} catch (Exception e) 
    		{
				Toast.makeText(getApplicationContext(), "Error, Please Re-install App.", Toast.LENGTH_LONG).show();
			}
    	}
        
        //checks for when a new Activity is created(redrawn)
        if(savedInstanceState != null){
        	records = new ArrayList(savedInstanceState.getParcelableArrayList("records"));
        }
        else
        {
        	records = new ArrayList<Record>(Database.records);
        }

        allRecordsNames = Database.allRecords();
        searchItems = new ArrayList<String>(allRecordsNames.length);
		for(int i = 0; i < allRecordsNames.length; i++)
			searchItems.add(allRecordsNames[i]);
		{
			//SQL Database Statements
		}
		
		//Returns user search input
		Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) 
        {
          String query = 
        		  intent.getStringExtra(SearchManager.QUERY);
          searchDB(query);
        }
        
        
    	
    }

    private void connectXML() 
    {//processes all the Views in Activity - connecting frontend to backend code
    	title = (TextView) findViewById(R.id.title);
    	jobField = (TextView) findViewById(R.id.jobField);
    	positionDescription = (TextView) findViewById(R.id.positionDescription);
    	entrySalary = (TextView) findViewById(R.id.entrySalary);
    	midCareerSalary = (TextView) findViewById(R.id.midCareerSalary);
    	seniorSalary = (TextView) findViewById(R.id.seniorSalary);
    	StressLevel = (TextView) findViewById(R.id.StressLevel);
    	investedTime = (TextView) findViewById(R.id.investedTime);
    	educationLevel = (TextView) findViewById(R.id.educationLevel);
    	Skill = (TextView) findViewById(R.id.Skill);
    	expectedCost = (TextView) findViewById(R.id.expectedCost);
    	MoneySource = (TextView) findViewById(R.id.MoneySource);
    	JobLocation = (TextView) findViewById(R.id.JobLocation);
    	jobType = (TextView) findViewById(R.id.jobType);
    	activePassive = (TextView) findViewById(R.id.activePassive);
    	educationNeeded = (TextView) findViewById(R.id.educationNeeded2);
    	pros = (TextView) findViewById(R.id.pros);
    	cons = (TextView) findViewById(R.id.cons);
    	jobOutlook = (TextView) findViewById(R.id.jobOutlook);
    	mainLink = (TextView) findViewById(R.id.mainLink);
    	secondaryLink = (TextView) findViewById(R.id.secondaryLink);
    	usefulLink = (TextView) findViewById(R.id.usefulLink);
    	it_positionDescription = (TextView) findViewById(R.id.it_positionDescription);
    	it_salary = (TextView) findViewById(R.id.it_salary);
    	it_Education = (TextView) findViewById(R.id.it_Education);
		it_jobType = (TextView) findViewById(R.id.it_jobType);
		it_prosCons = (TextView) findViewById(R.id.it_prosCons);
		it_links = (TextView) findViewById(R.id.it_links);
    	drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
    	submit = (Button) findViewById(R.id.submit);
    	
    	fieldIcon = (Button) findViewById(R.id.fieldIcon);
    	educationIcon = (Button) findViewById(R.id.educationIcon);
    	activeIcon = (Button) findViewById(R.id.activeIcon);
		salaryIcon = (Button) findViewById(R.id.salaryIcon);
		scalesIcon = (Button) findViewById(R.id.scalesIcon);
		skillIcon = (Button) findViewById(R.id.skillIcon);
		stressIcon = (Button) findViewById(R.id.stressIcon);
		timeIcon = (Button) findViewById(R.id.timeIcon);
		groupIcon = (Button) findViewById(R.id.groupIcon);
		
		salary  = (RelativeLayout) findViewById(R.id.salary);
		move = LinkMovementMethod.getInstance();
		mainLink.setMovementMethod(move);
		secondaryLink.setMovementMethod(move);
		usefulLink.setMovementMethod(move);
		it_positionDescription.setOnClickListener(this);
		it_salary.setOnClickListener(this);
		it_Education.setOnClickListener(this);
		it_jobType.setOnClickListener(this);
		it_prosCons.setOnClickListener(this);
		it_links.setOnClickListener(this);
    	fieldIcon.setOnClickListener(this);
    	educationIcon.setOnClickListener(this);
    	activeIcon.setOnClickListener(this);
    	salaryIcon.setOnClickListener(this);
    	scalesIcon.setOnClickListener(this);
    	skillIcon.setOnClickListener(this);
    	stressIcon.setOnClickListener(this);
    	timeIcon.setOnClickListener(this);
    	groupIcon.setOnClickListener(this);
	}

	private void searchDB(String query) 
    {//sets initial values for activity
    	

        //sets Database Size as well as Record Fields
        recordsSize = records.size();
        allRecordFields = records.get(0).getFields();
        educationOptions = records.get(0).geteducationOptions();
    	stressSet = records.get(0).getstressSet();
    	skillSet = records.get(0).getskillSet();
    	moneyOptions = records.get(0).getmoneyOptions();
    	locationOptions = records.get(0).getlocationOptions();
    	differentHours = records.get(0).getAllHours();
        next = 1;
		
		//sets text for Layout Drawer
        length = records.size();
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, allRecordsNames));
        listView.setOnItemClickListener(new OnItemClickListener() 
    	{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) 
			{	
				if(position < length)
				{
					next = position + 1;
					title.setText(records.get(position).title);
			    	jobField.setText(records.get(position).getJobField());
			    	positionDescription.setText(records.get(position).positionDescription);
			    	entrySalary.setText(records.get(position).getEntrySalary());
			    	midCareerSalary.setText(records.get(position).getMidCareerSalary());
			    	seniorSalary.setText(records.get(position).getSeniorSalary());
			    	StressLevel.setText(records.get(position).getStressLevel(true));
			    	investedTime.setText(records.get(position).getinvestedTime());
			    	educationLevel.setText(records.get(position).educationLevel);
			    	Skill.setText(records.get(position).getSkill(true));
			    	expectedCost.setText("Expected Cost: " + NumberFormat.getCurrencyInstance(
			    			new Locale("en", "US")).format(records.get(position).expectedCost));
			    	MoneySource.setText(records.get(position).getMoneySource(true));
			    	JobLocation.setText(records.get(position).getJobLocation(true));
			    	jobType.setText(records.get(position).jobType);
			    	activePassive.setText(records.get(position).getActivePassive());
			    	educationNeeded.setText(records.get(position).displayEducation());
			    	String[] prosCons = records.get(position).getprosCons();
			    	pros.setText(prosCons[0]);
			    	cons.setText(prosCons[1]);
			    	jobOutlook.setText("Job Outlook: " + String.valueOf(records.get(position).jobOutlook) + "% Growth");
			    	
			    	//Sets external Web HyperLinks
			    	String link = "<a href='" + records.get(position).mainLink + "'> " + records.get(position).title + " I</a>";
			    	mainLink.setText(Html.fromHtml(link));
			    	link = "<a href='" + records.get(position).secondaryLink + "'> " + records.get(position).title + " II</a>";
			    	secondaryLink.setText(Html.fromHtml(link));
			    	link = "<a href='" + records.get(position).usefulLink + "'> Getting Started </a>";
			    	usefulLink.setText(Html.fromHtml(link));
				}
				else
					Toast.makeText(getApplicationContext(), "Invalid Selection, Try Again", Toast.LENGTH_SHORT).show();
				drawerLayout.closeDrawers();
			}
        });
        
    	/*** checks and initializes Database***/
    	if(Database.records.isEmpty())//loads CSV Database into memory
    	{
    		AssetManager manager = getAssets();
    		try {
				Database.creation(manager,getApplicationContext());
			} catch (Exception e) 
    		{
				Toast.makeText(getApplicationContext(), "Error, Please Re-install App.", Toast.LENGTH_LONG).show();
			}
    	}
    	
    	title.setTypeface(null, Typeface.BOLD_ITALIC);
    	educationNeeded.setTypeface(null, Typeface.ITALIC);
    	title.setText(records.get(0).title);
    	jobField.setText(records.get(0).getJobField());
    	positionDescription.setText(records.get(0).positionDescription);
    	entrySalary.setText(records.get(0).getEntrySalary());
    	midCareerSalary.setText(records.get(0).getMidCareerSalary());
    	seniorSalary.setText(records.get(0).getSeniorSalary());
    	StressLevel.setText(records.get(0).getStressLevel(true));
    	investedTime.setText(records.get(0).getinvestedTime());
    	educationLevel.setText(records.get(0).educationLevel);
    	Skill.setText(records.get(0).getSkill(true));
    	expectedCost.setText("Expected Cost: " + NumberFormat.getCurrencyInstance(
    			new Locale("en", "US")).format(records.get(0).expectedCost));
    	MoneySource.setText(records.get(0).getMoneySource(true));
    	JobLocation.setText(records.get(0).getJobLocation(true));
    	jobType.setText(records.get(0).jobType);
    	activePassive.setText(records.get(0).getActivePassive());
    	educationNeeded.setText(records.get(0).displayEducation());
    	String[] prosCons = records.get(0).getprosCons();
    	pros.setText(prosCons[0]);
    	cons.setText(prosCons[1]);
    	jobOutlook.setText("Job Outlook: " + String.valueOf(records.get(0).jobOutlook) + "% Growth");
    	
    	//Sets external Web HyperLinks
    	String link = "<a href='" + records.get(0).mainLink + "'> " + records.get(0).title + " I</a>";
    	mainLink.setText(Html.fromHtml(link));
    	link = "<a href='" + records.get(0).secondaryLink + "'> " + records.get(0).title + " II</a>";
    	secondaryLink.setText(Html.fromHtml(link));
    	link = "<a href='" + records.get(0).usefulLink + "'> Getting Started </a>";
    	usefulLink.setText(Html.fromHtml(link));
    	
    	submit.setOnClickListener(this);    	
    	drawerLayout.openDrawer(listView);
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
	
	@SuppressWarnings("unused")
	private void showAlert() 
	{
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		dialogBuilder.setTitle("Database");
		dialogBuilder.setMessage("Entering Database");
		dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener()
		{

			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				Toast.makeText(null, "You CLicked OK", Toast.LENGTH_LONG).show();
				
			}
			
		});
		AlertDialog alertDialog = dialogBuilder.create();
		alertDialog.show();
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
        	update(Database.records);
        	return true;
        default:
            return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
    protected void onNewIntent(Intent intent) 
    {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) 
    {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) 
        {
            String query = intent.getStringExtra(SearchManager.QUERY);
            records = new ArrayList<Record>(Database.records);
            searchDB(query);
    	}
    }

	@Override
	public void onClick(View v) {
		String s = "";
		switch(v.getId())
		{
			case (R.id.submit): 
				if(next < recordsSize)
				{
					
					title.setText(records.get(next).title);
			    	jobField.setText(records.get(next).getJobField());
			    	positionDescription.setText(records.get(next).positionDescription);
			    	entrySalary.setText(records.get(next).getEntrySalary());
			    	midCareerSalary.setText(records.get(next).getMidCareerSalary());
			    	seniorSalary.setText(records.get(next).getSeniorSalary());
			    	StressLevel.setText(records.get(next).getStressLevel(true));
			    	investedTime.setText(records.get(next).getinvestedTime());
			    	educationLevel.setText(records.get(next).educationLevel);
			    	Skill.setText(records.get(next).getSkill(true));
			    	expectedCost.setText("Expected Cost: " + NumberFormat.getCurrencyInstance(
			    			new Locale("en", "US")).format(records.get(next).expectedCost));
			    	MoneySource.setText(records.get(next).getMoneySource(true));
			    	JobLocation.setText(records.get(next).getJobLocation(true));
			    	jobType.setText(records.get(next).jobType);
			    	activePassive.setText(records.get(next).getActivePassive());
			    	educationNeeded.setText(records.get(next).displayEducation());
			    	String[] prosCons = records.get(next).getprosCons();
			    	pros.setText(prosCons[0]);
			    	cons.setText(prosCons[1]);
			    	jobOutlook.setText("Job Outlook: " + String.valueOf(records.get(next).jobOutlook) + "% Growth");
			    	
			    	//Sets external Web HyperLinks
			    	String link = "<a href='" + records.get(next).mainLink + "'> " + records.get(next).title + " I</a>";
			    	mainLink.setText(Html.fromHtml(link));
			    	link = "<a href='" + records.get(next).secondaryLink + "'> " + records.get(next).title + " II</a>";
			    	secondaryLink.setText(Html.fromHtml(link));
			    	link = "<a href='" + records.get(next).usefulLink + "'> Getting Started </a>";
			    	usefulLink.setText(Html.fromHtml(link));
					next++;
				}
				break;
				
			case (R.id.groupIcon): 
				s = "Pick Where You Want to Work";
				singleChoiceBuilder(locationOptions, s, v.getId());
				break;
				
			case (R.id.timeIcon): 
				s = "Search By Hours:";
				singleChoiceBuilder(differentHours, s, v.getId());
				break;
				
			case (R.id.stressIcon): 
				s = "Search By Stress Level:";
				singleChoiceBuilder(stressSet, s, v.getId());
				break;
				
			case (R.id.skillIcon): 
				s = "Search By Skill Level:";
				singleChoiceBuilder(skillSet, s, v.getId());
				break;
				
			case (R.id.scalesIcon): 
				s = "Select Income Options:";
				singleChoiceBuilder(moneyOptions, s, v.getId());
				break;
				
			case (R.id.salaryIcon): 
				showDialog();
				break;
				
			case (R.id.activeIcon): 
				s = "Choose Active or Passive:";
				singleChoiceBuilder(incomeOptions, s, v.getId());
				break;		
					
			case (R.id.educationIcon): 
				s = "Choose Education Level:";
				singleChoiceBuilder(educationOptions, s, v.getId());
				break;		
				
			case (R.id.fieldIcon): 
				s = "Choose Job Field:";
				singleChoiceBuilder(allRecordFields, s, v.getId());
				break;
			
			case (R.id.it_positionDescription): 
				if(positionDescription.getVisibility() == View.VISIBLE)
					positionDescription.setVisibility(View.GONE);
				else
					positionDescription.setVisibility(View.VISIBLE);
				break;
			
			case (R.id.it_salary): 
				if(salary.getVisibility() == View.VISIBLE)
					salary.setVisibility(View.GONE);
				else
					salary.setVisibility(View.VISIBLE);
				break;
				
			case (R.id.it_Education): 
				if(educationLevel.getVisibility() == View.VISIBLE)
				{
					educationNeeded.setVisibility(View.GONE);
					educationLevel.setVisibility(View.GONE);
				}
				else
				{
					educationNeeded.setVisibility(View.VISIBLE);
					educationLevel.setVisibility(View.VISIBLE);
				}
				break;
				
			case (R.id.it_jobType): 
				if(jobType.getVisibility() == View.VISIBLE)
					jobType.setVisibility(View.GONE);
				else
					jobType.setVisibility(View.VISIBLE);
				break;
				
			case (R.id.it_prosCons): 
				if(pros.getVisibility() == View.VISIBLE)
				{
					pros.setVisibility(View.GONE);
					cons.setVisibility(View.GONE);
				}
				else
				{
					pros.setVisibility(View.VISIBLE);
					cons.setVisibility(View.VISIBLE);
				}
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

	public void showDialog()
	{//creates custom AlertDialog for Salary Search
		final AlertDialog.Builder popDialog = new AlertDialog.Builder(this);
		final LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
		final View Viewlayout = inflater.inflate(R.layout.activity_dialog,
				(ViewGroup) findViewById(R.id.layout_dialog));      
		
		final TextView item1 = (TextView)Viewlayout.findViewById(R.id.txtItem1); // txtItem1
		final TextView item2 = (TextView)Viewlayout.findViewById(R.id.txtItem2); // txtItem2
		final TextView item3 = (TextView)Viewlayout.findViewById(R.id.txtItem3); // txtItem3
		final RadioButton entryButton = (RadioButton) Viewlayout.findViewById(R.id.entryLevel_button); // Radio Button 1
		final RadioButton midButton = (RadioButton) Viewlayout.findViewById(R.id.median_button); // Radio Button 2
		final RadioButton seniorButton = (RadioButton) Viewlayout.findViewById(R.id.seniorLevel_button); // Radio Button 3
		entryButton.setClickable(false);
		midButton.setClickable(false);
		seniorButton.setClickable(false);
		popDialog.setIcon(R.drawable.ic_launcher);
		popDialog.setTitle("Choose a Salary Range: ");
		popDialog.setView(Viewlayout);
		
		//  seekBar1
		final SeekBar seek1 = (SeekBar) Viewlayout.findViewById(R.id.seekBar1);
		seek1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			int i = 0;
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
				i = progress;
				while(!(i%1000 == 0))
					i++;
			item1.setText("Entry Level Salary : " + NumberFormat.getCurrencyInstance(ENGLISH).format(i));
			salaryRange = i;
			}
		
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
			}
		
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}
		});
		
		//  seekBar2
		final SeekBar seek2 = (SeekBar) Viewlayout.findViewById(R.id.seekBar2);
		seek2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			int i = 0;
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
				i = progress;
				while(!(i%1000 == 0))
					i++;
				item2.setText("Median Salary : " + NumberFormat.getCurrencyInstance(ENGLISH).format(i));
				salaryRange = i;
			}
		
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}
		});
		
		//  seekBar3
		final SeekBar seek3 = (SeekBar) Viewlayout.findViewById(R.id.seekBar3);
		seek3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			int i = 0;
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
				i = progress;
				while(!(i%1000 == 0))
					i++;
				item3.setText("Senior Salary : " + NumberFormat.getCurrencyInstance(ENGLISH).format(i));
				salaryRange = i;
			}
		
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}
		});
		
		seek1.setEnabled(true);
		seek2.setEnabled(false);
		seek3.setEnabled(false);
		entryButton.setChecked(true);
		
		entryButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				seek2.setProgress(0);
				seek3.setProgress(0);
				seek1.setEnabled(true);
				seek2.setEnabled(false);
				seek3.setEnabled(false);
				midButton.setChecked(false);
				seniorButton.setChecked(false);
			}});
		
		midButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) { 
				seek1.setProgress(0);
				seek3.setProgress(0);
				seek1.setEnabled(false);
				seek2.setEnabled(true);
				seek3.setEnabled(false);
				entryButton.setChecked(false);
				seniorButton.setChecked(false);
			}});
		
		seniorButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) { 
				seek1.setProgress(0);
				seek2.setProgress(0);
				seek1.setEnabled(false);
				seek2.setEnabled(false);
				seek3.setEnabled(true);
				midButton.setChecked(false);
				entryButton.setChecked(false);
			}});
		
		// OK Button
		popDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		
			@Override
			public void onClick(DialogInterface dialog, int which) {
				ArrayList <Record> newRecords = new ArrayList<Record>(100);
				
				if(seek1.isEnabled()){
					String s = "entrySalary";
					newRecords = Database.compileSalary(s, salaryRange);					
				}else if(seek2.isEnabled()){
					String s = "midCareerSalary";
					newRecords = Database.compileSalary(s, salaryRange);					
				}else{
					String s = "seniorSalary";
					newRecords = Database.compileSalary(s, salaryRange);					
				}
				update(newRecords);
				dialog.dismiss();
			}
		});

		// CANCEL Button
		popDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
		
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		
		popDialog.create();
		popDialog.show();
	}	
	
	private void singleChoiceBuilder(final String[] selectableItems, String title, int id ) 
	{//creates pop up AlertDialog for User Selection
		final int view = id;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setIcon(R.drawable.ic_launcher);
        builder.setItems(selectableItems, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				ArrayList <Record> newRecords = new ArrayList<Record>(100);
				switch(view)
				{
				
					case (R.id.groupIcon): 
						floatingRecord.setJobLocation(which);
						newRecords = Database.compileHomeOffice(floatingRecord.homeOffice);
						update(newRecords);
						break;
						
					case (R.id.timeIcon): 
						floatingRecord.setHours(++which);
						newRecords = Database.compileHours(floatingRecord.fullPartTime);
						update(newRecords);
						break;
						
					case (R.id.stressIcon): 
						floatingRecord.setStressLevel(++which);
						newRecords = Database.compileStressLevel(floatingRecord.stressLevel);
						update(newRecords);
						break;
						
					case (R.id.skillIcon): 
						floatingRecord.setSkill(++which);
						newRecords = Database.compileSkillLevel(floatingRecord.skillLevel);
						update(newRecords);
						break;
						
					case (R.id.scalesIcon): 
						floatingRecord.setMoneySource(selectableItems[which]);
						newRecords = Database.compileCareerHobby(floatingRecord.careerHobby);
						update(newRecords);
						break;
						
					case (R.id.salaryIcon): 
						Toast.makeText(getApplicationContext(), "Salary Icon", Toast.LENGTH_LONG).show();
						break;
						
					case (R.id.activeIcon): 
						if(which == 0)
							floatingRecord.isPassive = false;
						else
							floatingRecord.isPassive = true;
						newRecords = Database.compileActivePassive(floatingRecord.isPassive);
						update(newRecords);
						break;
						
					case (R.id.educationIcon): 
						floatingRecord.setEducation(++which);
						newRecords = Database.compileEducationSet(floatingRecord.educationNum);
						update(newRecords);
						break;		
						
					case (R.id.fieldIcon): 
						floatingRecord.setjobField(selectableItems[which]);
						newRecords = Database.compileJobField(floatingRecord.jobField);
						update(newRecords);
						break;
				}
			}} );
        
        dialog = builder.create();//AlertDialog dialog; create like this outside onClick
        dialog.show();
		
	}

	private void update(ArrayList<Record> newRecords) {
		//updates text for Layout Drawer
        
        if(!(newRecords.isEmpty()))
        {   
        	length =  newRecords.size();
        	records = new ArrayList<Record>(newRecords);
            allRecordsNames = new String[length];
            for(int i = 0; i < length; i++)
            {
            	allRecordsNames[i] = records.get(i).title;
            }
            recordsSize = records.size();
            next = 1;
            listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, allRecordsNames));
	    	title.setText(records.get(0).title);
	    	jobField.setText(records.get(0).getJobField());
	    	positionDescription.setText(records.get(0).positionDescription);
	    	entrySalary.setText(records.get(0).getEntrySalary());
	    	midCareerSalary.setText(records.get(0).getMidCareerSalary());
	    	seniorSalary.setText(records.get(0).getSeniorSalary());
	    	StressLevel.setText(records.get(0).getStressLevel(true));
	    	investedTime.setText(records.get(0).getinvestedTime());
	    	educationLevel.setText(records.get(0).educationLevel);
	    	Skill.setText(records.get(0).getSkill(true));
	    	expectedCost.setText("Expected Cost: " + NumberFormat.getCurrencyInstance(
	    			new Locale("en", "US")).format(records.get(0).expectedCost));
	    	MoneySource.setText(records.get(0).getMoneySource(true));
	    	JobLocation.setText(records.get(0).getJobLocation(true));
	    	jobType.setText(records.get(0).jobType);
	    	activePassive.setText(records.get(0).getActivePassive());
	    	educationNeeded.setText(records.get(0).displayEducation());
	    	String[] prosCons = records.get(0).getprosCons();
	    	pros.setText(prosCons[0]);
	    	cons.setText(prosCons[1]);
	    	jobOutlook.setText("Job Outlook: " + String.valueOf(records.get(0).jobOutlook) + "% Growth");
	    	
	    	//Sets external Web HyperLinks
	    	String link = "<a href='" + records.get(0).mainLink + "'> " + records.get(0).title + " I</a>";
	    	mainLink.setText(Html.fromHtml(link));
	    	link = "<a href='" + records.get(0).secondaryLink + "'> " + records.get(0).title + " II</a>";
	    	secondaryLink.setText(Html.fromHtml(link));
	    	link = "<a href='" + records.get(0).usefulLink + "'> Getting Started </a>";
	    	usefulLink.setText(Html.fromHtml(link));
	    	    	
	    	drawerLayout.openDrawer(listView);   
		}
        else
        {
        	Toast.makeText(getApplicationContext(), "Selection Not Found", Toast.LENGTH_LONG).show();
        }
	}

	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState)
	{
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putParcelableArrayList("records", records);
	}
}