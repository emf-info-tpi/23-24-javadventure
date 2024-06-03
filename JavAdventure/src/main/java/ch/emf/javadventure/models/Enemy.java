/**
 * Project: Javadventure
 * File: Enemy.java
 *
 * Description: This is the Enemy class.
 *
 * Author: Nicolas Schwander
 *
 * Created: 21.05.2024
 *
 * License: GPL License
 *
 */
package ch.emf.javadventure.models;

import ch.emf.javadventure.ctrl.GameCtrl;

/**
 *
 * @author schwandern
 */
public class Enemy extends RoomElement{
    
  
    private int dificulte;
    private String dialogue;
    
    public Enemy(int dificulte, String dialogue) {
        super('E');
        this.dificulte = dificulte;
        this.dialogue = dialogue;
    }
    
     @Override
    public void collide(GameCtrl source) {
        source.runCombat(dificulte, this);
    }
    
}
