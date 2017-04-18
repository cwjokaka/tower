package com.game.collection;

public class Link <T> {
	
	private Node<T> head = null;
	private Node<T> tail = null;
	
	public Link (){
		tail = new Node<T>();
		head.next = tail;
	}
	
	public void add(T e){
		Node<T> node = new Node<T>();
		tail.next = node;
		tail = node;
	}
	
	public void remove(T e){
		
	}
	
	private class Node <T> {
		private Node<T> next = null;
		private Node<T> prev = null;
		
		
	}
}
