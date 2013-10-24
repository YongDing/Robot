package module;

import EnemySelector.LastScanned;
import Gun.CeaseFire;
import Gun.DistanceBase;
import Gun.Light;
import Gun.Medium;
import Gun.Powerful;
import Movement.CircularMovement;
import Movement.DodgeBulletMovement;
import Movement.LinearMovement;
import Movement.RandomMovement;
import Movement.SquareOffMovement;
import Movement.StopMovement;
import Movement.WallsMovement;
import Radar.*;
import Targeting.CircularTargeting;
import Targeting.HeadOnTargeting;
import Targeting.LinearTargeting;
import Targeting.QuietGun;

public class SuperTank extends Skeleton{
	
	public void initialize() {
		EnemySelector lastScanned = new LastScanned(this);
		
		Radar spinningRadar = new SpinnerRadar(this);
		Radar targetScopeFocusRadar = new TargetScopeFocusRadar(this);
		Radar targetFocusRadar = new TargetFocusRadar(this);
		Radar oscillatingRadar = new OscillatingRadar(this);
		
		Gun ceaseFire = new CeaseFire(this);
		Gun distanceBaseGun = new DistanceBase(this);
		Gun lightGun = new Light(this);
		Gun mediumGun = new Medium(this);
		Gun powerfulGun = new Powerful(this);
		
		Targeting quietGun = new QuietGun(this);
		Targeting headOnTargeting = new HeadOnTargeting(this);
		Targeting linearTargeting = new LinearTargeting(this);
		Targeting circularTargeting = new CircularTargeting(this);
		
		
		Movement circularMovement = new CircularMovement(this);
		Movement dodgeBulleMovement = new DodgeBulletMovement(this);
		Movement linearMovement = new LinearMovement(this);
		Movement randomMovement = new RandomMovement(this);
		Movement squareOffMovement = new SquareOffMovement(this);
		Movement wallsMovement = new WallsMovement(this);
		Movement stopMovement = new StopMovement(this);
	}
	
	public void selectBehavior() {
		//GA will go here to select best strategy
	}

}
