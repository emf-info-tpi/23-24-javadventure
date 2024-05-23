/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
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

    public void setPlayer(Player player);

    public RoomElement[][] move(char key);

    public void setCurrentRoom(Room currentRoom);

    public void updateRoom();

    public void loadJsonData(IGameView view, IGameCtrl gameCtrl);
    
    public void AddRoom(Room room);
    
    public void executeCommand(String command);

}
