package Gun;

import module.Gun;
import module.Skeleton;

public class CeaseFire extends Gun{

	public CeaseFire(Skeleton bot) {
		super(bot);
		// TODO Auto-generated constructor stub
	}
	
	public void fire() {
		bot.bulletPower = 0;
	}

}
