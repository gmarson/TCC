import Constants.Constants;
import ManyObjective.TableFunctions.TableFunctions;
import PerformanceMetrics.Erro;
import Population.*;
import Problems.*;
import Utilities.*;
import NSGAII.*;
import Fronts.*;
import SPEA2.*;
import ManyObjective.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


public class Main {

    private static String fileName = "KP_p-3_n-10_ins-1";
    private static String extension = ".dat";
    private static String parettoName = "Paretto";
    private static String directoryName ="KP/" ;
    public static String windowsPathRead = "MOEA/KP_p-3_n-10_ins-1Paretto.dat";

    public static void main(String[] args) throws Exception {
        currentDirectory();
        //writeParettoFromProblem();
        String file = fileName + parettoName + extension;
        Population parettoPopulation = readParettoFromFile(windowsPathRead);


        //Problem problem = new ProblemSCH();
        //Problem problem = new ProblemF2();
        //Problem problem = new ProblemKnapsack();
        Problem problem = new ProblemKnapsackFromFile("MOEA/KP/"+fileName+extension);


        NSGAII nsgaii = new NSGAII();
        SPEA2 spea2 = new SPEA2();
        AEMMT aemmt = new AEMMT();
        AEMMD aemmd = new AEMMD();


        //nsgaii.runAlgorithm(problem);
        spea2.runAlgorithm(problem);
        //aemmt.runAlgorithm(problem);
        //aemmd.runAlgorithm(problem);


        Erro erro = new Erro(problem);
        erro.messageBeforeResult();
        Population newPopulation = new Population();

        if(!nsgaii.paretto.membersAtThisFront.isEmpty())
            newPopulation.population = nsgaii.paretto.membersAtThisFront;
        else if (!spea2.paretto.membersAtThisFront.isEmpty())
            newPopulation.population = spea2.paretto.membersAtThisFront;
        else if (!aemmt.paretto.membersAtThisFront.isEmpty())
            newPopulation.population = aemmt.paretto.membersAtThisFront;
        else if (!aemmd.paretto.membersAtThisFront.isEmpty())
            newPopulation.population = aemmd.paretto.membersAtThisFront;



        System.out.println(erro.estimateBasedOnMetric(newPopulation,parettoPopulation));


    }


    private static Population  getBestPossibleParettoOfAGS(){
        NSGAII nsgaii = new NSGAII();
        SPEA2 spea2 = new SPEA2();
        AEMMT aemmt = new AEMMT();
        AEMMD aemmd = new AEMMD();

        Problem problem = new ProblemKnapsackFromFile(directoryName+fileName+extension);
        //Problem problem = new ProblemSCH();
        //Problem problem = new ProblemF2();
        //Problem problem = new ProblemKnapsack();

        Front nsgaiiMembers = new Front();
        Front spea2Members  = new Front();
        Front aemmtMembers  = new Front();
        Front aemmdMembers  = new Front();

        problem.printResolutionMessage();
        for (int i = 0; i < 2; i++) {

            //nsgaii.runAlgorithm(problem);
            //nsgaiiMembers = nsgaii.paretto;

            //spea2.runAlgorithm(problem);
            //spea2Members = spea2.paretto;

            Constants.NUMBER_OF_GENERATIONS *= 100;

            //aemmt.runAlgorithm(problem);
            //aemmtMembers = aemmt.paretto;


            aemmd.runAlgorithm(problem);
            aemmdMembers = aemmd.paretto;

            Constants.NUMBER_OF_GENERATIONS /= 100;


        }


        Population allFrontsMembers = new Population();
        allFrontsMembers.population.addAll(spea2Members.membersAtThisFront);
        allFrontsMembers.population.addAll(aemmtMembers.membersAtThisFront);
        allFrontsMembers.population.addAll(aemmdMembers.membersAtThisFront);
        allFrontsMembers.population.addAll(nsgaiiMembers.membersAtThisFront);

        allFrontsMembers.fastNonDominatedSort();


        Problem.removeSimilar(allFrontsMembers.fronts.allFronts.get(0),problem);
        allFrontsMembers.population = allFrontsMembers.fronts.allFronts.get(0).membersAtThisFront;

        return allFrontsMembers;
    }



    private static void writeParettoFromProblem(){
        Population parettoOfAGS = getBestPossibleParettoOfAGS();
        try {
            Serializer.writeObject(fileName+parettoName+extension,parettoOfAGS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static Population readParettoFromFile(String fileName){
        Population paretto = new Population();
        try{
            paretto = Serializer.readObject(fileName);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return paretto;
    }

    public static void currentDirectory(){
        String curDir = System.getProperty("user.dir");
        File GradeList = new File("GradeList.txt");
        System.out.println("Current sys dir: " + curDir);
        System.out.println("Current abs dir: " + GradeList.getAbsolutePath());
    }



}
