package Radar;

import robocode.Event;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;
import sample.SuperTank;
import module.Radar;
import module.Skeleton;

public class TargetScopeFocusRadar extends Radar{

	public TargetScopeFocusRadar(SuperTank superTank) {
		super(superTank);
		// TODO Auto-generated constructor stub
	}
	
	private int timeSinceLastScan = 10;
	private double enemyAbsoluteBearing;
	
	public void scan(){
		timeSinceLastScan++;
		double radarOffset = Double.NEGATIVE_INFINITY;
		if (timeSinceLastScan < 3) {
			radarOffset = Utils
					.normalRelativeAngle(bot.getRadarHeadingRadians()
							- enemyAbsoluteBearing);
			radarOffset += Math.signum(radarOffset) * 0.2;
		}
		bot.setTurnRadarLeftRadians(radarOffset);
	}
	
	public void listen(Event e){
		if (e instanceof ScannedRobotEvent){
			enemyAbsoluteBearing = bot.getHeadingRadians() + bot.enemy.bearingRadians;
			timeSinceLastScan = 0;
		}
	}

}
