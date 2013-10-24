package Movement;

import module.Movement;
import module.Skeleton;

public class CircularMovement extends Movement{

	public CircularMovement(Skeleton bot) {
		super(bot);
		// TODO Auto-generated constructor stub
	}
	
	public void move(){
		// switch directions if we've stopped
		if (bot.getVelocity() == 0)
			bot.moveDirection *= -1;

		// circle our enemy
		bot.setTurnRight(bot.enemy.bearing + 90);
		bot.setAhead(100 * bot.moveDirection);
	}

}
