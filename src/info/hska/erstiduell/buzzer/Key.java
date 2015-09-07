package info.hska.erstiduell.buzzer;

import java.awt.event.KeyEvent;

/**
 *
 * @author timroes
 */
public class Key {

    public final int keyCode;
    public final int keyLocation;
    private final String name;

    public Key(KeyEvent evt) {
        keyCode = evt.getKeyCode();
        keyLocation = evt.getKeyLocation();
        name = KeyEvent.getKeyText(evt.getKeyCode());
    }

    public Key(int keyCode, int keyLocation, String name) {
        this.keyCode = keyCode;
        this.keyLocation = keyLocation;
        this.name = name;
    }

    @Override
    public String toString() {
        return name + " (" + keyCode + "," + keyLocation + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass() != this.getClass()) {
            return false;
        }

        return equals((Key) o);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + this.keyCode;
        hash = 11 * hash + this.keyLocation;
        return hash;
    }

    public boolean equals(Key o) {
        return o.keyCode == keyCode && o.keyLocation == keyLocation;
    }

}
