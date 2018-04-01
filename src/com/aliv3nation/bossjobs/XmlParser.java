package com.aliv3nation.bossjobs;



public class XmlParser {
	static String[ ] tags = new String[]{"jobtitle","company","city","state","country","source",
		"date","snippet", "url","latitude","longitude","formattedLocationFull","formattedRelativeTime",
			"noUniqueUrl"};
	static String data = "";
	static String[ ] text;
	static String[ ] [ ] parsedResults = new String [25] [14];
	
	public XmlParser(String inputStream)
	{
		data = inputStream;
	}
	
	public static void main(String[] args) {
	}

	public String[ ][ ] getResults()
	{

		String delims = "[<>]+"; 
		text = data.split(delims);
		int count = 0;
		boolean isReady = false;
		for(int i = 0; i < tags.length; i++)//unique tags
		{
			for(int n = 0; n < text.length; n++)// cycle through text
			{
				if(text[n].equalsIgnoreCase(tags[i]))//compare strings
					{
						if(!((text[n+1].trim()).isEmpty()))//skip empty strings
						{
								//String s = tags[i] + " equals ==> " + text[n+1];
								//JOptionPane.showMessageDialog(null, s);
								if(tags[i].equalsIgnoreCase("jobtitle"))
								{
									isReady = true;
								}
								
								if(isReady)
								{
									 //JOptionPane.showMessageDialog(null, temp);
									switch(tags[i])//delegates array values
									{
										case "jobtitle": parsedResults[count][i] = text[n+1];
										//JOptionPane.showMessageDialog(null, "Your in " + "jobtitle" + "count is " + count);
															break;
										case "company": parsedResults[count][i] = text[n+1];
										//JOptionPane.showMessageDialog(null, "Your in " + "company" + "count is " + count);
															break;
										case "city": parsedResults[count][i] = text[n+1];
										//JOptionPane.showMessageDialog(null, "Your in " + "city" + "count is " + count);
															break;
										case "state": parsedResults[count][i] = text[n+1];
										//JOptionPane.showMessageDialog(null, "Your in " + "state" + "count is " + count);
															break;
										case "country": parsedResults[count][i] = text[n+1];
										//JOptionPane.showMessageDialog(null, "Your in " + "country" + "count is " + count);
															break;
										case "source": parsedResults[count][i] = text[n+1];
										//JOptionPane.showMessageDialog(null, "Your in " + "source" + "count is " + count);
															break;
										case "date": parsedResults[count][i] = text[n+1];
										//JOptionPane.showMessageDialog(null, "Your in " + "date" + "count is " + count);
															break;
										case "snippet": parsedResults[count][i] = text[n+1];
										//JOptionPane.showMessageDialog(null, "Your in " + "snippet" + "count is " + count);
															break;
										case "url": parsedResults[count][i] = text[n+1];
										//JOptionPane.showMessageDialog(null, "Your in " + "url" + "count is " + count);
															break;
										case "latitude": parsedResults[count][i] = text[n+1];
										//JOptionPane.showMessageDialog(null, "Your in " + "latitude" + "count is " + count);
															break;
										case "longitude": parsedResults[count][i] = text[n+1];
										//JOptionPane.showMessageDialog(null, "Your in " + "longitude" + "count is " + count);
															break;
										case "formattedLocationFull": parsedResults[count][i] = text[n+1];
										//JOptionPane.showMessageDialog(null, "Your in " + "formattedLocationFull" + "count is " + count);
															break;
										case "formattedRelativeTime": parsedResults[count][i] = text[n+1];
										//JOptionPane.showMessageDialog(null, "Your in " + "formattedRelativeTime" + "count is " + count);
															break;
										case "noUniqueUrl": parsedResults[count][i] = text[n+1];
										//JOptionPane.showMessageDialog(null, "Your in " + "noUniqueUrl" + "count is " + count);
															break;
										default: break;
									}


									count++;
									if(count == 25)//reset count
										count = 0;
								}
						}
					}
				
			}
		}
		return parsedResults;
	}
}
