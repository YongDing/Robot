package Targeting;

import sample.SuperTank;
import module.Skeleton;
import module.Targeting;

public class HeadOnTargeting extends Targeting{

	public HeadOnTargeting(SuperTank superTank) {
		super(superTank);
		// TODO Auto-generated constructor stub
	}
	
	public void target() {
		double absoluteBearing = bot.getHeadingRadians() + bot.enemy.bearingRadians;
		bot.setTurnGunRightRadians(robocode.util.Utils.normalRelativeAngle(absoluteBearing - bot.getGunHeadingRadians()));
	}

}
