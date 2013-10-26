package module;

import robocode.*;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;
import java.awt.Color;
import java.awt.event.InputEvent;


import robocode.AdvancedRobot;
import robocode.Bullet;
import robocode.Condition;
import robocode.CustomEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;

public class Skeleton extends AdvancedRobot{
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
		enemySelector.select();
		radar.scan();
		//The order is important!
		targeting.target();
		gun.fire();
		movement.move();
		execute();
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

}	
