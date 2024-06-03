/**
 * Project: Javadventure
 * File: DynamicJUnitTestLevel2.java
 *
 * Description: This is the DynamicJUnitTestLevel2 Class.
 *
 * Author: Nicolas Schwander
 *
 * Created: 21.05.2024
 *
 * License: GPL License
 *
 */
package ch.emf.javadventure.tests;

/**
 *
 * @author riedof
 */
import org.junit.jupiter.api.Test;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DynamicJUnitTestLevel2 {

    private static Object instance;

    public static void setInstance(Object instance) {
        DynamicJUnitTestLevel2.instance = instance;
    }

    @Test
    public void testAddition() throws Exception {
        
        
        // Use reflection to get the battle method
        Method battleMethod = instance.getClass().getDeclaredMethod("combatEnemiSalleEst");

        // Invoke the battle method
        ArrayList<String> result = (ArrayList<String>) battleMethod.invoke(instance);
       

        // Assert the result
        for (int i = 0; i < 10; i++) {
            assertTrue(result.contains(i+""));
        }
       
    }
}
