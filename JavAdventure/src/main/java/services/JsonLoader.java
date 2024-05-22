/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import ch.emf.javadventure.app.JavAdventure;
import static ch.emf.javadventure.app.JavAdventure.currentRoom;
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
     * Loads the JSON data for the current room and updates the game view.
     *
     * @param view the game view to be updated with the room data
     */
    public static void loadJsonData(IGameView view, IGameCtrl gameCtrl) {
        try (InputStream is = JavAdventure.class.getResourceAsStream("/data/data.json")) {
            if (is == null) {
                throw new IOException("Resource not found: /data/data.json");
            }

            JSONObject jsonObject = new JSONObject(new JSONTokener(is));
            JSONObject room = jsonObject.getJSONObject(String.valueOf(currentRoom[0]))
                    .getJSONObject(String.valueOf(currentRoom[1]))
                    .getJSONObject(String.valueOf(currentRoom[2]));

            //view.drawRoomMap(room.getString("roomMap"));
            Room newRoom = new Room(room.getString("roomMap"));
            
            
            view.setRoomDescription(room.getString("roomDescription"));
            view.setMapLegend("ici sera la légende");
            view.setOutputText("informations supplémentaires");

            JSONArray elementsArray = room.getJSONArray("elements");
            List<RoomElement> elements = new ArrayList<>();
            for (int i = 0; i < elementsArray.length(); i++) {
                JSONObject elementObject = elementsArray.getJSONObject(i);
                String type = elementObject.getString("type");
                int elementX = elementObject.getInt("x");
                int elementY = elementObject.getInt("y");
                RoomElement r = new RoomElement();
                //elements.add(r.createFrom(type, elementX, elementY)); // ajouter des monstres, portes etc
                newRoom.placeRoomEntity(r.createFrom(type), elementX, elementY);
            }

            Player player = new Player();
            gameCtrl.setCurrentRoom(newRoom);
            gameCtrl.setPlayer(player);

            //newRoom.placeRoomEntity(r, 0, 0)
            // Pour chacun des éléments dans la liste, on le dessinera sur la carte et si nécessaire dans la légende
            //view.setMapCharacter('ç', 5, 5);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
}
