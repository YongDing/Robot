package sample;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import robocode.*;
import AI.Enemy;
import ANN.Network;

/**
 * Yong - a robot by (your name here)
 */
public class Robotd extends AdvancedRobot {

	/**
	 * run: Yong's default behavior
	 */
	Enemy enemy = new Enemy();
	public static double PI = Math.PI;
	Network network = new Network();

	public Network getNetwork() {
		return network;
	}

	public void setNetwork(Network network) {
		this.network = network;
	}

	public String readToBuffer(StringBuffer buffer, String filePath)
			throws IOException {

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

	public ArrayList<Float> readToFloat(StringBuffer buffer, String filePath)
			throws IOException {
		InputStream is = new FileInputStream(filePath);
		ArrayList<Float> weights = new ArrayList<Float>();
		String[] text = { "hidden0_weights", "hidden1_weights",
				"hidden2_weights", "hidden3_weights", "hidden4_weights",
				"output_weights" };
		String line;
		float f;
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		line = reader.readLine();
		while (line != null) {
			if (!line.equals(text[0]) && !line.equals(text[1])
					&& !line.equals(text[2]) && !line.equals(text[3])
					&& !line.equals(text[4]) && !line.equals(text[5])) {
				f = Float.parseFloat(line.toString());
				weights.add(f);
	            line = reader.readLine();
			}
		}
		reader.close();
		is.close();
		return weights;
	}

	public void run() {
		// Initialization of the robot should be put here

		// After trying out your robot, try uncommenting the import at the top,
		// and the next line:

		// Robot main loop
		setAdjustGunForRobotTurn(true);
		setAdjustRadarForGunTurn(true);
		this.setColors(Color.red, Color.blue, Color.yellow, Color.black,
				Color.green);
		while (true) {
			// Replace the next 4 lines with any behavior you would like
			if (enemy.name == null) {
				setTurnRadarRightRadians(2 * PI);
				this.setTurnRight(360);
				
				// move
				ahead(200);
				execute();
			} else {
				this.setTurnRight(360);
				// move
				ahead(200);
				
//				fire(1);
				shoot();
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
		
		
//		shoot();
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
	
	public void shoot(){
		//input0 bearing of gun
		//input1 power of bullet
		//input2 distance
		//input3 heading
		//input4 velocity
		//input5 heading of enemy
		//input6 velocity of enemy
		//input7 bearing of enemy
		double input0, input1, input2, input3, input4, input5, input6, input7;
		input0= getGunHeadingRadians();
		input1=2;
		input2=enemy.distance;
		input3=getHeadingRadians();
		input4=getVelocity();
		input5=enemy.headingRadian;
		input6=enemy.velocity;
		input7=enemy.bearingRadian;

		network.setInputs(input0, input1, input2, input3, input4, input5, input6, input7);
		double trunradians=network.getOutput();
		this.turnGunRightRadians(trunradians);
		this.fire(2);
	}
}
