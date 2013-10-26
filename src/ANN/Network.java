package ANN;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Network {
	// three layers

	public static final int input_number = 4;
	public static final int hidden_number = 3;
	
	public Neuron[] n1;// Input nodes
	public Neuron[] n2;// Hidden nodes
	public Neuron[] bias; // bias nodes
	public Neuron n3;// Output node, the degree should turn

	//input0 heading of enemy
	//input1 velocity of enemy
	//input2 distance of enemy
	//input3 heading of gun
	double input0, input1, input2, input3;

	public Network() {
		initialStructure();
	}

	public Network(int hidden_number) {
		initialStructure(input_number, hidden_number);
	}

	
	public void setInputs(double input0, double input1, double input2,
			double input3) {

		// normalize input (x-min)/(max-min)
		
		//input0 heading of enemy
		//input1 velocity of enemy
		//input2 distance of enemy
		//input3 heading of gun
		this.input0 = (input0) / (Math.PI * 2);
		this.input1 = (input1 +8) / 16;
		this.input2 = (input2) / 1000;
		this.input3 = input3 / (Math.PI * 2);
		initialInput();
		initialValues();
	}
	
	

	public Neuron[] getInputNeurons() {
		return n1;
	}

	public void setInputNeurons(Neuron[] n1) {
		this.n1 = n1;
	}

	public Neuron[] getHiddenNeurons() {
		return n2;
	}

	public Neuron getOutputNeuron() {
		return n3;
	}

	public void initialInput() {
		n1[0].setValue(input0);
		n1[1].setValue(input1);
		n1[2].setValue(input2);
		n1[3].setValue(input3);

	}

	public void initialValues() {
		for (Neuron b : bias) {
			b.setValue(1);
		}

		for (Neuron n : n2) {
			n.setValue();
		}
		n3.setValue();
	}

	public void initialStructure() {

		n1 = new Neuron[input_number]; // Input nodes
		n2 = new Neuron[hidden_number]; // Hidden nodes
		bias = new Neuron[hidden_number + 1]; // bias nodes
		n3 = new Neuron();

		for (int i = 0; i < input_number; i++) {
			n1[i] = new Neuron();
		}
		for (int i = 0; i < hidden_number + 1; i++) {
			bias[i] = new Neuron();
		}
		for (int i = 0; i < hidden_number; i++) {
			n2[i] = new Neuron();
			for (int j = 0; j < input_number; j++) {
				n2[i].connect(n1[j]);
			}
			n2[i].connect(bias[i]);
			n3.connect(n2[i]);
		}
		n3.connect(bias[hidden_number]);
	}

	public void initialStructure(int input_number, int hidden_number) {
		n1 = new Neuron[input_number]; // Input nodes
		n2 = new Neuron[hidden_number]; // Hidden nodes
		bias = new Neuron[hidden_number + 1]; // bias nodes

		n3 = new Neuron();

		for (int i = 0; i < input_number; i++) {
			n1[i] = new Neuron();
		}
		for (int i = 0; i < hidden_number + 1; i++) {
			bias[i] = new Neuron();
		}
		for (int i = 0; i < hidden_number; i++) {
			n2[i] = new Neuron();
			for (int j = 0; j < input_number; j++) {
				n2[i].connect(n1[j]);
			}
			n2[i].connect(bias[i]);
			n3.connect(n2[i]);
		}
		n3.connect(bias[hidden_number]);
	}

	public double getOutput() {
		this.initialValues();
//		double angle = (n3.getNeuronOutput() - 0.5) * (47/180)*Math.PI;
		double angle = (n3.getNeuronOutput() - 0.5) * 2*Math.PI;
		return angle;
	}

	public void AddConnect(Neuron n1, Neuron n2) {
		n1.connect(n2);
	}

	public void cancelConnect(Neuron n) {
		ArrayList<Neuron> a = new ArrayList<Neuron>();
		n.setprevious(a);
	}


	
	public void updateWeight(ArrayList<Double> weights) {
		ArrayList<Double> temp = new ArrayList<Double>();
		if (weights.size() > 0) {
			for (int i = 0; i < hidden_number; i++) {
				temp=new ArrayList<Double>(weights.subList(i*(input_number+1), (i+1)*(input_number+1)));
				n2[i].setWeights(temp);	
			}
			temp=new ArrayList<Double>(weights.subList(hidden_number*(input_number+1), hidden_number*(input_number+1)+hidden_number+1));
			n3.setWeights(temp);			
		}
	}


	
}
