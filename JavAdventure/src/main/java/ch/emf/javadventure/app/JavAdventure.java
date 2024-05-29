/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package ch.emf.javadventure.app;

import ch.emf.javadventure.ctrl.GameCtrl;
import ch.emf.javadventure.ctrl.IGameCtrl;
import ch.emf.javadventure.models.IElement;
import ch.emf.javadventure.models.Player;
import ch.emf.javadventure.models.Room;
import ch.emf.javadventure.models.RoomElement;
import ch.emf.javadventure.views.IGameView;
import ch.emf.javadventure.views.VintageGameView;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;
import org.json.JSONArray;

import org.json.JSONObject;
import org.json.JSONTokener;
import ch.emf.javadventure.services.JsonLoader;

/**
 * The JavAdventure class serves as the entry point for the JavAdventure
 * application. It initializes the game view and loads the initial room data
 * from a JSON file.
 *
 * @author <a href="mailto:fanny.riedo@edufr.ch">Fanny Riedo</a>
 * @since 18.05.2024
 */
public class JavAdventure {

    /**
     * The main method starts the JavAdventure application. It sets the initial
     * room coordinates and initializes the game view.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {

        IGameView view = new VintageGameView();
        IGameCtrl gameCtrl = new GameCtrl();

        view.setGamectrl(gameCtrl);
        gameCtrl.setGameView(view);

        SwingUtilities.invokeLater(() -> {
            gameCtrl.initializeGame();

        });
    }

}
