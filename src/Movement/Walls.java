package Movement;

import module.Movement;
import module.Skeleton;

public class Walls extends Movement{

	public Walls(Skeleton bot) {
		super(bot);
		// TODO Auto-generated constructor stub
	}
	
	public void move() {
		if (bot.getHeading() % 90 != 0) bot.setTurnLeft(bot.getHeading() % 90);		
		bot.setAhead(Double.POSITIVE_INFINITY);
		if (bot.getVelocity()==0)
			bot.setTurnRight(90);
	}
	
}
