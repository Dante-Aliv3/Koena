package com.aliv3nation.bossjobs;



import com.aliv3nation.beingboss.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**********************************************************************************/
/**************Java Design Code for Fragmented(Tabbed) Display*********************/
/**********************************************************************************/

public class SearchFragment extends Fragment 
{//extends Fragment.class -> similar to custom JPanels
	ImageButton favoritesButton;// References to ImageButton Widget created in XML Definition
	@Override
	public View onCreateView(LayoutInflater Search, ViewGroup SearchLayout, Bundle savedInstanceState )
	{//Inflates XML Definition, then returns created View UI
		
		View SearchView =//View element for inflated Activity JobSearch XML Definition
				Search.inflate(R.layout.custom_dialog, SearchLayout, false);
		/*** we next call an .inflate( ) method off a LayoutInflater object 
		 * that we named Search, and pass the parameters of your 
		 * layout definition that Android stores in the location 
		 * R.layout.custom_dialog, as well as a ViewGroup object named 
		 * SearchLayout that we want inflated, along with a false boolean 
		 * value. This false boolean value was for setting the attachToRoot 
		 * parameter value. This third parameter in this method call defines
		 * whether or not we want to append or attach to a current View 
		 * (true value) or replace the current View value with a new View 
		 * (false value).  ****/
		
			
		return SearchView;
		
	}
	
}

