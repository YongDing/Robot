package sample;

import java.awt.Color;
import java.util.List;
import java.util.ArrayList;

import module.EnemySelector;
import module.Gun;
import module.Movement;
import module.Radar;
import module.Skeleton;
import module.Targeting;

import robocode.Condition;
import robocode.util.Utils;


import EnemySelector.LastScanned;
import GA.Chromosome;
import Gun.CeaseFire;
import Gun.DistanceBase;
import Gun.Light;
import Gun.Medium;
import Gun.Powerful;
import Movement.CircularMovement;
import Movement.DodgeBulletMovement;
import Movement.LinearMovement;
import Movement.RandomMovement;
import Movement.SquareOffMovement;
import Movement.StopMovement;
import Movement.WallsMovement;
import Radar.*;
import Targeting.CircularTargeting;
import Targeting.HeadOnTargeting;
import Targeting.LinearTargeting;
import Targeting.QuietGun;

public class SuperTank extends Skeleton{
	
	public List<Radar> radars = new ArrayList<Radar>();
	public List<Gun> guns = new ArrayList<Gun>();
	public List<Targeting> targetings = new ArrayList<Targeting>();
	public List<Movement> movements = new ArrayList<Movement>();
	public EnemySelector lastScanned;
	
	protected void initialize() {
		System.out.println("Initialising");
		  
		lastScanned = new LastScanned(this);
		
		Radar spinningRadar = new SpinnerRadar(this);
		Radar targetScopeFocusRadar = new TargetScopeFocusRadar(this);
		Radar targetFocusRadar = new TargetFocusRadar(this);
		Radar oscillatingRadar = new OscillatingRadar(this);
		radars.add(spinningRadar);
		radars.add(targetScopeFocusRadar);
		radars.add(targetFocusRadar);
		radars.add(oscillatingRadar);
		
		
		Gun ceaseFire = new CeaseFire(this);
		Gun distanceBaseGun = new DistanceBase(this);
		Gun lightGun = new Light(this);
		Gun mediumGun = new Medium(this);
		Gun powerfulGun = new Powerful(this);
		guns.add(ceaseFire);
		guns.add(distanceBaseGun);
		guns.add(lightGun);
		guns.add(mediumGun);
		guns.add(powerfulGun);
		
		Targeting quietGun = new QuietGun(this);
		Targeting headOnTargeting = new HeadOnTargeting(this);
		Targeting linearTargeting = new LinearTargeting(this);
		Targeting circularTargeting = new CircularTargeting(this);
		targetings.add(quietGun);
		targetings.add(headOnTargeting);
		targetings.add(linearTargeting);
		targetings.add(circularTargeting);
		
		
		Movement circularMovement = new CircularMovement(this);
		Movement dodgeBulleMovement = new DodgeBulletMovement(this);
		Movement linearMovement = new LinearMovement(this);
		Movement randomMovement = new RandomMovement(this);
		Movement squareOffMovement = new SquareOffMovement(this);
		Movement wallsMovement = new WallsMovement(this);
		Movement stopMovement = new StopMovement(this);
		movements.add(circularMovement);
		movements.add(dodgeBulleMovement);
		movements.add(linearMovement);
		movements.add(randomMovement);
		movements.add(squareOffMovement);
		movements.add(wallsMovement);
		movements.add(stopMovement);
	}
	
	protected void selectBehavior() {
		System.out.println("Selecting");
		Chromosome gene = Chromosome.generateRandom();
//		NO_OF_MOVEMENT, NO_OF_GUN, NO_OF_RADAR, NO_OF_TARGETING
		char[] strategies= gene.getGene().toCharArray();
		for (int i = 0; i < strategies.length; i++) {
			int idx = Integer.parseInt(Character.toString(strategies[i]));
			System.out.println("index:"+idx);
			switch (i) {
			case 0:
				movement = movements.get(idx);
				break;
			case 1:
				gun = guns.get(idx);
				break;
			case 2:
				radar = radars.get(idx);
				break;
			case 3:
				targeting = targetings.get(idx);
				break;
			}
		}
		enemySelector = lastScanned;
	}
	
	public void run() {
		System.out.println("Running");
		setAdjustRadarForRobotTurn(true);
		setAdjustGunForRobotTurn(true);
		setAdjustRadarForGunTurn(true);
		
		// Creating the custom event EnemyFires
		addCustomEvent(new Condition("EnemyFires") {
			public boolean test() {
				return (enemy != null
						&& enemy.previousEnergy > enemy.energy
						&& enemy.previousEnergy - enemy.energy <= robocode.Rules.MAX_BULLET_POWER
						&& !Utils.isNear((enemy.previousEnergy - enemy.energy),
								robocode.Rules.getBulletDamage(bulletPower)) && enemy.distance > 55);
			};
		});
		
		initialize();
		this.setColors(Color.red, Color.blue, Color.yellow, Color.black,
				Color.green);
		while (true) {
			updateEnemyBullets();
			selectBehavior();
			executeBehavior();
		}
	}
	

}
