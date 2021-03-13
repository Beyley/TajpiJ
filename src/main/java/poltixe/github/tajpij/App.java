package poltixe.github.tajpij;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;

public class App {
    public static boolean isEnabled;
    public static JCheckBox enabledCheckBox;

    public static InputHandler input;

    public static void main(String[] args) {
        // #region CREATE FRAME
        JFrame mainFrame = new JFrame("TajpiJ");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(400, 550);
        // #endregion

        // #region TOP PANEL
        JPanel topPanel = new JPanel();
        JLabel testInputHereLabel = new JLabel("Test input here!");
        topPanel.add(testInputHereLabel);
        // #endregion

        // #region BOTTOM PANEL
        JPanel southPanel = new JPanel();
        enabledCheckBox = new JCheckBox("Enabled", true);
        enabledCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                isEnabled = e.getStateChange() == 1;
            }
        });
        southPanel.add(enabledCheckBox);
        // #endregion

        // #region MIDDLE PANEL
        JTextArea textArea = new JTextArea();
        // #endregion

        // #region ADD COMPONENTS TO FRAME
        mainFrame.getContentPane().add(BorderLayout.NORTH, topPanel);
        mainFrame.getContentPane().add(BorderLayout.CENTER, textArea);
        mainFrame.getContentPane().add(BorderLayout.SOUTH, southPanel);
        mainFrame.setVisible(true);
        mainFrame.setResizable(false);
        // #endregion

        // #region REGISTER INPUT HANDLER
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        logger.setUseParentHandlers(false);

        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            JOptionPane.showMessageDialog(mainFrame,
                    "There was a problem registering the native hook.\n" + ex.getMessage());

            System.exit(1);
        }

        input = new InputHandler();
        GlobalScreen.addNativeKeyListener(input);
        // #endregion
    }
}
