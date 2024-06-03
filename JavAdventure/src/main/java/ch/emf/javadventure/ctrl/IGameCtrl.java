/**
 * Project: Javadventure
 * File: IGameCtrl.java
 *
 * Description: This is the Game Controller interface.
 *
 * Author: Nicolas Schwander
 *
 * Created: 21.05.2024
 *
 * License: GPL License
 *
 */
package ch.emf.javadventure.ctrl;

import ch.emf.javadventure.models.Player;
import ch.emf.javadventure.models.Room;
import ch.emf.javadventure.models.RoomElement;
import ch.emf.javadventure.views.IGameView;

/**
 *
 * @author schwandern
 */
public interface IGameCtrl {

    public void setGameView(IGameView gameView);

    public RoomElement[][] move(int key);

    public void initializeGame();
    
    public boolean executeCommand(String command);

}
