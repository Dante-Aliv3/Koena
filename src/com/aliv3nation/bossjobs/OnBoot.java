package com.aliv3nation.bossjobs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class OnBoot extends BroadcastReceiver
{

	int ATTEMPT_NUMBER = 0;

    @Override
    public void onReceive(Context context, Intent intent) 
    {
    	/**Log.d("DEBUG", "Running Background intent");
    	Toast.makeText(context, "Forever Young!", Toast.LENGTH_SHORT).show();
    	String MyPREFERENCES = "MyPrefs";
    	SharedPreferences sharedpreferences;
    	Editor editor;
    	sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		editor = sharedpreferences.edit();
		editor.putString("IntentService", "itWorked_OnBoot!!");
		editor.commit();
		
    	if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            	// Create Intent
		    	Intent serviceIntent = new Intent(context, BackgroundService.class);
		        // Start service
		        context.startService(serviceIntent);
	    	}**/
    	
    	SQLiteDatabase apiDatabase = context.openOrCreateDatabase("Intent_Service.db", Context.MODE_PRIVATE, null);
		String TABLENAME = "IntentService";
		String KEY_ATTEMPT_NUMBER = "KEY_ATTEMPT_NUMBER";
		ATTEMPT_NUMBER++;
		
			String INSERT_NEW_RECORD = "INSERT OR IGNORE INTO " + TABLENAME 
					+" ( " + KEY_ATTEMPT_NUMBER + " ) "
					+ " VALUES ( " + ATTEMPT_NUMBER + " );";
			apiDatabase.execSQL(INSERT_NEW_RECORD);
    }

 }

