package poltixe.github.tajpij;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Clipboard;
import java.awt.Toolkit;

public class InputHandler implements NativeKeyListener {
    char keyToLookFor = 'x';
    char lastKey = ' ';

    Robot robot;

    public InputHandler() {
        try {
            this.robot = new Robot();
        } catch (AWTException e) {

        }
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
        if (App.config.getEnabledOnStartup() == 0)
            return;

        char thisKey = e.getKeyChar();

        try {
            if (thisKey == this.keyToLookFor) {
                switch (this.lastKey) {
                case 's':
                    pressBackspace(2);
                    typeLetter("ŝ");
                    break;
                case 'c':
                    pressBackspace(2);
                    typeLetter("ĉ");
                    break;
                case 'g':
                    pressBackspace(2);
                    typeLetter("ĝ");
                    break;
                case 'h':
                    pressBackspace(2);
                    typeLetter("ĥ");
                    break;
                case 'j':
                    pressBackspace(2);
                    typeLetter("ĵ");
                    break;
                case 'u':
                    pressBackspace(2);
                    typeLetter("ŭ");
                    break;
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
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
    }
}
