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

    public Room(int x, int y) {
        content = new RoomElement[x][y];
        
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (i == 0 || i == x - 1 || j == 0 || j == y - 1) {
                    placeRoomEntity(new Wall(i, j), i, j);
                }
            }
        }
    }
    
    public int[] getPositionOfRoomEntity(RoomElement r) {
         return new int[] { r.getX(), r.getY() };
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
        if(this.content[x][y] == null) {
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
