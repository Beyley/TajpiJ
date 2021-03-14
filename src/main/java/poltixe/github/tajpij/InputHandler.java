package poltixe.github.tajpij;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Clipboard;
import java.awt.Toolkit;

public class InputHandler implements NativeKeyListener {
    char keyToLookFor = 'x';
    char lastKey = ' ';

    Robot robot;

    boolean shiftHeld = false;

    final static char[][] convertTable = { { 's', 'ŝ' }, { 'c', 'ĉ' }, { 'g', 'ĝ' }, { 'h', 'ĥ' }, { 'j', 'ĵ' },
            { 'u', 'ŭ' }, };

    public InputHandler() {
        try {
            this.robot = new Robot();
        } catch (AWTException e) {

        }
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        if (NativeKeyEvent.getKeyText(e.getKeyCode()) == "Shift")
            shiftHeld = true;
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        if (NativeKeyEvent.getKeyText(e.getKeyCode()) == "Shift")
            shiftHeld = false;
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
        if (App.config.getEnabledOnStartup() == 0)
            return;

        char thisKey = e.getKeyChar();
        System.out.println(lastKey + "" + thisKey);

        try {
            if (thisKey == this.keyToLookFor || thisKey == Character.toUpperCase(this.keyToLookFor)) {
                for (char[] charToCheck : convertTable) {
                    if (charToCheck[0] == Character.toLowerCase(lastKey)) {
                        // pressBackspace(2);
                        typeLetter(Character.isUpperCase(lastKey) ? String.valueOf(charToCheck[1]).toUpperCase()
                                : String.valueOf(charToCheck[1]));
                    }
                }
            }
        } catch (Exception ex) {

        }
        this.lastKey = thisKey;
    }

    public void pressBackspace(int length) {
        for (int i = 0; i < length; i++) {
            robot.keyPress(KeyEvent.VK_BACK_SPACE);
            robot.keyRelease(KeyEvent.VK_BACK_SPACE);
        }
    }

    public void typeLetter(String c) {
        StringSelection selection = new StringSelection(c);
        // Copy it to clipboard
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, null);

        // Paste it
        robot.keyPress(KeyEvent.VK_CONTROL);

        if (shiftHeld)
            robot.keyRelease(KeyEvent.VK_SHIFT);

        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);

        if (shiftHeld)
            robot.keyPress(KeyEvent.VK_SHIFT);

        robot.keyRelease(KeyEvent.VK_CONTROL);
    }
}
