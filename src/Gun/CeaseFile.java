package Gun;

import module.Gun;
import module.Skeleton;

public class CeaseFile extends Gun{

	public CeaseFile(Skeleton bot) {
		super(bot);
		// TODO Auto-generated constructor stub
	}
	
	public void fire() {
		bot.bulletPower = 0;
	}

}
