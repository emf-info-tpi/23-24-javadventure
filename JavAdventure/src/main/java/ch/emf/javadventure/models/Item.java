/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.emf.javadventure.models;

/**
 *
 * @author schwandern
 */
public class Item extends RoomElement {
    
    public Item() {
        super('I');
    }
    
    private String name;

    public void setName(String name) {
        this.name = name;
    }
    
    
    
}
