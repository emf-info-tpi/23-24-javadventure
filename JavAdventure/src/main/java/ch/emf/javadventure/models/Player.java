/**
 * Project: Javadventure
 * File: Player.java
 *
 * Description: This is the Player Class.
 *
 * Author: Nicolas Schwander
 *
 * Created: 21.05.2024
 *
 * License: GPL License
 *
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
    
    public void removeFromInventory(Item i){
        inventory.remove(i);
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
