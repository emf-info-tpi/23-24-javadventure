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
                    if (isDoor(i, j, lines)) {
                        content[i][j] = new Door();
                    } else {
                        content[i][j] = null; // Space is considered null
                    }
                }
            }
        }
    }

    private boolean isDoor(int i, int j, String[] lines) {
        int numRows = lines.length;
        int numCols = lines[0].length();

        // A door is a space in the wall in the first or last row
        if ((i == 0 || i == numRows - 1) && lines[i].charAt(j) == ' ') {
            return true;
        }

        // A door is a space in the wall in the first or last column
        if ((j == 0 || j == numCols - 1) && lines[i].charAt(j) == ' ') {
            return true;
        }

        return false;
    }

    public int[] getPositionOfRoomEntity(RoomElement r) {
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
            int[] lastPos = getPositionOfRoomEntity(r);
            content[x][y] = r;
            content[lastPos[0]][lastPos[1]] = null;
        }
        return this.content;
    }

    public RoomElement[][] getContent() {
        return content;
    }

}
