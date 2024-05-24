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
        Method battleMethod = instance.getClass().getDeclaredMethod("CombatEnemiSalleSud");

        // Invoke the battle method
        ArrayList<String> result = (ArrayList<String>) battleMethod.invoke(instance);
       

        // Assert the result
        for (int i = 0; i < 10; i++) {
            assertTrue(result.contains(i+""));
        }
       
    }
}
