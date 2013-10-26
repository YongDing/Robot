package Targeting;

import sample.SuperTank;
import module.Skeleton;
import module.Targeting;

public class QuietGun extends Targeting{

	public QuietGun(SuperTank superTank) {
		super(superTank);
		// TODO Auto-generated constructor stub
	}
	
	public void target(){
		bot.setTurnGunRightRadians(0.0001);
	}

}
