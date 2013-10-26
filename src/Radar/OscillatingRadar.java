package Radar;

import sample.SuperTank;
import module.Radar;
import module.Skeleton;

public class OscillatingRadar extends Radar{

	public OscillatingRadar(SuperTank superTank) {
		super(superTank);
		// TODO Auto-generated constructor stub
	}
	
	public void scan(){
		bot.scanDirection *= -1; // changes value from 1 to -1
		bot.setTurnRadarRight(360 * bot.scanDirection);
	}
	

}
