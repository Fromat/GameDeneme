package com.frogame.engine;

import java.util.concurrent.ArrayBlockingQueue;

import android.view.MotionEvent;

public class InputObject {

	public static final int ACTION_TOUCH_DOWN = 1;
	public static final int ACTION_TOUCH_MOVE = 2;
	public static final int ACTION_TOUCH_UP = 3;
	public ArrayBlockingQueue<InputObject> pool;
	public long time;
	public int action;
	public int x;
	public int y;
	
	public InputObject(ArrayBlockingQueue<InputObject> pool) {
		this.pool = pool;
	}
	
	public void useEvent(MotionEvent event) {
		int eventAction = event.getAction();
		switch (eventAction) {
		case MotionEvent.ACTION_DOWN:
			action = ACTION_TOUCH_DOWN;
			break;
		case MotionEvent.ACTION_MOVE:
			action = ACTION_TOUCH_MOVE;
			break;
		case MotionEvent.ACTION_UP:
			action = ACTION_TOUCH_UP;
			break;
		default:
			action = 0;
		}
		time = event.getEventTime();
		x = (int) event.getX();
		y = (int) event.getY();
	}
	
	public void useEventHistory(MotionEvent event, int historyItem) 
	{
		action = ACTION_TOUCH_MOVE;
		
		time = event.getHistoricalEventTime(historyItem);
		x = (int) event.getHistoricalX(historyItem);
		y = (int) event.getHistoricalY(historyItem);
	}
	
	public void returnToPool() {
		pool.add(this);
		}
}
