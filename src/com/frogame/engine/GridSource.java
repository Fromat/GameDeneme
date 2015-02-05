package com.frogame.engine;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class GridSource extends Grid{

	public Paint sourcePaint = new Paint();
	public GridSource(int left, int top, int right, int bottom,String color, Paint paint) {
		super(left, top, right, bottom, paint);
		// TODO Auto-generated constructor stub
		gridElement = 1;
		this.color = color;
		sourcePaint.setColor(Color.parseColor(color));
	}

	@Override
	public void drawGrid(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawRect(grid, paint);
        canvas.drawCircle(grid.exactCenterX(), grid.exactCenterY(), (float) (grid.height()*0.3), sourcePaint);
	}

}
