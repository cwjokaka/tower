package com.game.obj;

import com.game.ui.GameFrame;
import com.game.ui.GamePanel;
/**
 * 视点
 * */
public class Viewport {
	
	public double x;
	public double y;
	public double targetX;
	public double targetY;
	
	private static Viewport viewport = null;
	
	private Viewport(){}
	
	private Viewport(double x, double y, double targetX, double targetY){
		this.x = x;
		this.y = y;
		this.targetX = targetX;
		this.targetY = targetY;
	}
	
	public static Viewport createInstance(double x, double y, double targetX, double targetY){
		viewport = new Viewport(x, y, targetX, targetY);
		return viewport;
	}
	
	public static Viewport getInstance(){
		if (viewport == null)
		{
			viewport = new Viewport();
		}
		return viewport;
	}
	
	public void scrollTo(double d, double e){
		this.x = d;
		this.y = e;
	}
	
	public void setTarget(double targetX, double targetY){
		this.targetX = targetX;
		this.targetY = targetY;
	}
	
}
