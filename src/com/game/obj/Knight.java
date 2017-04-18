package com.game.obj;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import com.game.ui.GameLogic;
import com.game.ui.GamePanel;
import com.game.ui.Res;


public class Knight extends Sprite{

	private int color = 0;			//类型(颜色)
	private double angleToPrincess;	//对中心的弧度
	private int dirCount = -1;		//每点两次才会改变一次方向
	private int dir = 0;			//方向 左 上 右 下分别为0、1、2、3
	private int state = 0;			//有限状态  0=站立  1=攻击  2=死亡
	private int hp = 12;			//生命值
	private int healProgress = 0;	//治疗进程
	private int maxReliveTime = 20;	//初始复活时间
	private int curReliveTime = 20;	//当前复活剩余时间
	
	private static final int MAX_HP = 12;			//最大生命值
	private static final int HEAL_BASE_CD = 6;		//治疗基本CD为6秒
	
	public static final int STATE_STAND = 0;		
	public static final int STATE_ATTACK_1 = 1;
	public static final int STATE_ATTACK_2 = 2;
	public static final int STATE_DEAD = 3;

	//hp显示的颜色
	private static final Color DEEP_GREEN = new Color(0, 180, 80);
	private static final Color GREEN = new Color(50, 255, 120);
	private static final Color YELLOW = new Color(255, 255, 0);
	private static final Color RED = new Color(255, 0, 0);
			
	private Thread tHeal = null;
	
	
	public Knight(int color){
		//this.setImage(Res.greenKnight);
		Animation animation;
		if(color == 1)
			animation = new Animation(Res.grayKnight, 320, 128, 32, 32);
		else 
			animation = new Animation(Res.goldenKnight, 320, 128, 32, 32);
		
		this.setAnimation(animation, this);
		this.color = color;
		tHeal = new Thread(new THeal());
		tHeal.start();
		
	}

	//重写drawSprite方法
	public void drawSprite(Graphics g){
		drawHp(g);
		if (this.state == STATE_STAND)
			this.animation.drawAnimation(g, 0, dir*32, 96, (dir+1)*32);	
		else if (this.state == STATE_ATTACK_1){
			this.animation.drawAnimation(g, 96, dir*32, 192, (dir+1)*32);
			if (this.animation.getCurFrame() % this.animation.getCols() == 6){
				this.state = STATE_STAND;
			}
		}
		else if (this.state == STATE_ATTACK_2){
			this.animation.drawAnimation(g, 192, dir*32, 288, (dir+1)*32);
			if (this.animation.getCurFrame() % this.animation.getCols() == 9){
				this.state = STATE_STAND;
			}
		}
		else if (this.state == STATE_DEAD)
			this.fontSprite.drawSprite(g);
	}
	
	public void drawHp(Graphics g){
		g.setColor(DEEP_GREEN);
		g.fillArc((int)(this.x+this.xOff*getScale()+15), (int)(this.y+this.yOff*getScale()+12), 96, 96, 90, 360 * hp / MAX_HP);
		if (hp > 8)
			g.setColor(GREEN);	
		else if (hp > 4)
			g.setColor(YELLOW);	
		else 
			g.setColor(RED);
		
		g.fillArc((int)(this.x+this.xOff*getScale()+18), (int)(this.y+this.yOff*getScale()+15), 90, 90, 90, 360 * hp / MAX_HP);
		
	}
	
	public void changeHp(int change){
		this.hp += change;
		if (hp > MAX_HP)
			hp = MAX_HP;
		else if (hp < 0)
			hp = 0;
	}
	
	//逆时针转
	public void turnLeft(){
		dirCount++;
		if (dirCount == 2){
			this.dir--;
			dirCount = 0;
		}
		if (dir < 0)
			dir = 3;
		//System.out.println(dir);
	}
	
	//顺时针转
	public void turnRight(){
		dirCount--;
		if (dirCount == -2){
			this.dir++;
			dirCount = 0;
		}
		if (dir > 3)
			dir = 0;
		//System.out.println(dir);
	}
	
	//设置方向
	public void setDir(int dir){
		this.dir = dir;
	}
	
	public int getDir() {
		return dir;
	}
	
	public void setDirCount(int dirCount){
		this.dirCount = dirCount;
	}
	
	public int getColor() {
		return color;
	}
	
	public int getHp() {
		return hp;
	}

	public double getAngleToPrincess() {
		return this.angleToPrincess;
	}
	
	public void setState(int state) {
		this.state = state;
	}

	public int getState() {
		return state;
	}
	
	public int getCurReliveTime() {
		return curReliveTime;
	}

	//参数是角度，设置是弧度
	public void setAngleToPrincess(double angleToPrincess) {
		this.angleToPrincess = Math.toRadians(angleToPrincess);
	}
	
	//设置heal进程
	public void setHealProgress(int healProgress){
		this.healProgress = healProgress;
	}
	
	//治疗线程
	private class THeal implements Runnable{			
		public void run() {
			try {
				while (GameLogic.gameState == GameLogic.GAME_PLAY){
					Thread.sleep(1000);
					//还没死
					if (hp < MAX_HP && state != STATE_DEAD){
						healProgress++;					
						if (healProgress >= (int)HEAL_BASE_CD*((float)hp / MAX_HP) + 3){
							healProgress = 0;
							changeHp(1);
							spawn(new Effect("heal"), GameLogic.effects);			
						}					
					}			
					
					//死了
					if (Knight.this.state == STATE_DEAD){
						Knight.this.curReliveTime--;
						Knight.this.fontSprite.setFont(curReliveTime);
						if(curReliveTime == 0){
							GameLogic.kFonts.remove(fontSprite);
							fontSprite = null;
							state = STATE_STAND;
							hp = MAX_HP;		
							maxReliveTime += 10;
							curReliveTime = maxReliveTime;
							spawn(new Effect("heal"), GameLogic.effects);
						}
					}
				}
			} catch (InterruptedException e) {			
				e.printStackTrace();
			}	
		}	
	}

}
