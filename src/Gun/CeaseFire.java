package Gun;

import sample.SuperTank;
import module.Gun;
import module.Skeleton;

public class CeaseFire extends Gun{

	public CeaseFire(SuperTank superTank) {
		super(superTank);
		// TODO Auto-generated constructor stub
	}
	
	public void fire() {
		bot.bulletPower = 0;
	}

}
