package info.hska.erstiduell;

import info.hska.erstiduell.view.ControllerWindow;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Implements MouseListener 
 * @author Moritz Grimm
 */
public class Mouse implements MouseListener {
    ControllerWindow cw;
    public Mouse(ControllerWindow cw) {
        this.cw = cw;
    }

    public void mouseClicked(MouseEvent e) {
        cw.doChangeName(e);
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
    
}
