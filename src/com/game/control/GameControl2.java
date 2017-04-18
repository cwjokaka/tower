package com.game.control;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.game.obj.Knight;
import com.game.ui.GameFrame;
import com.game.ui.GameLogic;
import com.game.ui.GamePanel;
//键盘控制
public class GameControl2 extends KeyAdapter {
	private GamePanel gamePanel;
	private long curTime = 0;
	private static long WAIT_TIME = 100; 
	
	public GameControl2(GamePanel gamePanel){
		this.gamePanel = gamePanel;	
	}
	
	public void keyPressed(KeyEvent e) {
		long time = new Date().getTime();
		//System.out.println(time);
		if (time > curTime + WAIT_TIME){
			curTime = time;
			
			GameLogic gameLogic = gamePanel.gameLogic;
			List<Knight> Knights = gameLogic.knights;
			if (e.getKeyCode() == KeyEvent.VK_RIGHT){
				for (Knight k : Knights) {
					k.setAngleToPrincess(Math.toDegrees(k.getAngleToPrincess()) + 45);
					k.turnRight();
				}
			}
			else if (e.getKeyCode() == KeyEvent.VK_LEFT)
			{
				for (Knight k : Knights) {
					k.setAngleToPrincess(Math.toDegrees(k.getAngleToPrincess()) - 45);
					k.turnLeft();
				}
			}
		}
		
//		for (Knight k : Knights) {
//			System.out.println(Math.round(Math.toDegrees(k.getAngleToPrincess())));
//			
//		}
	}
		
}
