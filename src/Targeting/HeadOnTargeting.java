package Targeting;

import module.Skeleton;
import module.Targeting;

public class HeadOnTargeting extends Targeting{

	public HeadOnTargeting(Skeleton bot) {
		super(bot);
		// TODO Auto-generated constructor stub
	}
	
	public void target() {
		double absoluteBearing = bot.getHeadingRadians() + bot.enemy.bearingRadians;
		bot.setTurnGunRightRadians(robocode.util.Utils.normalRelativeAngle(absoluteBearing - bot.getGunHeadingRadians()));
	}

}
