/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.emf.javadventure.views;

import ch.emf.javadventure.ctrl.GameCtrl;
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
 * The VintageGameView class represents the main window of the JavAdventure game.
 * It displays the map, room description, map legend, output text, and user input areas.
 * 
 * @author <a href="mailto:fanny.riedo@edufr.ch">Fanny Riedo</a>
 * @since 18.05.2024
 */
public class VintageGameView extends JFrame implements IGameView {

    private JTextArea map;
    private JTextArea roomDescription;
    private JTextArea mapLegend;
    private JTextArea outputText;
    private JTextField userInput;
    private IGameCtrl gamectrl;

   

    /**
     * Constructs a new VintageGameView window and initializes its components.
     */
    public VintageGameView() {
        // Setup base window
        setTitle("JavAdventure");
        setSize(800, 800);
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

        // Room map (top left)
        map = createTextArea(30, 30, font);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;  // Span 2 rows
        add(map, gbc);
        
         map.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (gamectrl != null) {
                    updateRoom(gamectrl.move(e.getKeyChar()));
                }
            }
        });

        // Room description panel (bottom-left)
        roomDescription = createTextArea(6, 25, font);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        add(roomDescription, gbc);

        // Map legend panel (top-right)
        mapLegend = createTextArea(6, 20, font);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(mapLegend, gbc);

        // Output panel (middle-right)
        outputText = createTextArea(12, 20, font); 
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(outputText, gbc);

        // User input panel (bottom)
        userInput = new JTextField(20);  // 16 columns
        userInput.setBackground(Color.BLACK);
        userInput.setForeground(Color.WHITE);
        userInput.setFont(font);
        userInput.setBorder(new LineBorder(Color.BLACK)); // Remove the white border
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(userInput, gbc);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                userInput.requestFocusInWindow();
            }
        });

        setVisible(true);
    }

    @Override
    public void drawRoomMap(String roomMap) {
        map.setText(roomMap);
    }
    
    @Override
    public void setMapCharacter(char character, int row, int col) {
        try {
            //split the map into lines
            String[] lines = map.getText().split("\n");
            //change the character at the correct coordinates
            if (row < lines.length && col < lines[row].length()) {
                StringBuilder line = new StringBuilder(lines[row]);
                line.setCharAt(col, character);
                lines[row] = line.toString();
                map.setText(String.join("\n", lines));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void updateRoom(RoomElement[][] r) {
        
        
        String map = "";
        for (int i = 0; i < r.length; i++) {
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
    public void setMapLegend(String legend) {
        mapLegend.setText(legend);
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
     * Creates and returns a configured JTextArea with specified rows, columns, and font.
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
}