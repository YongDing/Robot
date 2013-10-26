package Radar;

import sample.SuperTank;
import module.Radar;
import module.Skeleton;

public class SpinnerRadar extends Radar{
	
	public SpinnerRadar(SuperTank bot) {
		// TODO Auto-generated constructor stub
		super(bot);
	}

	public void scan(){
		bot.setTurnRadarRightRadians(Double.POSITIVE_INFINITY);
	}

}
