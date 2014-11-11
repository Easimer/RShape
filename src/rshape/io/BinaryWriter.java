/*
 * Copyright (C) 2012 Rothens
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

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * rothens.tarhely.biz
 *
 * @author Rothens
 */
public class BinaryWriter {

    FileOutputStream fs;
    public DataOutputStream ds;

    public BinaryWriter(File f) {

        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            fs = new FileOutputStream(f);
            ds = new DataOutputStream(fs);
        } catch (IOException ioe) {
            System.out.println("IO Error");
        }
    }

    public void writeByte(int b) {
        try {

            ds.writeByte(b);
        } catch (IOException ex) {
            System.out.println("IO Error");
        }
    }

    public void writeTwoByte(int val) {
        byte a = (byte) ((val >> 8) & 0xff);
        byte b = (byte) (val & 0xff);
        try {
            ds.writeByte(a);
            ds.writeByte(b);
        } catch (IOException ex) {
            System.out.println("IO Error");
        }
    }

    public void writeTwoBytes(int[] i) {
        for (int in : i) {
            writeTwoByte(in);
        }
    }

    public void writeBytes(byte[] b) {
        try {
            for (byte by : b) {
                ds.writeByte(by);
            }
        } catch (IOException ex) {
            System.out.println("IO Error");
        }
    }

    public void close() {
        try {
            ds.close();
            fs.close();
        } catch (Exception e) {
            System.out.println("IO Error on close");
        }
    }
}
