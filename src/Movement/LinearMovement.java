package Movement;

import sample.SuperTank;
import module.Movement;
import module.Skeleton;

public class LinearMovement extends Movement{

	public LinearMovement(SuperTank superTank) {
		super(superTank);
		// TODO Auto-generated constructor stub
	}
	
	public void move(){
		bot.ahead(200);
		bot.back(200);
	}

}
