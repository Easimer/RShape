/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rshape.io;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 *
 * @author easimer
 */
public class StructWriter<T> {

    FileOutputStream fs;
    public ObjectOutputStream os;

    public StructWriter(File f) {

        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            fs = new FileOutputStream(f);
            os = new ObjectOutputStream(fs);
        } catch (IOException ioe) {
            System.out.println("IO Error");
        }
    }

    public void writeObject(T o) {
        try {
            os.writeObject(o);
        } catch (Exception ex) {
            System.out.println("IO Error");
        }
    }

    public void close() {
        try {
            os.close();
            fs.close();
        } catch (Exception e) {
            System.out.println("IO Error on close");
        }
    }
}
