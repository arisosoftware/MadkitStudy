package rmbplayer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.Action;

import madkit.bees.AbstractBee;

import madkit.kernel.Watcher;
import madkit.simulation.probe.PropertyProbe;
import madkit.simulation.probe.SingleAgentProbe;
import madkit.simulation.viewer.SwingViewer;

/**
 * This class will be used to display the simulation. We could have extended the
 * {@link Watcher} class, but there are a lot of things already defined in
 * {@link SwingViewer}. So why not use it.
 */
public class Viewer extends SwingViewer {

	/**
	 * environment's size, probed using a {@link SingleAgentProbe}.
	 */
	private Dimension envSize = new Dimension(600,400);

	
	
	 
	@Override
	protected void activate() {
		// 1 : request my role so that the scheduler can take me into account
		requestRole(GameRoom.COMMUNITY,GameRoom.SIMU_GROUP, GameRoom.VIEWER_ROLE);
 
		//  setup the frame for the display according to the environment's
		// properties
		getDisplayPane().setPreferredSize(envSize);
		getFrame().pack();

		// 4 (optional) set the synchronous painting mode: The display will be
		// updated
		// for each step of the simulation.
		// Here it is useful because the simulation goes so fast that the agents
		// are almost invisible
		setSynchronousPainting(true);
	}

	/**
	 * render is the method where the custom painting has to be done. Here, we
	 * just draw red points at the agents' location
	 */
	@Override
	protected void render(Graphics g) {
		g.setColor(Color.RED);
//		g.drawString("You are watching " + beeProbe.size() + " MaDKit agents",
//				10, 10);
//		Color lastColor = null;
//		final boolean trailMode = (Boolean) trailModeAction
//				.getValue(Action.SELECTED_KEY);
//		for (final AbstractBee arg0 : beeProbe.getCurrentAgentsList()) {
//			final BeeInformation b = beeProbe.getPropertyValue(arg0);
//			final Color c = b.getBeeColor();
//			if (c != lastColor) {
//				lastColor = c;
//				g.setColor(lastColor);
//			}
//			final Point p = b.getCurrentPosition();
//			if (trailMode) {
//				final Point p1 = b.getPreviousPosition();
//				g.drawLine(p1.x, p1.y, p.x, p.y);
//			} else {
//				g.drawLine(p.x, p.y, p.x, p.y);
//			}
//		}
	}

}
