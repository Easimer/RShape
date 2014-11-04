/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rshape;

import java.io.Serializable;

/**
 *
 * @author easimer
 */
public class Map implements Serializable {
    public Map()
    {
        this.version = 1;
    }
    
    public Map(int width, int height)
    {
        this(); //this Xddddd << bitshifts to the left
        this.width = width;
        this.height = height;
    }
    
    public Map(int width, int height, String title)
    {
        this(width, height);
        this.title = title;
    }
    
    public Map(int width, int height, String title, String... layers)
    {
        this(width, height, title);
        System.arraycopy(layers, 0, this.layers, 0, layers.length); //i don't know what the fuck is this but netbeans offered this instead of a for loop and it works, so
    }
    
    public void Clear()
    {
        throw new UnsupportedOperationException("Not implemented");
    }
    int width, height;
    String[] layers;
    int version;
    String title;
}
