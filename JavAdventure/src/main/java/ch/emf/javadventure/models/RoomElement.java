/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ch.emf.javadventure.models;

import ch.emf.javadventure.ctrl.GameCtrl;
import ch.emf.javadventure.models.IElement;

/**
 *
 * @author schwandern
 */
public class RoomElement implements IElement {

    private char texture;

    public RoomElement(char texture) {
        this.texture = texture;

    }

    public RoomElement() {
    }
    
   
    public static RoomElement createFrom(String type) {
        RoomElement res = null;
        switch (type) {
            case "MONSTRE" ->
                res = new Enemy();
            case "EPEE" ->
                res = new Item(type);
            case "HACHE" ->
                res = new Item(type);
            case "BOUCLIER" ->
                res = new Item(type);       
        }
        return res;
    }
    
    public void collide(GameCtrl ctrl) {
    }

    @Override
    public String toString() {
        return texture + "";
    }

    @Override
    public boolean isColliding(int x, int y) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getDescription() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
