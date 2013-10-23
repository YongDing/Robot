package Gun;

import robocode.Bullet;
import robocode.Rules;
import module.Gun;
import module.Skeleton;

public class Medium extends Gun{

	public Medium(Skeleton bot) {
		super(bot);
		// TODO Auto-generated constructor stub
	}
	
	public void fire(){
		double bulletPower= (Rules.MAX_BULLET_POWER+Rules.MIN_BULLET_POWER)*0.5;
		bot.bulletPower= bulletPower;
		if (bot.getGunHeat()==0){
			Bullet b = bot.setFireBullet(bulletPower);
			bot.registerBullet(b);
		}
	}
	
}
