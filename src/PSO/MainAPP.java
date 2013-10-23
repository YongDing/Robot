package PSO;

import java.io.IOException;

public class MainAPP {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		PSO pso=new PSO();
		System.out.println("initial ");
		pso.initialFile();
		
		pso.setEnviroment();
		
		pso.initial();
		pso.run();
		
		// Make sure that the Java VM is shut down properly
		pso.exit();
		System.exit(0);
	}

}
