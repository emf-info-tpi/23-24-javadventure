/**
 * Project: Javadventure
 * File: Door.java
 *
 * Description: This is the Door class.
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
public class Door extends RoomElement {
    
    private Direction direction;

    public enum Direction {
        TOP,
        BOTTOM,
        RIGHT,
        LEFT

    }

    public Door(Direction d) {
        super(' ');
        this.direction = d;
    }

    @Override
    public void collide(GameCtrl source) {
        source.navigateRooms(this.direction);
    }


}
