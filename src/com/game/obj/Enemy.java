package com.game.obj;

import java.awt.Graphics;
import java.util.Random;

import com.game.math.MyMath;
import com.game.ui.GameFrame;
import com.game.ui.GameLogic;
import com.game.ui.GamePanel;
import com.game.ui.Res;

public class Enemy extends Sprite{
	
	private int type; 						//种族:1.slim 2.knight 3.caster
	private int color;						//颜色:1.gray 2.gold
	private int speed = 3;					//总速度
	private double speedX = 0;				//横向速度
	private double speedY = 0;				//纵向速度
	private int dir;						//0123左上右下
	private double angleToPrincess = 0d;	//对中心角度
	private int state = 0;					//状态: 0=直线移动   1=改变方向
	private boolean hasChange = false;
	
	public final static int ENEMY_MOVE = 0;
	public final static int ENEMY_TURN = 1;
	public final static int ENEMY_BACK = 2;
	public final static int ENEMY_DEAD = 3;
	
	public static final int CHANGE_RADIUS = 250;	//改变方向时与中心的距离
	public static final int BACK_RADIUS	= 300;		//击退最大半径
	
	//种类枚举
	public final static int SLIM = 1;
	public final static int KNIGHT = 2;
	public final static int CASTER = 3;
	public final static int GRAY = 1;
	public final static int GOLD = 2;
	
	enum Type{
		SLIM,KNIGHT,CASTER
	}
	
	public Enemy(int type, int color){

		Animation animation = null;
		
		if (type == SLIM){
			if (color == GRAY)
				animation = new Animation(Res.e_slim_gray, 96, 128, 32, 32);
			else if (color == GOLD)
				animation = new Animation(Res.e_slim_gold, 96, 128, 32, 32);
		}
		if (type == KNIGHT){
			if (color == GRAY)
				animation = new Animation(Res.e_knight_gray, 96, 128, 32, 32);
			else if (color == GOLD)
				animation = new Animation(Res.e_knight_gold, 96, 128, 32, 32);
		}
		if (type == CASTER){
			if (color == GRAY)
				animation = new Animation(Res.e_caster_gray, 96, 128, 32, 32);
			else if (color == GOLD)
				animation = new Animation(Res.e_caster_gold, 96, 128, 32, 32);
		}
		
//		switch (enemyStr){
//		case "knightgray":
//			animation = new Animation(Res.e_knight_gray, 96, 128, 32, 32);
//			break;
//		case "knightgold":
//			animation = new Animation(Res.e_knight_gold, 96, 128, 32, 32);
//			break;
//		default:
//			animation = null;
//		}
		
		this.setAnimation(animation, this);
		this.type = type;
		this.color = color;
		Thread t = new Thread(new CreateDust());
		t.setName("dust");
		t.start();
//		this.setPosition(GameFrame.SCREEN_WIDTH/2  + RADIUS_TO_CENTER*Math.cos(Math.toRadians(angleToPrincess)),
//						 GameFrame.SCREEN_HEIGHT/2 + RADIUS_TO_CENTER*Math.sin(Math.toRadians(angleToPrincess)));
		//System.out.println(Math.toDegrees(angleToPrincess));
		//System.out.println(this.getX() +" "+this.getY());
	}
	
	//重写drawSprite方法
	public void drawSprite(Graphics g){	                                                                                                       
		this.animation.drawAnimation(g, 0, dir*32, 96, (dir+1)*32);
	}
	
	//行走
	public void enemyMove(){
		//this.setX(this.getX() + this.speed);
		switch (state){
		case ENEMY_MOVE:
			this.x += speedX;
			this.y += speedY;
			if (type != SLIM && !hasChange && MyMath.distance(x, y, GamePanel.LAYOUT_WIDTH/2, GamePanel.LAYOUT_HEIGHT/2) < CHANGE_RADIUS){
				this.state = ENEMY_TURN;		//状态跳转
				this.hasChange = true;
				new Thread(new Timer()).start();//状态跳转计时

				int angle = Math.random() > 0.5 ? 45 : -45;
				this.setAngleToPrincess((int)Math.round(Math.toDegrees(angleToPrincess))-angle);			
			}		
			break;
		case ENEMY_TURN:
			x = MyMath.lerp(x, GamePanel.LAYOUT_WIDTH / 2 + CHANGE_RADIUS * Math.cos(angleToPrincess), 0.15);
			y =	MyMath.lerp(y, GamePanel.LAYOUT_HEIGHT / 2 + CHANGE_RADIUS * Math.sin(angleToPrincess), 0.15);			
			break;
		case ENEMY_BACK:
			x = MyMath.lerp(x, GamePanel.LAYOUT_WIDTH / 2 + BACK_RADIUS * Math.cos(angleToPrincess), 0.15);
			y =	MyMath.lerp(y, GamePanel.LAYOUT_HEIGHT / 2 + BACK_RADIUS * Math.sin(angleToPrincess), 0.15);
			break;
		case ENEMY_DEAD:
			
			break;
		}	
	}

	public void setAngleToPrincess(int angles) {
		int angle = angles % 360;
		if (angle==0 || angle==45)
			this.dir = 0;
		else if (angle==90 || angle==135)
			this.dir = 1;
		else if (angle==180 || angle==225)
			this.dir = 2;
		else 
			this.dir = 3;
		
		this.angleToPrincess = Math.toRadians(angle);
		this.speedX = -Math.cos(this.angleToPrincess) * speed;
		this.speedY = -Math.sin(this.angleToPrincess) * speed;
	}

	public double getAngleToPrincess() {
		return this.angleToPrincess;
	}
	
	public int getColor(){
		return this.color;
	} 
	
	public void setState(int state){
		this.state = state;
	}
	
	public int getState(){
		return state;
	}
	
	public void setTimer(){
		new Thread(new Timer()).start();//状态跳转计时
	}
	
	private class Timer implements Runnable{

		public void run() {
			try {
				Thread.sleep(500);
				if (state == ENEMY_TURN)
					state = ENEMY_MOVE;
				if (state == ENEMY_BACK)
					state = ENEMY_DEAD;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		}	
	}
	
	private class CreateDust implements Runnable{

		public void run() {
			while (Enemy.this.state != Enemy.ENEMY_BACK){
				try {
					Thread.sleep(250);		
					if (Enemy.this.state == Enemy.ENEMY_MOVE){
						Effect e = new Effect("dust");
						e.animation.setPlaySpeed(10);
						spawn(e, GameLogic.effects);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}
}
