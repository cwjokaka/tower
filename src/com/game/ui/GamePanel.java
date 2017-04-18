package com.game.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

import com.game.math.MyMath;
import com.game.obj.*;
/**
 * 游戏画布类
 * 
 * */
public class GamePanel extends JPanel implements Runnable {
	
	public static Background background;			//背景对象引用
	public static GameLogic gameLogic;				//游戏逻辑引用
	
	private static Image iBuffer = null; 					//双缓冲
	private static Graphics gBuffer = null;
	
	private Viewport viewport = null;		//视点
	
	private Thread refresh = null;
	private static final int FPS = 60;		//帧率
	public static int refreshTime = 30;
	
	public static final int LAYOUT_WIDTH = 900;
	public static final int LAYOUT_HEIGHT = 900;
	
	public GamePanel(){
		//加载视点
		viewport = Viewport.getInstance();
		//计算每次刷新画布的间隔时间
		refreshTime = 1000 / FPS;
		refresh = new Thread(this);
		refresh.start();	
		this.init();
	}
	
	//画布初始化
	private void init(){
		viewport = Viewport.createInstance(GamePanel.LAYOUT_WIDTH/2, GamePanel.LAYOUT_HEIGHT/2, GamePanel.LAYOUT_WIDTH/2, GamePanel.LAYOUT_HEIGHT/2);
		background = new Background();
		gameLogic = new GameLogic();
	}
	
	//刷新画布的线程
	public void run() {
		while (true) {
			try {
				Thread.sleep(refreshTime);
				repaint();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}	
	}
	
	public void paint(Graphics g){
		//g.clearRect(0, 0, GamePanel.LAYOUT_WIDTH, GamePanel.LAYOUT_HEIGHT);
		if (iBuffer == null){
			iBuffer = this.createImage(GamePanel.LAYOUT_WIDTH, GamePanel.LAYOUT_HEIGHT);
			gBuffer = iBuffer.getGraphics();
		}
		gameLogic.callAction();
		gameLogic.drawElement(gBuffer);
			
		viewport.scrollTo(MyMath.lerp(viewport.x, viewport.targetX, 0.1),
						  MyMath.lerp(viewport.y, viewport.targetY, 0.1));
		
		g.drawImage(iBuffer,
					0, 										//目前先固定
					0,										//目前先固定
					GameFrame.SCREEN_WIDTH,					//目前先固定
					GameFrame.SCREEN_HEIGHT,				//目前先固定
					(int)viewport.x - GameFrame.SCREEN_WIDTH/2,	
					(int)viewport.y - GameFrame.SCREEN_HEIGHT/2,
					(int)viewport.x + GameFrame.SCREEN_WIDTH/2,
					(int)viewport.y + GameFrame.SCREEN_HEIGHT/2,
					null);
		
		//分数独立，不生成在缓冲里
		gameLogic.drawData(g);
	}	
}

