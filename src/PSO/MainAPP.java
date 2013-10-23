package PSO;

import java.io.IOException;

public class MainAPP {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		PSO pso=new PSO();
		System.out.println("cleaning data");
		pso.initialFile();

		
		System.out.println("xxxxxxx");
		pso.initial();
		System.out.println("xxxxxxx");
		System.out.println("runing pso algorithm:");
		pso.run();
		System.out.println("xxxxxxx");
		// Make sure that the Java VM is shut down properly
		pso.exit();
		System.exit(0);
	}

}
