package com.aliv3nation.bossjobs;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class BackgroundService extends IntentService {

	int ATTEMPT_NUMBER = 0;
	
	public BackgroundService() {
		super("Koena");
		Toast.makeText(getApplicationContext(), "Running Background intent", Toast.LENGTH_SHORT).show();
		
		//Log.d("DEBUG", "Running Background intent");
		/**String MyPREFERENCES = "MyPrefs";
    	SharedPreferences sharedpreferences;
    	Editor editor;
    	sharedpreferences = getApplicationContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		editor = sharedpreferences.edit();
		editor.putString("Intent_Service", "itWorked_BackgroundService!!");
		editor.commit();**/
	}

	@Override
    public void onCreate() {
		//
		super.onCreate();
		SQLiteDatabase apiDatabase = getApplicationContext().openOrCreateDatabase("Intent_Service.db", Context.MODE_PRIVATE, null);
		String TABLENAME = "IntentService";
		String KEY_ATTEMPT_NUMBER = "KEY_ATTEMPT_NUMBER";
		ATTEMPT_NUMBER++;

		Toast.makeText(getApplicationContext(), "What is Love!", Toast.LENGTH_SHORT).show();
			String INSERT_NEW_RECORD = "INSERT OR IGNORE INTO " + TABLENAME 
					+" ( " + KEY_ATTEMPT_NUMBER + " ) "
					+ " VALUES ( " + ATTEMPT_NUMBER + " );";
			apiDatabase.execSQL(INSERT_NEW_RECORD);
    }
	
	
	@Override
    protected void onHandleIntent(Intent workIntent) {
	      // Normally we would do some work here, like download a file.
		Toast.makeText(getApplicationContext(), "Im Not Afraid!", Toast.LENGTH_SHORT).show();
		/**long endTime = System.currentTimeMillis() + 5*1000;
	      while (System.currentTimeMillis() < endTime) {
	          synchronized (this) {
	              try {
	                  wait(endTime - System.currentTimeMillis());
	              } catch (Exception e) {
	              }
	          }
	      }**/
		// Gets data from the incoming Intent
        String dataString = workIntent.getDataString();
        // Do work here, based on the contents of dataString
    }
	
	@Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        super.onStart(intent, startId);
        Toast.makeText(this, "ServiceClass.onStart()", Toast.LENGTH_LONG).show();
        Log.d("Testing", "Service got started");
    }
}
