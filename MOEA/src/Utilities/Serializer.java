package Utilities;

import Population.Population;

import java.io.*;

/**
 * Created by gabrielm on 05/04/17.
 */
public class Serializer {

    public static void writeObject(String fileName, Population p) throws Exception{

        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName));
            os.writeObject(p);
            os.close();
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }

    }

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


}
