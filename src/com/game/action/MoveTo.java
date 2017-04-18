package com.game.action;

import com.game.obj.Sprite;
import com.game.ui.GamePanel;

public class MoveTo extends Action implements Runnable{

	private double x;
	private double y;
	private double speed;
	private double speedX;
	private double speedY;
	
	public MoveTo(double x, double y, double speed, Sprite s) {
		super();
		this.x = x;
		this.y = y;
		this.speed = speed;
	}

	@Override
	public void runAction() {
		new Thread(this);
	}


	@Override
	public void run() {
		while (isPlaying){
			try {
				
				Thread.sleep(GamePanel.refreshTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
