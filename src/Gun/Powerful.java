package Gun;

import robocode.Bullet;
import robocode.Rules;
import module.Gun;
import module.Skeleton;

public class Powerful extends Gun{

	public Powerful(Skeleton bot) {
		super(bot);
		// TODO Auto-generated constructor stub
	}
	
	public void fire(){
		double bulletPower= Rules.MAX_BULLET_POWER;
		bot.bulletPower= bulletPower;
		if (bot.getGunHeat()==0){
			Bullet b = bot.setFireBullet(bulletPower);
			bot.registerBullet(b);
		}
	}

}
