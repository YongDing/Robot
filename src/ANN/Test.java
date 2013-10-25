package ANN;

import java.io.File;

import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RobocodeEngine;
import robocode.control.RobotSpecification;
import robocode.control.events.BattleCompletedEvent;
import sample.AntiGravityRobot;
import sample.DodgeRobot;
import sample.RobotLu;
import sample.Robotd;
import AI.BattleObserver;

public class Test {
	public static void main(String[] args) {
		Robotd robot = new Robotd();
		BattleCompletedEvent result;
		RobotLu robotlu = new RobotLu();
		DodgeRobot dodgeRobot = new DodgeRobot();
		AntiGravityRobot antiGravityRobot = new AntiGravityRobot();
		// Disable log messages from Robocode
		RobocodeEngine.setLogMessagesEnabled(false);
		// Create the RobocodeEngine
		// RobocodeEngine engine = new RobocodeEngine(); // Run from current
		// working directory
		RobocodeEngine engine = new RobocodeEngine(new File("C:/robocode"));
		// RobocodeEngine engine = new RobocodeEngine(new
		// File("/Users/xiaoyilu/robocode"));
		// Add our own battle listener to the RobocodeEngine
		BattleObserver obsever = new BattleObserver();
		engine.addBattleListener(obsever);

		// Show the Robocode battle view
		engine.setVisible(true);
		// Setup the battle specification

		int numberOfRounds = 5;
		BattlefieldSpecification battlefield = new BattlefieldSpecification(
				800, 600); // 800x600
		RobotSpecification[] selectedRobots = engine
				.getLocalRepository(" sample.Robotd, sample.SittingDuck");
		// RobotSpecification[] selectedRobots =
		// engine.getLocalRepository(" sample.RobotLu, sample.Crazy");
		// RobotSpecification[] selectedRobots =
		// engine.getLocalRepository(" sample.DodgeRobot, sample.Crazy");
		// RobotSpecification[] selectedRobots =
		// engine.getLocalRepository(" sample.AntiGravityRobot, sample.Crazy");

		BattleSpecification battleSpec = new BattleSpecification(
				numberOfRounds, battlefield, selectedRobots);

		// Run our specified battle and let it run till it is over
		engine.runBattle(battleSpec, true); // waits till the battle finishes
		result = obsever.getResult();

		// engine.runBattle(battleSpec, true); // waits till the battle finishes
		// Cleanup our RobocodeEngine
		engine.close();

		// Make sure that the Java VM is shut down properly
		System.exit(0);
	}
}
