package PSO;

import java.io.IOException;

import sample.Robotd;

public class MainAPP {

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		Robotd robot=new Robotd();
		
		
		PSO pso=new PSO("sample.Robotd","sample.SittingDuck");
		System.out.println("cleaning data");
		pso.initialFile();
		System.out.println("set robocode environment");
		pso.setEnvironment();

		System.out.println("initialize positions of particles.......");
		pso.initial();
		
		System.out.println("runing pso algorithm:");
		pso.run();
		System.out.println("finish....");
		
		// Make sure that the Java VM is shut down properly
		pso.exit();
		System.exit(0);
	}

}
