package SPEA2;

/**
 * Created by gabrielm on 10/16/17.
 * Project : TCC.
 */
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import Fronts.Front;
import Population.*;
import Problems.Problem;
import Selections.Selection;
import Selections.SelectionArchive;
import SupportingFiles.Parameters;
import SupportingFiles.Printer;
import com.sun.org.apache.bcel.internal.generic.POP;
import org.apache.commons.math3.util.KthSelector;
import org.apache.commons.math3.util.Pair;



public class SPEAHADKA {

    Population population;
    private Selection selectedFitness = new SelectionArchive();
    Problem problem;
    public Front paretto = new Front();
    int genCounter = 0;

    public void runAlgorithm(Problem problem){
        this.problem = problem;
        population = new Population();
        population.population = problem.generateMembers(Parameters.POPULATION_SIZE);
        problem.evaluateAgainstObjectiveFunctions(population);


        while (genCounter < Parameters.NUMBER_OF_GENERATIONS){
            //System.out.println("GERACAO = "+ genCounter+"===========================================");//todo
            iterate();
            genCounter++;
        }


        Printer.printBinaryMembersWithAppliedFunctions(population);//todo

        saveParetto(problem);
        reset();
    }

    public void iterate(){
        Population offspring;


        offspring = problem.crossover.crossoverAndMutation(selectedFitness.selectParents(population));
        this.problem.evaluateAgainstObjectiveFunctions(offspring);

        offspring.population.addAll(this.population.deepCopy().population);
        Fitness.evaluate(offspring);
        this.population = new Population();


        offspring = EnvironmentalSelection.truncate(offspring);
        population.population.addAll(offspring.population);

    }

    private void reset(){
        genCounter = 0;
        population = new Population();
        selectedFitness = new SelectionArchive();
    }


    private void saveParetto(Problem problem){
        population.fastNonDominatedSort();
        paretto = population.getFirstFront();
        Problem.removeSimilar(paretto,problem);
    }
}
