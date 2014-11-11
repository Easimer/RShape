/*
 * Copyright (C) 2012 rothens
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

/**
 *
 * @author rothens
 */
public class BinaryReader {

    FileInputStream fs;
    public DataInputStream ds;

    /**
     * BinaryReader constructor
     *
     * @param f File
     */
    public BinaryReader(File f) {
        try {
            if (!f.exists()) {
                throw new IllegalArgumentException("File not found: " + f.getCanonicalPath());
            }
            fs = new FileInputStream(f);
            ds = new DataInputStream(fs);
        } catch (IOException ioe) {
            System.out.println("IO Error while opening binary file: " + ioe);
        }
    }

    /**
     * Read a byte
     *
     * @return Byte
     */
    public byte readByte() {
        try {
            return ds.readByte();
        } catch (IOException ioe) {
            System.out.println("IO Error while reading a byte: " + ioe);
        }
        return -127;
    }

    /**
     * Read two bytes
     *
     * @return Two bytes as an int
     */
    public int readTwoByte() {
        try {
            byte a = ds.readByte();
            byte b = ds.readByte();
            return ((a & 0xff << 8) | (b & 0xff));
        } catch (IOException ioe) {
            System.out.println("IO Error while reading two bytes: " + ioe);
        }
        return Integer.MIN_VALUE;
    }

    /**
     * Read 1 byte length prefixed string
     *
     * @return String
     */
    public String read1BLPString() {
        String s = "";
        try {
            int length = readByte();
            if (length != 0) {
                for (int i = 0; i < length; i++) {
                    Character c = (char) (int) ds.readByte();
                    if (c != '\0') {
                        s += c;
                    }
                }
            }
        } catch (IOException ioe) {
            System.out.println("IO Error while reading string: " + ioe);
        }
        return s;
    }

    public String read2BLPString() {
        String s = "";
        try {
            int length = readTwoByte();
            if (length != 0) {
                for (int i = 0; i < length; i++) {
                    Character c = (char) (int) ds.readByte();
                    if (c != '\0') {
                        s += c;
                    }
                }
            }
        } catch (IOException ioe) {
            System.out.println("IO Error while reading string: " + ioe);
        }
        return s;
    }

    /**
     * Close FileInputStream and DataInputStream
     */
    public void close() {
        try {
            ds.close();
            fs.close();
        } catch (IOException ex) {
            System.out.println("IO Error");
        }
    }
}
