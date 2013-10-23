package Gun;

import robocode.Bullet;
import robocode.Rules;
import module.Gun;
import module.Skeleton;

public class Light extends Gun{

	public Light(Skeleton bot) {
		super(bot);
		// TODO Auto-generated constructor stub
	}
	
	public void fire(){
		double bulletPower= Rules.MIN_BULLET_POWER;
		bot.bulletPower= bulletPower;
		if (bot.getGunHeat()==0){
			Bullet b = bot.setFireBullet(bulletPower);
			bot.registerBullet(b);
		}
	}

}
