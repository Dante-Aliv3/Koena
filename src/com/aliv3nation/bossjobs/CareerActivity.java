package com.aliv3nation.bossjobs;

import java.util.ArrayList;

import com.aliv3nation.beingboss.R;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.app.ActionBar.LayoutParams;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
import android.database.MatrixCursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CareerActivity extends ActionBarActivity 
{
	AlertDialog dialog;
	public static final String MyPREFERENCES = "MyPrefs";
	SharedPreferences sharedpreferences;
	Editor editor;
	EditText editText;
	ArrayList <String> searchItems;
	TextView careerLabel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_career);
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#482ECCFA")) );
    	getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    	editText = (EditText) findViewById(R.id.career_search);
    	careerLabel = (TextView) findViewById(R.id.careerLabel);
    	editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {


			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				careerLabel.setText(v.getText());
				Toast.makeText(getApplicationContext(), "onEditorAction Listener", Toast.LENGTH_LONG).show();
				return false;
			} 
		});
    	
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
		String [] temp = Database.allRecords();
		searchItems = new ArrayList<String>(temp.length);
		for(int i = 0; i < temp.length; i++)
			searchItems.add(temp[i]);
		//search();
        
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setIcon(R.drawable.ic_launcher);
			builder.setTitle("Job Search Terms");
			builder.setMessage("What are you searching for?");
			
			//Set an EditText view to get user input
			final EditText input = new EditText(this);
			builder.setView(input);
			
			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Toast.makeText(getApplicationContext(), "AlertDialog input = " + input.getText().toString(), Toast.LENGTH_LONG).show();
				}
			});
			
			builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which){
				}
			});
			builder.create().show();
			
		}
		//Collects Shared Preferences or initializes if there are none
		/**sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
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
		}**/
	}

	private void search()
	{
		//following code will be in your activity.java file 
		final CharSequence[] items = {" Easy "," Medium "," Hard "," Very Hard "};
		                // arraylist to keep the selected items
		                final ArrayList<Object> seletedItems = new ArrayList<>();
		               
		                AlertDialog.Builder builder = new AlertDialog.Builder(this);
		                builder.setTitle("Select The Difficulty Level");
		                builder.setMultiChoiceItems(items, null,
		                        new DialogInterface.OnMultiChoiceClickListener() 
		                {
		                 // indexSelected contains the index of item (of which checkbox checked)
		                 @Override
		                 public void onClick(DialogInterface dialog, int indexSelected,
		                         boolean isChecked) 
		                 {
		                     if (isChecked) 
		                     {
		                         // If the user checked the item, add it to the selected items
		                         // write your code when user checked the checkbox 
		                         seletedItems.add(indexSelected);
		                     } else if (seletedItems.contains(indexSelected)) 
		                     {
		                         // Else, if the item is already in the array, remove it 
		                         // write your code when user Uchecked the checkbox 
		                         seletedItems.remove(Integer.valueOf(indexSelected));
		                     }
		                 }
		             })
		              // Set the action buttons
		             .setPositiveButton("SET", new DialogInterface.OnClickListener() 
		             {
		                 @Override
		                 public void onClick(DialogInterface dialog, int id) 
		                 {
		                     //  Your code when user clicked on OK
		                     //  You can write the code  to save the selected item here
		                    
		                 }
		             })
		             .setNegativeButton("Cancel", new DialogInterface.OnClickListener() 
		             {
		                 @Override
		                 public void onClick(DialogInterface dialog, int id) 
		                 {
		                    //  Your code when user clicked on Cancel
		                   
		                 }
		             });
		       
		                dialog = builder.create();//AlertDialog dialog; create like this outside onClick
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
		        	return true;
		        default:
		            return super.onOptionsItemSelected(item);
				}
	}	  
}

