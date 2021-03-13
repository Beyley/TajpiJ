package poltixe.github.tajpij;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.CharBuffer;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;

import org.apache.commons.io.FileUtils;

public class App {
    public static boolean isEnabled;
    public static JCheckBox enabledCheckBox;

    public static InputHandler input;

    public static Config config;

    public static void main(String[] args) throws JsonProcessingException {
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

        // #region CONFIG HANDLER
        try {
            File f = new File("tajpijconfig.json");
            if (!f.exists() && !f.isDirectory()) {
                config = new Config();
                ObjectMapper objectMapper = new ObjectMapper();

                f.createNewFile();

                FileWriter fileWriter = new FileWriter("tajpijconfig.json");
                fileWriter.write(objectMapper.writeValueAsString(config));
                fileWriter.close();
            }

            ObjectMapper mapper = new ObjectMapper();

            String json = FileUtils.readFileToString(f, "utf-8");

            config = mapper.readValue(json, Config.class);
        } catch (IOException e) {

        }
        // #endregion
    }
}
