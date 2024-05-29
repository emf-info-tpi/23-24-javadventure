/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.emf.javadventure.ctrl;

import ch.emf.javadventure.models.Door.Direction;
import ch.emf.javadventure.models.Item;
import ch.emf.javadventure.models.Player;
import ch.emf.javadventure.models.Room;
import ch.emf.javadventure.models.RoomElement;
import ch.emf.javadventure.views.IGameView;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ch.emf.javadventure.services.JsonLoader;
import ch.emf.javadventure.services.TestRunner;
import java.util.HashSet;

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
    private int[] currentRoomNumber;
    private Map<String, Room> rooms;

    public GameCtrl() {

        jsonLoader = new JsonLoader();
        currentRoomNumber = new int[]{0, 0, 0};
        rooms = new HashMap<>();
    }

    @Override
    public void initializeGame() {

        currentRoom = jsonLoader.loadJsonData(currentRoomNumber);
        player = new Player();
        setPlayer(player, 3, 12);
        AddRoom(currentRoom);
        updateRoom();
    }

    @Override
    public RoomElement[][] move(int key) {
        int[] pos = currentRoom.getPositionOfRoomElement(player);
        int[] nextPos = new int[]{pos[0], pos[1]};
        switch (key) {
            //mouvement avec les fleches
            case 38 ->
                nextPos[0]--;
            case 37 ->
                nextPos[1]--;
            case 40 ->
                nextPos[0]++;
            case 39 ->
                nextPos[1]++;
            //mouvement avec les touches W,A,S,D    
            case 87 ->
                nextPos[0]--;
            case 65 ->
                nextPos[1]--;
            case 83 ->
                nextPos[0]++;
            case 68 ->
                nextPos[1]++;
        }

        boolean validPos = currentRoom.checkBoundary(nextPos);
        RoomElement[][] moveRoomEntity = this.currentRoom.moveRoomEntity(this.player, (validPos ? nextPos[0] : pos[0]), (validPos ? nextPos[1] : pos[1]));
        colisionDetection();
        return moveRoomEntity;

    }

    private void colisionDetection() {
        boolean detecte = false;
        List<RoomElement> elements = currentRoom.getAllNonWallElements();
        for (RoomElement element : elements) {
            if (currentRoom.areColiding(player, element)) {
                element.collide(this);
                detecte = true;
            }
        }
        if (!detecte) {
            showItemDesc("");
        }
    }

    public void runCombat(int dificulte, RoomElement elem) {
        TestRunner j = new TestRunner();
        boolean passed;
        passed = j.runCombat(dificulte);

        if (passed) {
            currentRoom.removeRoomElement(elem);
            gameView.setOutputText("ENEMI VAINCU !");
        } else {
            gameView.setOutputText("Le monstre est trop fort, il ne daigne même pas te tuer");
        }

    }

    public void showItemDesc(String desc) {
        gameView.setOutputText(desc);
    }

    public void navigateRooms(Direction e) {
        int[] nextRoomNumber = getNextRoomNumber(currentRoomNumber, e);

        String roomKey = getRoomKey(nextRoomNumber);
        Room nextRoom;

        if (rooms.containsKey(roomKey)) {
            nextRoom = rooms.get(roomKey);
        } else {
            nextRoom = jsonLoader.loadJsonData(nextRoomNumber);
            rooms.put(roomKey, nextRoom);
        }

        if (nextRoom != null) {
            // Remove player from current room
            currentRoom.removeRoomElement(player);

            // Update current room and player position
            currentRoom = nextRoom;
            currentRoomNumber = nextRoomNumber;
            placePlayerNearDoor(e); // Place the player near the door

            updateRoom();
            gameView.setRoomDescription(currentRoom.getRoomDescription());
        }
    }

    private void placePlayerNearDoor(Direction direction) {
        int playerX = 6; // Default positions, these might need to be adjusted based on your room size
        int playerY = 6;
        int[] roomSize = currentRoom.getSize();

        switch (direction) {
            case TOP:
                playerX = roomSize[0] - 2; // Place the player near the bottom of the room
                playerY = roomSize[1] / 2; // Center horizontally
                break;
            case BOTTOM:
                playerX = 1; // Place the player near the top of the room
                playerY = roomSize[1] / 2; // Center horizontally
                break;
            case LEFT:
                playerX = roomSize[0] / 2; // Center vertically
                playerY = roomSize[1] - 2; // Place the player near the right side of the room
                break;
            case RIGHT:
                playerX = roomSize[0] / 2; // Center vertically
                playerY = 1; // Place the player near the left side of the room
                break;
        }

        currentRoom.placeRoomEntity(player, playerX, playerY);
    }

    private int[] getNextRoomNumber(int[] currentRoomNumber, Direction direction) {
        int[] nextRoomNumber = currentRoomNumber.clone();
        switch (direction) {
            case TOP ->
                nextRoomNumber[1]--;
            case BOTTOM ->
                nextRoomNumber[1]++;
            case LEFT ->
                nextRoomNumber[2]--;
            case RIGHT ->
                nextRoomNumber[2]++;
        }
        return nextRoomNumber;
    }

    private String getRoomKey(int[] roomNumber) {
        return roomNumber[0] + "_" + roomNumber[1] + "_" + roomNumber[2];
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player, int row, int col) {
        this.player = player;
        currentRoom.placeRoomEntity(player, row, col);
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
        gameView.setRoomDescription(currentRoom.getRoomDescription());

    }

    public void AddRoom(Room room) {
        String roomKey = getRoomKey(currentRoomNumber);
        rooms.put(roomKey, room);
    }

    @Override
    public boolean executeCommand(String command) {
        boolean result = false;
        String[] split = command.split(" ");
        switch (split[0]) {
            case "regarder":
                switch (split[1]) {
                    case "murs":
                        gameView.setOutputText(currentRoom.getWallDescription());
                        result = true;
                        break;
                    case "inventaire":
                        gameView.setOutputText(player.printInventory());
                        result = true;
                        break;
                    default:
                        result = false;
                }

                break;
            case "prendre":
                HashSet<Item> test;
                test = currentRoom.getRoomItems();
                for (Item item : test) {
                    if (split[1].equals(item.getDescription())) {
                        player.addToInventory(item);
                        currentRoom.removeRoomElement(item);
                        updateRoom();
                        result = true;
                    }
                }

                break;
            case "poser":
                HashSet<Item> inventaire;
                inventaire = player.getInventory();
                for (Item item : inventaire) {
                    if (split[1].equals(item.getDescription())) {
                        player.removeFromInventory(item);
                        int[] pos = currentRoom.getPositionOfRoomElement(player);
                        currentRoom.placeRoomEntity(item, pos[0] + 1, pos[1]);
                        updateRoom();
                        result = true;
                    }
                }
        }
        return result;
    }

}
