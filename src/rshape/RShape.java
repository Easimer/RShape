/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rshape;
import rshape.io.BinaryReader;
import java.io.File;
import java.util.HashMap;
import rshape.io.BinaryWriter;
import rshape.io.StructReader;
import rshape.io.StructWriter;
/**
 *
 * @author easimer
 */



public class RShape {
    
    public static HashMap<Character, Integer> ints = new HashMap<Character, Integer>();
    public static HashMap<Integer, Character> landscape = new HashMap<Integer, Character>();
    public static HashMap<Integer, Character> object = new HashMap<Integer, Character>();
    public static HashMap<Integer, Character> entities = new HashMap<Integer, Character>();
    private static final int VERSION = 1;
    /**
     * Decompile binary map to Map object
     * @param filename
     * @return Map object
     */
    public static Map Decompile(String filename)
    {
        long start = System.currentTimeMillis();
        BinaryReader br = new BinaryReader(new File(filename));
        System.out.println("Reading size");
        int width = br.readTwoByte();
        int height = br.readTwoByte();
        int size = width * height;
	String[] layers = new String[5];
	for (int j = 0; j < layers.length; ++j) {
            System.out.println("Reading layer " + j);
            layers[j] = "";
            for (int i = 0; i < size; ++i) {
            	int b = br.readTwoByte();
		Character c = (j==0) ? landscape.get(b) : ((j==4)? entities.get(b) : object.get(b));                               
		layers[j] += (c == null) ? ((j == 0) ? "~" : "." ): c;
            }
	}
        System.out.println("Reading version");
        int version = br.readByte();
        System.out.println("Reading title");
        int titleLength = br.readTwoByte(); //min 0, max 65535
        String title = "";
        if(titleLength != 0)
            for(int i = 0; i < titleLength; i++)
                title += new Character((char)(int)br.readByte());
        br.close();
        System.out.println("Map decompiled in: " + (System.currentTimeMillis() - start) + "ms");
        return new Map(width, height, title, layers);
    }
    /**
     * Compile Map to binary file compatible with both 2DRPG 0.15 and Space-Game
     * @param filename Name of file to write
     * @param m Map object
     */
    public static void Compile(String filename, Map m)
    {
        long start = System.currentTimeMillis();
        int size = m.width * m.height;
        BinaryWriter bw = new BinaryWriter(new File(filename));
        System.out.println("Writing map...");
        bw.writeTwoByte(m.width);
        bw.writeTwoByte(m.height);
        for(int i = 0; i < m.layers.length; i++)
        {
            System.out.println("Writing layer " + i);
            int[] bdat = new int[size];
            for(int k = 0; k < size; k++)
            {
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
        System.out.println("Writing version and map title");
        bw.writeByte(m.version);
        bw.writeTwoByte(m.title.length());
        bw.writeBytes(m.title.getBytes());
        bw.close();
        System.out.println("Map compiled in: " + (System.currentTimeMillis() - start) + "ms");
    }
    /**
     * Loads a RShaper File into a Map object
     * @param filename
     * @return Map object
     */
    public static Map LoadRShaperFile(String filename)
    {
        System.out.format("Loading file %s\n", filename);
        StructReader<Map> sr = new StructReader(new File(filename));
        Map m = sr.readObject();
        sr.close();
        if(m != null) System.out.println("Successfully loaded");
        else System.out.println("Load failed");
        return m;
    }
    
    public static void SaveRShaperFile(String filename, Map m)
    {
        StructWriter sw = new StructWriter<Map>(new File(filename));
        sw.writeObject(m);
        sw.close();
    }
    
    public static void ErrorMsg(String msg)
    {
        System.out.format("Error: %s\n", msg);
        System.exit(1);
    }
    
    public static void main(String[] args) {
        // <editor-fold defaultstate="collapsed" desc="hashmap init">
        ints.put('.', 0);
	ints.put('1', 1);
	ints.put('2', 2);
	ints.put('3', 3);
	ints.put('4', 4);
	ints.put('5', 5);
	ints.put('6', 6);
	ints.put('7', 7);
	/*
	 * ---------
	 */
	ints.put('~', 0);
	ints.put('g', 1);
	ints.put('d', 2);
	ints.put('s', 3);
	ints.put('l', 4);
	/*
	 * ---------
	 */
	ints.put('z', 65535);
	ints.put('*', 1);
	ints.put('c', 2);
	ints.put('r', 3);
        ints.put('h', 4);
	/*
	 * ---------
	 */
	object.put(0, '.');
	object.put(1, '1');
	object.put(2, '2');
	object.put(3, '3');
	object.put(4, '4');
	object.put(5, '5');
	object.put(6, '6');
	object.put(7, '7');
	/*
	 * ---------
	 */
	landscape.put(0, '~');
	landscape.put(1, 'g');
	landscape.put(2, 'd');
	landscape.put(3, 's');
	landscape.put(4, 'l');
	/*
	 * ---------
	 */
	entities.put(65535, 'z');
	entities.put(0, '.');
	entities.put(1, '*');
	entities.put(2, 'c');
        entities.put(3, 'r');
        entities.put(4, 'h');
        entities.put(5, 'n');
        // </editor-fold>
        Boolean ifn = false;
        Boolean ofn = false;
        Boolean mode = false;
        Boolean gui = false;
        String outfn = "";
        String infn = "";
        String modes = "";
        RShapeGUI g;
        for(int i = 0; i < args.length; i++)
        {
            switch(args[i])
            {
                case "-gui":
                    gui = true;
                    g = new RShapeGUI();
                    g.Show();
                    break;
                case "-h":
                    System.out.format("RShape Compiler - v%d\nHelp:\nExample syntax: rsc -o [output file] -m [mode] [input file]\n\nParameters:\n-o - output filename\n-m - mode\n\nModes:\ncompile - compile rshaper file to binary\ndecompile - decompile binary to rshaper file\n", VERSION);
                    System.exit(0);
                    break;
                case "-o":
                    if(i + 1 < args.length - 1)
                    {
                        outfn = args[i + 1];
                        ofn = true;
                        i++;
                    }
                    break;
                case "-m":
                    if(i + 1 < args.length - 1)
                    {
                        modes = args[i + 1];
                        mode = true;
                        i++;
                    }
                    break;
            }
            if(ifn = ofn && mode)
                infn = args[i];
        }
        if(!(ifn && ofn && mode) && !gui)
                ErrorMsg("not enough parameters");
        System.out.format("Input file: %s\nOutput file: %s\nMode: %s\n", infn, outfn, modes);
        switch(modes)
        {
            case "compile":
                Compile(outfn, LoadRShaperFile(infn));
                break;
            case "decompile":
                SaveRShaperFile(outfn, Decompile(infn));
                break;
            default:
                if(!gui) ErrorMsg("invalid mode");
                break;
        }
    }
}
