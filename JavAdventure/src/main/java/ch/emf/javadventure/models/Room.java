/**
 * Project: Javadventure
 * File: Room.java
 *
 * Description: This is the Room Class.
 *
 * Author: Nicolas Schwander
 *
 * Created: 21.05.2024
 *
 * License: GPL License
 *
 */
package ch.emf.javadventure.models;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * Represents a room in the game. The room is made up of a 2D array of
 * RoomElement objects, which can be walls, doors, players, enemies, or items.
 * Provides methods to interact with and manipulate the elements within the
 * room.
 *
 * @see RoomElement
 * @see Wall
 * @see Door
 * @see Player
 * @see Enemy
 * @see Item
 */
public class Room {

    private RoomElement[][] content;
    private String roomDescription;
    private String wallDescription;

    /**
     * Constructs a Room object from a string representation of the room layout.
     *
     * @param room The string representation of the room layout.
     * @param roomDescription The description of the room.
     * @param wallDescription The description of the walls.
     */
    public Room(String room, String roomDescription, String wallDescription) {
        this.roomDescription = roomDescription;
        this.wallDescription = wallDescription;
        // Split the input string by new lines to get each row
        String[] lines = room.split("\n");

        // Determine the dimensions of the room
        int numRows = lines.length;
        int numCols = lines[0].length();

        // Initialize the 2D array of RoomElement
        content = new RoomElement[numRows][numCols];

        // Fill the array with RoomElement objects
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                char ch = lines[i].charAt(j);
                if (ch == '#') {

                    content[i][j] = new Wall();
                } else if (ch == ' ') {
                    Door.Direction dir = isDoor(i, j, lines);
                    if (dir != null) {
                        content[i][j] = new Door(dir);
                    } else {
                        content[i][j] = null; // Space is considered null
                    }
                }
            }
        }
    }

    /**
     * Default constructor for Room.
     */
    public Room() {
    }

    /**
     * Checks if a given position in the room layout represents a door.
     *
     * @param i The row index.
     * @param j The column index.
     * @param lines The room layout as an array of strings.
     * @return The direction of the door, or null if the position does not
     * represent a door.
     */
    private Door.Direction isDoor(int i, int j, String[] lines) {
        int numRows = lines.length;
        int numCols = lines[0].length();

        // A door is a space in the wall in the first or last row
        if ((i == 0 || i == numRows - 1) && lines[i].charAt(j) == ' ') {
            return (i == 0 ? Door.Direction.TOP : Door.Direction.BOTTOM);
        }

        // A door is a space in the wall in the first or last column
        if ((j == 0 || j == numCols - 1) && lines[i].charAt(j) == ' ') {
            return (j == 0 ? Door.Direction.LEFT : Door.Direction.RIGHT);
        }

        return null;
    }

    /**
     * Gets the position of a specified RoomElement in the room.
     *
     * @param r The RoomElement to find.
     * @return An array containing the row and column index of the element, or
     * null if the element is not found.
     */
    public int[] getPositionOfRoomElement(RoomElement r) {
        for (int i = 0; i < content.length; i++) {
            for (int j = 0; j < content[0].length; j++) {
                if (r.equals(content[i][j])) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    /**
     * Checks if a specified position is within the boundaries of the room.
     *
     * @param pos The position to check, as an array containing the row and
     * column index.
     * @return True if the position is within the boundaries of the room, false
     * otherwise.
     */
    public boolean checkBoundary(int[] pos) {
        return (pos[0] >= 0 && pos[0] < content.length) && (pos[1] >= 0 && pos[1] < content[0].length);
    }

    /**
     * Gets the size of the room.
     *
     * @return An array containing the number of rows and columns in the room.
     */
    public int[] getSize() {
        return new int[]{content.length, content[0].length};
    }

    /**
     * Places a RoomElement at the specified position in the room.
     *
     * @param r The RoomElement to place.
     * @param x The row index.
     * @param y The column index.
     * @return The updated room content.
     */
    public RoomElement[][] placeRoomEntity(RoomElement r, int x, int y) {
        content[x][y] = r;
        return this.content;
    }

    /**
     * Moves a RoomElement to the specified position in the room.
     *
     * @param r The RoomElement to move.
     * @param x The row index.
     * @param y The column index.
     * @return The updated room content.
     */
    public RoomElement[][] moveRoomEntity(RoomElement r, int x, int y) {
        if (this.content[x][y] == null) {
            int[] lastPos = getPositionOfRoomElement(r);
            content[x][y] = r;
            content[lastPos[0]][lastPos[1]] = null;
        }
        return this.content;
    }

    /**
     * Removes a specified RoomElement from the room.
     *
     * @param r The RoomElement to remove.
     * @return The updated room content.
     */
    public RoomElement[][] removeRoomElement(RoomElement r) {
        int[] pos = getPositionOfRoomElement(r);
        if (pos != null) {
            content[pos[0]][pos[1]] = null;
        }
        return this.content;
    }

    /**
     * Gets the content of the room.
     *
     * @return A 2D array of RoomElement representing the room content.
     */
    public RoomElement[][] getContent() {
        return content;
    }

    /**
     * Checks if two RoomElements are colliding.
     *
     * @param element1 The first RoomElement.
     * @param element2 The second RoomElement.
     * @return True if the elements are colliding, false otherwise.
     */
    public boolean areColiding(RoomElement element1, RoomElement element2) {
        int[] coord1 = getPositionOfRoomElement(element1);
        int[] coord2 = getPositionOfRoomElement(element2);

        if (coord1 == null || coord2 == null) {
            return false;
        }

        if (coord1.length != 2 || coord2.length != 2) {
            throw new IllegalArgumentException("Both arrays must contain exactly two elements.");
        }

        int x1 = coord1[0];
        int y1 = coord1[1];
        int x2 = coord2[0];
        int y2 = coord2[1];

        int deltaX = Math.abs(x1 - x2);
        int deltaY = Math.abs(y1 - y2);

        // Coordinates are next to each other if they are 1 unit apart in either the x or y direction, or diagonally
        return (deltaX <= 1 && deltaY <= 1) && !(deltaX == 0 && deltaY == 0);

    }

    /**
     * Gets all non-wall elements in the room.
     *
     * @return A list of RoomElement objects that are not walls.
     */
    public List<RoomElement> getAllNonWallElements() {
        /*
        List<RoomElement> nonWallElements = new ArrayList<>();
        for (int i = 0; i < content.length; i++) {
            for (int j = 0; j < content[0].length; j++) {
                RoomElement element = content[i][j];
                if (element != null && !(element instanceof Wall)) {
                    nonWallElements.add(element);
                }
            }
        }*/

        return Arrays.stream(content).flatMap(list -> Arrays.stream(list)).filter(Objects::nonNull).filter(el -> !(el instanceof Wall)).toList();
    }

    /**
     * Gets the description of the room.
     *
     * @return The description of the room.
     */
    public String getRoomDescription() {
        return roomDescription;

    }

    /**
     * Gets all RoomElements of a specified type in the room.
     *
     * @param c The class of the RoomElements to get.
     * @return A set of RoomElement objects of the specified type.
     */
    public HashSet<RoomElement> getRoomElements(Class c) {

        HashSet<RoomElement> elements = new HashSet<>();
        for (int i = 0; i < content.length; i++) {
            for (int j = 0; j < content[0].length; j++) {
                RoomElement element = content[i][j];
                if (element != null) {
                    if (c.isInstance(element)) {
                        elements.add(element);
                    }
                }
            }
        }
        return elements;

    }

    /**
     * Gets all items in the room.
     *
     * @return A set of Item objects in the room.
     */
    public HashSet<Item> getRoomItems() {

        HashSet<Item> items = new HashSet<>();
        for (int i = 0; i < content.length; i++) {
            for (int j = 0; j < content[0].length; j++) {
                RoomElement element = content[i][j];
                if (element != null) {
                    if (element instanceof Item) {
                        items.add((Item) element);
                    }
                }
            }
        }
        return items;

    }

    /**
     * Gets the description of the walls in the room.
     *
     * @return The description of the walls.
     */
    public String getWallDescription() {
        return wallDescription;
    }

}
