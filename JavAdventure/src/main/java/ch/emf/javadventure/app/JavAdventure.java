/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package ch.emf.javadventure.app;

import ch.emf.javadventure.ctrl.GameCtrl;
import ch.emf.javadventure.models.IElement;
import ch.emf.javadventure.models.Room;
import ch.emf.javadventure.models.RoomElement;
import ch.emf.javadventure.views.VintageGameView;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;
import org.json.JSONArray;

import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * The JavAdventure class serves as the entry point for the JavAdventure
 * application. It initializes the game view and loads the initial room data
 * from a JSON file.
 *
 * @author <a href="mailto:fanny.riedo@edufr.ch">Fanny Riedo</a>
 * @since 18.05.2024
 */
public class JavAdventure {

    public static int[] currentRoom;

    /**
     * The main method starts the JavAdventure application. It sets the initial
     * room coordinates and initializes the game view.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {

        currentRoom = new int[]{0, 1, 0}; // start on ground floor, center room.
        VintageGameView view = new VintageGameView();
        GameCtrl gameCtrl = new GameCtrl();

        view.setGamectrl(gameCtrl);

        SwingUtilities.invokeLater(() -> {
            loadJsonData(view);
        });
    }

    /**
     * Loads the JSON data for the current room and updates the game view.
     *
     * @param view the game view to be updated with the room data
     */
    private static void loadJsonData(VintageGameView view) {
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


            /*
            for (RoomElement[] row : newRoom.getContent()) {
            for (RoomElement element : row) {
                if (element == null) {
                    System.out.print(' ');
                } else {
                    System.out.print(element.toString());
                }
            }
            System.out.println();

            }*/
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

            view.updateRoom(newRoom.getContent());

            //newRoom.placeRoomEntity(r, 0, 0)
            // Pour chacun des éléments dans la liste, on le dessinera sur la carte et si nécessaire dans la légende
            //view.setMapCharacter('ç', 5, 5);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
