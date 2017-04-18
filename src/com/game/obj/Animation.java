package com.game.obj;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
/**
 * 动画类
 * */
public class Animation {
	
	private double x = 0;			//动画的坐标X & Y(与Sprite无关)
	private double y = 0;
	private int perWidth;			//每帧的宽度
	private int perHeight;			//每帧的高度
	protected float scale = 2.0f;	//图像大小比例
	private int rows;				//一共多少行
	private int cols;				//一共多少列
	private int nowProgress = 0;	//当前每帧的进度
	private int playSpeed = 10;		//播放速度
	private Image image;			//整张图片
	private int curFrame = 0;		//当前帧
	private boolean isPlay = true;	//正在播放
	private boolean loop = true;	//是否循环
	private boolean destroyOnEnd = false;		//结束后是否销毁
	private Sprite curSprite = null;//当前精灵的引用
	
	public Animation(Image image, int width, int height, int perWidth, int perHeight){
		this.image = image;	
		this.perWidth = perWidth;
		this.perHeight = perHeight;
		this.rows = height / perHeight;
		this.cols = width / perWidth;
	}
	
	//播放整张动画
	public void drawAnimation(Graphics g){
		//g.drawImage(image, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
		//计算偏移量之后的坐标
		x = curSprite.x + curSprite.xOff;
		y = curSprite.y + curSprite.yOff;
		g.drawImage(image, (int)x, (int)y, 					//显示在画布上的坐标
					(int)(x + perWidth * scale), 
					(int)(y + perHeight * scale), 
					curFrame % cols * perWidth,				//显示的区域
					curFrame / cols % rows * perHeight,
					curFrame % cols * perWidth + perWidth,
					curFrame / cols % rows * perHeight + perHeight, 
					null);
		
		if (isPlay){
			if (nowProgress % playSpeed==0){
				curFrame++;	
				nowProgress++;
				if (!loop && destroyOnEnd && curFrame == cols*rows-1){
					this.curSprite.removeAnimation();
				}
			}
			else
				nowProgress++;
		}
	}

	//播放整张动画的部分区域
	public void drawAnimation(Graphics g, int dx1, int dy1, int dx2, int dy2){
		int cols = (dx2 - dx1) / perWidth;		//3
		int rows = (dy2 - dy1) / perHeight;		//1

		//计算偏移量之后的坐标
		x = curSprite.x + curSprite.xOff;
		y = curSprite.y + curSprite.yOff;
		g.drawImage(image, (int)x, (int)y, 					//显示在画布上的坐标
					(int)(x + perWidth * scale), 
					(int)(y + perHeight * scale), 
					curFrame % cols * perWidth + dx1 ,				//显示的区域
					curFrame / cols % rows * perHeight + dy1,
					curFrame % cols * perWidth + perWidth + dx1,
					curFrame / cols % rows * perHeight + perHeight + dy1, 
					null);

		if (isPlay){
			if (nowProgress % playSpeed==0){
				curFrame++;	
				nowProgress++;
				if (!loop && destroyOnEnd && curFrame == cols*rows-1){
					this.curSprite.removeAnimation();
				}
			}
			else
				nowProgress++;
		}
	}

	public int getPerWidth() {
		return perWidth;
	}

	public void setPerWidth(int perWidth) {
		this.perWidth = perWidth;
	}

	public int getPerHeight() {
		return perHeight;
	}

	public void setPerHeight(int perHeight) {
		this.perHeight = perHeight;
	}
	
	public Image getImage(){
		return this.image;
	}

	public Sprite getCurSprite() {
		return curSprite;
	}

	public void setCurSprite(Sprite curSprite) {
		this.curSprite = curSprite;
	}
	
	public void setPlaySpeed(int playSpeed){
		if (playSpeed > 1)
			this.playSpeed = playSpeed;
		else
			this.playSpeed = 1;
	}

	public int getCols() {
		return cols;
	}

	public int getCurFrame() {
		return curFrame;
	}

	public void setCurFrame(int curFrame) {
		this.curFrame = curFrame;
	}

	public boolean GetIsPlay() {
		return isPlay;
	}

	public void setIsPlay(boolean isPlay) {
		this.isPlay = isPlay;
	}
	
	public double getScale(){
		return scale;
	}

	public boolean isDestroyOnEnd() {
		return destroyOnEnd;
	}

	public void setDestroyOnEnd(boolean destroyOnEnd) {
		this.destroyOnEnd = destroyOnEnd;
	}
	
	public void setLoop(boolean loop){
		this.loop = loop;
	}
	
}
