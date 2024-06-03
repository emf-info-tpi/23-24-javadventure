/**
 * Project: Javadventure
 * File: DynamicJUnitTestLevel1.java
 *
 * Description: This is the DynamicJUnitTestLevel1 Class.
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

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DynamicJUnitTestLevel1 {

    private static Object instance;

    public static void setInstance(Object instance) {
        DynamicJUnitTestLevel1.instance = instance;
    }

    @Test
    public void testAddition() throws Exception {
        
        
        // Use reflection to get the battle method
        Method battleMethod = instance.getClass().getDeclaredMethod("combatEnemiSalleSud",int.class, int.class);

        // Invoke the battle method
        int result = (int) battleMethod.invoke(instance, 2, 3);
       

        // Assert the result
        assertEquals(5, result); // Check result
       
    }
}
