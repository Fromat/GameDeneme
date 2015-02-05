package com.frogame.engine;

import java.util.concurrent.ArrayBlockingQueue;

import com.fromat.gamedeneme.GameView;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

@SuppressLint("WrongCall")
public class GameLogic extends Thread{
	private SurfaceHolder surfaceHolder;
	private GameView mGameView;
	private int game_state;
	private long time_orig;
	private long time_interim;

	public static final int PAUSE = 0;
	public static final int READY = 1;
	public static final int RUNNING = 2;
	
	private ArrayBlockingQueue<InputObject> inputQueue = new ArrayBlockingQueue<InputObject>(20);
	private Object inputQueueMutex = new Object();

	public GameLogic(SurfaceHolder surfaceHolder, GameView mGameView) {
		super();
		this.surfaceHolder = surfaceHolder;
		this.mGameView = mGameView;
	}

	public int getGame_state() {
		return game_state;
	}

	public void setGame_state(int game_state) {
		this.game_state = game_state;
	} 

	@Override
	public void run() {
		Canvas canvas;

		while (game_state == RUNNING) {

			canvas = null;

			try{
				canvas = this.surfaceHolder.lockCanvas();
				synchronized (surfaceHolder) {
					try {
						Thread.sleep(30);
					} catch (InterruptedException e1) {
					}
					time_interim = System.currentTimeMillis();
					int delta_time = (int)(time_interim - time_orig);
					mGameView.uptade(delta_time);
					processInput(); //this is the new way to process input.
					time_orig = time_interim;
					this.mGameView.draw(canvas); 
				}
					
				
			}
			finally{
				if (canvas != null) {
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
		}

	}

	private void processInput() {
		// TODO Auto-generated method stub
		synchronized(inputQueueMutex) {
			ArrayBlockingQueue<InputObject> inputQueue = this.inputQueue;
			while (!inputQueue.isEmpty()) {
				try {
					InputObject input = inputQueue.take();
					mGameView.processMotionEvent(input);
					input.returnToPool();
				} catch (InterruptedException e) {
				}
			}
		}
	}

	public void feedInput(InputObject input) {
		// TODO Auto-generated method stub
		synchronized(inputQueueMutex) {
			try {
				inputQueue.put(input);
			} catch (InterruptedException e) {
			}
		}
	}




}
