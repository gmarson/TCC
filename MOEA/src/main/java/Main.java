import SupportingFiles.Parameters;
import PerformanceMetrics.Erro;
import PerformanceMetrics.GenerationalDistance;
import PerformanceMetrics.ParetoSubset;
import Population.*;
import Problems.*;
import SupportingFiles.*;
import NSGAII.*;
import SPEA2.*;
import ManyObjective.*;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;


public class Main {

    private static String fileName = "KP_p-2_n-100_ins-1";
    //private static String fileName = "KPTESTE";
    //private static String fileName = "KPTIAGO";
    private static String extension = ".dat";
    private static String paretoName = "Pareto";
    private static String directoryName ="KP/" ;
    public static String windowsPathRead = "MOEA/"+fileName+"Paretto.dat";
    private static String macPathRead = fileName+ paretoName +extension;
    private static String macPathGetProblemFrom = "MOEA/"+directoryName+fileName+extension;
    private static String pathToPareto = "MOEA/Pareto/";
    private static ProgressBar progressBar;

    public static void main(String[] args) throws Exception {

        //testLargeFile();

        //spaceOfObjectives();
        //writeParetoFromProblem();
        compareToParettoFront();
        //normal();

    }

    private static void normal(){
        //Problem problem = new ProblemSCH();
        //Problem problem = new ProblemF2();
        //Problem problem = new ProblemKnapsack();
        ProblemKnapsackFromFile problem = new ProblemKnapsackFromFile(macPathGetProblemFrom);

        //NSGAII algorithm = new NSGAII();
        SPEA2 algorithm = new SPEA2();
        //AEMMT algorithm = new AEMMT();
        //AEMMD algorithm = new AEMMD();
        //MOEAD algorithm = new MOEAD();

        int x =1;
        int counter = 0;

        if (AEMMD.class.isInstance(algorithm) || AEMMT.class.isInstance(algorithm)){
            Parameters.NUMBER_OF_GENERATIONS = 15000;
        }
        else{
            Parameters.NUMBER_OF_GENERATIONS = problem.items.size() < 100? 100 : 200;
        }

        while (counter < x) {
            algorithm.runAlgorithm(problem);
            counter++;
        }
    }

    private static void compareToParettoFront(){
        ProblemKnapsackFromFile problem = new ProblemKnapsackFromFile(macPathGetProblemFrom);

        Population parettoPopulation = readParetoFromFile(pathToPareto+fileName+paretoName);

        double error = 0;
        double gd = 0;
        double ps = 0;

        double turns = 5;

        for (int i = 0; i < (int)turns; i++) {
            NSGAII nsgaii = new NSGAII();
            SPEA2  spea2 = new SPEA2();
            AEMMT  aemmt = new AEMMT();
            AEMMD  aemmd = new AEMMD();
            MOEAD  moead = new MOEAD();
            AEMMTUNLIMITED aemmtunlimited = new AEMMTUNLIMITED();

            Parameters.NUMBER_OF_GENERATIONS = 200;
            //nsgaii.runAlgorithm(problem);

            spea2.runAlgorithm(problem);

            //moead.runAlgorithm(problem);

            Parameters.NUMBER_OF_GENERATIONS = 15000;

            aemmtunlimited.runAlgorithm(problem);
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
            else if (aemmt.paretto != null && !aemmt.paretto.membersAtThisFront.isEmpty())
                newPopulation.population = aemmt.paretto.membersAtThisFront;
            else if (aemmd.paretto != null && !aemmd.paretto.membersAtThisFront.isEmpty())
                newPopulation.population = aemmd.paretto.membersAtThisFront;
            else if (aemmtunlimited.pareto != null && !aemmtunlimited.pareto.membersAtThisFront.isEmpty())
                newPopulation.population = aemmtunlimited.pareto.membersAtThisFront;
            else if (!moead.pareto.membersAtThisFront.isEmpty())
                newPopulation.population = moead.pareto.membersAtThisFront;


            paretoSubset.estimateBasedOnMetric(newPopulation,parettoPopulation);
            paretoSubset.messageAfterProcess();

            ps += paretoSubset.result;

            erro.estimateBasedOnMetric(newPopulation,parettoPopulation);
            erro.messageAfterProcess();
            error += erro.result;

            generationalDistance.estimateBasedOnMetric(newPopulation,parettoPopulation);
            generationalDistance.messageAfterProcess();
            gd += generationalDistance.result;
        }

        System.out.println("erro: "+error/turns+"\ngd: "+gd/turns+"\nps:"+ps/turns);


    }

    private static void writeParetoFromProblem(){
        Population paretoOfAGS = getBestPossibleParettoOfAGS();
        try {
            Serializer.writeToFile(pathToPareto+fileName+"Pareto",paretoOfAGS);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        ProblemKnapsackFromFile problem = new ProblemKnapsackFromFile(macPathGetProblemFrom);
        progressBar = new ProgressBar((double) numberOfRounds);

        for (int i = 0; i < numberOfRounds; i++) {

            Parameters.NUMBER_OF_GENERATIONS = problem.items.size() < 100? 100 : 200;

            System.out.println("NSGAII");
            nsgaii.runAlgorithm(problem);
            allFrontsMembers.population.addAll(nsgaii.paretto.membersAtThisFront);

            System.out.println("SPEA2");
            spea2.runAlgorithm(problem);
            allFrontsMembers.population.addAll(spea2.paretto.membersAtThisFront);

            //moead.runAlgorithm(problem);
            //allFrontsMembers.population.addAll( moead.pareto.membersAtThisFront);

            Parameters.NUMBER_OF_GENERATIONS = 15000;
            System.out.println("AEMMT");
            aemmt.runAlgorithm(problem);
            allFrontsMembers.population.addAll(aemmt.paretto.membersAtThisFront);

            System.out.println("AEMMD");
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

    private static Population readParetoFromFile(String fileName){
        Population pareto = new Population();

        if(Parameters.PROBLEM_SIZE == -1) {
            System.out.println("Problem size not defined!");
            return null;
        }

        try{
            pareto = Serializer.readFromFile(fileName);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return pareto;
    }

    static void currentDirectory(){
        String curDir = System.getProperty("user.dir");
        File GradeList = new File("GradeList.txt");
        System.out.println("Current sys dir: " + curDir);
        System.out.println("Current abs dir: " + GradeList.getAbsolutePath());
    }


    static void spaceOfObjectives(){
        Population p = readParetoFromFile(macPathRead);
        Printer.printMembersWithAppliedFunctions(p);
    }

    static void testLargeFile() throws IOException {
        Population p  = new Population();
        Problem problem = new ProblemKnapsackFromFile(macPathGetProblemFrom);
        ArrayList<Member> members = problem.generateMembers(200000);
        p.population = members;
        problem.evaluateAgainstObjectiveFunctions(p);
        Serializer.writeToFile("testFile",p);
        Serializer.readFromFile("testFile");
    }

}
