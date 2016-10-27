package game;

import java.util.ArrayList;
import java.util.List;

import grid.Player;

public class Statistics {
	private static int CIRCLEWIN = 0;
	private static int CROSSWIN = 0;
	private static List<StatisticsListener> _listeners = new ArrayList<>();
	
	public Statistics() {}

	public void addWin(Player player) {
		if (player.equals(Player.CIRCLE)) {
			CIRCLEWIN++;
		} else {
			CROSSWIN++;
		}
		
		notifyListeners();
	}
	
	public int getCircleWin() {
		return CIRCLEWIN;
	}
	
	public int getCrossWin() {
		return CROSSWIN;
	}
	
	public void addListener(StatisticsListener listener) {
		_listeners.add(listener);
	}
	
	private void notifyListeners() {
		for (StatisticsListener listener : _listeners) {
			listener.fireUpdates();
		}
	}
}
