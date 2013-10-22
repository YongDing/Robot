package sample;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.jar.Attributes.Name;

import robocode.*;
import AI.Enemy;


public class RobotLu extends AdvancedRobot {
	private int moveDirection = 1;
	
	private int wallMargin = 60; 
	private int tooCloseToWall = 0;
	
	Enemy enemy = new Enemy();
	public static double PI = Math.PI;
	
    public String readToBuffer(StringBuffer buffer, String filePath) throws IOException {
        InputStream is = new FileInputStream(filePath);
        String line;
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        line = reader.readLine();
        while (line != null) {
            buffer.append(line); 
            buffer.append("\n"); 
            line = reader.readLine(); 
        }
        reader.close();
        is.close();
        return buffer.toString();
    }
 
	public void run() {
		// Initialization of the robot should be put here

		// After trying out your robot, try uncommenting the import at the top,
		// and the next line:

		// Robot main loop
		setAdjustGunForRobotTurn(true);
		setAdjustRadarForGunTurn(true);
		
		addCustomEvent(new Condition("too_close_to_walls") {
			public boolean test() {
				return (
					// we're too close to the left wall
					(getX() <= wallMargin ||
					 // or we're too close to the right wall
					 getX() >= getBattleFieldWidth() - wallMargin ||
					 // or we're too close to the bottom wall
					 getY() <= wallMargin ||
					 // or we're too close to the top wall
					 getY() >= getBattleFieldHeight() - wallMargin)
					);
				}
			});
		
		this.setColors(Color.red, Color.blue, Color.yellow, Color.black,
				Color.green);
		while (true) {
			// Replace the next 4 lines with any behavior you would like
			if (enemy.name == null) {
				setTurnRadarRightRadians(2 * PI);
				
				//move
				setAhead(100 * moveDirection);
				
				execute();
			} else {
				//move
				setAhead(100 * moveDirection);
				execute();

			}

		}
	}
	
	

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		// Replace the next line with any behavior you would like
		enemy.update(e, this);
		double Offset = rectify(enemy.direction - getRadarHeadingRadians());
		setTurnRadarRightRadians(Offset * 1.1);
//		doCirclingMove(e);
		doStrafingMove(e);
//		doWallAvoidMove(e);
	}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		// Replace the next line with any behavior you would like
		// back(10);
	}

	/**
	 * onHitWall: What to do when you hit a wall
	 */
	public void onHitWall(HitWallEvent e) {
		// Replace the next line with any behavior you would like
		
		moveDirection *= -1;
		enemy.name=null;
	}
	
	public void onHitRobot(HitRobotEvent e) {
		// Replace the next line with any behavior you would like
		
		moveDirection *= -1;
		enemy.name=null;
	}

	public double rectify(double angle) {
		if (angle < -Math.PI)
			angle += 2 * Math.PI;
		if (angle > Math.PI)
			angle -= 2 * Math.PI;
		return angle;
	}
	
	//Strafing
	public void doStrafingMove(ScannedRobotEvent enemy) {

		// always square off against our enemy
		setTurnRight(enemy.getBearing() + 90);

		// strafe by changing direction every 20 ticks
		if (getTime() % 20 == 0) {
			moveDirection *= -1;
			setAhead(150 * moveDirection);
		}
	}
	
	public void doCirclingMove(ScannedRobotEvent enemy) {

		// switch directions if we've stopped
		if (getVelocity() == 0)
			moveDirection *= -1;

		// circle our enemy
		setTurnRight(enemy.getBearing() + 90 - (10 * moveDirection));
		setAhead(100 * moveDirection);
	}
	
	public void onCustomEvent(CustomEvent e) {
		if (e.getCondition().getName().equals("too_close_to_walls"))
		{
			moveDirection *= -1;
			setAhead(150 * moveDirection);
		}
	}
	
	// Don't get too close to the walls

	public void doWallAvoidMove(ScannedRobotEvent enemy) {
		// always square off against our enemy, turning slightly toward him
		setTurnRight(enemy.getBearing() + 90 - (10 * moveDirection));

		// if we're close to the wall, eventually, we'll move away
		if (tooCloseToWall > 0) tooCloseToWall--;

		// normal movement: switch directions if we've stopped
		if (getVelocity() == 0) {
			moveDirection *= -1;
			setAhead(1000 * moveDirection);
		}
	}

}
