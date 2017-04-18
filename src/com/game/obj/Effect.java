package com.game.obj;

import com.game.ui.Res;

//特效类
public class Effect extends Sprite {
	
	public Effect(String name){
		Animation animation = null;
		switch (name){
		case "boom":
			animation = new Animation(Res.boom, 160, 32, 32, 32);
			break;
		case "heal":
			animation = new Animation(Res.heal, 160, 32, 32, 32);
			break;
		case "dust":
			animation = new Animation(Res.dust, 128, 32, 32, 32);
			break;
		case "attack_1":
			animation = new Animation(Res.attack_1, 256, 64, 64, 64);
			animation.scale = 1.0f;
			break;
		case "attack_2":
			animation = new Animation(Res.attack_2, 256, 64, 64, 64);
			animation.scale = 1.0f;
			break;
		default:
			animation = null;
		}
		
		animation.setPlaySpeed(5);
		animation.setDestroyOnEnd(true);
		animation.setLoop(false);
		this.setAnimation(animation, this);	
	}
	
}
