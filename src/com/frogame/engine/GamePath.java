package com.frogame.engine;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

public class GamePath {

	public Path mPath;
	public String color;
	public boolean pathFinished;
	public ArrayList<Point> pointList  = new ArrayList<Point>();
	public ArrayList<Point> removeList  = new ArrayList<Point>();

	public GamePath() {
		mPath = new Path();
	}

	public GamePath(String color)
	{
		this.color = color;
		mPath = new Path();
	}

	public void addPoint(int x, int y)
	{
		pointList.add(new Point(x, y));
	}

	public void setColor(String color)
	{
		this.color = color;
	}

	public String getColor()
	{
		return color;
	}

	public void reset()
	{
		pointList.clear();
		mPath.reset();
		pathFinished = false;

	}

	public Point getLastPoint()
	{
		if(!pointList.isEmpty())
			return pointList.get(pointList.size()-1);
		return null;
	}

	public void drawPath(Canvas canvas, Paint paint) 
	{
		mPath.reset();
		for(int i=0; i < pointList .size(); i++)
		{
			if( i == 0 ) mPath.moveTo(pointList.get(i).x, pointList.get(i).y);
			else
			{
				mPath.lineTo(pointList.get(i).x, pointList.get(i).y);
			}
		}
		canvas.drawPath(mPath, paint);
	}

	public void setPointTo(int x, int y)
	{
		if( pointList.size() != 0 )
		{
			for(int i=pointList.size()-1; i != 0; i--)
			{
				if( pointList.get(i).equals(new Point(x,y)) )
				{
					break;
				}
				else
				{                    
					pointList.remove(i);
				}
			}	
		}

	}

	public boolean isEmpty()
	{
		return pointList.isEmpty();
	}

	public boolean isInPath(int x, int y)
	{
		return pointList.contains(new Point(x,y));
	}
}
