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
public class Door extends RoomElement {

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
        System.out.println(this.direction);
    }

    private Direction direction;

}
