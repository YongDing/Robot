package Strategies;

import java.awt.Robot;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import robocode.AdvancedRobot;

public class MovingPattern {
	private AdvancedRobot advancedRobot;
	private List<AdvancedRobot> enemies = new ArrayList<AdvancedRobot>();
	
	
	public List<AdvancedRobot> getEnemies() {
		return enemies;
	}


	public void setEnemies(List<AdvancedRobot> enemies) {
		this.enemies = enemies;
	}


	public AdvancedRobot getAdvancedRobot() {
		return advancedRobot;
	}


	public void setAdvancedRobot(AdvancedRobot advancedRobot) {
		this.advancedRobot = advancedRobot;
	}


	void random_move() {
		switch (Math.random() > 0.5 ? 0 : 1) {
		case 0:
			this.advancedRobot.setTurnRightRadians(Math.random()*0.5*Math.PI);
			break;
		case 1:
			this.advancedRobot.setTurnLeftRadians(Math.random()*0.5*Math.PI);
			break;
		}
		this.advancedRobot.execute();
	}
	
	void linear_move() {
		this.advancedRobot.ahead(200);
		this.advancedRobot.setBack(200);
		this.advancedRobot.execute();
	}
	
	void circular_move() {
		this.advancedRobot.setTurnRightRadians(3*Math.PI);
		this.advancedRobot.setMaxVelocity(4);
		this.advancedRobot.ahead(100);
	}
	
}
