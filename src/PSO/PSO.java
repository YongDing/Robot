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
	Particle[] pbest;
	Particle gbest;

	double[][] velocity;

	// robocode environment
	RobocodeEngine engine;
	BattleObserver obsever;
	BattleCompletedEvent results;
	BattleSpecification battleSpec;

	public PSO() {
		dimension = (Network.input_number + 1) * Network.hidden_number
				+ (Network.hidden_number + 1);
		swarm_size = (int) (10 + 2 * Math.sqrt(dimension));
		range_max = 2;
		range_min = 0.1;
		w = 0.73;
		c1 = 1.19;
		c2 = 1.19;
		generations = 500;
		vmax = range_max;
		population = new Particle[swarm_size];
		velocity = new double[swarm_size][dimension];
		pbest = new Particle[swarm_size];
	}

	public void modify_parameters(double w, double c1, double c2) {
		this.w = w;
		this.c1 = c1;
		this.c2 = c2;
	}

	public void initial() {
		setEnviroment();
		
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
			velocity[i] = positions;
			positions = new double[dimension];
		}
	}

	public void setEnviroment() {
		// Disable log messages from Robocode
		RobocodeEngine.setLogMessagesEnabled(false);

		// Create the RobocodeEngine
		engine = new RobocodeEngine(new File("C:\\robocode"));

		// Add our own battle listener to the RobocodeEngine
		obsever = new BattleObserver();
		engine.addBattleListener(obsever);

		// Show the Robocode battle view
		engine.setVisible(false);
		// Setup the battle specification

		int numberOfRounds = 5;
		BattlefieldSpecification battlefield = new BattlefieldSpecification(
				800, 600); // 800x600
		RobotSpecification[] selectedRobots = engine
				.getLocalRepository("sample.Robotd, sample.Crazy");

		battleSpec = new BattleSpecification(numberOfRounds, battlefield,
				selectedRobots);
		// Run our specified battle and let it run till it is over
	}

	public void exit() {
		// Cleanup our RobocodeEngine
		engine.close();

		// Make sure that the Java VM is shut down properly
		System.exit(0);
	}

	public int fitness(Particle c) {
		engine.runBattle(battleSpec, true); // waits till the battle finishes
		results = obsever.getResult();
		int sum = 0;
		for (robocode.BattleResults result : results.getSortedResults()) {
			sum += result.getScore();
		}
		return sum / 5;
	}

	public void run() {
		c_generation = 1;
		double r1, r2;
		double[] new_velocity = new double[dimension];
		double[] new_positions = new double[dimension];
		while (c_generation < generations) {
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

				if (fitness(population[i]) > fitness(pbest[i])) {
					pbest[i] = population[i];
					if (fitness(population[i]) > fitness(gbest)) {
						gbest = population[i];
					}
				}
			}

			// System.out.println("-----------------------------------");
			exit();
		}
	}
}
