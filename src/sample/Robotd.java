package sample;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import robocode.AdvancedRobot;
import robocode.HitByBulletEvent;
import robocode.HitWallEvent;
import robocode.RobocodeFileOutputStream;
import robocode.ScannedRobotEvent;
import AI.Enemy;
import ANN.Network;

/**
 * Yong - a robot by (your name here)
 */
public class Robotd extends AdvancedRobot {

	/**
	 * run: Yong's default behavior
	 */
	static boolean incrementedBattles = false;
	Enemy enemy = new Enemy();
	boolean flag = true;

	public static double PI = Math.PI;
	Network network = new Network();

	public Network getNetwork() {
		return network;
	}

	public void setNetwork(Network network) {
		this.network = network;
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

		int roundCount, battleCount;
		
		
		try {
			BufferedReader reader = null;
			try {
				// Read file "count.dat" which contains 2 lines, a round count, and a battle count
				reader = new BufferedReader(new FileReader(getDataFile("training_weights.dat")));

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
			w = new PrintStream(new RobocodeFileOutputStream(getDataFile("training_weights.dat")));

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
		
		while (true) {
			// Replace the next 4 lines with any behavior you would like

			if (enemy.name == null) {
				setTurnRadarRightRadians(2 * PI);
				this.setTurnRight(360);
				// move
				ahead(200);
				execute();
			} else {
//				if (flag) {
//					try {
//						if (isTraining(enemy.name)) {
//							ArrayList<Double> weights = this
//									.getBestWeight(enemy.name);
//							network.updateWeight(weights);
//							flag = false;
//						} else {
//							getTrainingWeight();
//							// ArrayList<Double> weights =
//							// this.getTrainingWeight();
//							// network.updateWeight(weights);
//							flag = false;
//						}
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
				
				
				
				this.setTurnRight(360);
				// move
				ahead(200);
				// fire(1);
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

	public void shoot() {
		// input0 bearing of gun
		// input1 power of bullet
		// input2 distance
		// input3 heading
		// input4 velocity
		// input5 heading of enemy
		// input6 velocity of enemy
		// input7 bearing of enemy
		double input0, input1, input2, input3, input4, input5, input6, input7;
		input0 = getGunHeadingRadians();
		input1 = 2;
		input2 = enemy.distance;
		input3 = getHeadingRadians();
		input4 = getVelocity();
		input5 = enemy.headingRadian;
		input6 = enemy.velocity;
		input7 = enemy.bearingRadian;

		network.setInputs(input0, input1, input2, input3, input4, input5,
				input6, input7);
		double trunradians = network.getOutput();
		this.turnGunRightRadians(trunradians);
		this.fire(2);
	}

	public boolean isTraining(String enemyName) throws IOException {
		BufferedReader reader = null;
		reader = new BufferedReader(new FileReader(getDataFile(enemyName)));
		if (reader.readLine() != null) {
			return true;
		}
		return false;
	}

	public ArrayList<Double> getBestWeight(String enemyName) throws IOException {
		ArrayList<Double> weights = new ArrayList<Double>();
		BufferedReader reader = null;
		ArrayList<String> text = new ArrayList<String>();
		for (int i = 0; i < Network.hidden_number; i++) {
			text.add("hidden_" + i + "_weights");
		}
		text.add("output_weights");
		String line;
		double d;

		reader = new BufferedReader(new FileReader(getDataFile(enemyName)));
		line = reader.readLine();
		while (line != null) {
			if (!text.contains(line)) {
				d = Double.parseDouble(line.toString());
				weights.add(d);
				line = reader.readLine();
			}
		}
		return weights;
	}

	public ArrayList<Double> getTrainingWeight() throws IOException {
		ArrayList<Double> weights = new ArrayList<Double>();
		ArrayList<String> text = new ArrayList<String>();
		for (int i = 0; i < Network.hidden_number; i++) {
			text.add("hidden_" + i + "_weights");
		}
		text.add("output_weights");
		double d;

		

		// RobocodeFileWriter fileWriter = new
		// RobocodeFileWriter("training_weights");
		// fileWriter.write("1111.111");

		// reader = new BufferedReader(new FileReader(file));
		// line = reader.readLine();
		// while (line != null) {
		// if (!text.contains(line)) {
		// d = Double.parseDouble(line.toString());
		// weights.add(d);
		// line = reader.readLine();
		// }
		// }
		return weights;
	}
}
