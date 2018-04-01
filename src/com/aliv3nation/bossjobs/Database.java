package com.aliv3nation.bossjobs;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;

import com.aliv3nation.bossjobs.Record.Education;
import com.aliv3nation.bossjobs.Record.Hours;
import com.aliv3nation.bossjobs.Record.JobLocation;
import com.aliv3nation.bossjobs.Record.MoneySource;
import com.aliv3nation.bossjobs.Record.RecordField;
import com.aliv3nation.bossjobs.Record.Skill;
import com.aliv3nation.bossjobs.Record.Stress;

import android.content.Context;
import android.content.res.AssetManager;
import android.widget.Toast;


public class Database 
{
	static  private ArrayList <InputStream> filePath = new ArrayList<InputStream>();
	static ArrayList <Record> records = new ArrayList<Record>(100);
	static private InputStream input = null;
	static private AssetManager manager;
	static private int currentFiles = 0;
	static final int RECORDSIZE = 24;
	static private Context active;
	Database()
	{
	}
	
	public static void creation(AssetManager assets, Context current) throws Exception 
	{
		try
		{
			//Access application assets  
		     manager = assets;
		     //Set application context
		     active = current;
		    //Open our data file path(s) as InputStream(s)
			InputStream in01 = 
					manager.open("dba-f.csv");
		    InputStream in02 = 
		    		manager.open("dbg-l.csv");
		    InputStream in03 = 
		    		manager.open("dbm-r.csv");
		    InputStream in04 = 
		    		manager.open("dbs-z.csv");
			filePath.add(in01);
			filePath.add(in02);
			filePath.add(in03);
			filePath.add(in04);
		}
		catch(Exception err)
		{
			throw err;
		}
		String s;
		final  int LIMIT = filePath.size();
		for(int i = 0; i <  LIMIT; i++)
		{
			
			try
			{
				//sets file currently being accessed
				input = filePath.get(i);
				BufferedReader reader = new BufferedReader(new InputStreamReader (input));
				final int RESET = 26;
				int count = 0;
				int value = 0;
				//reads Template Data in File and throws out the data
				while(count < RESET && reader.readLine() != null)
				{
					count++;
				}
				
				count = currentFiles;
				
				s = reader.readLine();
				if(s != null)
				{
					records.add(new Record());
				}
				
				while(s !=  null)
				{//assigns data to Record fields
					if (s.length() == 0)
						s = reader.readLine();
					int last = s.length() - 1;
					s = s.substring(0, last);
					Locale locale = Locale.ENGLISH;
					switch(value)
					{
					case (0): 
						records.get(count).title = s;
						//Toast.makeText(active.getApplicationContext(), s, Toast.LENGTH_LONG).show();
						break;
					case (1): 
						records.get(count).setjobField(s);
						break;
					case (2): 
						records.get(count).positionDescription = s;
						break;
					case (3): 
						records.get(count).entrySalary = Integer.parseInt(s);
						break;
					case (4): 
						records.get(count).midCareerSalary = Integer.parseInt(s);
						break;
					case (5): 
						records.get(count).seniorSalary = Integer.parseInt(s);
						break;
					case (6): 
						records.get(count).setStressLevel(Integer.parseInt(s));
						break;
					case (7): 
						records.get(count).investedTime = Integer.parseInt(s);
						break;
					case (8): 
						records.get(count).setEducation(Integer.parseInt(s));
						break;			
					case (9): 
						records.get(count).educationLevel = s;
						break;
					case (10): 
						records.get(count).setSkill(Integer.parseInt(s));
						break;
					case (11): 
						records.get(count).expectedCost = Integer.parseInt(s);
						break;
					case (12):  if(s.toLowerCase(locale).equals("true"))
									records.get(count).isPassive = true;
								else
									records.get(count).isPassive = false;
						break;
					case (13): 
						records.get(count).setMoneySource(s);
						break;
					case (14): if(s.toLowerCase(locale).equals("true"))
									records.get(count).quickMoney = true;
								else
									records.get(count).quickMoney = false;
						break;
					case (15): 
						records.get(count).setJobLocation(Integer.parseInt(s));
						break;
					case (16): 
						records.get(count).setHours(Integer.parseInt(s));
						break;
					case (17): 
						records.get(count).jobType = s;
						break;
					case (18): 
						records.get(count).prosCons = s;
						break;
					case (19): 
						records.get(count).jobOutlook = Integer.parseInt(s);
						break;
					case (20): 
						records.get(count).mainLink = s;
						break;
					case (21): 
						records.get(count).secondaryLink = s;
						break;
					case (22): 
						records.get(count).usefulLink = s;
						break;
					case (23):
						records.get(count).imageURL = s;
						break;
					default: //error handling for Record 
						//Field match not found
						String statement = records.get(count).title +
							" is missing Field #" + value + "; " + s; //and has caused an error.";
						Toast.makeText(active.getApplicationContext(), statement, Toast.LENGTH_LONG).show();
						break;
					}
					value++;
					s = reader.readLine();
					if(value == RECORDSIZE)//resets fields, creates new Record
					{
						count++;
						value = 0;
						if(s != null)
						{
							records.add(new Record());
						}

					}
				}
				currentFiles = count; //saves completed Records #
				reader.close();
			}
			catch (Exception err)
			{
					throw err;
			}
		}
	}	
	static ArrayList<Record> compileHours(Hours h)
	{
		ArrayList <Record> temp = new ArrayList<Record>();
		for(int i = 0; i < records.size(); i++)
		{
			if(records.get(i).fullPartTime == h)
				temp.add(records.get(i));
		}
		return temp;
		
	}
	static ArrayList<Record> compileActivePassive(boolean q)
	{
		ArrayList <Record> temp = new ArrayList<Record>();
		for(int i = 0; i < records.size(); i++)
		{
			if(records.get(i).isPassive == q)
				temp.add(records.get(i));
		}
		return temp;
		
	}
	static ArrayList<Record> compileHomeOffice(JobLocation j)
	{
		ArrayList <Record> temp = new ArrayList<Record>();
		for(int i = 0; i < records.size(); i++)
		{
			if(records.get(i).homeOffice == j)
				temp.add(records.get(i));
		}
		return temp;
		
	}
	static ArrayList<Record> compileCareerHobby(MoneySource m)
	{
		ArrayList <Record> temp = new ArrayList<Record>();
		for(int i = 0; i < records.size(); i++)
		{
			if(records.get(i).careerHobby == m)
				temp.add(records.get(i));
		}
		return temp;
		
	}
	static ArrayList<Record> compileJobField(RecordField r)
	{
		ArrayList <Record> temp = new ArrayList<Record>();
		for(int i = 0; i < records.size(); i++)
		{
			if(records.get(i).jobField == r)
				temp.add(records.get(i));
		}
		return temp;
		
	}
	
	static ArrayList<Record> compileEducationSet(Education e)
	{
		ArrayList <Record> temp = new ArrayList<Record>();
		for(int i = 0; i < records.size(); i++)
		{
			if(records.get(i).educationNum == e)
				temp.add(records.get(i));
		}
		return temp;
		
	}
	
	static ArrayList<Record> compileStressLevel(Stress s)
	{
		ArrayList <Record> temp = new ArrayList<Record>();
		for(int i = 0; i < records.size(); i++)
		{
			if(records.get(i).stressLevel == s)
				temp.add(records.get(i));
		}
		return temp;
		
	}
	
	static ArrayList<Record> compileSkillLevel(Skill s)
	{
		ArrayList <Record> temp = new ArrayList<Record>();
		for(int i = 0; i < records.size(); i++)
		{
			if(records.get(i).skillLevel == s)
				temp.add(records.get(i));
		}
		return temp;
		
	}
	
	static ArrayList<Record> compileSalary(String variable, int range)
	{
		ArrayList <Record> temp = new ArrayList<Record>();
		switch(variable)
		{
					case "entrySalary": 
						for(int i = 0; i < records.size(); i++)
						{
							if(records.get(i).entrySalary >= range)
								temp.add(records.get(i));
						}
						break;
						
					case "midCareerSalary": 
						for(int i = 0; i < records.size(); i++)
						{
							if(records.get(i).midCareerSalary >= range)
								temp.add(records.get(i));
						}
						break;
						
					case "seniorSalary": 
						for(int i = 0; i < records.size(); i++)
						{
							if(records.get(i).seniorSalary >= range)
								temp.add(records.get(i));
						}
						break;
		}
		return temp;
	}

	public static String[] allRecords() {
		String [] allRecordsNames = new String[records.size()];
        for(int i=0; i < records.size(); i++)
        {
        	allRecordsNames[i] = records.get(i).title;
        }
		return allRecordsNames;
	}
}

