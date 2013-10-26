package Movement;

import sample.SuperTank;
import module.Movement;
import module.Skeleton;

public class StopMovement extends Movement{

	public StopMovement(SuperTank superTank) {
		super(superTank);
		// TODO Auto-generated constructor stub
	}
	
	public void move(){
		bot.setAhead(0.0001);
	}
	
}
