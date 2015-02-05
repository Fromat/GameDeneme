package com.frogame.engine;

import java.util.ArrayList;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

public class Board {

	int[][] board = {
			  {0, 1, 0, 0, 0, 0, 0, 2}, 
			  {0, 3, 0, 0, 0, 0, 4, 0}, 
			  {0, 0, 0, 0, 1, 0, 0, 0}, 
			  {0, 0, 0, 0, 0, 0, 2, 0}, 
			  {0, 0, 0, 5, 0, 0, 0, 0}, 
			  {0, 3, 4, 0, 0, 0, 0, 0}, 
			  {0, 0, 0, 0, 0, 0, 0, 0}, 
			  {0, 0, 0, 0, 0, 0, 0, 5}
			};
	public int board_row, board_column;
	public Grid grids[][];
	public int dimension = 8;
	public int board_width,board_height;
	public int grid_width , grid_height;
	public Point board_location;
	public Paint gridPaint = new Paint();
	public ArrayList<String> sourceInGrid = new ArrayList<String>();
	
	String[] colors = {"red","blue","yellow","green","cyan","gray"};
	
	public Board(int dimension, int width, int height)  
	{
		this.dimension = dimension;
		this.board_width = width;
		this.board_height = height;
		this.board_location = new Point(0, 1176 - board_height);
		setPaintAttributes();
		grid_width  = board_width  / dimension;
		grid_height = board_width  / dimension;
	}
	
	public void setPaintAttributes()
	{
	
		gridPaint.setAntiAlias(true);
		gridPaint.setColor(Color.parseColor("#cdc9c9"));
		gridPaint.setStyle(Paint.Style.STROKE);
		

	}
	
	public void CreateBoard()
	{
		grids = new Grid[dimension][dimension];
		
		for (int i = 0; i < dimension; i++ ) 
			for (int j = 0; j < dimension; j++ ) 
			{
				int type = board[i][j];

				if(type == 0){
					grids[i][j] = new GridEmpty((int) (j * grid_width) ,	//left
							(int) (board_location.y + i * grid_height),		//top
							(int) ((j+1) * grid_width),						//right
							(int) (board_location.y + (i+1) * grid_height),	//bottom
							gridPaint);	
				}
				else if(type !=0)
				{
					String color = colors[board[i][j]];
					if(!sourceInGrid.contains(color))
					{
						sourceInGrid.add(color);
					}
					grids[i][j] = new GridSource((int) (j * grid_width) ,	//left
							(int) (board_location.y + i * grid_height),		//top
							(int) ((j+1) * grid_width),						//right
							(int) (board_location.y + (i+1) * grid_height),	//bottom
							color,
							gridPaint);	

				}
			}

	}

	public void drawBoard(Canvas canvas)
	{
		for (int i = 0; i < dimension ; i++) 
		{
			for (int j = 0; j < dimension ; j++)
			{
				grids[i][j].drawGrid(canvas);
			}
		}	
	}
}
