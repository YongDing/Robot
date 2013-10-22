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
	final int input_number = 8, hidden_number = 10;

	public Neuron[] n1;// Input nodes
	public Neuron[] n2;// Hidden nodes
	public Neuron[] bias; // bias nodes
	public Neuron n3;// Output node, the degree should turn

	// input0 bearing of gun
	// input1 power of bullet
	// input2 distance
	// input3 heading
	// input4 velocity
	// input5 heading of enemy
	// input6 velocity of enemy
	// input7 bearing of enemy
	double input0, input1, input2, input3, input4, input5, input6, input7;

	public Network() {
		initialStructure();
	}

	public Network(int hidden_number) {
		initialStructure(7, hidden_number);
	}

	public void setInputs(double input0, double input1, double input2,
			double input3, double input4, double input5, double input6,
			double input7) {

		// normalize input (x-min)/(max-min)
		this.input0 = (input0 + Math.PI) / (Math.PI * 2);
		this.input1 = (input1 - 1) / 2;
		this.input2 = (input2 - 0) / 1000;
		this.input3 = input3 / (Math.PI * 2);
		this.input4 = (input4 + 8) / 16;
		this.input5 = input5 / (Math.PI * 2);
		this.input6 = (input6 + 8) / 16;
		this.input7 = (input7 + Math.PI) / (Math.PI * 2);
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
		n1[4].setValue(input4);
		n1[5].setValue(input5);
		n1[6].setValue(input6);
		n1[7].setValue(input7);

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
		double angle = (n3.getNeuronOutput() - 0.5) * 2 * Math.PI;
		return angle;
	}

	public void AddConnect(Neuron n1, Neuron n2) {
		n1.connect(n2);
	}

	public void cancelConnect(Neuron n) {
		ArrayList<Neuron> a = new ArrayList<Neuron>();
		n.setprevious(a);
	}

	public void updateWeight() {

		for (int i = 0; i < hidden_number; i++) {

		}
	}

	public ArrayList<Float> readToFloat(String filePath) throws IOException {
		InputStream is = new FileInputStream(filePath);
		ArrayList<Float> weights = new ArrayList<Float>();
		ArrayList<String> text = new ArrayList<String>();
		for (int i = 0; i < hidden_number; i++) {
			text.add("hidden_" + i + "_weights");
		}
		String line;
		float f;
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		line = reader.readLine();
		while (line != null) {
			if (!text.contains(line)) {
				f = Float.parseFloat(line.toString());
				weights.add(f);
				line = reader.readLine();
			}
		}
		reader.close();
		is.close();
		return weights;
	}

	public void writeFile(String filePath, String content) throws IOException {
		FileWriter fw = new FileWriter(filePath);
		fw.write(content);
		fw.close();
	}
}
