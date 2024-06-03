/**
 * Project: Javadventure
 * File: IGameView.java
 *
 * Description: This is the IGameView interface.
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

/**
 * The IGameView interface defines the methods required for the game view in the
 * JavAdventure application. It includes methods to draw the room map, set room
 * descriptions, map legends, output text, and manage user input.
 *
 * @author <a href="mailto:fanny.riedo@edufr.ch">Fanny Riedo</a>
 * @since 18.05.2024
 */
public interface IGameView {

    public void setGamectrl(IGameCtrl gamectrl);

    public void updateRoom(RoomElement[][] r);

    /**
     * Sets the description of the current room in the game view.
     *
     * @param description the description of the room
     */
    public void setRoomDescription(String description);

    /**
     * Sets the legend of the map in the game view.
     *
     * @param legend the legend of the map
     */
    public void setMapLegend(String legend);

    /**
     * Sets the additional output text in the game view.
     *
     * @param text the additional output text
     */
    public void setOutputText(String text);

    /**
     * Clears the user input in the game view.
     */
    public void clearUserInput();

    /**
     * Gets the user input from the game view.
     *
     * @return the user input as a string
     */
    public String getUserInput();

    // methodes presentes dans le squelette initialles mais pas utilisées
    /**
     * Draws the room map in the game view.
     *
     * @param roomMap a string representation of the room map
     */
    //public void drawRoomMap(String roomMap);
    /**
     * Sets a single character in the room map at the specified row and column.
     *
     * @param character the character to set
     * @param row the row position
     * @param col the column position
     */
    //public void setMapCharacter(char character, int row, int col) ;
}
