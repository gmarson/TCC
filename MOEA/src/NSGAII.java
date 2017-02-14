import java.util.ArrayList;

/**
 * Created by gmarson on 12/20/2016.
 * TCC UFU
 */
public class NSGAII {

    public void runAlgorithm(Problem problem)
    {
       
        Front lastFrontToMergeWithPopulation = null;
        int genCounter = 0;
        Population union, sortedUnion;
        Population p = new Population();

        Selection selectRanked = new SelectionRank();
        Selection selectRankCrowded = new SelectionRankCrowding();

        p.population = problem.generateRandomMembers();
        problem.evaluateAgainstObjectiveFunctions(p);
        p.fastNonDominatedSort();

        Population selected = selectRanked.selectParents(p);
        Population children = problem.crossover.crossoverAndMutation(selected);

        sortedUnion = new Population();
        while(genCounter < Constants.NUMBER_OF_GENERATIONS)
        {
            System.out.println("GERACAO = "+ genCounter+"===========================================");
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

        sortedUnion.fastNonDominatedSort();


        sortedUnion.fronts.printFirstFrontOccurances();
    }

}
