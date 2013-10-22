package ANN;

public class Test {
	public static void main(String[] args) {
		Network network=new Network();
		double input0, input1, input2, input3, input4, input5, input6, input7;
		while(true){
		input0=(Math.random()-0.5)*5;
		input1=(Math.random()-0.5)*5;
		input2=(Math.random()-0.5)*5;
		input3=(Math.random()-0.5)*5;
		input4=500;
		input5=(Math.random()-0.5)*5;
		input6=(Math.random()-0.5)*5;
		input7=(Math.random()-0.5)*5;
		network.setInputs(input0, input1, input2, input3, input4, input5, input6, input7);
		
		double trunradians=network.getOutput();
		
		System.out.println(trunradians);}
		
		
		
	}
}
