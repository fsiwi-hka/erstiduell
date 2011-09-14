package de.timroes.quizduell.buzzer;

import de.timroes.quizduell.Game;
import de.timroes.quizduell.view.ControllerWindow;
import de.timroes.quizduell.view.GameWindow;
import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;

/**
 *
 * @author timroes
 */
public class BuzzerHandler extends EventQueue {

	private static BuzzerHandler instance;

	public static void setKeys(Key[] hotkeys) {
		instance = new BuzzerHandler(hotkeys);
	}

	public static BuzzerHandler getInstance() {
		return instance;
	}

	// INSTANCE

	private Key[] hotkeys;
	private GameWindow gw;
	private ControllerWindow cw;
	private boolean blocked = true;
	private int player;
	private boolean enabled;
	private Game game;
	private long[] lastBuzz = new long[] { 0, 0, 0, 0};

	private BuzzerHandler(Key[] hotkeys) {
		this.hotkeys = hotkeys;
		this.enabled = true;
	}

	public void setGameWindow(GameWindow gw) {
		this.gw = gw;
		this.game = this.gw.getGame();
	}

	public void setControllerWindow(ControllerWindow cw) {
		this.cw = cw;
	}

	@Override
	protected void dispatchEvent(AWTEvent e) {

		if(enabled && gw != null && e instanceof KeyEvent) {
			if(this.game.getCurrentQuestion() == null) return;

			KeyEvent evt = (KeyEvent)e;
			int numAnswers = this.game.getCurrentQuestion().getAnswers().size();
			long now = System.currentTimeMillis();

			for(int i = 0; i < hotkeys.length; i++) {
				if(hotkeys[i].equals(new Key(evt))
						&& this.game.getTeams().get(i).getPenalty() < numAnswers) {
					if(blocked) {
						lastBuzz[i] = now;
						break;
					} else if (now - lastBuzz[i] < 1000) {
						break;
					}
					blocked = true;
					player = i + 1;
					gw.showBuzzer(player);
					this.game.setCurrentTeam(i);
					cw.refresh();
					break;
				}
			}

			return;
		}

		super.dispatchEvent(e);
	}

	public int getBuzzerPlayer() {
		return player;
	}

	public void release() {
		player = 0;
		blocked = false;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the blocked
	 */
	public boolean isBlocked() {
		return blocked;
	}

}