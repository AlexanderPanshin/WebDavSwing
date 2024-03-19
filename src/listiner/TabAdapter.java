package listiner;


import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TabAdapter extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_TAB)
        {
            e.consume();
            KeyboardFocusManager.
                    getCurrentKeyboardFocusManager().focusNextComponent();
        }

        if (e.getKeyCode() == KeyEvent.VK_TAB
                &&  e.isShiftDown())
        {
            e.consume();
            KeyboardFocusManager.
                    getCurrentKeyboardFocusManager().focusPreviousComponent();
        }
    }
}
