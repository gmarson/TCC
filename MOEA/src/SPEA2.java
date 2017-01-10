/**
 * Created by gabrielm on 09/01/17.
 */
public class SPEA2 {

    protected static int POPULATION_SIZE =100;
    protected static int ARCHIVE_SIZE = 10;
    protected static int MUTATION_RATE = 5;
    protected static double CROSSOVER_RATE = 1;
    protected static double NUMBER_OF_GENERATIONS = 25;
    protected static int SEED = 1; //for test only

    public void runAlgorithm(){
        int genCounter = 0;
        Population p = new Population();
        Problem problem = new ProblemSCH();
        Front archive = new Front();
        Population union = new Population();

        p.population = problem.generateRandomMembers();

        while(genCounter < NUMBER_OF_GENERATIONS)
        {
            problem.evaluateAgainstObjectiveFunctions(p);
            union.mergePopulationWithFront(p,archive);
            union.fastNonDominatedSort();

            archive = union.getNonDominatedFront();


            genCounter++;
        }
    }


    public void calculateStrength(Member member)
    {
        member.strength = member.solutionsThatThisMemberDominates.size();
    }

    public void calculateRawFitness(Member memberToBeEvaluated, Population union)
    {
        for (Member member: union.population)
        {
            if(member.solutionsThatThisMemberDominates.contains(memberToBeEvaluated))
                memberToBeEvaluated.rawFitness += member.strength;
        }
    }

    public void calculateDensity(Member member, Population union)
    {
        calculateDistanceBetweenMembers(member,union);
    }

    public void calculateDistanceBetweenMembers(Member member,Population union)
    {
        //todo calcular distancia aki
    }

    public void calculateFitness(Population union)
    {
        for(Member member: union.population)
        {
            calculateStrength(member);
            calculateRawFitness(member, union);
            calculateDensity(member, union);        //todo implementar
            member.fitness = member.rawFitness + member.density;
        }
    }


}
