package com.game.ui;

import java.awt.Component;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;

import com.game.main.Main;

/**
 * 资源加载类
 * */
public class Res{
	
	public static MediaTracker tracker = null;
	//静态图片素材
	public static Image background = null;
	
	//动画素材(人物)
	public static Image princess = null;
	public static Image grayKnight = null;
	public static Image goldenKnight = null;
	public static Image e_slim_gray = null;
	public static Image e_slim_gold = null;
	public static Image e_knight_gray = null;
	public static Image e_knight_gold = null;
	public static Image e_caster_gray = null;
	public static Image e_caster_gold = null;
	
	//动画素材(效果)
	public static Image boom = null;
	public static Image heal = null;
	public static Image dust = null;
	public static Image attack_1 = null;
	public static Image attack_2 = null;
	public static Image arrow = null;
	
	//文字素材
	public static Image font_sprite = null;
	
	static {
		System.out.println(Main.class.getResource("/images/boom.png"));
//		tracker = new MediaTracker(null);
		boom = getImage("./images/boom.png");
		heal = getImage("./images/heal.png");
		dust = getImage("./images/dust.png");
		background = getImage("./images/background.png");
		e_slim_gray = getImage("./images/e_slim_gray.png");
		e_slim_gold = getImage("./images/e_slim_gold.png");
		grayKnight = getImage("./images/grayKnight.png");
		goldenKnight = getImage("./images/goldenKnight.png");
		princess = getImage("./images/princess.png");
		e_knight_gray = getImage("./images/e_knight_gray.png");
		e_knight_gold = getImage("./images/e_knight_gold.png");
		e_caster_gray = getImage("./images/e_caster_gray.png");
		e_caster_gold = getImage("./images/e_caster_gold.png");
		font_sprite = getImage("./images/font_sprite.png");
		attack_1 = getImage("./images/attack_1.png");
		attack_2 = getImage("./images/attack_2.png");
		arrow = getImage("./images/arrow.png");
//		tracker.addImage(greenKnight, 0);
//		tracker.addImage(girl, 0);
//		tracker.addImage(girl2, 0);
//		tracker.checkAll(true);
	}
	
	private static Image getImage(String filename){
		 return Toolkit.getDefaultToolkit().getImage(filename);
	}
}
