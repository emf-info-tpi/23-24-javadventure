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
public class RoomElement {

    private char texture;
    private String description;

    public RoomElement(char texture) {
        this.texture = texture;

    }

    public RoomElement() {
    }

    public static RoomElement createFrom(String type) {
        RoomElement res = null;
        switch (type) {
            case "MONSTRE niv1" ->
                res = new Enemy(1);
            case "MONSTRE niv2" ->
                res = new Enemy(2);
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

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
