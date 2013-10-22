package AI;


import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

public class Enemy {
	public double x, y;
	public String name = null;
	public double headingRadian = 0.0D;
	public double bearingRadian = 0.0D;
	public double distance = 1000D;
	public double direction = 0.0D;
	public double velocity = 0.0D;
	public double energy = 100.0D;

	public void update(ScannedRobotEvent e, AdvancedRobot me) {
		name = e.getName();
		headingRadian = e.getHeadingRadians();
		bearingRadian = e.getBearingRadians();
		energy = e.getEnergy();
		velocity = e.getVelocity();
		distance = e.getDistance();
		direction = bearingRadian + me.getHeadingRadians();
		x = me.getX() + Math.sin(direction) * distance;
		y = me.getY() + Math.cos(direction) * distance;
	}
}