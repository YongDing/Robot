package PSO;

import robocode.control.events.BattleAdaptor;
import robocode.control.events.BattleCompletedEvent;
import robocode.control.events.BattleErrorEvent;
import robocode.control.events.BattleMessageEvent;

public class PSOObserver extends BattleAdaptor {
	 BattleCompletedEvent result;
	 
    public BattleCompletedEvent getResult() {
		return result;
	}

	public void setResult(BattleCompletedEvent result) {
		this.result = result;
	}

	// Called when the battle is completed successfully with battle results
    public void onBattleCompleted(BattleCompletedEvent e) {
   	 result=e;
    }

    // Called when the game sends out an information message during the battle
    public void onBattleMessage(BattleMessageEvent e) {
//        System.out.println("Msg> " + e.getMessage());
    }

    // Called when the game sends out an error message during the battle
    public void onBattleError(BattleErrorEvent e) {
        System.out.println("Err> " + e.getError());
    }
}