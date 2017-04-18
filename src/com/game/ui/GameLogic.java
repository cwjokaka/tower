package com.game.ui;
import java.awt.Graphics;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import com.game.math.MyMath;
import com.game.obj.Arrow;
import com.game.obj.Background;
import com.game.obj.Effect;
import com.game.obj.Enemy;
import com.game.obj.FontSprite;
import com.game.obj.Princess;
import com.game.obj.Knight;
import com.game.obj.Sprite;
import com.game.obj.Viewport;
/**
 * 游戏逻辑类
 * */
public class GameLogic{
	
	public static List<Knight> knights = null;
	public static List<Enemy> enemys = null;
	public static List<Sprite> effects = null;
	public static List<Arrow> arrows = null;
	public static List<FontSprite> fonts = null;
	public static List<FontSprite> kFonts = null;
	public static Princess princess = null;
	public static Background background = null;
	
	private Thread tCreateEnemy = null;			//生产敌人的线程
	
	public static final int RADIUS = 130;				//圆半径
	public static final int BIG_RADIUS = 600;			//敌人产生的范围半径
	public static final int CAMERA_RADIUS = 50;			//镜头移动半径
	
	public static int gameState = 1;			//游戏状态：0开始，1进行，2结束
	
	public static final int GAME_START = 0;
	public static final int GAME_PLAY = 1;
	public static final int GAME_END = 2;
	
	public GameLogic(){
		knights = new CopyOnWriteArrayList<Knight>();
		enemys = new CopyOnWriteArrayList<Enemy>();
		effects = new CopyOnWriteArrayList<Sprite>();
		kFonts = new CopyOnWriteArrayList<FontSprite>();
		fonts = new CopyOnWriteArrayList<FontSprite>();
		arrows = new CopyOnWriteArrayList<Arrow>();
		
		FontSprite fs = new FontSprite(Res.font_sprite, 320, 32, 32, 32, 1);
		fs.setPosition(50, 50);
		fonts.add(fs);
		tCreateEnemy = new Thread(new TCreateEnemy());
		tCreateEnemy.start();	
		init();
	}
	
	//游戏布局初始化
	public void init(){
		//just test
//		Arrow a = new Arrow(30);
//		a.setPosition(200, 200);
//		arrows.add(a);
		
		//背景
		background = new Background();
		//中心的对象
		princess = new Princess();
		princess.setPosition(GamePanel.LAYOUT_WIDTH / 2, GamePanel.LAYOUT_HEIGHT / 2);
		//System.out.println("princess"+princess.getY());
		princess.setOff(-32, -32);
		
		//初始化八位骑士的出生位置
		int dir = 2;
		Knight k;
		for (int i=0; i<8; i++){
			//颜色设置
			if (i % 2 == 0)
				k = new Knight(1);
			else
				k = new Knight(2);
			
			//角度设置
			k.setAngleToPrincess(i * 45);
			
			//初始化方向
			k.setDir(dir);
			if ((i+1) % 2 == 0){
				dir++;		
			}
			if (dir == 4)
				dir = 0;
			
			k.setPosition((GamePanel.LAYOUT_WIDTH / 2), 
					  (GamePanel.LAYOUT_HEIGHT / 2 ));
			k.setOff(-32, -32);
			knights.add(k);
		}
			
	}
	
	//每帧执行
	public void moveKnight(){
		for (Knight k : knights) {
			//lerp(Self.X,Princess.X+cos(Self.AngleToPrincess)*150,0.15)
			k.setPosition(MyMath.lerp(k.getX(), GamePanel.LAYOUT_WIDTH / 2 + RADIUS * Math.cos(k.getAngleToPrincess()), 0.15), 
						  MyMath.lerp(k.getY(), GamePanel.LAYOUT_HEIGHT / 2 + RADIUS * Math.sin(k.getAngleToPrincess()), 0.15));
		}
	}
	
	public void drawElement(Graphics g){
		//绘制背景
		background.drawBackGround(g);
		//绘制箭头
		for (Arrow a : arrows){
			a.drawSprite(g);
		}
		//绘制princess
		princess.drawSprite(g);
		//绘制骑士
		for (Knight k : knights) {
			k.drawSprite(g);		
		}
		
		//绘制特效
		for (Sprite e : effects){
			e.drawSprite(g);
		}
		
		//绘制敌人
		for (Enemy e : enemys){
			e.drawSprite(g);
		}
		
		//绘制动态文字
		for (FontSprite kfs : kFonts){
			kfs.drawSprite(g);
		}
	
	}
	
	public void drawData(Graphics g){
		//绘制文字
		for (FontSprite fs : fonts){
			fs.drawSprite(g);
		}
	}
	
	//游戏行为&逻辑
	public void callAction(){
		moveKnight();
		moveEnemy();
		collision();
		checkDeath();
	}
	
	//检查死亡
	public void checkDeath(){
		for (Enemy e : enemys){
			if (e.getState() == Enemy.ENEMY_DEAD){
				e.spawn(new Effect("boom"), effects);
				enemys.remove(e);
			} 
		}
	}

	//移动
	private void moveEnemy(){
		for (Enemy e : enemys)
		{
			e.enemyMove();
		}
	}
	
	//碰撞事件
	private void collision(){		
		for (Knight k : knights){
			for (Enemy e : enemys){
				if(isCollision(princess, e)){
					e.setState(Enemy.ENEMY_DEAD);
				}
				if(isCollision(k, e)){
					if (k.getState() != Knight.STATE_DEAD && e.getState() != Enemy.ENEMY_BACK){
						k.changeHp(k.getColor() == e.getColor() ? -2 : -3);
						
						e.setState(Enemy.ENEMY_BACK);
						e.setTimer();
						//回复进程归零
						k.setHealProgress(0);
						
						//被打死
						if(k.getHp() <= 0){
							k.setState(Knight.STATE_DEAD);
							FontSprite f = new FontSprite(Res.font_sprite, 320, 32, 32, 32, 20, k);
							f.setFont(k.getCurReliveTime());
							kFonts.add(f);
							k.setFontSprite(f);
						}
						else{
							//击打效果
							Effect eff = null;
							if (k.getColor() == e.getColor()){
								eff = new Effect("attack_1");
								k.setState(Knight.STATE_ATTACK_1);
								k.getAnimation().setCurFrame(k.getDir() * k.getAnimation().getCols() + 3);
							}else{
								eff = new Effect("attack_2");
								k.setState(Knight.STATE_ATTACK_2);
								k.getAnimation().setCurFrame(k.getDir() * k.getAnimation().getCols() + 6);
						
							}
							
							k.spawn(eff, effects);
							eff.setPosition((k.getX() + e.getX()) / 2, (k.getY() + e.getY()) / 2);
						}
					}
				}
			}
		}
		
	}
	
	//碰撞检测(矩形)
	private boolean isCollision(Sprite s1, Sprite s2){
		
		if (s1.getX() + 32 <= s2.getX() || s1.getY() + 32 <= s2.getY() 
			|| s1.getX() >= s2.getX() + 32 || s1.getY() >= s2.getY() + 40 ){
			return false;	
		}	
		return true;	
	}
	
	//制作敌人
	private class TCreateEnemy implements Runnable{
		
		private int count = 3;	//生成存余数 	
		private int dir = 0;	//方向顺时针0-7		
		private int type = 1;	//生成种族
		private int color = 1;	//生成颜色
		private Random r = new Random();
		
		public void run() {
			while (gameState == GAME_PLAY){
				try {
					if (count > 0){
						dir = (int)Math.floor(Math.random()*8);
						//设置颜色种族
						type = Math.abs(r.nextInt()%3) + 1;
						color = Math.abs(r.nextInt()%2) + 1;
						Enemy e = new Enemy(type, color);
						e.setAngleToPrincess(dir * 45); 
						e.setPosition(GamePanel.LAYOUT_WIDTH/2 + BIG_RADIUS * Math.cos(e.getAngleToPrincess()),
									  GamePanel.LAYOUT_HEIGHT/2 + BIG_RADIUS * Math.sin(e.getAngleToPrincess()));
						e.setOff(-32, -32);
						Viewport.getInstance().setTarget(GamePanel.LAYOUT_WIDTH/2 + CAMERA_RADIUS * Math.cos(e.getAngleToPrincess()),
														 GamePanel.LAYOUT_HEIGHT/2 + CAMERA_RADIUS * Math.sin(e.getAngleToPrincess()));
						enemys.add(e);
						count--;
					}else {
						count = 3;
						Thread.sleep(1500);
					}
					
					//额外清理以下内存
					for (Sprite effect : effects){
						if (effect.getAnimation() == null)
							effects.remove(effect);
					}
					
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}	
		}
	}	
	
	
}
