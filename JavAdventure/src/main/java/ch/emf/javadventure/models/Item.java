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
public class Item extends RoomElement {
    
    public Item(String name) {
        super('I');
        this.name = name;
    }
    
    private String name;

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public void collide(GameCtrl source) {
        System.out.println("ITEM !!");
    }
    
}
