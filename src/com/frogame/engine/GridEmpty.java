package com.frogame.engine;

import android.graphics.Canvas;
import android.graphics.Paint;

public class GridEmpty extends Grid
{
    public GridEmpty(int left, int top, int right, int bottom, Paint paint)
    {        
        super(left, top, right, bottom, paint);
        gridElement = 0;
    }

    public void drawGrid(Canvas canvas)
    {
        canvas.drawRect(grid, paint);
    }
}
