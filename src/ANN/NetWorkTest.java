package ANN;

public class NetWorkTest {
	public static void main(String[] args) {

		// input0 bearing of gun
		// input1 power of bullet
		// input2 distance
		// input3 heading
		// input5 heading of enemy
		// input6 velocity of enemy
		// input7 bearing of enemy

		double input0, input1, input2, input3, input4, input5, input6, input7;
		
		int plus_mins=0;
		while (true) {
			Network network = new Network();
			{
				input0 = (Math.random()*(Math.random()>0.5?1:-1)) * Math.PI;
				input1 = Math.round(Math.random() * 2)+1;
				input2 = Math.round((Math.random()) * 1000);
				input3 = (Math.random()) * 2*Math.PI;
				input5 = Math.random() * 2*Math.PI;
				input6 = Math.random() * 8*(Math.random()>0.5?1:-1);
				input7 = Math.random()*(Math.random()>0.5?1:-1) * Math.PI;
			}
			network.setInputs(input0, input1, input2, input3, input5,
					input6, input7);

 			double trunradians = network.getOutput();

			System.out.println(trunradians);
		}

	}
}