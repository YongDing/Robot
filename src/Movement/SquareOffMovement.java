package Movement;

import sample.SuperTank;
import module.Movement;
import module.Skeleton;

public class SquareOffMovement extends Movement{

	public SquareOffMovement(SuperTank superTank) {
		super(superTank);
		// TODO Auto-generated constructor stub
	}
	
	public void move(){
		bot.setTurnRight(bot.enemy.bearing + 90);

		// strafe by changing direction every 20 ticks
		if (bot.getTime() % 20 == 0) {
			bot.moveDirection *= -1;
			bot.setAhead(150 * bot.moveDirection);
		}
	}

}
