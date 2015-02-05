package com.frogame.engine;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public abstract class Grid {
	public Paint paint = new Paint();
	public Rect grid = new Rect();
	public int gridElement;
	public String color;
	public boolean selected = false;
	public Grid(int left, int top, int right, int bottom, Paint paint) {
		grid.left = left;
		grid.top = top;
		grid.right = right;
		grid.bottom = bottom;
		this.paint = paint;
	}
	
	public abstract void drawGrid(Canvas canvas);
	
	public boolean isInGrid(int x, int y)
    {
        return grid.contains(x, y);
    }
	public void setColor(String color)
    {
    	this.color = color;
    }
    
    public String getColor()
    {
        return color;
    }
    public int getGridType()
    {
        return gridElement;
    }
        
    public int centerX()
    {
        return grid.centerX();
    }
    
    public int centerY()
    {
        return grid.centerY();
    }
}
