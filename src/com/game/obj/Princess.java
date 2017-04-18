package com.game.obj;

import com.game.ui.Res;

public class Princess extends Sprite {
	
	public Princess(){
		//this.setImage(Res.girl);
		//this.setAnimation(new Animation(Res.girl2, 32, 32));
		Animation animation = new Animation(Res.princess, 32, 128, 32, 32);
		this.setAnimation(animation, this);
		//设为静态图
		this.getAnimation().setIsPlay(false);
	}
}
