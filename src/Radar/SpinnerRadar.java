package Radar;

import module.Radar;
import module.Skeleton;

public class SpinnerRadar extends Radar{

	public SpinnerRadar(Skeleton bot) {
		super(bot);
		// TODO Auto-generated constructor stub
	}
	
	public void scan(){
		bot.setTurnRadarRightRadians(Double.POSITIVE_INFINITY);
	}

}
