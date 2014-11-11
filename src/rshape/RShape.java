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

import rshape.io.BinaryReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import rshape.io.BinaryWriter;
import rshape.io.StructReader;
import rshape.io.StructWriter;
import org.json.*;

/**
 *
 * @author easimer
 */
public class RShape {

    public static HashMap<Character, Integer> ints = new HashMap<Character, Integer>();
    public static HashMap<Integer, Character> landscape = new HashMap<Integer, Character>();
    public static HashMap<Integer, Character> data1 = new HashMap<Integer, Character>();
    public static HashMap<Integer, Character> entities = new HashMap<Integer, Character>();
    public static HashMap<Integer, Character> data2 = new HashMap<Integer, Character>();

    static String gameinfo = "template.json";

    static char DefaultTileLS = '~';
    static char DefaultTileD = '.';

    private static final int VERSION = 1;

    /**
     * Decompile binary map to Map object
     *
     * @param filename
     * @return Map object
     */
    public static Map Open(String filename) {
        long start = System.currentTimeMillis();
        BinaryReader br = new BinaryReader(new File(filename));
        int width = br.readTwoByte();
        int height = br.readTwoByte();
        int size = width * height;
        String[] layers = new String[5];
        for (int j = 0; j < layers.length; ++j) {
            layers[j] = "";
            for (int i = 0; i < size; ++i) {
                int b = br.readTwoByte();
                Character c = (j == 0) ? landscape.get(b) : ((j == 4) ? entities.get(b) : ((j == 1 || j == 3) ? data1.get(b) : data2.get(b)));
                layers[j] += (c == null) ? ((j == 0) ? DefaultTileLS : DefaultTileD) : c;
            }
        }
        int version = br.readByte();
        String title = br.read2BLPString();
        br.close();
        System.out.println("Map decompiled in: " + (System.currentTimeMillis() - start) + "ms");
        return new Map(width, height, title, layers[0], layers[1], layers[2], layers[3], layers[4]);
    }

    /**
     * Compile Map to binary file compatible with 2DRPG 0.15
     *
     * @param filename Name of file to write
     * @param m Map object
     */
    public static void Save(String filename, Map m) {
        long start = System.currentTimeMillis();
        int size = m.width * m.height;
        BinaryWriter bw = new BinaryWriter(new File(filename));
        bw.writeTwoByte(m.width);
        bw.writeTwoByte(m.height);
        for (int i = 0; i < m.layers.length; i++) {
            int[] bdat = new int[size];
            for (int k = 0; k < size; k++) {
                bdat[k] = 0; //Fill bdat with zeros in case the layer's length not equals 'size'
            }
            int j = (size > m.layers[i].length()) ? m.layers[i].length() : size;
            for (int k = 0; k < j; ++k) {
                char ch = m.layers[i].charAt(k);
                Integer val = ints.get(ch);
                bdat[k] = (val == null) ? 0 : val;
            }
            bw.writeTwoBytes(bdat);
        }
        bw.writeByte(m.version);
        bw.writeTwoByte(m.title.length());
        bw.writeBytes(m.title.getBytes());
        bw.close();
        System.out.println("Map compiled in: " + (System.currentTimeMillis() - start) + "ms");
    }

    /**
     * Display error
     *
     * @param msg Error message
     */
    public static void ErrorMsg(String msg) {
        System.out.format("Error: %s\n", msg);
        System.exit(1);
    }

    public static void main(String[] args) {

        RShapeGUI g;
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-h":
                    System.out.format("RShape Compiler - v%d\nHelp:\nExample syntax: RShape.jar -g mygame.json\n\nParameters:\n-g - name of the gameinfo file\n", VERSION);
                    System.exit(0);
                    break;
                case "-g":
                    if (i + 1 < args.length - 1) {
                        gameinfo = args[i + 1];
                        i++;
                    }
                    break;
            }
        }
        JSONTokener jt = null;
        try {
            jt = new JSONTokener(new String(Files.readAllBytes(Paths.get("games/" + gameinfo)), Charset.defaultCharset()));
        } catch (IOException ex) {
            ErrorMsg(String.format("Error: Gameinfo file \"games/%s\" missing\n", gameinfo));
        }
        if (jt != null) {
            JSONObject o = new JSONObject(jt);

            DefaultTileLS = o.getString("default_tile").charAt(0);
            DefaultTileD = o.getString("default_dtile").charAt(0);
            JSONObject landso = o.getJSONObject("landscape");
            JSONObject data1o = o.getJSONObject("data1");
            JSONObject data2o = o.getJSONObject("data2");
            JSONObject ento = o.getJSONObject("entities");
            for (String key : landso.keySet()) {
                landscape.put(landso.getInt(key), key.charAt(0));
            }
            for (String key : data1o.keySet()) {
                data1.put(data1o.getInt(key), key.charAt(0));
            }
            for (String key : data2o.keySet()) {
                data2.put(data2o.getInt(key), key.charAt(0));
            }
            for (String key : ento.keySet()) {
                entities.put(ento.getInt(key), key.charAt(0));
            }
            for (Integer i : landscape.keySet()) {
                ints.put(landscape.get(i), i);
            }
            for (Integer i : data1.keySet()) {
                ints.put(data1.get(i), i);
            }
            for (Integer i : data2.keySet()) {
                ints.put(data2.get(i), i);
            }
            for (Integer i : entities.keySet()) {
                ints.put(entities.get(i), i);
            }
        }
        new RShapeGUI().Show();
    }
}
