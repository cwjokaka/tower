package com.game.math;

public class MyMath {
	
	//线性插值
	public static double lerp(double a, double b, double n){	
		//(1 - weight) * a + weight * b ;
		return (1 - n) * a + n * b; 
	}
	
	//计算距离
	public static double distance(double x1, double y1, double x2, double y2){		
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
		
	}
}
