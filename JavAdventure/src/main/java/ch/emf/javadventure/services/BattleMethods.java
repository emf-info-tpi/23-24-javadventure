/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.emf.javadventure.services;

import java.util.ArrayList;

/**
 *
 * @author schwandern
 */
public class BattleMethods {

    public int CombatEnemiSalleNord(int a, int b) {
        return a + b;
    }

    public ArrayList<String> CombatEnemiSalleSud() {
        ArrayList<String> exercice = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            exercice.add(i + "");
        }

        return exercice;
    }

}
