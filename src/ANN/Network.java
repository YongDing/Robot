package ANN;
import java.util.ArrayList;

public class Network {
	// three layers

	public Neuron[] n1;// Input nodes
	public Neuron[] n2;// Hidden nodes
	public Neuron[] bias; // bias nodes
	public Neuron n3;// Output nodes
	public int target_output; // be used for calculating accuracy.

	double input0, input1, input2, input3;

	public Network() {
		initialStructure();
	}
	
	public Network(int input_number, int hidden_number) {
		initialStructure(input_number,hidden_number);
	}

	public void setInputs(double input0, double input1, double input2,
			double input3) {
		this.input0 = input0;
		this.input1 = input1;
		this.input2 = input2;
		this.input3 = input3;

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

	public int getTarget_output() {
		return target_output;
	}

	public void setTarget_output(int target_output) {
		this.target_output = target_output;
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
		int input_number=4,hidden_number=3;
		n1 = new Neuron[input_number]; // Input nodes
		n2 = new Neuron[hidden_number]; // Hidden nodes
		bias = new Neuron[hidden_number+1]; // bias nodes
		n3 = new Neuron();
		
		for(int i=0;i<input_number;i++){
			n1[i]=new Neuron();
		}
		for(int i=0;i<hidden_number+1;i++){
			bias[i]=new Neuron();
		}
		for(int i=0;i<hidden_number;i++){
			n2[i]=new Neuron();
			for(int j=0;j<input_number;j++){
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
		bias = new Neuron[hidden_number+1]; // bias nodes
		n3 = new Neuron();
		
		for(int i=0;i<input_number;i++){
			n1[i]=new Neuron();
		}
		for(int i=0;i<hidden_number+1;i++){
			bias[i]=new Neuron();
		}
		for(int i=0;i<hidden_number;i++){
			n2[i]=new Neuron();
			for(int j=0;j<input_number;j++){
				n2[i].connect(n1[j]);
			}
			n2[i].connect(bias[i]);
			n3.connect(n2[i]);
		}
		n3.connect(bias[hidden_number]);
	}


	public double getOutput() {
		this.initialValues();
		return n3.getNeuronOutput();
	}

	public int getFinalOutput() {
		this.initialValues();
		return (int) n3.getOutput();
	}

	public boolean correct(int value) {
		if (value == target_output) {
			return true;
		} else
			return false;
	}

	public void AddConnect(Neuron n1, Neuron n2) {
		n1.connect(n2);
	}

	public void cancelConnect(Neuron n) {
		ArrayList<Neuron> a = new ArrayList<Neuron>();
		n.setprevious(a);
	}

}
