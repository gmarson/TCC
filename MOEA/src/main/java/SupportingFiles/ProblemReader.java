package SupportingFiles;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;

/**
 * Created by gabrielm on 02/04/17.
 */
public class ProblemReader {

    public static int numberOfObjectives;
    public static int numberOfItems;
    public static int totalWeight;
    public static ArrayList<Double> objectives = new ArrayList<Double>();
    public static ArrayList<Double> weights = new ArrayList<Double>();

    public static void getProblemFromFile(String fileName) throws IOException {
        String[] lines = Files.readAllLines(new File(fileName).toPath()).toArray(new String[0]);

        setParameters(lines);
        setObjectiveValues(lines);
        setWeightValues(lines);
    }

    private static void setParameters(String[] lines){
        numberOfObjectives = Integer.parseInt(lines[0].substring(0));
        numberOfItems = Integer.parseInt(lines[1].substring(0));
        totalWeight = Integer.parseInt(lines[2].substring(0));
    }


    private static void setObjectiveValues(String[] lines){
        for (int i = 3; i < numberOfObjectives+3; i++) {
            String[] tokens = lines[i].replaceAll("[^0-9\\s]", "").split(" ");
            for (String token : tokens){
                objectives.add( (double) Integer.parseInt(token));
            }

        }
    }

    private static void setWeightValues(String[] lines){
        String[] weightTokens = lines[lines.length-1].replaceAll("[^0-9\\s]", "").split(" ");
        for (String token : weightTokens){
            weights.add( (double) Integer.parseInt(token));
        }
    }



}
