package com.fromat.gamedeneme;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

import com.frogame.engine.Board;
import com.frogame.engine.GameLogic;
import com.frogame.engine.GamePath;
import com.frogame.engine.Grid;
import com.frogame.engine.InputObject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{

	public Object instance = new Object();
	static Board mboard;
	static int gridNumber;
	Canvas mCanvas;
	Paint mPaint;
	GameLogic mGameLogic;
	private ArrayBlockingQueue<InputObject> inputObjectPool;
	static ArrayList<GamePath> paths   = new ArrayList<GamePath>();
	static ArrayList<Grid> selectedList   = new ArrayList<Grid>();
	GamePath selectedPath = new GamePath();
	
	static int screenHeight;
	static int screenWidth;
	boolean pathSelected = false;
    public static int finishedPathNumber;
    static boolean levelFinished;
    public static int levelNumber;
	
	
	public GameView(Context context) {
		super(context);	
		createInputObjectPool();
		setPaintAttributes();
		mCanvas	= new Canvas();
		gridNumber = 8;
		mboard = new Board(gridNumber, 800, 800); //default 
		mboard.CreateBoard();
		
		
		mGameLogic = new GameLogic(getHolder(), this);
		getHolder().addCallback(this);
	}	
	
	private void createInputObjectPool() {
		// TODO Auto-generated method stub
		inputObjectPool = new ArrayBlockingQueue<InputObject>(20);
		for (int i = 0; i < 20; i++) {
			inputObjectPool.add(new InputObject(inputObjectPool));
			}
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		mGameLogic.setGame_state(GameLogic.RUNNING);
		mGameLogic.start();
		for(int i = 0; i < mboard.sourceInGrid.size(); i++)
    	{
    	    	paths.add(new GamePath(mboard.sourceInGrid.get(i)));
    	}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public static void setPaths()
	    {
		    mboard = new Board(gridNumber, screenWidth, screenWidth);
			mboard.CreateBoard();
	    	for(int i = 0; i < mboard.sourceInGrid.size(); i++)
	    	{
	    	    	paths.add(new GamePath(mboard.sourceInGrid.get(i)));
	    	}
	    }
	 
	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		
		mPaint.setStrokeWidth(mboard.grid_width * 0.15f);
		
		mboard.drawBoard(canvas);
		
		
  	    //selectedPath.drawPath(canvas, mPaint);
		
  	    for(int i = 0; i < paths.size(); i++)
  	    {
  	    	mPaint.setColor(Color.parseColor(paths.get(i).getColor()));
  	    	paths.get(i).drawPath(canvas, mPaint);		    	
  	    }
	}
	
	public void uptade(int delta_time) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	public void selectPath(String color)         
	{
        pathSelected = true;
	    for(int i = 0; i < paths.size(); i++)
	    {
	        if(paths.get(i).getColor() == color)
	        {
	            selectedPath = paths.get(i);
	            return;
	        }
	    }
	    pathSelected = false;
	}
	
	Grid lastGrid;
	
	void completeFlow()
	{
		
		for (Grid c : selectedList) {
			c.selected = false;
		}
		selectedList.clear();
		lastGrid = null;
	}
	
	private void touch_start(int x, int y) {
		// TODO Auto-generated method stub
		Grid selectedGrid = mboard.grids[y][x];

		int mX = selectedGrid.centerX();
		int mY = selectedGrid.centerY();

	
		
		if(selectedGrid.gridElement == 1){
		selectPath(selectedGrid.getColor());
		//mPaint.setColor(Color.parseColor(selectedGrid.getColor()));
		selectedPath.addPoint(mX, mY);
		}
		
		else if( !selectedPath.isEmpty() && selectedGrid.getColor() != null )
        {
            selectPath(selectedGrid.getColor());
            selectedPath.addPoint(mX, mY);
            //Log.i("touchstart_setPointo", "selectedPath" + String.valueOf(selectedPath.getColor()));
        }
	}
	private void touch_move(int x, int y) {
		// TODO Auto-generated method stub
	   
		int mX = mboard.grids[y][x].centerX();
		int mY = mboard.grids[y][x].centerY();
		
		if(mboard.grids[y][x].gridElement != 1)
		selectedPath.addPoint(mX, mY);
			
	}
	
	public void setPaintAttributes()
	{
		mPaint = new Paint();
    	mPaint.setAntiAlias(true);
    	mPaint.setDither(true);
    	mPaint.setColor(Color.BLUE);
    	mPaint.setStyle(Paint.Style.STROKE);
    	mPaint.setStrokeJoin(Paint.Join.ROUND);
    	mPaint.setStrokeCap(Paint.Cap.ROUND);
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,int bottom) {
		// TODO Auto-generated method stub
		super.onLayout(changed, left, top, right, bottom);
		mboard.board_width = right;
		mboard.board_height = right;
		screenHeight = bottom;
		screenWidth = right;
		
	}

	public void processMotionEvent(InputObject input) {
		// TODO Auto-generated method stub
		 int X = input.x / mboard.grid_width;
		 int Y = (input.y - mboard.board_location.y) / mboard.grid_width;
		
		if(input.action == InputObject.ACTION_TOUCH_DOWN ){
			touch_start(X,Y);
			Log.i("gamedeneme", "basýldý" + input.x + " "+  input.y);
		}
		else if(input.action == InputObject.ACTION_TOUCH_MOVE)
			touch_move(X,Y);
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		try {
			int hist = event.getHistorySize();
			if (hist > 0) {
				for (int i = 0; i < hist; i++) {
					InputObject input = inputObjectPool.take();
					input.useEventHistory(event, i);
					mGameLogic.feedInput(input);
				}
			}
			InputObject input = inputObjectPool.take();
			input.useEvent(event);
			mGameLogic.feedInput(input);
		} catch (InterruptedException e) {
		}
		try {
			Thread.sleep(16);
		} catch (InterruptedException e) {
		}
		return true;
	}

	
	

}
