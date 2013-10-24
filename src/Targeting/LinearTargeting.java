package Targeting;

import java.awt.geom.Point2D;

import robocode.util.Utils;

import module.Skeleton;
import module.Targeting;

public class LinearTargeting extends Targeting{

	public LinearTargeting(Skeleton bot) {
		super(bot);
		// TODO Auto-generated constructor stub
	}
	
	public void target(){
		double myX = bot.getX();
		double myY = bot.getY();
		double absoluteBearing = bot.getHeadingRadians() + bot.enemy.bearingRadians;
		double enemyX = bot.getX() + bot.enemy.distance * Math.sin(absoluteBearing);
		double enemyY = bot.getY() + bot.enemy.distance * Math.cos(absoluteBearing);
		double enemyHeading = bot.enemy.headingRadians;
		double enemyVelocity = bot.enemy.velocity;
		 
		 
		double deltaTime = 0;
		double battleFieldHeight = bot.getBattleFieldHeight(), 
		       battleFieldWidth = bot.getBattleFieldWidth();
		double predictedX = enemyX, predictedY = enemyY;
		while((++deltaTime) * (20.0 - 3.0 * bot.bulletPower) < 
		      Point2D.Double.distance(myX, myY, predictedX, predictedY)){		
			predictedX += Math.sin(enemyHeading) * enemyVelocity;	
			predictedY += Math.cos(enemyHeading) * enemyVelocity;
			if(	predictedX < 18.0 
				|| predictedY < 18.0
				|| predictedX > battleFieldWidth - 18.0
				|| predictedY > battleFieldHeight - 18.0){
				predictedX = Math.min(Math.max(18.0, predictedX), 
		                    battleFieldWidth - 18.0);	
				predictedY = Math.min(Math.max(18.0, predictedY), 
		                    battleFieldHeight - 18.0);
				break;
			}
		}
		double theta = Utils.normalAbsoluteAngle(Math.atan2(
		    predictedX - bot.getX(), predictedY - bot.getY()));
		 
		bot.setTurnRadarRightRadians(
		    Utils.normalRelativeAngle(absoluteBearing - bot.getRadarHeadingRadians()));
		bot.setTurnGunRightRadians(Utils.normalRelativeAngle(theta - bot.getGunHeadingRadians()));
	}
	

}
