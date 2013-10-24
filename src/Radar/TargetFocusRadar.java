package Radar;

import module.Radar;
import module.Skeleton;

public class TargetFocusRadar extends Radar{

	public TargetFocusRadar(Skeleton bot) {
		super(bot);
		// TODO Auto-generated constructor stub
	}
	
	public void scan() {
		bot.setTurnRadarRight(bot.getHeading() - bot.getRadarHeading() + bot.enemy.bearing);
	}

}
