package com.game.obj;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.imageio.ImageIO;

import com.game.ui.GamePanel;

/*
 *精灵类
 *继承自Node
 * */

public class Sprite extends Node{
	
	private int width = 0;
	private int height = 0;
	
	//静态图片
	protected Image image = null;
	//动画
	protected Animation animation = null;
	//动画组
	protected HashMap<String, Animation> animationMap = null;
	//文字
	protected FontSprite fontSprite = null;
	
	public void drawSprite(Graphics g){
		//优先显示文字素材
		if (fontSprite != null){
			fontSprite.drawSprite(g);
		}
		//如果该精灵只是一张静态图，则只显示图片
		else if (image != null)
			g.drawImage(image, (int)x+xOff, (int)y+yOff, null);
		//如果是动画或包含各种动画组，则播放动画
		else if (animation != null)	
			this.animation.drawAnimation(g);
	}
	
	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	
	public Animation getAnimation() {
		return animation;
	}

	public void setAnimation(Animation animation, Sprite sprite) {
		this.animation = animation;
		animation.setCurSprite(sprite);
		
	}

	public void setFontSprite(FontSprite fontSprite) {
		this.fontSprite = fontSprite;
	}

	public int getWidth() {
		if (width == 0)
			width = image.getWidth(null);// * (int)this.getScale();
		
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		if (height == 0)
			height = image.getHeight(null);// * (int)this.getScale();
		
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public double getScale(){
		return animation.scale;
	}
	
	//动画组
	public void addAnimation(String animationName, Animation animation, Sprite sprite){
		animation.setCurSprite(sprite);
		this.animationMap.put(animationName, animation);
	}
	
	//设置当前播放的动画(针对)
	public void setCurAnimation(String animationName){
		this.animation = animationMap.get(animationName);	
	}
	
	public void removeAnimation(){
		this.animation = null;
	}
	
	//原地生成其他对象
	public void spawn(Sprite sprite, List<Sprite> List){
		sprite.setPosition(x, y);
		sprite.setOff(this.xOff, this.yOff);
		List.add(sprite);
	}
	
	/*private class PlayAnimation implements Runnable{

		public void run() {	
			try {
				Thread.sleep(GamePanel.refreshTime);
				animation.
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}	
	}*/
}
