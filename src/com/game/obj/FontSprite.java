package com.game.obj;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

/**
 * 精灵文字类
 * */

public class FontSprite extends Node {
	
	private Image image = null;
	private int perWidth;			//每帧的宽度
	private int perHeight;			//每帧的高度
	private int rows;				//一共多少行
	private int cols;				//一共多少列
	private int curFrame = 1;		//当前帧
	protected float scale = 1.0f;	//图像大小比例
	private Node curSprite = null;	
	
	private int num = 0;
	private int fontCount = 0;		//文字的位数(至少一位)
	
	

	public FontSprite(Image image, int width, int height, int perWidth, int perHeight, int num){
		this.image = image;
		this.perWidth = perWidth;
		this.perHeight = perHeight;
		this.cols = width/perWidth;
		this.rows = height/perHeight;
		this.num = num;

		while (num / 10 != 0){
			num /= 10;
			fontCount++;
		}
	}
	
	public FontSprite(Image image, int width, int height, int perWidth, int perHeight, int num, Sprite sprite){
		this(image, width, height, perWidth, perHeight, num);
		this.curSprite = sprite;
	}
	
	public void drawSprite(Graphics g){
		
		if(this.curSprite != null){
			this.x = curSprite.x + curSprite.xOff;
			this.y = curSprite.y + curSprite.yOff;
		}
													//某位数
		for (int i=fontCount; i>-1; i--){
			curFrame = num / (int)Math.pow(10, i) % 10;		
			g.drawImage(image,
					(int)x + perWidth * (fontCount - i), 
					(int)y, 						//显示在画布上的坐标
					(int)(x + perWidth * scale + perWidth * (fontCount - i)), 
					(int)(y + perHeight * scale), 
					curFrame % cols * perWidth,						//显示的区域
					curFrame / cols % rows * perHeight,
					curFrame % cols * perWidth + perWidth,
					curFrame / cols % rows * perHeight + perHeight, 
					null);
		}	
	}
	
	public void setFont(int num){
		this.num = num;
		fontCount = 0;
		
		while (num / 10 != 0){
			num /= 10;
			fontCount++;
		}
	}
	
}
