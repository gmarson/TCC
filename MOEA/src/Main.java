import Constants.Constants;
import PerformanceMetrics.Erro;
import PerformanceMetrics.GenerationalDistance;
import PerformanceMetrics.InvertedError;
import PerformanceMetrics.ParetoSubset;
import Population.*;
import Problems.*;
import Utilities.*;
import NSGAII.*;
import Fronts.*;
import SPEA2.*;
import ManyObjective.*;

import java.io.File;


public class Main {

    private static String fileName = "KP_p-3_n-50_ins-2";
    //private static String fileName = "KPTESTE";
    //private static String fileName = "KPTIAGO";
    private static String extension = ".dat";
    private static String parettoName = "Paretto";
    private static String directoryName ="KP/" ;
    public static String windowsPathRead = "MOEA/"+fileName+"Paretto.dat";
    private static String macPathRead = fileName+parettoName+extension;
    private static String macPathGetProblemFrom = "MOEA/"+directoryName+fileName+extension;
    private static ProgressBar progressBar;

    public static void main(String[] args) throws Exception {

        //spaceOfObjectives();
        //writeParettoFromProblem();
        //compareToParettoFront();
        normal();

    }


    private static void normal(){
        //Problem problem = new ProblemSCH();
        //Problem problem = new ProblemF2();
        //Problem problem = new ProblemKnapsack();
        Problem problem = new ProblemKnapsackFromFile(macPathGetProblemFrom);

        //NSGAII algorithm = new NSGAII();
        //SPEA2 algorithm = new SPEA2();
        AEMMT algorithm = new AEMMT();
        //AEMMD algorithm = new AEMMD();
        //MOEAD algorithm = new MOEAD();

        int x =1;
        int counter = 0;

        if (AEMMD.class.isInstance(algorithm) || AEMMT.class.isInstance(algorithm)){
            Constants.NUMBER_OF_GENERATIONS = 30000;
        }

        while (counter < x) {
            algorithm.runAlgorithm(problem);
            counter++;
        }

    }

    private static void compareToParettoFront(){

        Population parettoPopulation = readParettoFromFile(macPathRead);


        Problem problem = new ProblemKnapsackFromFile(macPathGetProblemFrom);

        NSGAII nsgaii = new NSGAII();
        SPEA2 spea2 = new SPEA2();
        AEMMT aemmt = new AEMMT();
        AEMMD aemmd = new AEMMD();
        MOEAD moead = new MOEAD();

        //nsgaii.runAlgorithm(problem);
        //spea2.runAlgorithm(problem);
        moead.runAlgorithm(problem);

        Constants.NUMBER_OF_GENERATIONS = 30000;
        //aemmt.runAlgorithm(problem);
        //aemmd.runAlgorithm(problem);


        Erro erro = new Erro(problem);
        ParetoSubset paretoSubset = new ParetoSubset(problem);
        GenerationalDistance generationalDistance = new GenerationalDistance(problem);

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

        erro.estimateBasedOnMetric(newPopulation,parettoPopulation);
        erro.messageAfterProcess();

        paretoSubset.estimateBasedOnMetric(newPopulation,parettoPopulation);
        paretoSubset.messageAfterProcess();

        generationalDistance.estimateBasedOnMetric(newPopulation,parettoPopulation);
        generationalDistance.messageAfterProcess();


    }

    //Do not call this
    private static Population  getBestPossibleParettoOfAGS(){
        int numberOfRounds = 10;
        Population allFrontsMembers = new Population();

        NSGAII nsgaii = new NSGAII();
        SPEA2 spea2 = new SPEA2();
        AEMMT aemmt = new AEMMT();
        AEMMD aemmd = new AEMMD();
        MOEAD moead = new MOEAD();

        Problem problem = new ProblemKnapsackFromFile(macPathGetProblemFrom);
        progressBar = new ProgressBar((double) numberOfRounds);

        for (int i = 0; i < numberOfRounds; i++) {

            Constants.NUMBER_OF_GENERATIONS = 400;

            nsgaii.runAlgorithm(problem);
            allFrontsMembers.population.addAll(nsgaii.paretto.membersAtThisFront);

            spea2.runAlgorithm(problem);
            allFrontsMembers.population.addAll(spea2.paretto.membersAtThisFront);

            moead.runAlgorithm(problem);
            allFrontsMembers.population.addAll( moead.paretto.membersAtThisFront);

            Constants.NUMBER_OF_GENERATIONS = 30000;
            aemmt.runAlgorithm(problem);
            allFrontsMembers.population.addAll(aemmt.paretto.membersAtThisFront);

            aemmd.runAlgorithm(problem);
            allFrontsMembers.population.addAll(aemmd.paretto.membersAtThisFront);

            progressBar.addJobDone();

            allFrontsMembers.fastNonDominatedSort();
            Problem.removeSimilar(allFrontsMembers.fronts.allFronts.get(0),problem);
            allFrontsMembers.population = allFrontsMembers.fronts.allFronts.get(0).membersAtThisFront;


        }

        problem.printResolutionMessage();
        //Printer.printBinaryMembers(allFrontsMembers);
        System.out.println("ALL FRONTS SIZE: "+allFrontsMembers.population.size());

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
