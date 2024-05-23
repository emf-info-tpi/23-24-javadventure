/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.emf.javadventure.models;

import java.util.HashSet;

/**
 *
 * @author schwandern
 */
public class Player extends RoomElement {

    private HashSet<Item> inventory;

    public Player() {
        super('C');
        inventory = new HashSet<>();
                
    }

    public void addToInventory(Item i) {
        
        inventory.add(i);
    }

    public HashSet<Item> getInventory() {
        return inventory;
    }

    public String printInventory() {
        String result = "Ton inventaire contient :";
        if (!inventory.isEmpty()) {
            for (Item item : inventory) {
                result += " " + item.getDescription()+" ,";
            }

        }
        else{
            result = "ton inventaire est vide";
        }
        return result;
    }

}
