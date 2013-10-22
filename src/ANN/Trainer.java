package ANN;

public class Trainer {
	Network model;


	double learning_rate;


	Trainer(Network model){
		this.model=model;
	}
	
	public Network getModel() {
		return model;
	}

	public void setModel(Network model) {
		this.model = model;
	}
	
	
	public void train(double x0, double x1, double x2,double x3,double x4,double x5,double x6,double x7) {
		
		double [] y=new double[model.getHiddenNeurons().length];
		for(int i=0;i<model.getHiddenNeurons().length;i++){
			y[i]=(model.getHiddenNeurons())[i].getNeuronOutput();
		}
		
		double z = model.getOutput(); // Output layer neuron output
	}	
	
}
