/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
        Method battleMethod = instance.getClass().getDeclaredMethod("CombatEnemiSalleNord",int.class, int.class);

        // Invoke the battle method
        int result = (int) battleMethod.invoke(instance, 2, 3);
       

        // Assert the result
        assertEquals(5, result); // Check result
       
    }
}
