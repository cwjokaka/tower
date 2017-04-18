package com.game.obj;

/**
 * 节点类
 * 这是一个所有元素的基类
 **/

public class Node {
	//X Y坐标
	protected double x = 0;
	protected double y = 0;
	
	//锚点偏移量
	protected int xOff = 0;		
	protected int yOff = 0;
	
	//父节点  & 子节点
	protected Node parent = null;
	protected Node child = null;
	/**
	 * 坐标的读写
	 * */
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public void setPosition(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	//锚点偏移量
	public void setOff(int xOff, int yOff){
		this.xOff = xOff;
		this.yOff = yOff;
	}
	
	public int getXoff() {
		return xOff;
	}

	public int getYoff() {
		return yOff;
	}
	public Node getParent() {
		return parent;
	}
	public void setParent(Node parent) {
		this.parent = parent;
	}
	public Node getChild() {
		return child;
	}
	public void setChild(Node child) {
		this.child = child;
	}
	
	
}
