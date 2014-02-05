package com.example.dancinandroid;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private static final int MAX_MOVES = 1000;  //maximum number of moves the blob can do at once
	private static final int MAX_DANCERS = 5;
	
	boolean dancing = false;  //control whether or not you can add more moves
	
	private Vector<Integer> moves = new Vector<Integer>();  //current queue of moves
	private ImageView[] dancers = new ImageView[MAX_DANCERS];
	private Drawable[] drawID = new Drawable[5];  //number of different moves
	
	
	//To generate negative image
	  float[] colorMatrix_Negative = { 
	    -1.0f, 0, 0, 0, 255, //red
	    0, -1.0f, 0, 0, 255, //green
	    0, 0, -1.0f, 0, 255, //blue
	    0, 0, 0, 1.0f, 0 //alpha  
	  };
	  ColorFilter colorFilter_Negative = new ColorMatrixColorFilter(colorMatrix_Negative);
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		dancers[0] = (ImageView) findViewById(R.id.dancer);
		dancers[1] = (ImageView) findViewById(R.id.dancerL);
		dancers[2] = (ImageView) findViewById(R.id.DancerR);
		dancers[3] = (ImageView) findViewById(R.id.DancerLL);
		dancers[4] = (ImageView) findViewById(R.id.DancerRR);
		
		drawID[0] = getResources().getDrawable(R.drawable.dancedef);
		drawID[1] = getResources().getDrawable(R.drawable.dancedown);
		drawID[2] = getResources().getDrawable(R.drawable.danceleft);
		drawID[3] = getResources().getDrawable(R.drawable.danceright);
		drawID[4] = getResources().getDrawable(R.drawable.danceup);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.About:
            	this.startActivity(new Intent(this, AboutActivity.class));
                return true;
            case R.id.action_settings:
            	this.startActivity(new Intent(this, SettingsActivity.class));
            default:
            	return super.onOptionsItemSelected(item);
               
        }
    }
	
	//determines which button was clicked, and what to do as a result
	public void buttonClick (View view) {
		if (moves.size() < MAX_MOVES-1) {
			switch (view.getId()) {
				//case movement buttons: add a dance move to the queue.  If currently dancing, do not add to queue
				case R.id.downButton:
					if (!dancing) moves.add(R.drawable.dancedown);
					break;
				case R.id.leftButton:
					if (!dancing) moves.add(R.drawable.danceleft);
					break;
				case R.id.rightButton:
					if (!dancing) moves.add(R.drawable.danceright);
					break;
				case R.id.upButton:
					if (!dancing) moves.add(R.drawable.danceup);
					break;
				//case dance: perform all moves in queue and clear queue
				case R.id.danceButton:
					dancing = true;
					TextView t = (TextView) findViewById(R.id.dancin);
					t.setText(R.string.dancing);
					Timer timer = new Timer();
					updateDancers();
					for (int i = 0; i < moves.size()+1; i++) {
						timer.schedule(new danceloop(GlobalsSingleton.getInstance().getDancers()), i*200);  
							//i*(number of milliseconds delay for each image)
					}
				default:
					break;
			}
		} else {
			TextView t = (TextView) findViewById(R.id.dancin);
			t.setText(R.string.movemax);
		}
	}
	
	public void updateDancers() {
		for (int i = GlobalsSingleton.getInstance().getDancers(); i < MAX_DANCERS; i++) {
			dancers[i].setImageResource(R.drawable.empty);
		}
		if (GlobalsSingleton.getInstance().getcolor()) {
			for (int i = 0; i < 5; i++) {
				drawID[i].clearColorFilter();
			}
		} else {
			for (int i = 0; i < 5; i++) {
				drawID[i].setColorFilter(colorFilter_Negative);
			}
		}
	}
	
	//after a delayed period of time, changes the current drawable image.
	protected class danceloop extends TimerTask {
		private int numDancers;
		danceloop(int numDancers) {
			this.numDancers = numDancers;
		}
	    @Override
	    public void run() {
	    	runOnUiThread( new Runnable() {
	    		
	            @Override
	            public void run() {
	            	if (!moves.isEmpty()) {
	            		for (int i = 0; i < numDancers; i++) {
	            			//if there is another move to peform, perform and remove from queue
	            			dancers[i].setImageResource(moves.get(0));
	            		}
	            		moves.remove(0);
	            	} else {
	            		for (int i = 0; i < numDancers; i++) {
	            			dancers[i].setImageResource(R.drawable.dancedef);
	            		}
	            		//reset queue and displays
	            		TextView t = (TextView) findViewById(R.id.dancin);
	            		t.setText(R.string.empty);
		            	dancing = false;
	            	}	         
	            }
	        } );
	    }
	};
}
