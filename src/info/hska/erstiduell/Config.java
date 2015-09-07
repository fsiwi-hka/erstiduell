package info.hska.erstiduell;

import info.hska.erstiduell.buzzer.Key;
import java.awt.Color;
import java.awt.GraphicsDevice;

/**
 *
 * @author Tim Roes
 */
public class Config {

    public int players;
    public GraphicsDevice display;
    public Color foreground;
    public Color background;
    public Key[] hotkeys;

    public Config(int players, GraphicsDevice display,
            Color foreground, Color background, Key[] hotkeys) {
        this.players = players;
        this.display = display;
        this.foreground = foreground;
        this.background = background;
        this.hotkeys = hotkeys;
    }

}
