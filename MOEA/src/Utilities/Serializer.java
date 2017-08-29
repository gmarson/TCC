package Utilities;

import Population.Population;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import Population.*;

/**
 * Created by gabrielm on 05/04/17.
 */
public class Serializer {

//    public static void writeObject(String fileName, Population p) throws Exception{
//
//        try {
//            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName));
//            os.writeObject(p);
//            os.close();
//        }
//        catch (FileNotFoundException e){
//            e.printStackTrace();
//        }
//
//    }

    public static Population readObject(String fileName) throws Exception{
        Population p  = new Population();

        try {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(fileName));
            p = (Population) is.readObject();
            is.close();
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }

        return p;

    }


    public static void writeObject(String filename, Population p) throws IOException {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(filename+".txt"), "utf-8"))) {

            for(Member member : p.population){
                String resultOfFuctions = "";



            }

            writer.write("something");
        }
    }

}
