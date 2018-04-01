package com.aliv3nation.bossjobs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {
	//version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
	
	// TODO Auto-generated method stub
	private static String TABLENAME = "Job Records";
	private static String KEY_JOBID = "Job Title";
	private static String KEY_COMPANY = "Company";
	private static String KEY_POSITION_DESC = "Position Description";
	private static String KEY_JOBLOCATION = "Job Location";
	private static String KEY_EDUCATION_LEVEL = "Education Level";
	private static String KEY_CREATION_DATE = "Date Created";
	private static String KEY_JOB_SOURCE = "JobFeed Source";
	private static String KEY_HYPERLINK = "Hyperlink";
	private static String KEY_PUBLISH_DATE = "Date Published";
	private static SQLiteDatabase database;
	private static String databaseName;
	
	public SQLiteHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor to super
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//String KEY_SALARY = "Salary";
		
		String CREATE_JOBS_TABLE = "CREATE TABLE IF NOT EXISTS" + TABLENAME + "( "
				+ KEY_JOBID + " TEXT NOT NULL PRIMARY KEY, " 
				+ KEY_COMPANY + " TEXT NOT NULL, "
				+ KEY_POSITION_DESC + " TEXT NOT NULL, "
				+ KEY_COMPANY + " TEXT NOT NULL, "
				+ KEY_JOBLOCATION + " TEXT, "
				+ KEY_EDUCATION_LEVEL + " TEXT, "
				+ KEY_CREATION_DATE + " TEXT, " //convert to Date, Data Type
				+ KEY_JOB_SOURCE + " TEXT, "
				+ KEY_HYPERLINK + " TEXT, "
				+ KEY_PUBLISH_DATE + " TEXT);";
		db.execSQL(CREATE_JOBS_TABLE);
		database = db;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
		onCreate(db);
	}
	
	public SQLiteDatabase getWritableDatabase() {
		return database;
	}
	
	public void insert(String Data)
	{
		database.execSQL(Data);
	}
}
