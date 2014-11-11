/*
 * Copyright (C) 2014 easimer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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

    public Map(int width, int height, String title, String... layers) {
        this.version = 1;
        this.layers = new String[5];
        this.width = width;
        this.height = height;
        this.title = title;
        for (int i = 0; i < layers.length; i++) {
            try {
                this.layers[i] = layers[i];
            } catch (Exception ex) {
                Logger.getGlobal().log(Level.SEVERE, "Layer " + i + " is bad.");
            }
        }
        Clear();
    }

    public void Clear() {
        this.layers = new String[5];
        this.title = "";
        for (int i = 0; i < layers.length; i++) {
            if (i == 1) {
                for (int j = 0; j < width * height; j++) {
                    layers[i] += RShape.DefaultTileLS;
                }
            } else {
                for (int j = 0; j < width * height; j++) {
                    layers[i] += RShape.DefaultTileD;
                }
            }
        }
    }
    int width, height;
    String[] layers;
    int version;
    String title;
}
