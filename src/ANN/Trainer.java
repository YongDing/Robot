package ANN;

import java.io.File;

import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RobocodeEngine;
import robocode.control.RobotSpecification;
import robocode.control.events.BattleCompletedEvent;
import AI.BattleObserver;

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
	
	public void setEnviroment(){
		BattleCompletedEvent result;
   	 
        // Disable log messages from Robocode
        RobocodeEngine.setLogMessagesEnabled(false);
        
        // Create the RobocodeEngine
        RobocodeEngine engine = new RobocodeEngine(new File("C:\\robocode"));

        // Add our own battle listener to the RobocodeEngine 
        BattleObserver obsever=new BattleObserver(); 
        engine.addBattleListener(obsever);

        // Show the Robocode battle view
        engine.setVisible(true);
        // Setup the battle specification

        int numberOfRounds = 5;
        BattlefieldSpecification battlefield = new BattlefieldSpecification(800, 600); // 800x600
        RobotSpecification[] selectedRobots = engine.getLocalRepository("sample.Robotd, sample.Crazy");

        BattleSpecification battleSpec = new BattleSpecification(numberOfRounds, battlefield, selectedRobots);
        // Run our specified battle and let it run till it is over
        engine.runBattle(battleSpec, true); // waits till the battle finishes
        result=obsever.getResult();

        
        engine.runBattle(battleSpec, true); // waits till the battle finishes
        // Cleanup our RobocodeEngine
        engine.close();

        // Make sure that the Java VM is shut down properly
        System.exit(0);
	}
	public void train(double x0, double x1, double x2,double x3,double x4,double x5,double x6,double x7) {
		
	}	
	
}
