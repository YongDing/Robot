package PSO;



import ANN.Network;

public class PSO {
	int swarm_size,dimension;
	int generations;
	int c_generation = 1;
	// parameters of PSO
	double w, c1, c2, range_max,range_min;
	double vmax;
	Particle[] population;
	Particle[] pbest, velocity;
	Particle gbest;
	
	public PSO(int input_number, int hidden_number ) {
		dimension=(input_number+1)*hidden_number+(hidden_number+1);
		swarm_size = (int) (10 + 2 * Math.sqrt(dimension));
		range_max = 2;
		range_min =0.1;
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
		double[] positions=new double[dimension];
		// initial pbest and particles
		for (int i = 0; i < swarm_size; i++) {
			for(int j=0;j<dimension;j++){
				positions[j]=(Math.random()*(range_max-range_min)+range_min) * (Math.random() > 0.5 ? 1 : -1);
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
			re = Math.random() * vmax * (Math.random() > 0.5 ? 1 : -1);
			im = Math.random() * vmax * (Math.random() > 0.5 ? 1 : -1);
			velocity[i] = new Complex(re, im);
		}

		// save the information to compare performance
		System.arraycopy(population, 0, population_copy, 0,
				population_copy.length);
		gbest_copy=population_copy[0];
		for (int i = 0; i < population.length; i++) {
			pbest_copy[i] = population_copy[i];
			if (fitness(population_copy[i]) > fitness(gbest_copy)) {
				gbest_copy = population_copy[i];
			}
		}

	}
	
	
}
