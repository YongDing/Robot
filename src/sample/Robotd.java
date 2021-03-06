package sample;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import robocode.AdvancedRobot;
import robocode.BattleEndedEvent;
import robocode.BulletHitEvent;
import robocode.BulletMissedEvent;
import robocode.DeathEvent;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
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
	Enemy enemy = new Enemy();

	public static double PI = Math.PI;
	public static int count_hit=0;
	public static int count_miss = 0;
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
		double gunbearing = 0;
		setAdjustGunForRobotTurn(true);
		setAdjustRadarForGunTurn(true);
		this.setColors(Color.red, Color.blue, Color.yellow, Color.black,
				Color.green);
		
		this.setBulletColor(Color.GREEN);

		// ArrayList<Double> weights=new ArrayList<Double>();
		// if (enemy.name != null) {
		// if (isTraining(enemy.name)) {
		// weights = getBestWeight(enemy.name);
		// network.updateWeight(weights);
		// } else {
		// weights = getTrainingWeight();
		// network.updateWeight(weights);
		// }
		// } else {
		// weights = getTrainingWeight();
		// network.updateWeight(weights);
		// }

		ArrayList<Double> weights = getTrainingWeight();
		network.updateWeight(weights);
		// execute();

		while (true) {
			gunbearing = this.getRadarHeadingRadians()
					- this.getGunHeadingRadians();
			if (gunbearing < Math.PI && gunbearing > -Math.PI) {
				setTurnGunRightRadians(gunbearing);
			} else {
				setTurnGunLeftRadians(2 * Math.PI - gunbearing);
			}
			if (enemy.name == null) {
				setTurnRadarRightRadians(2 * PI);
				execute();
			} else {
				setTurnRight(180);
				setAhead(200);
				execute();

				if (enemy.distance < 100) {
					shoot(3);
				} else if (enemy.distance < 500 && enemy.distance >= 100) {
					shoot(2);
				} else {
					shoot(1);
				}

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
		setBack(200);
		enemy.name = null;
	}

	public double rectify(double angle) {
		if (angle < -Math.PI)
			angle += 2 * Math.PI;
		if (angle > Math.PI)
			angle -= 2 * Math.PI;
		return angle;
	}

	public void onHitRobot(HitRobotEvent event) {
		enemy.name = null;
		if (event.getBearing() > -90 && event.getBearing() <= 90) {
			setBack(100);
		} else {
			setAhead(100);
		}
	}

	public void onBulletMissed(BulletMissedEvent event) {
		count_miss++;
	}

	public void onBulletHit(BulletHitEvent event) {
		count_hit++;
	}

	public void onDeath(DeathEvent event){
		
	}
	
	public void onBattleEnded(BattleEndedEvent event) {
		this.writeBulletResults();
		count_hit=0;
		count_miss=0;
	}
	
	public void shoot(int power) {
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
		input1 = power;
		input2 = enemy.distance;
		input3 = getHeadingRadians();
		input4 = getVelocity();
		input5 = enemy.headingRadian;
		input6 = enemy.velocity;
		input7 = enemy.bearingRadian;

		network.setInputs(input0, input2, input3, input7);
		double trunradians = network.getOutput();

		if (getGunHeat() == 0 && Math.abs(getGunTurnRemaining()) < 10) {
			turnGunRightRadians(trunradians);
			fire(power);
		}
	}

	public boolean isTraining(String enemyName) {
		boolean flag = false;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(getDataFile(enemyName)));
			if (reader.readLine() != null) {
				flag = true;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return flag;

	}

	public void writeBulletResults() {
		PrintStream w = null;
		try {
			w = new PrintStream(new RobocodeFileOutputStream(
					getDataFile("bullets")));

			w.println(count_miss);
			w.println(count_hit);

			// PrintStreams don't throw IOExceptions during prints, they simply
			// set a flag.... so check it here.
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
	}

	public ArrayList<Double> getBestWeight(String enemyName) {
		ArrayList<Double> weights = new ArrayList<Double>();

		ArrayList<String> text = new ArrayList<String>();
		for (int i = 0; i < Network.hidden_number; i++) {
			text.add("hidden_" + i + "_weights");
		}
		text.add("output_weights");
		String line = "";
		double d;

		try {
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader(
						getDataFile(enemyName)));
				line = reader.readLine();
				while (line != null) {
					if (!text.contains(line)) {
						d = Double.parseDouble(line.toString());
						weights.add(d);
					}
					line = reader.readLine();
				}
			} finally {
				if (reader != null) {
					reader.close();
				}
			}
		} catch (IOException e) {
			// Something went wrong reading the file, reset to 0.
		}
		return weights;
	}

	public ArrayList<Double> getTrainingWeight() {
		ArrayList<Double> weights = new ArrayList<Double>();

		ArrayList<String> text = new ArrayList<String>();
		for (int i = 0; i < Network.hidden_number; i++) {
			text.add("hidden_" + i + "_weights");
		}
		text.add("output_weights");
		String line = "";
		double d;

		try {
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader(
						getDataFile("training_weights.dat")));
				line = reader.readLine();
				while (line != null) {
					if (!text.contains(line)) {
						d = Double.parseDouble(line.toString());
						weights.add(d);
					}
					line = reader.readLine();
				}
			} finally {
				if (reader != null) {
					reader.close();
				}
			}
		} catch (IOException e) {
			// Something went wrong reading the file, reset to 0.
		}
		return weights;
	}

}
