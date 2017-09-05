package NSGAII;

import Fronts.Front;
import Population.Population;
import Problems.Problem;
import Selections.Selection;
import Selections.SelectionRank;
import Selections.SelectionRankCrowding;
import SupportingFiles.Constants;

/**
 * Created by gmarson on 12/20/2016.
 * TCC UFU
 */
public class NSGAII {

    private Front lastFrontToMergeWithPopulation = null;
    private int genCounter = 0;
    private Population union;
    public Population sortedUnion;
    private Population p = new Population();
    private Selection selectRanked = new SelectionRank();
    private Selection selectRankCrowded = new SelectionRankCrowding();

    public Front paretto = new Front();


    public void runAlgorithm(Problem problem)
    {
        p.population = problem.generateMembers(Constants.POPULATION_SIZE);
        problem.evaluateAgainstObjectiveFunctions(p);
        p.fastNonDominatedSort();
        Population selected = selectRanked.selectParents(p);
        Population children = problem.crossover.crossoverAndMutation(selected);

        sortedUnion = new Population();
        while(genCounter < Constants.NUMBER_OF_GENERATIONS)
        {
            //System.out.println("GERACAO = "+ genCounter+"===========================================");
            genCounter++;

            problem.evaluateAgainstObjectiveFunctions(children);
            union = p.mergeWithCurrentPopulation(children);
            union.fastNonDominatedSort();
            sortedUnion = new Population();

            int k=0;
            for(Front front: union.fronts.allFronts)
            {
                front.crowdingDistanceAssignment();
                if(sortedUnion.population.size() + union.fronts.allFronts.get(k).membersAtThisFront.size() > Constants.POPULATION_SIZE)
                {
                    lastFrontToMergeWithPopulation = front;
                    break;
                }
                else
                    sortedUnion.addFrontToPopulation(front);
                k++;
            }

            int i=lastFrontToMergeWithPopulation.membersAtThisFront.size()-1;
            while(sortedUnion.population.size() < Constants.POPULATION_SIZE)
            {
                sortedUnion.population.add(lastFrontToMergeWithPopulation.membersAtThisFront.get(i));
                i--;
            }

            selected = selectRankCrowded.selectParents(sortedUnion);
            p = sortedUnion;
            children = problem.crossover.crossoverAndMutation(selected);

        }


        saveParetto(problem);

        //problem.printResolutionMessage(); //todo
        //Problem.removeSimilar(sortedUnion.getFirstFront(),problem);//todo
        //Printer.printFirstFront(sortedUnion); //todo

        reset();
    }

    private void reset(){
        lastFrontToMergeWithPopulation = null;
        genCounter = 0;
        union = null;
        sortedUnion = null;
        p = new Population();
        selectRanked = new SelectionRank();
        selectRankCrowded = new SelectionRankCrowding();
    }

    private void saveParetto(Problem problem){
        sortedUnion.fastNonDominatedSort();
        paretto = sortedUnion.getFirstFront();
        Problem.removeSimilar(paretto,problem);
    }

}
