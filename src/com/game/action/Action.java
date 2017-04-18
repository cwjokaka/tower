package com.game.action;

import com.game.obj.Sprite;

public abstract class Action {
	
	protected Sprite sprite;
	protected boolean isPlaying = true;
	
	public abstract void runAction();
}
