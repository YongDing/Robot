package Movement;

import module.Movement;
import module.Skeleton;

public class LinearMovement extends Movement{

	public LinearMovement(Skeleton bot) {
		super(bot);
		// TODO Auto-generated constructor stub
	}
	
	public void move(){
		bot.setAhead(200);
		bot.setBack(200);
	}

}
