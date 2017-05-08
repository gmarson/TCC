import Constants.Constants;
import ManyObjective.TableFunctions.TableFunctions;
import PerformanceMetrics.Erro;
import PerformanceMetrics.ParetoSubset;
import Population.*;
import Problems.*;
import Utilities.*;
import NSGAII.*;
import Fronts.*;
import SPEA2.*;
import ManyObjective.*;

import javax.xml.bind.annotation.XmlElement;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


public class Main {

    private static String fileName = "KP_p-3_n-10_ins-1";
    private static String extension = ".dat";
    private static String parettoName = "Paretto";
    private static String directoryName ="KP/" ;
    public static String windowsPathRead = "MOEA/"+fileName+"Paretto.dat";
    public static String macPathRead = fileName+parettoName+extension;
    public static String macPathGetProblemFrom = "MOEA/"+directoryName+fileName+extension;
    private static ProgressBar progressBar;

    public static void main(String[] args) throws Exception {

        //spaceOfObjectives();

        //writeParettoFromProblem();

        normal();

    }

    private static void normal(){
        //Problem problem = new ProblemSCH();
        //Problem problem = new ProblemF2();
        //Problem problem = new ProblemKnapsack();
        Problem problem = new ProblemKnapsackFromFile(macPathGetProblemFrom);

        //NSGAII algorithm = new NSGAII();
        //SPEA2 algorithm = new SPEA2();
        //AEMMT algorithm = new AEMMT();
        //AEMMD algorithm = new AEMMD();
        MOEAD algorithm = new MOEAD();


        algorithm.runAlgorithm(problem);
    }

    private static void compareToParettoFront(){
        currentDirectory();
        Population parettoPopulation = readParettoFromFile(macPathRead);

        Problem problem = new ProblemKnapsackFromFile(macPathGetProblemFrom);

        NSGAII nsgaii = new NSGAII();
        SPEA2 spea2 = new SPEA2();
        AEMMT aemmt = new AEMMT();
        AEMMD aemmd = new AEMMD();
        MOEAD moead = new MOEAD();

        //nsgaii.runAlgorithm(problem);
        //spea2.runAlgorithm(problem);
        //moead.runAlgorithm(problem);

        Constants.NUMBER_OF_GENERATIONS *=100;
        //aemmt.runAlgorithm(problem);
        //aemmd.runAlgorithm(problem);

        //ParetoSubset metrics = new ParetoSubset(problem);
        Erro metrics = new Erro(problem);
        metrics.messageBeforeResult();
        Population newPopulation = new Population();

        if(!nsgaii.paretto.membersAtThisFront.isEmpty())
            newPopulation.population = nsgaii.paretto.membersAtThisFront;
        else if (!spea2.paretto.membersAtThisFront.isEmpty())
            newPopulation.population = spea2.paretto.membersAtThisFront;
        else if (!aemmt.paretto.membersAtThisFront.isEmpty())
            newPopulation.population = aemmt.paretto.membersAtThisFront;
        else if (!aemmd.paretto.membersAtThisFront.isEmpty())
            newPopulation.population = aemmd.paretto.membersAtThisFront;
        else if (!moead.paretto.membersAtThisFront.isEmpty())
            newPopulation.population = moead.paretto.membersAtThisFront;


        System.out.println(metrics.estimateBasedOnMetric(newPopulation,parettoPopulation));
    }

    //Do not call this
    private static Population  getBestPossibleParettoOfAGS(){
        int numberOfRounds = 10;

        NSGAII nsgaii = new NSGAII();
        SPEA2 spea2 = new SPEA2();
        AEMMT aemmt = new AEMMT();
        AEMMD aemmd = new AEMMD();
        MOEAD moead = new MOEAD();

        Problem problem = new ProblemKnapsackFromFile(macPathGetProblemFrom);

        Front nsgaiiMembers = new Front();
        Front spea2Members  = new Front();
        Front aemmtMembers  = new Front();
        Front aemmdMembers  = new Front();
        Front moeadMembers = new Front();

        progressBar = new ProgressBar((double) numberOfRounds);

        for (int i = 0; i < numberOfRounds; i++) {

            nsgaii.runAlgorithm(problem);
            nsgaiiMembers = nsgaii.paretto;

            spea2.runAlgorithm(problem);
            spea2Members = spea2.paretto;

            moead.runAlgorithm(problem);
            moeadMembers = moead.paretto;

            Constants.NUMBER_OF_GENERATIONS *= 100;

            aemmt.runAlgorithm(problem);
            aemmtMembers = aemmt.paretto;

            aemmd.runAlgorithm(problem);
            aemmdMembers = aemmd.paretto;

            Constants.NUMBER_OF_GENERATIONS /= 100;
            progressBar.addJobDone();

        }

        problem.printResolutionMessage();
        Population allFrontsMembers = new Population();
        allFrontsMembers.population.addAll(spea2Members.membersAtThisFront);
        allFrontsMembers.population.addAll(aemmtMembers.membersAtThisFront);
        allFrontsMembers.population.addAll(aemmdMembers.membersAtThisFront);
        allFrontsMembers.population.addAll(nsgaiiMembers.membersAtThisFront);
        allFrontsMembers.population.addAll(moeadMembers.membersAtThisFront);

        allFrontsMembers.fastNonDominatedSort();


        Problem.removeSimilar(allFrontsMembers.fronts.allFronts.get(0),problem);
        allFrontsMembers.population = allFrontsMembers.fronts.allFronts.get(0).membersAtThisFront;
        Printer.printMembersWithBinaryValue(allFrontsMembers);

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

    static void currentDirectory(){
        String curDir = System.getProperty("user.dir");
        File GradeList = new File("GradeList.txt");
        System.out.println("Current sys dir: " + curDir);
        System.out.println("Current abs dir: " + GradeList.getAbsolutePath());
    }



    static void spaceOfObjectives(){
        Population p = readParettoFromFile(macPathRead);
        Printer.printMembersWithAppliedFunctions(p);
    }

}
