package GA;

import java.util.Random;

/**
 * This class is used to define a chromosome for the gentic algorithm 
 * simulation.  
 * 
 * This class is essentially nothing more than a container for the details 
 * of the chromosome, namely the gene (the string that represents our target 
 * string) and the fitness (how close the gene is to the target string).
 *
 * Note that this class is immutable.  Calling <code>mate(Chromsome)</code>
 * or <code>mutate</code> will result in a new <code>Chromosome</code>
 * instance being created.
 * 
 */
public class Chromosome implements Comparable<Chromosome> {
        private final String gene;
        private double fitness;
        
        public static final int NO_OF_MOVEMENT = 7;
        public static final int NO_OF_GUN = 5;
        public static final int NO_OF_RADAR = 4;
        public static final int NO_OF_TARGETING = 4;
        public static final int[] STRATEGIES_ARR= { NO_OF_MOVEMENT, NO_OF_GUN, NO_OF_RADAR, NO_OF_TARGETING };
        
        /** The target gene, converted to an array for convenience. */
        private static final char[] TARGET_GENE = "1111".toCharArray();

        /** Convenience randomizer. */
        private static final Random rand = new Random(System.currentTimeMillis());
        
        /**
         * Default constructor.
         *
         * @param gene The gene representing this <code>Chromosome</code>.
         */
        public Chromosome(String gene) {
                this.gene    = gene;
                this.fitness = 0.0;
        }
        
        /**
         * Method to retrieve the gene for this <code>Chromosome</code>.
         *
         * @return The gene for this <code>Chromosome</code>.
         */
        public String getGene() {
                return gene;
        }
        
        /**
         * Method to retrieve the fitness of this <code>Chromosome</code>.  Note
         * that a lower fitness indicates a better <code>Chromosome</code> for the
         * solution.
         *
         * @return The fitness of this <code>Chromosome</code>.
         */

        
        

        public double getFitness() {
			return fitness;
		}

		public void setFitness(double fitness) {
			this.fitness = fitness;
		}

		/**
         * Method to generate a new <code>Chromosome</code> that is a random
         * mutation of this <code>Chromosome</code>.  This method randomly
         * selects one character in the <code>Chromosome</code>s gene, then
         * replaces it with another random (but valid) character.  Note that
         * this method returns a new <code>Chromosome</code>, it does not
         * modify the existing <code>Chromosome</code>.
         * 
         * @return A mutated version of this <code>Chromosome</code>.
         */
        public Chromosome mutate() {
                char[] arr  = gene.toCharArray();
                int idx     = rand.nextInt(arr.length);
//                int delta   = (rand.nextInt() % 90) + 32;
//                arr[idx]    = (char) ((arr[idx] + delta) % 122);
                arr[idx] = Integer.toString(rand.nextInt(STRATEGIES_ARR[idx])).toCharArray()[0];
                return new Chromosome(String.valueOf(arr));
        }

        /**
         * Method used to mate this <code>Chromosome</code> with another.  The
         * resulting child <code>Chromosome</code>s are returned.
         * 
         * @param mate The <code>Chromosome</code> to mate with.
         * 
         * @return The resulting <code>Chromosome</code> children.
         */
        public Chromosome[] mate(Chromosome mate) {
                // Convert the genes to arrays to make thing easier.
                char[] arr1  = gene.toCharArray();
                char[] arr2  = mate.gene.toCharArray();
                
                // Select a random pivot point for the mating
                int pivot    = rand.nextInt(arr1.length);
                
                // Provide a container for the child gene data
                char[] child1 = new char[gene.length()];
                char[] child2 = new char[gene.length()];
                
                // Copy the data from each gene to the first child.
                System.arraycopy(arr1, 0, child1, 0, pivot);
                System.arraycopy(arr2, pivot, child1, pivot, (child1.length - pivot));
                
                // Repeat for the second child, but in reverse order.
                System.arraycopy(arr2, 0, child2, 0, pivot);
                System.arraycopy(arr1, pivot, child2, pivot, (child2.length - pivot));

                return new Chromosome[] { new Chromosome(String.valueOf(child1)), 
                                new Chromosome(String.valueOf(child2))}; 
        }
        
        /**
         * A convenience method to generate a randome <code>Chromosome</code>.
         * 
         * @return A randomly generated <code>Chromosome</code>.
         */
        /* package */ public static Chromosome generateRandom() {
                char[] arr = new char[STRATEGIES_ARR.length];
                for (int i = 0; i < arr.length; i++) {
                	arr[i] = Integer.toString(rand.nextInt(STRATEGIES_ARR[i])).toCharArray()[0];
                }

                return new Chromosome(String.valueOf(arr));
        }

        /**
         * Method to allow for comparing <code>Chromosome</code> objects with
         * one another based on fitness.  <code>Chromosome</code> ordering is 
         * based on the natural ordering of the fitnesses of the
         * <code>Chromosome</code>s.  
         */
        @Override
        public int compareTo(Chromosome c) {
                if (fitness < c.fitness) {
                        return 1;
                } else if (fitness > c.fitness) {
                        return -1;
                }
                
                return 0;
        }
        
        /**
         * @see Object#equals(Object)
         */
        @Override
        public boolean equals(Object o) {
                if (!(o instanceof Chromosome)) {
                        return false;
                }
                
                Chromosome c = (Chromosome) o;
                return (gene.equals(c.gene) && fitness == c.fitness);
        }
        
        /**
         * @see Object#hashCode()
         */
        @Override
        public int hashCode() {                
                return new StringBuilder().append(gene).append(fitness)
                                .toString().hashCode();
        }
        
        public static void main(String[] args) {
//        	Chromosome gene1 = Chromosome.generateRandom();
//        	System.out.println(gene1.getGene());
        	System.out.println((double)1/3);
        	
        }
}