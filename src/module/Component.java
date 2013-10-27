package module;

import robocode.Event;
import java.awt.Graphics2D;
import java.awt.event.InputEvent;

public class Component {
	public void listen(Event e){};
	public void listenInput(InputEvent e){};
	public void onPaint(Graphics2D g){};
}
