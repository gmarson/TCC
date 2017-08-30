package SupportingFiles;

import Constants.Constants;
import Population.Population;

import java.io.*;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                    functions.append(" , ");
                    commasAdded++;
                }

            }

            writer.write(functions.toString());

        }
    }

    public static void readFromFile(String filename){
        File file = new File(filename+".txt");
        Population paretto = new Population();

        try (FileInputStream fis = new FileInputStream(file)) {

            System.out.println("Total file size to read (in bytes) : "+ fis.available());
            String allFunctions = "";

            int content;
            while ((content = fis.read()) != -1) {
                allFunctions += (char) content;
            }

            Pattern p = Pattern.compile("-?[0-9]*\\.?[0-9]+");
            Matcher m = p.matcher(allFunctions);
            int counter = 0;
            Member member = new Member(-1);

            while (m.find()) {
                if(counter  % Constants.PROBLEM_SIZE  == 0 & counter != 0){
                    counter = 0;
                    paretto.population.add(member.deepCopy());
                    member = new Member(-1);
                }

                member.resultOfFunctions.add(new Double(m.group()));

                counter++;
                
            }

            Printer.printBinaryMembersWithAppliedFunctions(paretto);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
