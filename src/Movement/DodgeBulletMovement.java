package Movement;

import sample.SuperTank;
import module.Movement;
import module.Skeleton;

public class DodgeBulletMovement extends Movement{

	public DodgeBulletMovement(SuperTank superTank) {
		super(superTank);
		// TODO Auto-generated constructor stub
	}
	
	public void move(){
		bot.setTurnRight(bot.enemy.bearing+90-
		         30*bot.moveDirection);
		         
		     // If the bot has small energy drop,
		    // assume it fired
		    double changeInEnergy =
		      bot.enemy.previousEnergy-bot.enemy.energy;
		    if (changeInEnergy>0 &&
		        changeInEnergy<=3) {
		         // Dodge!
		         bot.moveDirection =
		          -bot.moveDirection;
		         bot.setAhead((bot.enemy.distance/4+25)*bot.moveDirection);
		     }
	}

}
