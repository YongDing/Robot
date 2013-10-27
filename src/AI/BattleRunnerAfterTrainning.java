package AI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import robocode.BattleResults;
import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RobocodeEngine;
import robocode.control.RobotSpecification;
import robocode.control.events.BattleCompletedEvent;
import File.FileOperator;
import GA.Chromosome;
import GA.Population;

public class BattleRunnerAfterTrainning {
	public static void main(String[] args) throws IOException {
		BattleCompletedEvent result;
		String tankNameString = "sample.SuperTank";
		String enemyNameString = "sample.Crazy";
		String shortTankName = "SuperTank";
		FileOperator fOperator = new FileOperator();
//		DodgeRobot dodgeRobot = new DodgeRobot();
		  
		// Disable log messages from Robocode
		RobocodeEngine.setLogMessagesEnabled(false);
		// Create the RobocodeEngine
		// RobocodeEngine engine = new RobocodeEngine(); // Run from current
		// working directory
		RobocodeEngine engine = new RobocodeEngine(new File("/Users/xiaoyilu/robocode"));
//		RobocodeEngine engine = new RobocodeEngine(new File("C:/robocode"));
		// Add our own battle listener to the RobocodeEngine
		BattleObserver obsever = new BattleObserver();
		engine.addBattleListener(obsever);

		// Show the Robocode battle view
		engine.setVisible(true);
		// Setup the battle specification

		int numberOfRounds = 10;
		BattlefieldSpecification battlefield = new BattlefieldSpecification(
				800, 600); // 800x600
		
		 RobotSpecification[] selectedRobots =
		 engine.getLocalRepository(tankNameString+","+enemyNameString);
 

		BattleSpecification battleSpec = new BattleSpecification(
				numberOfRounds, battlefield, selectedRobots);
		
		//======================GA

        // The size of the simulation population
        final int populationSize = 10;
        
        // The maximum number of generations for the simulation.
        final int maxGenerations = 300;
        
        // The probability of crossover for any member of the population,
        // where 0.0 <= crossoverRatio <= 1.0
        final float crossoverRatio = 0.8f;
        
        // The portion of the population that will be retained without change
        // between evolutions, where 0.0 <= elitismRatio < 1.0
        final float elitismRatio = 0.1f;
        
        // The probability of mutation for any member of the population,
        // where 0.0 <= mutationRatio <= 1.0
        final float mutationRatio = 0.1f;

        // Get the current run time.  Not very accurate, but useful for 
        // some simple reporting.
        long startTime = System.currentTimeMillis();
        
//        String file = "best_gene_results_2";
//        BufferedReader br = new BufferedReader(new FileReader(file));
//        String line;
//        while ((line = br.readLine()) != null) {
//        	System.out.println(line);
//        	fOperator.writeFile("bin/sample/"+ shortTankName+".data/gene.dat", line);
//        	engine.runBattle(battleSpec, true);
//        }
//        br.close();
        String geneString = "1413";
        fOperator.writeFile("bin/sample/"+ shortTankName+".data/gene.dat", geneString);
        engine.runBattle(battleSpec, true);
        
        		
       
        // Get the end time for the simulation.
        long endTime = System.currentTimeMillis();
		

		// engine.runBattle(battleSpec, true); // waits till the battle finishes
		// Cleanup our RobocodeEngine
		engine.close();

		// Make sure that the Java VM is shut down properly
		System.exit(0);
	}
}
