package ANN;

import java.io.File;
import java.io.IOException;

import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RobocodeEngine;
import robocode.control.RobotSpecification;
import robocode.control.events.BattleCompletedEvent;
import sample.AntiGravityRobot;
import sample.DodgeRobot;
import sample.RobotLu;
import sample.RobotTest;
import sample.Robotd;
import AI.BattleObserver;
import File.FileOperator;

public class Result {
	public static void main(String[] args) throws IOException {
		Robotd robot = new Robotd();
		RobotTest robott = new RobotTest();
		BattleCompletedEvent result;
		
		String result_text=FileOperator.readResult();
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
		
		String[] words = result_text.split("\n");
		int count = 0;
		for (int i = 0; i < words.length; i++) {
			if (words[i] != null && words[i].length() > 0) {
				count++;
			}
		}
				

		// Show the Robocode battle view
		engine.setVisible(true);
		// Setup the battle specification

		int numberOfRounds = 1;
		BattlefieldSpecification battlefield = new BattlefieldSpecification(
				800, 600); // 800x600
		RobotSpecification[] selectedRobots = engine
				.getLocalRepository(" sample.Robotd,sample.RobotTest");
		// RobotSpecification[] selectedRobots =
		// engine.getLocalRepository(" sample.RobotLu, sample.Crazy");
		// RobotSpecification[] selectedRobots =
		// engine.getLocalRepository(" sample.DodgeRobot, sample.Crazy");
		// RobotSpecification[] selectedRobots =
		// engine.getLocalRepository(" sample.AntiGravityRobot, sample.Crazy");

		BattleSpecification battleSpec = new BattleSpecification(
				numberOfRounds, battlefield, selectedRobots);

		String path="bin//" + "sample" + "//" + "Robotd"
				+ ".data//training_weights.dat";
		
		int index=0;
		while(count>0){
			String temp="";
			System.out.println(words[index]);
			for(int i=1;i<24;i++){
				temp+=words[i+index]+"\n";
			}
			System.out.println(temp);
			FileOperator.writeResult(path, temp);
			engine.runBattle(battleSpec, true); // waits till the battle finishes
			result = obsever.getResult();
			count-=24;
			index+=24;
		}
		// Run our specified battle and let it run till it is over
		

		// engine.runBattle(battleSpec, true); // waits till the battle finishes
		// Cleanup our RobocodeEngine
		engine.close();

		// Make sure that the Java VM is shut down properly
		System.exit(0);
	}
}
