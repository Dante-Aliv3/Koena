package com.aliv3nation.bossjobs;

import java.util.ArrayList;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aliv3nation.beingboss.R;

public class MainActivity extends ActionBarActivity 
{
	AlertDialog dialog;
	public static final String MyPREFERENCES = "MyPrefs";
	SharedPreferences sharedpreferences;
	Editor editor;
	ImageButton favorites;
	ImageButton search_preferences;
	ArrayList <String> searchItems;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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
		//searchItems  = new ArrayList<String>(Record.getQuery());
		String [] temp = Database.allRecords();
		searchItems = new ArrayList<String>(temp.length);
		for(int i = 0; i < temp.length; i++)
			searchItems.add(temp[i]);
        
		TextView textView = (TextView) findViewById(R.id.textview);
		ImageView demoImage = (ImageView) findViewById(R.id.slideshow);
		int imagesToShow[] = { R.drawable.bussinesswomancomputer, R.drawable.familynbed, R.drawable.women_architect };
		String textToShow[] = {
				"Educate the Mind to Think,"
				+ " the heart to feel,"
				+ " the body to act.",
				"Dream as if you will live forever, Live as if you will die tomorrow.",
				"There is no elevator to sucess,"
				+ " You have to take the stairs",};
		animate(demoImage, imagesToShow, 0,true);
		animation(textView, textToShow, 0, true);
		search();
		
		//Collects Shared Preferences or initializes if there are none
		sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		editor = sharedpreferences.edit();
		if(!(sharedpreferences.contains("isInitiliazed")))
		{
			editor.putString("isInitiliazed", "true");
			editor.putString("jobField", "general labor");
			editor.putString("careerHobby", "career"); 
			editor.putString("activePassive", "active");
			editor.putInt("entrySalary", 30000);
			editor.putInt("midCareerSalary", 50000);
			editor.putInt("seniorSalary", 70000);
			editor.putInt("stressLevel", 3);
			editor.putInt("educationNum", 4);
			editor.putInt("skillLevel", 1);
			editor.putInt("homeOffice", 1);
			editor.putInt("fullPartTime", 1);
			editor.commit();
		}
		
		Intent ishintent = new Intent(this, BackgroundService.class);
	    PendingIntent pintent = PendingIntent.getService(this, 0, ishintent, 0);
	    AlarmManager alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
	    alarm.cancel(pintent);
	    alarm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),5000, pintent);
		
		String TABLENAME = "IntentService";
		SQLiteDatabase apiDatabase = openOrCreateDatabase("Intent_Service.db", MODE_PRIVATE,null);
		String KEY_ATTEMPT_NUMBER = "KEY_ATTEMPT_NUMBER";
		String CREATE_JOBS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLENAME + " ( "
				+ KEY_ATTEMPT_NUMBER + " INTEGER PRIMARY KEY );";
		apiDatabase.execSQL(CREATE_JOBS_TABLE);
		Cursor cursor = apiDatabase.query(
				TABLENAME, null, null, null, null, null, null);
		if (cursor != null)
		{Toast.makeText(getApplicationContext(), "cursor != null ", Toast.LENGTH_LONG).show();
			if(cursor.moveToFirst())
				{
				Toast.makeText(getApplicationContext(), "Success Record Saved!", Toast.LENGTH_LONG).show();
				String temp2 = "Value Saved >>> " + cursor.getString(cursor.getColumnIndex(KEY_ATTEMPT_NUMBER));
				Toast.makeText(getApplicationContext(), temp2, Toast.LENGTH_LONG).show();
				}
		}
		
		favorites = (ImageButton) findViewById(R.id.favorites);
		favorites.setOnClickListener( new View.OnClickListener() 
		{  
			@Override
			public void onClick(View v) 
			{
				newActvity();
			} 
		});
		search_preferences = (ImageButton) findViewById(R.id.search_preferences);
		search_preferences.setOnClickListener( new View.OnClickListener() 
		{  
			@Override
			public void onClick(View v) 
			{
				//Toast.makeText(getApplicationContext(), "Nothing...", Toast.LENGTH_LONG).show();
				newActivity();
			} 
		});
	}

	protected void newActvity() {
		Intent career = new Intent(this, CareerActivity.class);
		this.startActivity(career);
	}

	protected void newActivity() {
		Intent jobDatabase = new Intent(this, JobActivity.class);
		this.startActivity(jobDatabase);
	}
	
	private void search()
	{
		//following code will be in your activity.java file 
		
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
       final SearchView  mSearchView = (SearchView) MenuItemCompat.getActionView(mSearchMenuItem);
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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) 
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

		  private void animate(final ImageView imageView, final int images[], final int imageIndex, final boolean forever) {

		  //imageView <-- The View which displays the images
		  //images[] <-- Holds R references to the images to display
		  //imageIndex <-- index of the first image to show in images[] 
		  //forever <-- If equals true then after the last image it starts all over again with the first image resulting in an infinite loop. You have been warned.

		    int fadeInDuration = 500; // Configure time values here
		    int timeBetween = 3000;
		    int fadeOutDuration = 1000;

		    imageView.setVisibility(View.INVISIBLE);    //Visible or invisible by default - this will apply when the animation ends
		    imageView.setImageResource(images[imageIndex]);

		    Animation fadeIn = new AlphaAnimation(0, 1);
		    fadeIn.setInterpolator(new DecelerateInterpolator()); // add this
		    fadeIn.setDuration(fadeInDuration);

		    Animation fadeOut = new AlphaAnimation(1, 0);
		    fadeOut.setInterpolator(new AccelerateInterpolator()); // and this
		    fadeOut.setStartOffset(fadeInDuration + timeBetween);
		    fadeOut.setDuration(fadeOutDuration);

		    AnimationSet animation = new AnimationSet(false); // change to false
		    animation.addAnimation(fadeIn);
		    animation.addAnimation(fadeOut);
		    animation.setRepeatCount(1);
		    imageView.setAnimation(animation);

		    animation.setAnimationListener(new AnimationListener() 
		    {
		        @Override
				public void onAnimationEnd(Animation animation) 
		        {
		            if (images.length - 1 > imageIndex) 
		            {
		                animate(imageView, images, imageIndex + 1,forever); //Calls itself until it gets to the end of the array
		            }
		            else {
		                if (forever == true)
		                {
		                animate(imageView, images, 0,forever);  //Calls itself to start the animation all over again in a loop if forever = true
		                }
		            }
		        }
		        @Override
				public void onAnimationRepeat(Animation animation) 
		        {
		            // TODO Auto-generated method stub
		        }
		        @Override
				public void onAnimationStart(Animation animation) 
		        {
		            // TODO Auto-generated method stub
		        }
		    });
	}
		  
			private void animation(final TextView textView, final String[] textToShow, final int textIndex, final boolean forever) {

				  //imageView <-- The View which displays the images
				  //images[] <-- Holds R references to the images to display
				  //imageIndex <-- index of the first image to show in images[] 
				  //forever <-- If equals true then after the last image it starts all over again with the first image resulting in an infinite loop. You have been warned.

				    int fadeInDuration = 500; // Configure time values here
				    int timeBetween = 3025;
				    int fadeOutDuration = 1000;

				    textView.setVisibility(View.INVISIBLE);    //Visible or invisible by default - this will apply when the animation ends
				    textView.setText(textToShow[textIndex]);

				    Animation fadeIn = new AlphaAnimation(0, 1);
				    fadeIn.setInterpolator(new DecelerateInterpolator()); // add this
				    fadeIn.setDuration(fadeInDuration);

				    Animation fadeOut = new AlphaAnimation(1, 0);
				    fadeOut.setInterpolator(new AccelerateInterpolator()); // and this
				    fadeOut.setStartOffset(fadeInDuration + timeBetween);
				    fadeOut.setDuration(fadeOutDuration);

				    AnimationSet animation = new AnimationSet(false); // change to false
				    animation.addAnimation(fadeIn);
				    animation.addAnimation(fadeOut);
				    animation.setRepeatCount(1);
				    textView.setAnimation(animation);
				    textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
				    animation.setAnimationListener(new AnimationListener() {
				        @Override
						public void onAnimationEnd(Animation animation) {
				            if (textToShow.length - 1 > textIndex) {
				                animation(textView, textToShow, textIndex + 1,forever); //Calls itself until it gets to the end of the array
				            }
				            else {
				                if (forever == true){
				                animation(textView, textToShow, 0,forever);  //Calls itself to start the animation all over again in a loop if forever = true
				                }
				            }
				        }
				        @Override
						public void onAnimationRepeat(Animation animation) {
				            // TODO Auto-generated method stub
				        }
				        @Override
						public void onAnimationStart(Animation animation) {
				            // TODO Auto-generated method stub
				        }
				    });
			}
		  
}

