package com.aliv3nation.bossjobs;

import java.util.ArrayList;

import com.aliv3nation.beingboss.R;

import android.content.Context;
import android.database.Cursor;
import android.widget.TextView;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyCursor extends CursorAdapter {
	 private ArrayList <String> items;
	 
	    private TextView text;
	 
	    @SuppressWarnings({ "unchecked", "rawtypes" })
		public MyCursor(Context context, Cursor cursor, ArrayList items) {
	 
	        super(context, cursor, false);
	 
	        this.items = items;
	 
	    }
	 
	    @Override
	    public void bindView(View view, Context context, Cursor cursor) {
	 
	        // Show list item data from cursor
	        text.setText(items.get(cursor.getPosition()));
	 
	        // Alternatively show data direct from database
	        //text.setText(cursor.getString(cursor.getColumnIndex("column_name")));
	 
	    }
	 
	    @Override
	    public View newView(Context context, Cursor cursor, ViewGroup parent) {
	 
	        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	 
	        View view = inflater.inflate(R.layout.item, parent, false);
	 
	        text = (TextView) view.findViewById(R.id.text);
	 
	        return view;
	 
	    }
	 
	}