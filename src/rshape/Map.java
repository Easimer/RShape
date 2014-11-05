/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rshape;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author easimer
 */
public class Map implements Serializable {
    public Map()
    {
        this.version = 1;
        this.layers = new String[5];
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
        for(int i = 0; i < layers.length; i++)
        {
            try
            {
                this.layers[i] = layers[i];
            }
            catch(Exception ex)
            {
                Logger.getGlobal().log(Level.SEVERE, "Layer " + i + " is bad.");
            }
        }
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
