/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
public class GameCtrl implements IGameCtrl {

    private Player player;
    private Room currentRoom;
    private IGameView gameView;

    public RoomElement[][] move(char key) {
        int[] pos = currentRoom.getPositionOfRoomElement(player);
        int[] nextPos = new int[]{pos[0], pos[1]};
        switch (key) {
            case 'w' ->
                nextPos[1]--;
            case 'a' ->
                nextPos[0]--;
            case 's' ->
                nextPos[1]++;
            case 'd' ->
                nextPos[0]++;
        }

        boolean validPos = currentRoom.checkBoundary(nextPos);
        System.out.println("Valid: " + validPos + " next:" + nextPos[0] + ", " + nextPos[1] + " pos:" + pos[0] + ", " + pos[1]);
        return this.currentRoom.moveRoomEntity(this.player, (validPos ? nextPos[0] : pos[0]), (validPos ? nextPos[1] : pos[1]));
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
        currentRoom.placeRoomEntity(player, 3, 3);
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;

    }

    public void setGameView(IGameView gameView) {
        this.gameView = gameView;
    }

    public void updateRoom() {
        gameView.updateRoom(currentRoom.getContent());
    }

}
