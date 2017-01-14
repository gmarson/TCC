import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by gabrielm on 09/01/17.
 */
public class SPEA2 {

    Matrix distanceMatrix = new Matrix(Constants.POPULATION_SIZE, Constants.POPULATION_SIZE);
    Scanner s = new Scanner(System.in);

    public void runAlgorithm(){
        int genCounter = 0;
        Population p = new Population();
        Problem problem = new ProblemSCH();
        Front archive = new Front();
        Population union = new Population();

        p.population = problem.generateRandomMembers();

        while(genCounter < Constants.NUMBER_OF_GENERATIONS)
        {
            problem.evaluateAgainstObjectiveFunctions(p);
            union.mergePopulationWithFront(p,archive);
            union.fastNonDominatedSort();
            calculateFitness(union);

            archive = union.getNonDominatedFront();
            //todo verificar se os numeros estao em ordem de dominancia

            genCounter++;

            prepareForNextGen();
        }
    }

    public void prepareForNextGen()
    {
        this.distanceMatrix = new Matrix(Constants.DISTANCE_MATRIX_SIZE, Constants.DISTANCE_MATRIX_SIZE);
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

    public void calculateDensity(Member member, Population union, int indexOfMatrix)
    {
        calculateDistanceBetweenMembers(member,union, indexOfMatrix);
        double sigma = calculateSigma(indexOfMatrix);
    }

    public void calculateDistanceBetweenMembers(Member member,Population union,int indexOfMatrix)
    {
        //todo ver se o membro no indice da matriz eh o mesmo membro do que foi passado por paramentro
        Member mi = union.population.get(indexOfMatrix), mj;
        for (int j = 0; j < distanceMatrix.distance[0].length; j++)
        {
            mj = union.population.get(j);
            distanceMatrix.distance[indexOfMatrix][j] = Utils.euclidianDistance(mi,mj);
        }
    }

    public double calculateSigma(int indexOfMatrix)
    {
        int positionOfSigma = (int) Math.floor(Math.sqrt((double)distanceMatrix.columns));
        ArrayList<Double> orderedMatrixRow = Utils.returnOrderedArray(distanceMatrix, indexOfMatrix);
        return orderedMatrixRow.get(positionOfSigma);
    }

    public void calculateFitness(Population union)
    {
        int indexOfMatrix=0;
        for(Member member: union.population)
        {
            calculateStrength(member);
            calculateRawFitness(member, union);
            calculateDensity(member, union, indexOfMatrix);
            member.fitness = member.rawFitness + member.density;
            indexOfMatrix++;
        }
    }


}
