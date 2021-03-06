package poltixe.github.tajpij;

import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ItemEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.BorderLayout;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import org.apache.commons.io.FileUtils;

public class App {
    public static JCheckBox enabledCheckBox;

    public static InputHandler input;

    public static Config config;

    public static final String configFile = "tajpijconfig.json";

    public static void writeConfig() {
        try {
            File f = new File(configFile);

            if (!f.exists()) {
                config = new Config();
            } else if (config == null) {
                readConfig();
            }

            if (!f.isDirectory()) {
                ObjectMapper objectMapper = new ObjectMapper();

                f.delete();
                f.createNewFile();

                FileWriter fileWriter = new FileWriter(configFile);
                fileWriter.write(objectMapper.writeValueAsString(config));
                fileWriter.close();
            }
        } catch (IOException e) {

        }
    }

    public static void readConfig() {
        try {
            File f = new File(configFile);

            ObjectMapper mapper = new ObjectMapper();

            String json = FileUtils.readFileToString(f, "utf-8");

            config = mapper.readValue(json, Config.class);
        } catch (IOException e) {

        }
    }

    public static void main(String[] args) throws JsonProcessingException {
        // #region CONFIG HANDLER
        writeConfig();
        readConfig();
        // #endregion

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
        enabledCheckBox = new JCheckBox("Enabled", config.getEnabledOnStartup() == 1);
        enabledCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                config.setEnabledOnStartup(e.getStateChange() == 1 ? 1 : 0);
                writeConfig();
            }
        });
        southPanel.add(enabledCheckBox);
        // #endregion

        // #region MIDDLE PANEL
        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
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
