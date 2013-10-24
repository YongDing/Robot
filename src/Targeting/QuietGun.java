package Targeting;

import module.Skeleton;
import module.Targeting;

public class QuietGun extends Targeting{

	public QuietGun(Skeleton bot) {
		super(bot);
		// TODO Auto-generated constructor stub
	}
	
	public void target(){
		bot.setTurnGunRightRadians(0.0001);
	}

}
