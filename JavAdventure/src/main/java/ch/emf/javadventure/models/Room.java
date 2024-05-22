/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.emf.javadventure.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 *
 * @author schwandern
 */
public class Room {

    private RoomElement[][] content;

    public Room(String room) {
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

    public Room() {
    }
    
    
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

    public boolean checkBoundary(int[] pos) {
        return (pos[0] >= 0 && pos[0] < content.length) && (pos[1] >= 0 && pos[1] < content[0].length);
    }

    public int[] getSize() {
        return new int[]{content.length, content[0].length};
    }

    public RoomElement[][] placeRoomEntity(RoomElement r, int x, int y) {
        content[x][y] = r;
        return this.content;
    }

    public RoomElement[][] moveRoomEntity(RoomElement r, int x, int y) {
        if (this.content[x][y] == null) {
            int[] lastPos = getPositionOfRoomElement(r);
            content[x][y] = r;
            content[lastPos[0]][lastPos[1]] = null;
        }
        return this.content;
    }

    public RoomElement[][] getContent() {
        return content;
    }
    
    public boolean areColiding(RoomElement element1, RoomElement element2){
        int[] coord1 = getPositionOfRoomElement(element1);
        int[] coord2 = getPositionOfRoomElement(element2);
        
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
    
    public List<RoomElement> getAllNonWallElements() {
        List<RoomElement> nonWallElements = new ArrayList<>();
        for (int i = 0; i < content.length; i++) {
            for (int j = 0; j < content[0].length; j++) {
                RoomElement element = content[i][j];
                if (element != null && !(element instanceof Wall)) {
                    nonWallElements.add(element);
                }
            }
        }
        return nonWallElements;
    }

}
