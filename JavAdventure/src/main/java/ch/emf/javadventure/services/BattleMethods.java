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

    /**
     * Code cette méthode pour battre l'ennemi dans la salle sud !. cette
     * méthode doit retourner le résultat de l'addition des deux int reçus en
     * paramètre
     *
     * @param a le premier paramètre a additionner
     * @param b le deuxième paramètre a additionner
     * @return le résultat de l'addition des deux paramètres
     */
    public int combatEnemiSalleSud(int a, int b) {
        return a + b;
    }

    /**
     * Code cette méthode pour battre l'ennemi dans la salle Est. cette méthode
     * doit retourner un Arraylist de string remplis avec les valeurs de 0 a 9.
     *
     * @return un arraylist de string
     */
    public ArrayList<String> combatEnemiSalleEst() {
        ArrayList<String> exercice = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            exercice.add(i + "");
        }

        return exercice;
    }

}
