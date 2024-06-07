/**
 * Project: Javadventure
 * File: GameCtrl.java
 *
 * Description: This is the main controller of the javAdventure game.
 *
 * Author: Nicolas Schwander
 *
 * Created: 21.05.2024
 *
 * License: GPL License
 *
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

    /**
     * Constructs a new GameCtrl instance and initializes the game components.
     * Initializes the JsonLoader, sets the initial room number, and prepares
     * the rooms map.
     */
    public GameCtrl() {

        jsonLoader = new JsonLoader();
        currentRoomNumber = new int[]{0, 0, 0};
        rooms = new HashMap<>();
    }

    /**
     * Initializes the game by loading the initial room data, creating a new
     * player, setting the player in the room, adding the room to the rooms map,
     * and updating the room view.
     */
    @Override
    public void initializeGame() {

        currentRoom = jsonLoader.loadJsonData(currentRoomNumber);
        player = new Player();
        setPlayer(player, 3, 12);
        AddRoom(currentRoom);
        updateRoom();
    }

    /**
     * Moves the player based on the given key input. Supports movement with
     * arrow keys (38, 37, 40, 39) and WASD keys (87, 65, 83, 68). Checks
     * boundaries and updates the room elements accordingly.
     *
     * @param key the key code representing the direction of movement
     * @return a 2D array of RoomElement representing the new state of the room
     * after movement
     */
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
        this.currentRoom.moveRoomEntity(this.player, (validPos ? nextPos[0] : pos[0]), (validPos ? nextPos[1] : pos[1]));
        colisionDetection();
        return currentRoom.getContent();

    }

    /**
     * Detects collisions between the player and other non-wall elements in the
     * current room. If a collision is detected, it handles the collision by
     * invoking the appropriate method on the colliding element. If no collision
     * is detected, it clears the output text.
     */
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
            gameView.setOutputText("");
        }
    }

    /**
     * Runs a combat scenario at a given difficulty level room element. Executes
     * a test using the TestRunner and updates the game view based on the test
     * result.
     *
     * @param dificulte the difficulty level of the combat
     * @param elem the room element representing the enemy
     */
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

    /**
     * Displays the description of an item in the game view.
     *
     * @param desc the description of the item to be displayed
     */
    public void showItemDesc(String desc) {
        gameView.setOutputText(desc);
    }

    /**
     * Navigates to a different room based on the given direction. Loads the
     * next room if it has not been visited before and updates the player's
     * position near the door.
     *
     * @param e the direction to navigate (TOP, BOTTOM, LEFT, RIGHT)
     */
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

            gameView.setRoomDescription(currentRoom.getRoomDescription());
        }
    }

    /**
     * Places the player near the door in the new room based on the direction of
     * entry.
     *
     * @param direction the direction from which the player is entering the room
     */
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

    /**
     * Calculates the next room number based on the current room number and the
     * given direction.
     *
     * @param currentRoomNumber the current room number as an array [floor, x,
     * y]
     * @param direction the direction to move to
     * @return an array representing the next room number
     */
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

    /**
     * returns the string key for a room based on its number.
     *
     * @param roomNumber the room number as an array
     * @return a string representing the unique key for the room
     */
    private String getRoomKey(int[] roomNumber) {
        return roomNumber[0] + "_" + roomNumber[1] + "_" + roomNumber[2];
    }

    public Player getPlayer() {
        return player;
    }

    /**
     * Sets the player instance and places the player at the specified position
     * in the current room.
     *
     * @param player the player instance to be set
     * @param row the row position to place the player
     * @param col the column position to place the player
     */
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

    /**
     * Sets the game view instance.
     *
     * @param gameView the game view instance to be set
     */
    public void setGameView(IGameView gameView) {
        this.gameView = gameView;
    }

    /**
     * Updates the room view by refreshing its content and setting the room
     * description in the game view.
     */
    public void updateRoom() {
        gameView.updateRoom(currentRoom.getContent());
        gameView.setRoomDescription(currentRoom.getRoomDescription());

    }

    /**
     * Adds a room to the rooms map using the current room number as the key.
     *
     * @param room the room instance to be added
     */
    public void AddRoom(Room room) {
        String roomKey = getRoomKey(currentRoomNumber);
        rooms.put(roomKey, room);
    }

    /**
     * Executes a game command input by the player. Supports commands such as
     * "regarder murs", "regarder inventaire", "prendre item", and "poser item".
     *
     * @param command the command string input by the player
     * @return true if the command was executed successfully, false otherwise
     */
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
