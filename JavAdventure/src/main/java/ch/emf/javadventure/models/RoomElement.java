/**
 * Project: Javadventure
 * File: RoomElement.java
 *
 * Description: This is the RoomElement Class.
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
public class RoomElement {

    private char texture;
    private String description;

    public RoomElement(char texture) {
        this.texture = texture;

    }

    public RoomElement() {
    }

    public static RoomElement createFrom(String type,String dialogue) {
        RoomElement res = null;

        switch (type) {
            case "MONSTRE niv1" ->
                res = new Enemy(1,dialogue);
            case "MONSTRE niv2" ->
                res = new Enemy(2,dialogue);
            case "EPEE" ->
                res = new Item(type);
            case "HACHE" ->
                res = new Item(type);
            case "BOUCLIER" ->
                res = new Item(type);
        }
        if (res != null) {
            res.setDescription(type.toLowerCase());
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
