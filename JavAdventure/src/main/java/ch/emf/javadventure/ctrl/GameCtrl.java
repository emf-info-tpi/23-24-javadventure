/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.emf.javadventure.ctrl;

import ch.emf.javadventure.models.Door;
import ch.emf.javadventure.models.Door.Direction;
import ch.emf.javadventure.models.Enemy;
import ch.emf.javadventure.models.Item;
import ch.emf.javadventure.models.Player;
import ch.emf.javadventure.models.Room;
import ch.emf.javadventure.models.RoomElement;
import ch.emf.javadventure.views.IGameView;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import static org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder.request;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import ch.emf.javadventure.services.CustomClassLoader;
import ch.emf.javadventure.services.DynamicJUnitTestLevel1;
import ch.emf.javadventure.services.DynamicJUnitTestLevel2;
import ch.emf.javadventure.services.JsonLoader;

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

    public RoomElement[][] move(char key) {
        int[] pos = currentRoom.getPositionOfRoomElement(player);
        int[] nextPos = new int[]{pos[0], pos[1]};
        switch (key) {
            case 'w' ->
                nextPos[0]--;
            case 'a' ->
                nextPos[1]--;
            case 's' ->
                nextPos[0]++;
            case 'd' ->
                nextPos[1]++;
        }

        boolean validPos = currentRoom.checkBoundary(nextPos);
        System.out.println("Valid: " + validPos + " next:" + nextPos[0] + ", " + nextPos[1] + " pos:" + pos[0] + ", " + pos[1]);
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

    public void runCombat(int dificulte) {
        try {

            // Compile the source file
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            String[] args = new String[]{"-d", ".\\target\\classes\\", ".\\src\\main\\java\\ch\\emf\\javadventure\\services\\BattleMethods.java"};
            int compilationResult = compiler.run(null, System.out, System.out, args);

            if (compilationResult == 0) { // If compilation successful
                CustomClassLoader classLoader = new CustomClassLoader(".\\target\\classes\\");

                System.out.println("Class Loader: " + classLoader);

                classLoader.unloadClasses(); // Unload previously loaded classes

                // Load the compiled class
                Class<?> loadedClass = classLoader.loadClass("ch.emf.javadventure.services.BattleMethods");
                System.out.println("Loaded Class: " + loadedClass.getName());

                // Create an instance of the compiled class
                Object recompiledClassInstance = loadedClass.getDeclaredConstructor().newInstance();

                // Set the instance in DynamicJUnitTestExample
                if (dificulte == 1) {
                    DynamicJUnitTestLevel1.setInstance(recompiledClassInstance);

                    //Execute the test
                    if (runJUnitTest(DynamicJUnitTestLevel1.class)) {
                        System.out.println("TEST PASSED");
                    } else {
                        System.out.println("TEST FAILED");
                    }
                } else if (dificulte == 2) {
                    DynamicJUnitTestLevel2.setInstance(recompiledClassInstance);

                    //Execute the test
                    if (runJUnitTest(DynamicJUnitTestLevel2.class)) {
                        System.out.println("TEST PASSED");
                    } else {
                        System.out.println("TEST FAILED");
                    }
                }

            } else {
                System.err.println("compilation failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static boolean runJUnitTest(Class<?> testClass) {
        // Crée une requête de découverte pour les tests, spécifiant la classe de test à exécuter
        LauncherDiscoveryRequest request = request()
                .selectors(DiscoverySelectors.selectClass(testClass))
                .build();

        // Crée un lanceur (launcher) pour exécuter les tests
        Launcher launcher = LauncherFactory.create();

        // Crée un écouteur (listener) qui génère un résumé des tests exécutés
        SummaryGeneratingListener listener = new SummaryGeneratingListener();

        // Enregistre l'écouteur avec le lanceur
        launcher.registerTestExecutionListeners(listener);

        // Exécute les tests en utilisant la requête de découverte
        launcher.execute(request);

        // Récupère le résumé des tests après l'exécution
        TestExecutionSummary summary = listener.getSummary();

        // Affiche le résumé des tests dans la sortie standard
        summary.printTo(new PrintWriter(System.out));

        // Récupère le nombre de tests échoués
        long testsFailedCount = summary.getTestsFailedCount();

        // Détermine si tous les tests ont réussi
        boolean testResult = testsFailedCount == 0;

        // renvoyer le résultat
        return testResult;
    }

    public void showItemDesc(String desc) {
        gameView.setOutputText(desc);
    }

    public void navigateRooms(Enum e) {
        if (e instanceof Direction) {
            Direction direction = (Direction) e;
            int[] nextRoomNumber = getNextRoomNumber(currentRoomNumber, direction);

            String roomKey = getRoomKey(nextRoomNumber);
            Room nextRoom;

            if (rooms.containsKey(roomKey)) {
                nextRoom = rooms.get(roomKey);
            } else {
                nextRoom = jsonLoader.loadJsonDataRoom(nextRoomNumber);
                rooms.put(roomKey, nextRoom);
            }

            if (nextRoom != null) {
                // Remove player from current room
                currentRoom.removeRoomElement(player);

                // Update current room and player position
                currentRoom = nextRoom;
                currentRoomNumber = nextRoomNumber;
                placePlayerNearDoor(direction); // Place the player near the door

                updateRoom();
                gameView.setRoomDescription(currentRoom.getRoomDescription());
            }
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

    public void setPlayer(Player player) {
        this.player = player;
        currentRoom.placeRoomEntity(player, 3, 3);
    }

    public ch.emf.javadventure.models.Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(ch.emf.javadventure.models.Room currentRoom) {
        this.currentRoom = currentRoom;

    }

    public void setGameView(IGameView gameView) {
        this.gameView = gameView;
    }

    public void updateRoom() {
        gameView.updateRoom(currentRoom.getContent());
        gameView.setRoomDescription(currentRoom.getRoomDescription());

    }

    public void loadJsonData(IGameView view, IGameCtrl gameCtrl) {

        JsonLoader.loadJsonData(view, gameCtrl, currentRoomNumber);

    }

}
