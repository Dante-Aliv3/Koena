package com.aliv3nation.bossjobs;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import android.os.Parcel;
import android.os.Parcelable;

public class Record implements Parcelable {
		public enum RecordField {
			Acting, Administrative, Aircraft,
			Animal_Careers, Architecture, Arts, Assisted_Living,
			Audio, Business, Construction, Consultant, 
			Criminal_Justice, Education, Energy_Utilities,
			Law_Enforcement, Finance, Freelance, Funeral, 
			Game_Industry, General_Labor, Government, 
			HealthCare, Human_Resources, Manufacturing,
			Marketing, Media, Military, Nonprofit, Resort,
			Sales, Service_Industry, Social_Media,
			Sports, Technology, Telecom	};
		public enum Education{HIGH_SCHOOL, OTJ_TRAINING, 
			ASSOCIATES, BACHELORS, MASTERS, DOCTORATE};
		public enum Stress{NONE, LOW, MID, HIGH, KILL_ME};
		public enum Skill{BASIC, NOVICE, EXPERIENCED, PROFESSIONAL, FALSE};
		public enum MoneySource {CAREER, HOBBY, BOTH};
		public enum JobLocation{HOME, OFFICE, ON_SITE};
		public enum Hours{FULL_TIME, PART_TIME, BOTH, CONTRACT, ALL};
		final static String[] Fields = ("Acting,Administrative,Aircraft,"
				+ "Animal Careers,Architecture,Arts,Assisted Living,"
				+ "Audio,Business,Construction,Consultant,"
				+ "Criminal Justice,Education,Energy Utilities,"
				+ "Law Enforcement,Finance,Freelance,Funeral,"
				+ "Game Industry,General Labor,"
				+ "Government,HealthCare,Human Resources,"
				+ "Manufacturing,Marketing,"
				+ "Media,Military,Nonprofit,Resort,"
				+ "Sales,Service Industry,Social Media,"
				+ "Sports,Technology,Telecom").split(",");
		final static String [] educationOptions = {"High School", "Training on the Job", 
			"Associates", "Bachelors", "Masters", "Doctorate" };
		final static String [] stressSet ={"Very Low",
			"Low", "Medium", "High", "Very High"};
		final static String [] skillSet = {"Basic", "Novice",
			"Experienced", "Professional"};
		final static String [] moneyOptions = {"Career", "Hobby", "Both"};
		final static String [] locationOptions = {"Home", "Office", "On Site"};
		final static String [] allHours = {"Full Time", "Part Time" ,
			"Full/Part", "Contract", "All"};
		JobLocation homeOffice;
		Stress stressLevel;
		Skill skillLevel;
		MoneySource careerHobby;
		RecordField jobField;
		Education educationNum;
		Hours fullPartTime;
		public String title, positionDescription, 
		jobType,prosCons, mainLink, secondaryLink,
		usefulLink, educationLevel, imageURL;
		public int entrySalary, midCareerSalary,
		seniorSalary, investedTime, expectedCost,
		jobOutlook;
		boolean quickMoney, isPassive;
		static Locale ENGLISH;
		
		Record()
		{
			 ENGLISH = new Locale("en", "US");
		}
		public void setjobField(String s) 
		{
			switch(s.toLowerCase(ENGLISH))
			{
			case ("acting"):
				jobField = RecordField.Acting;
			break;
			case ("administrative"):
				jobField = RecordField.Administrative;
			break;
			case ("aircraft"):
				jobField = RecordField.Aircraft;
			break;
			case ("animal careers"):
				jobField = RecordField.Animal_Careers;
			break;
			case ("architecture"):
				jobField = RecordField.Architecture;
			break;
			case ("arts"):
				jobField = RecordField.Arts;
			break;
			case ("audio"):
				jobField = RecordField.Audio;
			break;
			case ("assisted living"):
				jobField = RecordField.Assisted_Living;
			break;
			case ("business"):
				jobField = RecordField.Business;
			break;
			case ("construction"):
				jobField = RecordField.Construction;
			break;
			case ("consultant"):
				jobField = RecordField.Consultant;
			break;
			case ("criminal justice"):
				jobField = RecordField.Criminal_Justice;
			break;
			case ("education"):
				jobField = RecordField.Education;
			break;
			case ("energy utilities"):
				jobField = RecordField.Energy_Utilities;
			break;
			case ("law enforcement"):
				jobField = RecordField.Law_Enforcement;
			break;
			case ("finance"):
				jobField = RecordField.Finance;
			break;
			case ("freelance"):
				jobField = RecordField.Freelance;
			break;
			case ("funeral"):
				jobField = RecordField.Funeral;
			break;
			case ("game industry"):
				jobField = RecordField.Game_Industry;
			break;
			case ("general labor"):
				jobField = RecordField.General_Labor;
			break;
			case ("government"):
				jobField = RecordField.Government;
			break;
			case ("healthcare"):
				jobField = RecordField.HealthCare;
			break;
			case ("human resources"):
				jobField = RecordField.Human_Resources;
			break;
			case ("manufacturing"):
				jobField = RecordField.Manufacturing;
			break;
			case ("marketing"):
				jobField = RecordField.Marketing;
			break;
			case ("media"):
				jobField = RecordField.Media;
			break;
			case ("military"):
				jobField = RecordField.Military;
			break;
			case ("nonprofit"):
				jobField = RecordField.Nonprofit;
			break;
			case ("resort"):
				jobField = RecordField.Resort;
			break;
			case ("sales"):
				jobField = RecordField.Sales;
			break;
			case ("service industry"):
				jobField = RecordField.Service_Industry;
			break;
			case ("social media"):
				jobField = RecordField.Social_Media;
			break;
			case ("sports"):
				jobField = RecordField.Sports;
			break;
			case ("technology"):
				jobField = RecordField.Technology;
			break;
			case ("telecom"):
				jobField = RecordField.Telecom;
			break;
			default: jobField = RecordField.General_Labor;
			break;
			}
		}
		public void setStressLevel(int i) 
		{
			switch(i)
			{
			case (1):
				stressLevel = Stress.NONE;
			break;
			case (2):
				stressLevel = Stress.LOW;
			break;
			case (3):
				stressLevel = Stress.MID;
			break;
			case (4):
				stressLevel = Stress.HIGH;
			break;
			case (5):
				stressLevel = Stress.KILL_ME;
			break;
			default: stressLevel = Stress.MID;
			break;
			}
		
		}
		public void setEducation(int i) 
		{
			switch(i)
			{
			case (1):
				educationNum = Education.HIGH_SCHOOL;
			break;
			
			case (2):
				educationNum = Education.OTJ_TRAINING;
			break;
			
			case (3):
				educationNum = Education.ASSOCIATES;
			break;
			
			case (4):
				educationNum = Education.BACHELORS;
			break;
			
			case (5):
				educationNum = Education.MASTERS;
			break;	
			
			case (6):
				educationNum = Education.DOCTORATE;
			break;
			
			default: educationNum = Education.BACHELORS;
			break;
			}
		}
		
		public String getImage()
		{
			return imageURL;
		}
		
		public void setSkill(int i) 
		{
			switch(i)
			{
			case (1):
				skillLevel = Skill.BASIC;
			break;
			case (2):
				skillLevel = Skill.NOVICE;
			break;
			case (3):
				skillLevel = Skill.EXPERIENCED;
			break;
			case (4):
				skillLevel = Skill.PROFESSIONAL;
			break;
			case (5):
				skillLevel = Skill.FALSE;
			break;	
			default: skillLevel = Skill.BASIC;
			break;
			}
		}
		public void setMoneySource(String s) 
		{
			switch(s.toLowerCase(ENGLISH))
			{
			case ("career"):
				careerHobby = MoneySource.CAREER;
			break;
			case ("hobby"):
				careerHobby = MoneySource.HOBBY;
			break;
			case ("both"):
				careerHobby = MoneySource.BOTH;
			break;
			default: careerHobby = MoneySource.CAREER;
			break;
			}
		}
		public void setJobLocation(int i) 
		{
			switch(i)
			{
			case (0):
				homeOffice = JobLocation.HOME;
			break;
			case (1):
				homeOffice = JobLocation.OFFICE;
			break;
			case (2):
				homeOffice = JobLocation.ON_SITE;
			break;
			case (3):
				homeOffice = JobLocation.ON_SITE;
			break;
			default: homeOffice = JobLocation.OFFICE;
			break;
			}
		}
		public void setHours(int i) 
		{
			switch(i)
			{
			case (1):
				fullPartTime = Hours.FULL_TIME;
			break;
			case (2):
				fullPartTime = Hours.PART_TIME;
			break;
			case (3):
				fullPartTime = Hours.BOTH;
			break;
			case (4):
				fullPartTime = Hours.CONTRACT;
			break;
			case (5):
				fullPartTime = Hours.ALL;
			break;
			default:
				fullPartTime = Hours.FULL_TIME;
				break;
			}
		}
		public String getStressLevel(boolean b) 
		{
			String s = "";
			if(b == true)
				s = "Stress Level: ";
			
			switch(stressLevel)
			{
			case NONE:
				s = s + "Very Low";
				break;
			case LOW:
				s = s + "Low";
				break;
			case MID:
				s = s + "Medium";
				break;
			case HIGH:
				s = s + "High";
				break;
			case KILL_ME:
				s = s + "Very High";
				break;
			default: s = s + "3";
				break;
			}
			return s;
		}
		
		public String getEducation() 
		{
			String s = "";
			switch(educationNum)
			{
			case HIGH_SCHOOL:
				s = "1";
			break;
			case OTJ_TRAINING:
				s = "2";
			break;
			case ASSOCIATES:
				s = "3";
			break;
			case BACHELORS:
				s = "4";
			break;
			case MASTERS:
				s = "5";
			break;	
			case DOCTORATE:
				s = "6";
			break;
			default: s = "4";
			break;
			}
			return s;
		}
		
		public String displayEducation() 
		{
			String s = "";
			switch(educationNum)
			{
			case HIGH_SCHOOL:
				s = "High School Diploma";
			break;
			case OTJ_TRAINING:
				s = "On The Job Training";
			break;
			case ASSOCIATES:
				s = "Associates's Degree";
			break;
			case BACHELORS:
				s = "Bachelor's Degree";
			break;
			case MASTERS:
				s = "Master's Degree";
			break;	
			case DOCTORATE:
				s = "Doctorate's";
			break;
			default: s = "Bachelor's Degree";
			break;
			}
			return s;
		}
		public String getSkill(boolean b) 
		{
			String s = "";
			if(b == true)
				s = "Skill Level: ";
			
			switch(skillLevel)
			{
			case BASIC:
				s = s + "Basic";
			break;
			case NOVICE:
				s = s + "Novice";
			break;
			case EXPERIENCED:
				s = s + "Experienced";
			break;
			case PROFESSIONAL:
				s = s + "Professional";
			break;
			case FALSE:
				s = s + "unRated";
			break;	
			default: s = s + "Basic";
			break;
			}
			return s;
	
		}
		public String getMoneySource(boolean b) 
		{
			String s = "";
			if(b == true)
				s = "Job Options: ";
			
			switch(careerHobby)
			{
			case CAREER:
				s = s + "Career";
			break;
			case HOBBY:
				s = s + "Hobby";
			break;
			case BOTH:
				s = s + "Career or Hobby";
			break;
			default: s = s + "Career";
			break;
			}
			return s;
		}
		public String getJobLocation(boolean b) 
		{
			String s = "";
			if(b == true)
				s = "Job Location: ";
			
			switch(homeOffice)
			{
			case HOME:
				s = s + "Home";
			break;
			case OFFICE:
				s = s + "Office";
			break;
			case ON_SITE:
				s = s + "On Site";
			break;
			default: s = s + "Office";
			break;
			}
			return s;
		}
		public String getHours() 
		{
			String s = "";
			switch(fullPartTime)
			{
			case FULL_TIME:
				 s = "1";
			break;
			case PART_TIME:
				s = "2";
			break;
			case BOTH:
				s = "3";
			break;
			case CONTRACT:
				s = "4";
			break;
			case ALL:
				s = "5";
			break;
			default:
				s = "2";
				break;
			}
			return s;
		}
		public String getJobField() 
		{
			String s = "";
			switch(jobField)
			{
				case Acting:
					s = "Acting";
				break;
				case Administrative:
					s = "Administrative";
				break;
				case Aircraft:
					s = "Aircraft";
				break;
				case Animal_Careers:
					s = "Animal careers";
				break;
				case Architecture:
					s = "Architecture";
				break;
				case Arts:
					s = s + "Arts";
				break;
				case Assisted_Living:
					s = "Assisted living";
				break;
				case Audio:
					s = "Audio";
				break;
				case Business:
					s = "Business";
				break;
				case Construction:
					s = "Construction";
				break;
				case Consultant:
					s = "Consultant";
				break;
				case Criminal_Justice:
					s = "Criminal Justice";
				break;
				case Education:
					s = "Education";
				break;
				case Energy_Utilities:
					s = "Energy Utilities";
				break;
				case Law_Enforcement:
					s = "Law Enforcement";
				break;
				case Finance:
					s = "Finance";
				break;
				case Freelance:
					s = "Freelance";
				break;
				case Funeral:
					s = "Funeral";
				break;
				case Game_Industry:
					s = "Game Industry";
				break;
				case General_Labor:
					s = "General Labor";
				break;
				case Government:
					s = "Government";
				break;
				case HealthCare:
					s = "Healthcare";
				break;
				case Human_Resources:
					s = "human resources";
				break;
				case Manufacturing:
					s = "Manufacturing";
				break;
				case Marketing:
					s = "Marketing";
				break;
				case Media:
					s = "Media";
				break;
				case Military:
					s = "Military";
				break;
				case Nonprofit:
					s = "Nonprofit";
				break;
				case Resort:
					s = "Resort";
				break;
				case Sales:
					s = "Sales";
				break;
				case Service_Industry:
					s = "Service Industry";
				break;
				case Social_Media:
					s = "Social Media";
				break;
				case Sports:
					s = "Sports";
				break;
				case Technology:
					s = "Technology";
				break;
				case Telecom:
					s = "Telecom";
				break;
				default: s = "General Labor";
				break;
			}
			return s;
		}
		
		public String getActivePassive() {
			String s = "Work Type: ";
			if(isPassive)
				s = s + "Passive";
			else
				s = s + "Active";
						
			return s;
		}
		
		public String getinvestedTime() 
		{
			String s = "Invested Time: ";
			for(int i = 0; i <= investedTime; i++)
			{
				if(i == investedTime)
				{
					if(i > 12)
						{
							int months = 0;
							int years = 0;
							months = i % 12;
							years = i/12;
							if (months == 0)
									s = s + years + " years ";
							else
								s = s + years + " years and " + months + " months";
							
						}
					else if (i == 0)
					{
						s = s + " none";
					}
					else
					{
						s = s + i + " months";
					}
				}
			}
			return s;
		}
		
		public String getEntrySalary() {//returns formatted salary data
			String s = NumberFormat.getCurrencyInstance(ENGLISH).format(entrySalary);
			int end = s.length() - 3;
			s = s.substring(0, end);
			return s;
		}
		public String getMidCareerSalary() {//returns formatted salary data
			String s = NumberFormat.getCurrencyInstance(ENGLISH).format(midCareerSalary);
			int end = s.length() - 3;
			s = s.substring(0, end);
			return s;
		}
		public String getSeniorSalary() {//returns formatted salary data
			String s = NumberFormat.getCurrencyInstance(ENGLISH).format(seniorSalary);
			int end = s.length() - 3;
			s = s.substring(0, end);
			return s;
		}
		public String[] getprosCons() {
			String[] s = {"",""};
			String[]  temp = prosCons.split("\\|");
			s[0] = "Pros: " + temp[0];
			s[1] = "Cons: " + temp[1];
			return s;
		}
		public String[] getFields() {
			return Fields;
		}

		public String[] geteducationOptions() {
			return educationOptions;
		}
		
		public String[] getstressSet() {
			return stressSet;
		}
		
		public String[] getskillSet() {
			return skillSet;
		}
		
		public String[] getmoneyOptions() {
			return moneyOptions;
		}
		
		public String[] getlocationOptions() {
			return locationOptions;
		}
		
		public String[] getAllHours() {
			return allHours;
		}
		
    protected Record(Parcel in) {
    	setJobLocation(Integer.parseInt(in.readString()));
        setStressLevel( Integer.parseInt(in.readString()));
        setSkill( Integer.parseInt(in.readString()));
        setMoneySource(in.readString());
        setjobField( in.readString());
        setEducation( Integer.parseInt(in.readString()));
        setHours(Integer.parseInt(in.readString()));
        title = in.readString();
        positionDescription = in.readString();
        jobType = in.readString();
        prosCons = in.readString();
        mainLink = in.readString();
        secondaryLink = in.readString();
        usefulLink = in.readString();
        educationLevel = in.readString();
        imageURL = in.readString();
        entrySalary = in.readInt();
        midCareerSalary = in.readInt();
        seniorSalary = in.readInt();
        investedTime = in.readInt();
        expectedCost = in.readInt();
        jobOutlook = in.readInt();
        quickMoney = in.readByte() != 0x00;
        isPassive = in.readByte() != 0x00;
        ENGLISH = new Locale("en", "US");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getJobLocation(false));
        dest.writeString(getStressLevel(false));
        dest.writeString(getSkill(false));
        dest.writeString(getMoneySource(false));
        dest.writeString(getJobField());
        dest.writeString(getEducation());
        dest.writeString(getHours());
        dest.writeString(title);
        dest.writeString(positionDescription);
        dest.writeString(jobType);
        dest.writeString(prosCons);
        dest.writeString(mainLink);
        dest.writeString(secondaryLink);
        dest.writeString(usefulLink);
        dest.writeString(educationLevel);
        dest.writeString(imageURL);
        dest.writeInt(entrySalary);
        dest.writeInt(midCareerSalary);
        dest.writeInt(seniorSalary);
        dest.writeInt(investedTime);
        dest.writeInt(expectedCost);
        dest.writeInt(jobOutlook);
        dest.writeByte((byte) (quickMoney ? 0x01 : 0x00));
        dest.writeByte((byte) (isPassive ? 0x01 : 0x00));
    }

    public static final Parcelable.Creator<Record> CREATOR = new Parcelable.Creator<Record>() {
        @Override
        public Record createFromParcel(Parcel in) {
            return new Record(in);
        }

        @Override
        public Record[] newArray(int size) {
            return new Record[size];
        }
    };

	public static ArrayList<String> getQuery() {
		ArrayList <String> searchItems = new ArrayList<String>(100);
		searchItems.add("Active");
		searchItems.add("Passive");
		
		for(int i = 0; i < Fields.length; i++)
		{
			searchItems.add(Fields[i]);
		}
		
		for(int i = 0; i < educationOptions.length; i++)
		{
			searchItems.add(educationOptions[i]);
		}
		
		for(int i = 0; i < stressSet.length; i++)
		{
			searchItems.add(stressSet[i]);
		}
		String[] skillSet = Database.records.get(0).getskillSet();
		for(int i = 0; i < skillSet.length; i++)
		{
			searchItems.add(skillSet[i]);
		}

		for(int i = 0; i < moneyOptions.length; i++)
		{
			searchItems.add(moneyOptions[i]);
		}

		for(int i = 0; i < locationOptions.length; i++)
		{
			searchItems.add(locationOptions[i]);
		}

		for(int i = 0; i < allHours.length; i++)
		{
			searchItems.add(allHours[i]);
		}
		return searchItems;
	}

}