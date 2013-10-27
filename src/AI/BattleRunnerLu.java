package AI;

import java.awt.Robot;
import java.io.File;
import java.io.IOException;

import File.FileOperator;
import GA.Chromosome;
import GA.Population;



import robocode.*;
import robocode.control.*;
import robocode.control.events.*;
import sample.AntiGravityRobot;
import sample.DodgeRobot;
import sample.RobotLu;
import sample.Robotd;
import sample.SuperTank;

public class BattleRunnerLu {
 
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
        
        // Create the initial population
        Population pop = new Population(populationSize, crossoverRatio, 
                        elitismRatio, mutationRatio);
        
        for (int i = 0; i < pop.getPopulation().length; i++) {
			Chromosome geneChromosome = pop.getPopulation()[i];
			String gene = geneChromosome.getGene();
			fOperator.writeFile("bin/sample/"+ shortTankName+".data/gene.dat", gene);
			// Run our specified battle and let it run till it is over
			engine.runBattle(battleSpec, true); // waits till the battle finishes
			int myScore = 0, enemyScore = 0;
			double fitness = 0.0;
			
			for (BattleResults result1 : obsever.getResult().getSortedResults()) {
				
				if (result1.getTeamLeaderName().equals(tankNameString)) {
					myScore = result1.getScore();
				}else {
					enemyScore = result1.getScore();
				}
			
			}
			
			fitness =(double) myScore/(myScore + enemyScore);
			fOperator.writeFile("bin/sample/"+ shortTankName+".data/fitness.dat", ""+fitness);
			pop.getPopulation()[i].setFitness(fitness);
			geneChromosome.setFitness(fitness);
		}

        // Start evolving the population, stopping when the maximum number of
        // generations is reached, or when we find a solution.
        int i = 0;
        Chromosome best = pop.getPopulation()[0];
        
        while ((i++ <= maxGenerations)) {
                
                pop.evolve();
                best = pop.getPopulation()[0];
                System.out.println("Generation " + i + ": " + best.getGene() + " Fitness => " + best.getFitness());
                
                for (int j = 0; j < pop.getPopulation().length; j++) {
        			Chromosome geneChromosome = pop.getPopulation()[j];
        			String gene = geneChromosome.getGene();
        			fOperator.writeFile("bin/sample/"+ shortTankName+".data/gene.dat", gene);
        			// Run our specified battle and let it run till it is over
        			engine.runBattle(battleSpec, true); // waits till the battle finishes
        			int myScore = 0, enemyScore = 0;
        			double fitness = 0.0;
        			
        			for (BattleResults result1 : obsever.getResult().getSortedResults()) {
        				
        				if (result1.getTeamLeaderName().equals(tankNameString)) {
        					myScore = result1.getScore();
        				}else {
        					enemyScore = result1.getScore();
        				}
        			
        			}
        			
        			fitness =(double) myScore/(myScore + enemyScore);
        			fOperator.writeFile("bin/sample/"+ shortTankName+".data/fitness.dat", ""+fitness);
        			pop.getPopulation()[j].setFitness(fitness);
        			geneChromosome.setFitness(fitness);
        		}
               
        }
        
        // Get the end time for the simulation.
        long endTime = System.currentTimeMillis();
        
        // Print out some information to the console.
        System.out.println("Generation " + i + ": " + best.getGene());
        System.out.println("Total execution time: " + (endTime - startTime) + 
                        "ms");
        
		
		
		

		// engine.runBattle(battleSpec, true); // waits till the battle finishes
		// Cleanup our RobocodeEngine
		engine.close();

		// Make sure that the Java VM is shut down properly
		System.exit(0);
	}
}
