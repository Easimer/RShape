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

	public BinaryReader(File f) {
		try {
			if (!f.exists()) {
				throw new IllegalArgumentException("File not found: " + f.getCanonicalPath());
			}
			fs = new FileInputStream(f);
			ds = new DataInputStream(fs);
		} catch (IOException ioe) {
			System.out.println("IO Error");
		}
	}

	public byte readByte() {
		try {
			return ds.readByte();
		} catch (IOException ex) {
			System.out.println("IO Error");
		}
		return -127;
	}

	public int readTwoByte() {
		try {
			byte a = ds.readByte();
			byte b = ds.readByte();
			return ((a & 0xff << 8) | (b & 0xff));
		} catch (IOException ioe) {
			System.out.println("IO Error");
		}
		return Integer.MIN_VALUE;
	}

	public void close() {
		try {
			ds.close();
			fs.close();
		} catch (IOException ex) {
			System.out.println("IO Error");
		}
	}
}
