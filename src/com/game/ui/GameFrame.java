package com.game.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.game.control.GameControl;
import com.game.control.GameControl2;

public class GameFrame extends JFrame{
	
	public static final int SCREEN_WIDTH = 700;
	public static final int SCREEN_HEIGHT = 700;
	private GamePanel gamePanel = null;
	
	public GameFrame(){
		this.setTitle("TowerDef");
		this.setSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		gamePanel = new GamePanel();
		this.add(gamePanel);
		this.addMouseListener(new GameControl(gamePanel));
		this.addKeyListener(new GameControl2(gamePanel));
		this.setLocationRelativeTo(null);//设置位置居中
		this.setVisible(true);
		
	}
	
}
