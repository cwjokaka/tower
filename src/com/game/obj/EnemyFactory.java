package com.game.obj;

public class EnemyFactory {
	public static Enemy makeEnemy(String name, String color){
		String enemyStr = name + color;
		
		switch (enemyStr) {
			case "knight_gray":
				//return new Enemy("normal", "gray");
			default:
				return null;
		}
	}
}
