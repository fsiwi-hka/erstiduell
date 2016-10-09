/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.hska.erstiduell.buzzer;

import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;

/**
 * Singleton Event Queue
 *
 * @author Moritz Grimm
 */
public final class BuzzerEventQueue extends EventQueue {

    private static BuzzerEventQueue instance;

    public static void setKeys(Key[] hotkeys, BuzzerHandler bh) {
        instance = new BuzzerEventQueue(hotkeys, bh);
    }

    public static synchronized BuzzerEventQueue getInstance() {
        return instance;
    }
    private final Key[] hotkeys;
    private boolean enabled;
    private BuzzerHandler bh;
    private int player;

    // INSTANCE
    public BuzzerEventQueue(Key[] hotkeys, BuzzerHandler bh) {
        this.hotkeys = hotkeys;
        this.enabled = true;
        this.bh = bh;
    }

    public void setController(BuzzerHandler bh) {
        this.bh = bh;
    }

    @Override
    protected void dispatchEvent(AWTEvent e) {

        if (enabled && e instanceof KeyEvent) {

            KeyEvent evt = (KeyEvent) e;

            for (int i = 0; i < hotkeys.length; i++) {
                if (hotkeys[i].equals(new Key(evt))) {

                    player = i;

                    bh.setSuccessfulBuzz(player);

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

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
