/*********************************
 * UCSD VIS 141A project
 * DancinAndroid 
 * 
 * Created By: Monica Liu
 * Last Modified 2/10/14
 ********************************/

package com.example.dancinandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class SettingsActivity extends Activity implements OnSeekBarChangeListener, OnClickListener {

	private int numDancers = 1;
	private TextView numLabel;
	private Button colorb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		SeekBar numSeekBar = (SeekBar) findViewById(R.id.seekBar1);
		numSeekBar.setOnSeekBarChangeListener(this);
		//numSeekBar.setOnKeyListener(this);
		
		numLabel = (TextView) findViewById(R.id.barlabel);
		colorb = (Button) findViewById(R.id.colorButton);
		
		if (GlobalsSingleton.getInstance().getcolor()) colorb.setText(R.string.white);
		else colorb.setText(R.string.black);
		
		colorb.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		if (GlobalsSingleton.getInstance().getcolor()) {
			GlobalsSingleton.getInstance().setcolor(false);
			colorb.setText(R.string.black);
		}
		else {
			GlobalsSingleton.getInstance().setcolor(true);
			colorb.setText(R.string.white);
		}		
	}

	
	/***** seekbar methods *****/
	@Override
	public void onStartTrackingTouch(SeekBar arg0) {	
	}
	@Override
	public void onProgressChanged(SeekBar arg0, int progress, boolean fromUser) {
		numDancers = (progress / 25) + 1;
		numLabel.setText("" + numDancers);
	}
	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		GlobalsSingleton.getInstance().setDancers(numDancers);	
	}

} 
