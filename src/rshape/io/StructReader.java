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

package rshape.io;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author easimer
 */
public class StructReader<T> {

    FileInputStream fs;
    public ObjectInputStream is;

    public StructReader(File f) {
        try {
            if (!f.exists()) {
                throw new IllegalArgumentException("File not found: " + f.getCanonicalPath());
            }
            fs = new FileInputStream(f);
            is = new ObjectInputStream(fs);
        } catch (IOException ioe) {
            System.out.println("IO Error");
        }
    }

    public T readObject() {
        try {
            return (T) is.readObject();
        } catch (Exception ex) {
            System.out.println("IO Error");
            return null;
        }
    }

    public void close() {
        try {
            is.close();
            fs.close();
        } catch (IOException ex) {
            System.out.println("IO Error");
        }
    }
}
