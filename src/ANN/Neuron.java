package ANN;
import java.util.ArrayList;

public class Neuron {
	private double value;
	// the id of its previous nodes
	private ArrayList<Neuron> previous;
	// their corresponding weights
	private ArrayList<Double> weights;

	public Neuron() {
		previous = new ArrayList<Neuron>();
		weights = new ArrayList<Double>();
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public void setValue() {
		this.value = this.getNeuronOutput();
	}

	public ArrayList<Neuron> getprevious() {
		return previous;
	}

	public void setprevious(ArrayList<Neuron> previous) {
		this.previous = previous;
	}

	public ArrayList<Double> getWeights() {
		return weights;
	}

	public void setWeights(ArrayList<Double> weights) {
		this.weights = weights;
	}

	public void connect(Neuron n) {
		previous.add(n);
		int plus_mins=Math.random()>0.5?1:-1;
		weights.add((Math.random()*(2-0.1)+0.1)*plus_mins);
	}

	public double getNeuronOutput() {
		double m = 0d;
		for (int i = 0; i < previous.size(); i++) {
			Neuron n = (Neuron) previous.get(i);
			double weight = weights.get(i);
			m += n.getValue() * weight;
		}
		return (1 / (1 + Math.exp(-m)));
	}

}
