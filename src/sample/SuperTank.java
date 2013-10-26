package sample;

import java.awt.Color;
import java.awt.event.InputEvent;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Vector;

import module.BulletInfo;
import module.BulletInfoEnemy;
import module.Enemy;
import module.EnemySelector;
import module.Gun;
import module.Movement;
import module.Radar;
import module.Skeleton;
import module.Targeting;

import robocode.AdvancedRobot;
import robocode.Bullet;
import robocode.BulletHitBulletEvent;
import robocode.BulletHitEvent;
import robocode.BulletMissedEvent;
import robocode.Condition;
import robocode.CustomEvent;
import robocode.DeathEvent;
import robocode.Event;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;
import robocode.SkippedTurnEvent;
import robocode.WinEvent;
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

public class SuperTank extends AdvancedRobot{
	public int moveDirection = 1;
	public int scanDirection = 1;
	//Core Component
	public Radar radar;
	public Gun gun;
	public Movement movement;
	public EnemySelector enemySelector;
	public Targeting targeting;
	
	// The power of the next bullet
	public double bulletPower;
	// The current Enemy
	public Enemy enemy = new Enemy();

	// A Hash-table of all the scanned Enemies
	public Hashtable<String,Enemy> enemies = new Hashtable<String, Enemy>();

	// A Vector of all the fired bullets
	public Vector<BulletInfo> bullets = new Vector<BulletInfo>();
	public Vector<BulletInfoEnemy> enemyBullets = new Vector<BulletInfoEnemy>();
	
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
		
		this.initialize();
		this.selectBehavior();
		
		this.setColors(Color.red, Color.blue, Color.yellow, Color.black,
				Color.green);
		turnRadarRightRadians(2 * Math.PI);
		
		while (true) {
			this.updateEnemyBullets();
			
			this.executeBehavior();
		}
	}
	
	private void listenEvent(Event e) {
		enemySelector.listen(e);
		radar.listen(e);
		gun.listen(e);
		targeting.listen(e);
		movement.listen(e);
	}

	private void listenInputEvent(InputEvent e) {
		if (enemySelector != null)
			enemySelector.listenInput(e);
		if (radar != null)
			radar.listenInput(e);
		if (gun != null)
			gun.listenInput(e);
		if (targeting != null)
			targeting.listenInput(e);
		if (movement != null)
			movement.listenInput(e);
	}
	
	protected void executeBehavior() {
		try {
//			radar.scan();
//			enemySelector.select();
//			//The order is important!
//			targeting.target();
//			gun.fire();
			enemySelector.select();
			radar.scan();
			
			execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void registerBullet(Bullet bullet) {
		BulletInfo bulletInfo = new BulletInfo();
		bulletInfo.bullet = bullet;
		bulletInfo.toName = enemy.name;
		bulletInfo.targeting = targeting.getClass().getSimpleName();
		bulletInfo.timeFire = (int) getTime();
		bullets.add(bulletInfo);
	}
	
	//Calcute the position of the bullet
	protected void updateEnemyBullets() {
		Iterator<BulletInfoEnemy> i = enemyBullets.iterator();
		while (i.hasNext()) {
			BulletInfoEnemy bullet = i.next();
			bullet.x = -1 * Math.sin(bullet.headingRadians) * bullet.velocity
					+ bullet.x;
			bullet.y = -1 * Math.cos(bullet.headingRadians) * bullet.velocity
					+ bullet.y;
		}
	}
	
	public void onScannedRobot(ScannedRobotEvent e) {
		//Got you!
		Enemy scanned = enemies.get(e.getName());
		if (scanned == null)
			scanned = new Enemy();
		scanned.name = e.getName();
		scanned.bearing = e.getBearing();
		scanned.bearingRadians = e.getBearingRadians();
		scanned.previousHeadingRadians = scanned.headingRadians;
		scanned.headingRadians = e.getHeadingRadians();
		scanned.distance = e.getDistance();
		scanned.x = getX() + e.getDistance()
				* Math.sin(getHeadingRadians() + e.getBearingRadians());
		scanned.y = getY() + e.getDistance()
				* Math.cos(getHeadingRadians() + e.getBearingRadians());
		scanned.velocity = e.getVelocity();
		scanned.previousEnergy = scanned.energy;
		scanned.energy = e.getEnergy();
		scanned.timeSinceLastScan = (int) e.getTime() - scanned.timeScanned;
		scanned.timeScanned = (int) e.getTime();
		enemies.put(e.getName(), scanned);
		
		gun.fire();
		targeting.target();
		movement.move();
		
		listenEvent(e);
	}
	
	// Handling the custom event
	public void onCustomEvent(CustomEvent e) {
		Condition condition = e.getCondition();
		if (condition.getName().equals("EnemyFires")) {
			BulletInfoEnemy enemyBullet = new BulletInfoEnemy();
			enemyBullet.fromName = enemy.name;
			enemyBullet.x = enemy.x;
			enemyBullet.y = enemy.y;
			enemyBullet.power = enemy.previousEnergy - enemy.energy;
			enemyBullet.headingRadians = Utils.normalAbsoluteAngle(Math.atan2(
					enemy.x - getX(), enemy.y - getY()));
			enemyBullet.velocity = robocode.Rules
					.getBulletSpeed(enemyBullet.power);
			enemyBullets.add(enemyBullet);
		}
		listenEvent(e);
	}
	
	public void onHitByBullet(HitByBulletEvent e) {
		listenEvent(e);
	}

	public void onHitRobot(HitRobotEvent e) {
		listenEvent(e);
	}

	public void onHitWall(HitWallEvent e) {
		moveDirection *= -1;
		listenEvent(e);
	}

	public void onBulletHit(BulletHitEvent e) {
		listenEvent(e);
	}

	public void onBulletHitBullet(BulletHitBulletEvent e) {
		listenEvent(e);
	}

	public void onBulletMissed(BulletMissedEvent e) {
		listenEvent(e);
	}

	public void onRobotDeath(RobotDeathEvent e) {
		listenEvent(e);
		enemies.remove(e.getName());
		enemySelector.select();
	}

	public void onWin(WinEvent e) {
		listenEvent(e);
	}

	public void onDeath(DeathEvent e) {
		listenEvent(e);
	}

	public void onSkippedTurn(SkippedTurnEvent e) {
		listenEvent(e);
	}
	public static void main(String[] args) {
		SuperTank sTank = new SuperTank();
		sTank.initialize();
		sTank.selectBehavior();
	}
}
