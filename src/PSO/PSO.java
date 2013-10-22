package PSO;

import java.io.File;

import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RobocodeEngine;
import robocode.control.RobotSpecification;
import robocode.control.events.BattleCompletedEvent;
import AI.BattleObserver;
import ANN.Network;

public class PSO {
	int swarm_size, dimension;
	int generations;
	int c_generation = 1;
	// parameters of PSO
	double w, c1, c2, range_max, range_min;
	double vmax;
	Particle[] population;
	Particle[] pbest, velocity;
	Particle gbest;

	BattleCompletedEvent result;
	
	public PSO(int input_number, int hidden_number) {
		dimension = (input_number + 1) * hidden_number + (hidden_number + 1);
		swarm_size = (int) (10 + 2 * Math.sqrt(dimension));
		range_max = 2;
		range_min = 0.1;
		w = 0.73;
		c1 = 1.19;
		c2 = 1.19;
		generations = 500;
		vmax = range_max / 2;
		population = new Particle[swarm_size];
		velocity = new Particle[swarm_size];
		pbest = new Particle[swarm_size];
	}

	public void modify_parameters(double w, double c1, double c2) {
		this.w = w;
		this.c1 = c1;
		this.c2 = c2;
	}

	public void initial() {
		double[] positions = new double[dimension];
		// initial pbest and particles
		for (int i = 0; i < swarm_size; i++) {
			for (int j = 0; j < dimension; j++) {
				positions[j] = (Math.random() * (range_max - range_min) + range_min)
						* (Math.random() > 0.5 ? 1 : -1);
			}
			population[i] = new Particle(positions);
			pbest[i] = population[i];
		}

		// initial gbest
		gbest = population[0];
		for (int i = 0; i < swarm_size; i++) {
			if (fitness(population[i]) > fitness(gbest)) {
				gbest = population[i];
			}
		}

		// initial velocity of all particles
		for (int i = 0; i < swarm_size; i++) {
			for (int j = 0; j < dimension; j++) {
				positions[j] = Math.random() * vmax
						* (Math.random() > 0.5 ? 1 : -1);
			}
			velocity[i] = new Particle(positions);
		}
	}

	
	public void setEnviroment(){
        // Disable log messages from Robocode
        RobocodeEngine.setLogMessagesEnabled(false);
        
        // Create the RobocodeEngine
        RobocodeEngine engine = new RobocodeEngine(new File("C:\\robocode"));

        // Add our own battle listener to the RobocodeEngine 
        BattleObserver obsever=new BattleObserver(); 
        engine.addBattleListener(obsever);

        // Show the Robocode battle view
        engine.setVisible(false);
        // Setup the battle specification

        int numberOfRounds = 5;
        BattlefieldSpecification battlefield = new BattlefieldSpecification(800, 600); // 800x600
        RobotSpecification[] selectedRobots = engine.getLocalRepository("sample.Robotd, sample.Crazy");

        BattleSpecification battleSpec = new BattleSpecification(numberOfRounds, battlefield, selectedRobots);
        // Run our specified battle and let it run till it is over
        engine.runBattle(battleSpec, true); // waits till the battle finishes
        result=obsever.getResult();

        
        engine.runBattle(battleSpec, true); // waits till the battle finishes
        // Cleanup our RobocodeEngine
        engine.close();

        // Make sure that the Java VM is shut down properly
        System.exit(0);
	}
	
	public int fitness(Particle c) {
		BattleCompletedEvent result;
	   	 
        // Disable log messages from Robocode
        RobocodeEngine.setLogMessagesEnabled(false);
        
        // Create the RobocodeEngine
        RobocodeEngine engine = new RobocodeEngine(new File("C:\\robocode"));

        // Add our own battle listener to the RobocodeEngine 
        BattleObserver obsever=new BattleObserver(); 
        engine.addBattleListener(obsever);

        // Show the Robocode battle view
        engine.setVisible(false);
        // Setup the battle specification

        int numberOfRounds = 5;
        BattlefieldSpecification battlefield = new BattlefieldSpecification(800, 600); // 800x600
        RobotSpecification[] selectedRobots = engine.getLocalRepository("sample.Robotd, sample.Crazy");

        BattleSpecification battleSpec = new BattleSpecification(numberOfRounds, battlefield, selectedRobots);
        // Run our specified battle and let it run till it is over
        engine.runBattle(battleSpec, true); // waits till the battle finishes
        result=obsever.getResult();

        
        engine.runBattle(battleSpec, true); // waits till the battle finishes
        // Cleanup our RobocodeEngine
        engine.close();

        // Make sure that the Java VM is shut down properly
        System.exit(0);
        
		return 0;
	}

}
