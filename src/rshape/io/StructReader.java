/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
