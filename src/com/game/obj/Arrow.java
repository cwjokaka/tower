package com.game.obj;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.game.ui.GamePanel;
import com.game.ui.Res;

/**
 * 标示箭头
 * */
public class Arrow extends Sprite {
	
	private double angle;
	
	public Arrow(double angle){
		this.setImage(Res.arrow);
		this.angle = angle;
		this.setOff(-100, -35);
	}
	
	public void drawSprite(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.drawImage(image, (int)x+xOff, (int)y+yOff, null);

	}
}
