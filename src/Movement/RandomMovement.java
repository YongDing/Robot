package Movement;

import module.Movement;
import module.Skeleton;

public class RandomMovement extends Movement{

	public RandomMovement(Skeleton bot) {
		super(bot);
		// TODO Auto-generated constructor stub
	}
	
	public void move(){
		switch (Math.random() > 0.5 ? 0 : 1) {
		case 0:
			bot.setTurnRightRadians(Math.random()*0.5*Math.PI);
			break;
		case 1:
			bot.setTurnLeftRadians(Math.random()*0.5*Math.PI);
			break;
		}
	}
	

}
