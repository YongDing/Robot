package Movement;

import module.Movement;
import module.Skeleton;

public class Stop extends Movement{

	public Stop(Skeleton bot) {
		super(bot);
		// TODO Auto-generated constructor stub
	}
	
	public void move(){
		bot.setAhead(0.0001);
	}
	
}
