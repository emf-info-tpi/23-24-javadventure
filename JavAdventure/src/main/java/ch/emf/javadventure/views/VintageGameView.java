/**
 * Project: Javadventure
 * File: VintageGameView.java
 *
 * Description: This is the View for the javAdventure Game.
 *
 * Author: Nicolas Schwander
 *
 * Created: 21.05.2024
 *
 * License: GPL License
 *
 */
package ch.emf.javadventure.views;

import ch.emf.javadventure.ctrl.IGameCtrl;
import ch.emf.javadventure.models.RoomElement;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.border.LineBorder;

/**
 * The VintageGameView class represents the main window of the JavAdventure
 * game. It displays the map, room description, map legend, output text, and
 * user input areas.
 *
 * @author <a href="mailto:fanny.riedo@edufr.ch">Fanny Riedo</a>
 * @since 18.05.2024
 */
public class VintageGameView extends JFrame implements IGameView {

    private JTextArea map;
    private JTextArea roomDescription;
    private JTextArea outputText;
    private JTextField userInput;
    private IGameCtrl gamectrl;

    /**
     * Constructs a new VintageGameView window and initializes its components.
     */
    public VintageGameView() {
        // Setup base window
        setTitle("JavAdventure");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Set the background color of the content pane to black
        getContentPane().setBackground(Color.BLACK);

        // Setup font
        Font font;
        try {
            InputStream is = VintageGameView.class.getResourceAsStream("/fonts/Px437_IBM_VGA_9x16.ttf");
            font = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(16f);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            font = new Font("Monospaced", Font.PLAIN, 20);
        }

        // Room description panel (top left)
        roomDescription = createTextArea(3, 20, font);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(roomDescription, gbc);

        // Output panel (top right)
        outputText = createTextArea(3, 20, font);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(outputText, gbc);

        // Room map (middle, stretched under both room description and output)
        map = createTextArea(20, 50, font);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;  // Span 2 columns
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        //map.setBorder(new LineBorder(Color.WHITE));

        gbc.insets = new Insets(10, 10, 10, 10);
        add(map, gbc);

        map.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (gamectrl != null) {
                    updateRoom(gamectrl.move(e.getKeyCode()));
                }
            }
        });

        // User input panel (bottom, stretched under the map)
        userInput = new JTextField(20);
        userInput.setBackground(Color.BLACK);
        userInput.setForeground(Color.WHITE);
        userInput.setFont(font);
        userInput.setBorder(new LineBorder(Color.WHITE));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;  // Span 2 columns
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(userInput, gbc);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                userInput.requestFocusInWindow();
            }
        });

        userInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    // Process command entered in the text field
                    boolean passed = gamectrl.executeCommand(userInput.getText().trim());
                    if (passed) {
                        userInput.setText("");
                    } else {
                        outputText.setText("la commande n'existe pas");
                    }
                }
            }
        });

        setVisible(true);
    }

    /**
     * Updates the current room display with the provided 2D array of
     * RoomElement objects.
     *
     * This method takes a 2D array of RoomElement objects representing the
     * current room layout and constructs a visual representation of the room.
     * Each RoomElement is converted to its string representation and appended
     * to a map string. If a position in the array is null, it is represented by
     * a space in the map. The resulting map string is then set as the text of
     * the `map` component.
     *
     * @param r a 2D array of RoomElement objects representing the current room
     * layout.
     */
    public void updateRoom(RoomElement[][] r) {
        String map = "";
        for (int i = 0; i < r.length; i++) {
            map += "            ";
            for (int j = 0; j < r[0].length; j++) {
                map += r[i][j] != null ? r[i][j] : " ";
            }
            map += "\n";
        }
        this.map.setText(map);

    }

    @Override
    public void setRoomDescription(String description) {
        roomDescription.setText(description);
    }

    @Override
    public void setOutputText(String text) {
        outputText.setText(text);
    }

    @Override
    public String getUserInput() {
        return userInput.getText();
    }

    @Override
    public void clearUserInput() {
        userInput.setText("");
    }

    /**
     * Creates and returns a configured JTextArea with specified rows, columns,
     * and font.
     *
     * @param rows the number of rows in the text area
     * @param cols the number of columns in the text area
     * @param font the font to be used in the text area
     * @return the configured JTextArea
     */
    private JTextArea createTextArea(int rows, int cols, Font font) {
        JTextArea textArea = new JTextArea(rows, cols);
        textArea.setFont(font);
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.WHITE);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        return textArea;
    }

    public void setGamectrl(IGameCtrl gamectrl) {
        this.gamectrl = gamectrl;
    }

    @Override
    public void setMapLegend(String legend) {

    }
}
