package com.game.control;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;

import com.game.obj.Knight;
import com.game.ui.GameFrame;
import com.game.ui.GameLogic;
import com.game.ui.GamePanel;
//鼠标控制
public class GameControl extends MouseAdapter{
	
	private GamePanel gamePanel;
	
	public GameControl(GamePanel gamePanel){
		this.gamePanel = gamePanel;
	}
	
	public void mousePressed(MouseEvent e) {
		GameLogic gameLogic = gamePanel.gameLogic;
		List<Knight> Knights = gameLogic.knights;
		if (e.getX() > GameFrame.SCREEN_WIDTH / 2){
			for (Knight k : Knights) {
				k.setAngleToPrincess(Math.toDegrees(k.getAngleToPrincess()) + 45);
				k.turnRight();
			}
		}
		else{
			for (Knight k : Knights) {
				k.setAngleToPrincess(Math.toDegrees(k.getAngleToPrincess()) - 45);
				k.turnLeft();
			}
		}
//		for (Knight k : Knights) {
//			System.out.println(Math.round(Math.toDegrees(k.getAngleToPrincess())));
//			
//		}
	}

}
