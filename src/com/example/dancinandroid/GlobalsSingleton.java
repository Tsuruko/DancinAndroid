/*********************************
 * UCSD VIS 141A project
 * DancinAndroid 
 * 
 * Created By: Monica Liu
 * Last Modified 2/10/14
 ********************************/

package com.example.dancinandroid;

public class GlobalsSingleton {
	
	private int numDancers = 1;  //MIN_DANCERS
	private boolean color = true;
	
	public int getDancers() {
		return numDancers;
	}
	
	public boolean getcolor() {
		return color;
	}
	
	public void setDancers(int dancers) {
		this.numDancers = dancers;
	}
	
	public void setcolor(boolean data) {
		this.color = data;
	}
	
	private static final GlobalsSingleton globals = new GlobalsSingleton();
	public static GlobalsSingleton getInstance() {
		return globals;
	}
}
