package ANN;

public class Test {
	public static void main(String[] args) {
		
		
		//input0 bearing of gun
		//input1 power of bullet
		//input2 distance
		//input3 heading
		//input4 velocity
		//input5 heading of enemy
		//input6 velocity of enemy
		//input7 bearing of enemy
		
		double input0, input1, input2, input3, input4, input5, input6, input7;
		Network network=new Network();
		while(true){
		
		input0=(Math.random())*Math.PI;
		input1=(Math.random())*3;
		input2=(Math.random())*1000;
		input3=(Math.random())*Math.PI;
		input4=Math.random()*8;
		input5=(Math.random())*Math.PI;
		input6=(Math.random()-0.5)*5;
		input7=(Math.random()-0.5)*Math.PI;
		network.setInputs(input0, input1, input2, input3, input4, input5, input6, input7);
		
		double trunradians=network.getOutput();
		
		System.out.println(trunradians);}
		
		
		
	}
}
