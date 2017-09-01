package SupportingFiles;

import Constants.Constants;
import Population.Population;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

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

    public static Population readFromFile(String filename){
        File file = new File(filename+".txt");
        Population paretto = new Population();

        try (FileInputStream fis = new FileInputStream(file)) {

            System.out.println("Total file size to read (in bytes) : "+ fis.available());
            String allFunctions = "";


            Reader reader = new FileReader(filename + ".txt");
            try {
                try(BufferedReader br = new BufferedReader(new FileReader(file))) {
                    for(String line; (line = br.readLine()) != null; ) {
                        allFunctions += line;
                    }
                }

            } finally {
                reader.close();
            }

            Pattern p = Pattern.compile("-?[0-9]*\\.?[0-9]+");
            Matcher m = p.matcher(allFunctions);
            int counter = 1;
            Member member = new Member(-1);

            while (m.find()) {
                if(counter % (Constants.PROBLEM_SIZE +1) == 0){
                    paretto.addMember(member.deepCopy());
                    counter = 1;
                    member = new Member(-1);
                }

                member.resultOfFunctions.add(new Double(m.group()));



                counter++;
            }

            paretto.addMember(member.deepCopy());

            Printer.printBinaryMembersWithAppliedFunctions(paretto);
            Utils.stop();//todo

        } catch (IOException e) {
            e.printStackTrace();
        }

        return paretto;
    }
}
