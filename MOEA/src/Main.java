import Constants.Constants;
import ManyObjective.TableFunctions.TableFunctions;
import Population.*;
import Problems.*;
import Utilities.*;
import NSGAII.*;
import Fronts.*;
import SPEA2.*;
import ManyObjective.*;

import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {


        getBestPossibleParettoOfAGS();

        //Problem sch = new ProblemSCH();
        //Problem f2 = new ProblemF2();
        //Problem knapsack = new ProblemKnapsack();

        //Problem knapsackFromFile = new ProblemKnapsackFromFile("KP/KP_p-3_n-10_ins-1.dat");


        //NSGAII nsgaii = new NSGAII();
        //nsgaii.runAlgorithm(knapsackFromFile);

        //SPEA2 spea2 = new SPEA2();
        //spea2.runAlgorithm(knapsackFromFile);

        //AEMMT aemmt = new AEMMT();
        //aemmt.runAlgorithm(knapsackFromFile);

        //AEMMD aemmd = new AEMMD();
        //aemmd.runAlgorithm(knapsackFromFile);

    }


    static ArrayList<Front>  getBestPossibleParettoOfAGS(){
        NSGAII nsgaii = new NSGAII();
        SPEA2 spea2 = new SPEA2();
        AEMMT aemmt = new AEMMT();

        Problem knapsackFromFile = new ProblemKnapsackFromFile("KP/KP_p-3_n-10_ins-1.dat");
        knapsackFromFile.printResolutionMessage();

        Population spea2Paretto = new Population();
        Population nsgaiiParetto = new Population();
        Population aemmtParetto = new Population();

        for (int i = 0; i < 2; i++) {

            nsgaii.runAlgorithm(knapsackFromFile);
            ArrayList<Member> nsgaiiMembers = nsgaii.sortedUnion.getFirstFrontMembers();
            nsgaiiParetto.population.addAll(nsgaiiMembers);

            spea2.runAlgorithm(knapsackFromFile);
            ArrayList<Member> spea2Members = spea2.archive.getFirstFrontMembers(); //todo tem que ver se o archive ja nao a fronteira
            spea2Paretto.population.addAll(spea2Members);

            Constants.NUMBER_OF_GENERATIONS *= 100;

            aemmt.runAlgorithm(knapsackFromFile);
            ArrayList<Member> aemmtMembers = TableFunctions.tables.get(TableFunctions.tables.size()-1).pop.population;
            aemmtParetto.population.addAll(aemmtMembers);

            Constants.NUMBER_OF_GENERATIONS /= 100;


        }


        return null;
    }

    void buildParettoUsingAlgorithms(ArrayList<Front> separetedParettoFronts){

    }

    private void testFunction() {
        Population pop = new Population();
        Member m1, m2, m3, m4, m5, m6;

        m1 = new Member(0);
        m1.fitness = 0;
        m2 = new Member(1);
        m2.fitness = 1;
        m3 = new Member(2);
        m3.fitness = 2;
        m4 = new Member(-3);
        m4.fitness = -3;
        m5 = new Member(4);
        m5.fitness = 4;
        m6 = new Member(5);
        m6.fitness = 5;

        pop.population.add(m2);
        pop.population.add(m1);
        pop.population.add(m5);
        pop.population.add(m3);
        pop.population.add(m6);
        pop.population.add(m4);

        Sorts.quickSortMembersByKey(pop, "fitness");

        Printer.printMembersWithFitness(pop);
    }


    public void writeAndRead(){

        //TIRADO DO AEMMT
        /*try {
            Serializer.writeObject("AEMMT",p);
        }
        catch (Exception e){
            e.printStackTrace();
        }


        Population teste = new Population();
        try{
            teste = Serializer.readObject("AEMMT");
        }
        catch (Exception e){
            e.printStackTrace();
        }*/
    }


}
