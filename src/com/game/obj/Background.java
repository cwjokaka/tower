package com.game.obj;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import com.game.ui.GameFrame;
import com.game.ui.Res;
//地图类
public class Background extends Sprite{

	public void drawBackGround(Graphics g){
		//g.setColor(new Color(74,128,64));
		//g.fillRect(0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT);
		this.setImage(Res.background);	
		g.drawImage(this.getImage(), 0, 0, null);
		//g.drawImage(this.getImage(), -100, -100, 900, 900, null);

	}
}
