/**
 * Created by gmarson on 12/20/2016.
 * TCC UFU
 */
public class NSGAII {

    protected static int POPULATION_SIZE =100;
    protected static int MUTATION_RATE = 5;
    protected static double CROSSOVER_RATE = 1;
    protected static double NUMBER_OF_GENERATIONS = 25;
    protected static int SEED = 1; //for test only

    public void runAlgorithm()
    {
        Front lastFrontToMergeWithPopulation = null;
        int genCounter = 0;
        Population union, sortedUnion;
        Population p = new Population();
        ProblemSCH problem = new ProblemSCH();
        Crossover binaryCrossover = new BinaryCrossover();

        p.population = p.problem.generateRandomMembers();
        problem.evaluateAgainstObjectiveFunctions(p);
        p.fastNonDominatedSort();

        Population selected = Selection.selectParentsByRank(p);
        Population children = binaryCrossover.crossoverAndMutation(selected);

        sortedUnion = new Population();
        while(genCounter < NUMBER_OF_GENERATIONS)
        {
            genCounter++;
            problem.evaluateAgainstObjectiveFunctions(children);
            union = p.mergeWithCurrentPopulation(children);
            union.fastNonDominatedSort();
            sortedUnion = new Population();

            int k=0;
            for(Front front: union.fronts.allFronts)
            {
                front.crowdingDistanceAssignment();
                if(sortedUnion.population.size() + union.fronts.allFronts.get(k).membersAtThisFront.size() > POPULATION_SIZE)
                {
                    lastFrontToMergeWithPopulation = front;
                    break;
                }
                else
                    sortedUnion.addFrontToPopulation(front);


                k++;
            }

            int i=lastFrontToMergeWithPopulation.membersAtThisFront.size()-1;
            while(sortedUnion.population.size() < POPULATION_SIZE)
            {
                sortedUnion.population.add(lastFrontToMergeWithPopulation.membersAtThisFront.get(i));
                i--;

            }


            selected = Selection.selectParentsByRankAndCrowding(sortedUnion);
            p = sortedUnion;
            children = binaryCrossover.crossoverAndMutation(selected);

        }

        sortedUnion.fastNonDominatedSort();
        System.out.println(sortedUnion.fronts.returnFirstFrontOccurances());

    }

}
