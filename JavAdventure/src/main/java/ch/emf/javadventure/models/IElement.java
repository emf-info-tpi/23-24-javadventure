/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ch.emf.javadventure.models;

/**
 * The IElement interface represents an element in the game that can interact with the player.
 * (items, enemies, stairs, doors)
 * It provides methods to check for collisions and to get a description of the element.
 * 
 * @author <a href="mailto:fanny.riedo@edufr.ch">Fanny Riedo</a>
 * @since 18.05.2024
 */
public interface IElement {

    /**
     * Checks if the element is colliding with the given coordinates.
     *
     * @param x the x-coordinate to check for collision
     * @param y the y-coordinate to check for collision
     * @return true if the element is colliding with the given coordinates, false otherwise
     */
    public boolean isColliding(int x, int y);
    
    //public void collideAction();

    /**
     * Gets a description of the element.
     *
     * @return a string description of the element
     */
    public String getDescription();
}