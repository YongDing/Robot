package Gun;

import robocode.Bullet;
import robocode.Rules;
import sample.SuperTank;
import module.Gun;
import module.Skeleton;

public class DistanceBase extends Gun{

	public DistanceBase(SuperTank superTank) {
		super(superTank);
		// TODO Auto-generated constructor stub
	}
	
	public void fire() {
		double bulletPower = Math.min(400 / bot.enemy.distance, Rules.MAX_BULLET_POWER);
		
		if (bot.getGunHeat()==0){
			Bullet b = bot.setFireBullet(bulletPower);
			bot.registerBullet(b);
		}
	}

}
