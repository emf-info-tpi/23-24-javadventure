/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.emf.javadventure.services;

import ch.emf.javadventure.tests.DynamicJUnitTestLevel1;
import ch.emf.javadventure.tests.DynamicJUnitTestLevel2;
import java.io.PrintWriter;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import static org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder.request;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

/**
 *
 * @author schwandern
 */
public class TestRunner {

    public boolean runCombat(int dificulte) {
        boolean result = false;
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
                    if (TestRunner.runJUnitTest(DynamicJUnitTestLevel1.class)) {
                        System.out.println("TEST PASSED");

                        result = true;
                    } else {
                        System.out.println("TEST FAILED");
                    }
                } else if (dificulte == 2) {
                    DynamicJUnitTestLevel2.setInstance(recompiledClassInstance);

                    //Execute the test
                    if (TestRunner.runJUnitTest(DynamicJUnitTestLevel2.class)) {
                        System.out.println("TEST PASSED");

                        result = true;
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

        return result;
    }

    public static boolean runJUnitTest(Class<?> testClass) {
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

}
