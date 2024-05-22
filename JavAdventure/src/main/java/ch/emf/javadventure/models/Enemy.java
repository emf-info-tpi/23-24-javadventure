/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.emf.javadventure.models;

import ch.emf.javadventure.ctrl.GameCtrl;

/**
 *
 * @author schwandern
 */
public class Enemy extends RoomElement{
    
  
    public Enemy() {
        super('E');
    }
    
     @Override
    public void collide(GameCtrl source) {
        System.out.println("Aie y'a un ennemi !!");
    }
    
}