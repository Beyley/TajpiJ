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

    public static void main(String[] args) {
        // Creating the Frame
        JFrame mainFrame = new JFrame("TajpiJ");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(400, 550);

        JPanel topPanel = new JPanel();
        JLabel testInputHereLabel = new JLabel("Test input here!");
        topPanel.add(testInputHereLabel);

        // Creating the panel at bottom and adding components
        JPanel southPanel = new JPanel();
        enabledCheckBox = new JCheckBox("Enabled", true);
        enabledCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                isEnabled = e.getStateChange() == 1;
            }
        });
        southPanel.add(enabledCheckBox);

        // Text Area at the Center
        JTextArea textArea = new JTextArea();

        // Adding Components to the frame.
        mainFrame.getContentPane().add(BorderLayout.NORTH, topPanel);
        mainFrame.getContentPane().add(BorderLayout.CENTER, textArea);
        mainFrame.getContentPane().add(BorderLayout.SOUTH, southPanel);
        mainFrame.setVisible(true);
        mainFrame.setResizable(false);

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

        GlobalScreen.addNativeKeyListener(new InputHandler());
    }
}
