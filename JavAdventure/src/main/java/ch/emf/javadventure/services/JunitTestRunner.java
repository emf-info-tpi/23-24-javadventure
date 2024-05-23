/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.emf.javadventure.services;

import java.io.PrintWriter;
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
public class JunitTestRunner {
    
    
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
