package SupportingFiles;

import Population.Population;

import java.io.*;

import Population.*;

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


    public static void writeToFile(String filename, Population p) throws IOException {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(filename+".txt"), "utf-8"))) {

            StringBuilder functions = new StringBuilder();
            int commasAdded = 0;

            for(Member member : p.population){
                functions.append(member.functionsToString());
                if(commasAdded != p.population.size() - 1){
                    functions.append(",");
                    commasAdded++;
                }

            }

            writer.write(functions.toString());

        }
    }

    public static void readFromFile(String filename){
        File file = new File(filename+".txt");

        try (FileInputStream fis = new FileInputStream(file)) {

            System.out.println("Total file size to read (in bytes) : "+ fis.available());
            String allFunctions = "";

            int content;
            while ((content = fis.read()) != -1) {
                //todo yesterday you were a shame, time to compensate today, man!
                allFunctions += (char) content;

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
