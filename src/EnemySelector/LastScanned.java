package EnemySelector;

import java.util.Iterator;

import module.Component;
import module.Enemy;
import module.EnemySelector;
import module.Skeleton;

public class LastScanned extends EnemySelector {

	public LastScanned(Skeleton bot) {
		super(bot);
		// TODO Auto-generated constructor stub
	}

	public void select() {
		Iterator<Enemy> iterator= bot.enemies.values().iterator();
		int maxTime= Integer.MIN_VALUE;
		Enemy selected=null;
		while (iterator.hasNext()){
			Enemy e= iterator.next();
			if (maxTime<e.timeScanned){
				selected=e;
				maxTime=e.timeScanned;
			}				
		}

		bot.enemy=selected;
	}
}
