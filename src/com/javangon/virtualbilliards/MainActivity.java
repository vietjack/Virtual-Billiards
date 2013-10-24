package com.javangon.virtualbilliards;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Main Activity will be responsible for having a timer that calls
 * invalidate on the DrawView at regular intervals 
 * @author Zack
 *
 */
public class MainActivity extends Activity {

	private Button plus;
	private Button minus;
	private Button clear;
	
	private DrawView table;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		plus  = (Button) findViewById(R.id.plus);    // 
		minus = (Button) findViewById(R.id.minus);   // Get references to views
		clear = (Button) findViewById(R.id.clear);   //
		table = (DrawView) findViewById(R.id.table); // 
		
		plus.setOnClickListener(new OnClickListener() { // 
			@Override									//
			public void onClick(View v) {				// Set up click listener
				table.incrementScale();					// for the plus button.
			}											//
		});												//
		
		minus.setOnClickListener(new OnClickListener() { //
			@Override									 //
			public void onClick(View v) {				 // Set up click listener
				table.decrementScale();					 // for the minus button.
			}											 //
		});												 //
		
		clear.setOnClickListener(new OnClickListener() { //
			@Override									 //
			public void onClick(View v) {				 // Set up click listener
				table.clearTable();						 // for the clear button.
			}											 //
		});												 //

		new Timer().scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				table.postInvalidate();
			}
		}, 0, 20);
	}
}
