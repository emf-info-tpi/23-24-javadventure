/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.emf.javadventure.ctrl;

import ch.emf.javadventure.models.Door;
import ch.emf.javadventure.models.Enemy;
import ch.emf.javadventure.models.Item;
import ch.emf.javadventure.models.Player;
import ch.emf.javadventure.models.Room;
import ch.emf.javadventure.models.RoomElement;
import ch.emf.javadventure.views.IGameView;
import java.util.List;
import services.JsonLoader;

/**
 *
 * @author schwandern
 */
public class GameCtrl implements IGameCtrl {

    //currentRoomNumber = new int[]{0, 1, 0}; // start on ground floor, center room.
    private Player player;
    private Room currentRoom;
    private IGameView gameView;
    private JsonLoader jsonLoader;
    private int[] currentRoomNumber = new int[]{0, 0, 0};

    public GameCtrl() {
        jsonLoader = new JsonLoader();

    }

    public RoomElement[][] move(char key) {
        int[] pos = currentRoom.getPositionOfRoomElement(player);
        int[] nextPos = new int[]{pos[0], pos[1]};
        switch (key) {
            case 'w' ->
                nextPos[0]--;
            case 'a' ->
                nextPos[1]--;
            case 's' ->
                nextPos[0]++;
            case 'd' ->
                nextPos[1]++;
        }

        boolean validPos = currentRoom.checkBoundary(nextPos);
        System.out.println("Valid: " + validPos + " next:" + nextPos[0] + ", " + nextPos[1] + " pos:" + pos[0] + ", " + pos[1]);
        RoomElement[][] moveRoomEntity = this.currentRoom.moveRoomEntity(this.player, (validPos ? nextPos[0] : pos[0]), (validPos ? nextPos[1] : pos[1]));
        colisionDetection();
        return moveRoomEntity;

    }

    private void colisionDetection() {
        List<RoomElement> elements = currentRoom.getAllNonWallElements();
        for (RoomElement element : elements) {
            if (currentRoom.areColiding(player, element)) {
                element.collide(this);
            }
        }
    }

    public void navigateRooms() {

        Room r = new Room();
        r = jsonLoader.loadJsonDataRoom(new int[]{0, 1, 0});
        if (r != null) {
            currentRoom = r;
            currentRoom.placeRoomEntity(player, 6, 6);
            updateRoom();
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
        currentRoom.placeRoomEntity(player, 3, 3);
    }

    public ch.emf.javadventure.models.Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(ch.emf.javadventure.models.Room currentRoom) {
        this.currentRoom = currentRoom;

    }

    public void setGameView(IGameView gameView) {
        this.gameView = gameView;
    }

    public void updateRoom() {
        gameView.updateRoom(currentRoom.getContent());
    }

    public void loadJsonData(IGameView view, IGameCtrl gameCtrl) {

        JsonLoader.loadJsonData(view, gameCtrl, currentRoomNumber);

    }

}
