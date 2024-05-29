/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.emf.javadventure.services;

import ch.emf.javadventure.app.JavAdventure;
import ch.emf.javadventure.ctrl.IGameCtrl;
import ch.emf.javadventure.models.Player;
import ch.emf.javadventure.models.Room;
import ch.emf.javadventure.models.RoomElement;
import ch.emf.javadventure.views.IGameView;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *
 * @author schwandern
 */
public class JsonLoader {
    
    
    
     /**
     * Loads the JSON data for the current room.
     *
     * 
     */
    
   
    public Room loadJsonData(int[] currentRoomNumber) {
        try (InputStream is = JavAdventure.class.getResourceAsStream("/data/data.json")) {
            if (is == null) {
                throw new IOException("Resource not found: /data/data.json");
            }

            JSONObject jsonObject = new JSONObject(new JSONTokener(is));
            JSONObject room = jsonObject.getJSONObject(String.valueOf(currentRoomNumber[0]))
                    .getJSONObject(String.valueOf(currentRoomNumber[1]))
                    .getJSONObject(String.valueOf(currentRoomNumber[2]));

           
            Room newRoom = new Room(room.getString("roomMap"),room.getString("roomDescription"), room.getString("wallDescription"));

            JSONArray elementsArray = room.getJSONArray("elements");
            
            for (int i = 0; i < elementsArray.length(); i++) {
                JSONObject elementObject = elementsArray.getJSONObject(i);
                String type = elementObject.getString("type");
                String dialogue = elementObject.getString("dialogue");
                int elementX = elementObject.getInt("x");
                int elementY = elementObject.getInt("y");
                RoomElement r = new RoomElement();
                newRoom.placeRoomEntity(r.createFrom(type,dialogue), elementX, elementY);
            }

            return newRoom;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
}
