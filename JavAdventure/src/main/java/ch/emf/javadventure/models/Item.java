/**
 * Project: Javadventure
 * File: Item.java
 *
 * Description: This is the Item Class.
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
        source.showItemDesc("vous avez trouvé :"+name.toLowerCase());
    }
    
}
