package sample;


import robocode.AdvancedRobot;
import robocode.HitByBulletEvent;
import robocode.HitWallEvent;
import robocode.RobocodeFileOutputStream;
import robocode.ScannedRobotEvent;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import AI.Enemy;
import ANN.Network;

public class RobotY extends AdvancedRobot {
	static boolean incrementedBattles = false;
	Enemy enemy = new Enemy();
	boolean flag = true;

	public static double PI = Math.PI;
	public void run() {
		setBodyColor(Color.yellow);
		setGunColor(Color.yellow);

		int roundCount, battleCount;
			
			
		try {
			BufferedReader reader = null;
			try {
				// Read file "count.dat" which contains 2 lines, a round count, and a battle count
				reader = new BufferedReader(new FileReader(getDataFile("count.dat")));

				// Try to get the counts
				roundCount = Integer.parseInt(reader.readLine());
				battleCount = Integer.parseInt(reader.readLine());

			} finally {
				if (reader != null) {
					reader.close();
				}
			}
		} catch (IOException e) {
			// Something went wrong reading the file, reset to 0.
			roundCount = 0;
			battleCount = 0;
		} catch (NumberFormatException e) {
			// Something went wrong converting to ints, reset to 0
			roundCount = 0;
			battleCount = 0;
		}

		// Increment the # of rounds
		roundCount++;

		// If we haven't incremented # of battles already,
		// Note: Because robots are only instantiated once per battle, member variables remain valid throughout it.
		if (!incrementedBattles) {
			// Increment # of battles
			battleCount++;
			incrementedBattles = true;
		}

		PrintStream w = null;
		try {
			w = new PrintStream(new RobocodeFileOutputStream(getDataFile("count.dat")));

			w.println(roundCount);
			w.println(battleCount);

			// PrintStreams don't throw IOExceptions during prints, they simply set a flag.... so check it here.
			if (w.checkError()) {
				out.println("I could not write the count!");
			}
		} catch (IOException e) {
			out.println("IOException trying to write: ");
			e.printStackTrace(out);
		} finally {
			if (w != null) {
				w.close();
			}
		}
		out.println("I have been a sitting duck for " + roundCount + " rounds, in " + battleCount + " battles."); 
		
		while(true){
			if (enemy.name == null) {
				setTurnRadarRightRadians(2 * PI);
				this.setTurnRight(360);
				// move
				ahead(200);
				execute();
			} else {
				//end test
				this.setTurnRight(360);
				// move
				ahead(200);

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

		// shoot();
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
		this.turnRight(180);
		enemy.name = null;
	}

	public double rectify(double angle) {
		if (angle < -Math.PI)
			angle += 2 * Math.PI;
		if (angle > Math.PI)
			angle -= 2 * Math.PI;
		return angle;
	}
}
