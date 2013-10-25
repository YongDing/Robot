package PSO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RobocodeEngine;
import robocode.control.RobotSpecification;
import robocode.control.events.BattleCompletedEvent;
import AI.BattleObserver;
import ANN.Network;

public class PSO {
	int numberOfRounds = 10;
	int swarm_size, dimension;
	int generations = 500;
	int c_generation = 1;
	// parameters of PSO
	double w, c1, c2, range_max, range_min;
	double vmax;
	Particle[] population;
	Particle[] pbest;
	Particle gbest;

	String enemy = "";
	String robot = "";
	String pack="";
	String name="";
	String trainpath="";
	
	double[][] velocity;

	int[] bestfitness;
	// robocode environment
	RobocodeEngine engine;
	PSOObserver obsever;
	BattleCompletedEvent results;
	BattleSpecification battleSpec;

	public PSO(String robot, String enemy) {
		this.enemy = enemy;
		this.robot = robot;
		pack=robot.substring(0, robot.indexOf("."));
		name=robot.substring(robot.indexOf(".")+1, robot.length());
		
		
		dimension = (Network.input_number + 1) * Network.hidden_number
				+ (Network.hidden_number + 1);
		swarm_size = (int) (10 + 2 * Math.sqrt(dimension));
		range_max = 2;
		range_min = 0.1;
		w = 0.73;
		c1 = 1.19;
		c2 = 1.19;
		vmax = range_max;
		population = new Particle[swarm_size];
		velocity = new double[swarm_size][dimension];
		pbest = new Particle[swarm_size];

		bestfitness = new int[swarm_size];
	}

	public void modify_parameters(double w, double c1, double c2) {
		this.w = w;
		this.c1 = c1;
		this.c2 = c2;
	}

	public void initialFile() throws IOException {
		trainpath="bin//"+pack+"//"+name+".data//training_weights.dat";
		FileWriter fw = new FileWriter(trainpath);
		fw.write("");
		fw.close();
	}

	public void initial() throws IOException {

		double[] positions = null;
		// initial pbest and particles
		for (int i = 0; i < swarm_size; i++) {
			positions = new double[dimension];
			for (int j = 0; j < dimension; j++) {
				positions[j] = (Math.random() * (range_max - range_min) + range_min)
						* (Math.random() > 0.5 ? 1 : -1);
			}
			population[i] = new Particle(positions);
			pbest[i] = population[i];
		}

		// initial gbest
		gbest = population[0];
		int gbest_index = 0;
		for (int i = 0; i < swarm_size; i++) {
			bestfitness[i] = fitness(population[i]);
			if (bestfitness[i] > bestfitness[gbest_index]) {
				gbest_index = i;
			}
		}
		gbest = population[gbest_index];

		// initial velocity of all particles
		double[] temp = new double[dimension];
		for (int i = 0; i < swarm_size; i++) {
			for (int j = 0; j < dimension; j++) {
				temp[j] = Math.random() * vmax
						* (Math.random() > 0.5 ? 1 : -1);
			}
			velocity[i] = temp;
			temp = new double[dimension];
		}
	}

	public void setEnvironment() {
		// Disable log messages from Robocode
		RobocodeEngine.setLogMessagesEnabled(false);

		// Create the RobocodeEngine
		engine = new RobocodeEngine(new File("C:\\robocode"));

		// Add our own battle listener to the RobocodeEngine
		obsever = new PSOObserver();
		engine.addBattleListener(obsever);

		// Show the Robocode battle view
		engine.setVisible(false);
		// Setup the battle specification

		BattlefieldSpecification battlefield = new BattlefieldSpecification(
				800, 600); // 800x600
		RobotSpecification[] selectedRobots = engine.getLocalRepository(robot
				+ "," + enemy);

		battleSpec = new BattleSpecification(numberOfRounds, battlefield,
				selectedRobots);
		// Run our specified battle and let it run till it is over
	}

	public void exit() {
		// Cleanup our RobocodeEngine
		engine.close();
	}

	public int fitness(Particle c) throws IOException {
		c.writeFile(trainpath,c.generateText());
		engine.runBattle(battleSpec, true); // waits till the battle finishes
		results = obsever.getResult();
		int sum = 0;
		for (robocode.BattleResults result : results.getSortedResults()) {
			if (result.getTeamLeaderName().equals(robot)) {
				sum += result.getBulletDamage();
			}
		}
		return sum/numberOfRounds;
	}

	public void run() throws IOException {
		c_generation = 1;
		double r1, r2;
		double[] new_velocity = new double[dimension];
		double[] new_positions = new double[dimension];
		while (c_generation < generations) {
			System.out.println("generation: " + c_generation);
			print_pbest();
			c_generation++;
			for (int i = 0; i < swarm_size; i++) {
				r1 = Math.random();
				r2 = Math.random();

				for (int j = 0; j < dimension; j++) {
					new_velocity[j] = w
							* velocity[i][j]
							+ c1
							* r1
							* (pbest[i].getPosition()[j] - population[i]
									.getPosition()[j])
							+ c2
							* r2
							* (gbest.getPosition()[j] - population[i]
									.getPosition()[j]);
					if (new_velocity[j] > vmax) {
						new_velocity[j] = vmax;
					} else if (new_velocity[j] < -vmax) {
						new_velocity[j] = -vmax;
					} else {
					}
				}

				velocity[i] = new_velocity;
				new_velocity = new double[dimension];

				for (int j = 0; j < dimension; j++) {
					if ((population[i].getPosition()[j] + velocity[i][j] < range_max && population[i]
							.getPosition()[j] + velocity[i][j] > range_min)
							|| (population[i].getPosition()[j] + velocity[i][j] > -range_max && population[i]
									.getPosition()[j] + velocity[i][j] < -range_min)) {
						new_positions[j] = population[i].getPosition()[j]
								+ velocity[i][j];

					}
				}

				population[i].setPosition(new_positions);
				new_positions = new double[dimension];

				int x = fitness(population[i]);
				if (x > bestfitness[i]) {
					pbest[i] = population[i];
					bestfitness[i] = x;
					if (x > fitness(gbest)) {
						gbest = population[i];
					}
				}
				gbest.writeFile("gbest", gbest.generateText());
			}// end for
			
		}// end while
	}

	public void print_pbest() throws IOException {
		for (int i = 0; i < swarm_size; i++) {
			System.out.println(" fitness:" + bestfitness[i]);
		}
	}
}
