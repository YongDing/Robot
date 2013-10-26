package Radar;

import sample.SuperTank;
import module.Radar;
import module.Skeleton;

public class TargetFocusRadar extends Radar{

	public TargetFocusRadar(SuperTank superTank) {
		super(superTank);
		// TODO Auto-generated constructor stub
	}
	
	public void scan() {
		bot.setTurnRadarRight(bot.getHeading() - bot.getRadarHeading() + bot.enemy.bearing);
	}

}
