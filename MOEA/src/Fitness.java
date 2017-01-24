import java.util.ArrayList;

/**
 * Created by gabrielm on 16/01/17.
 */
public abstract class Fitness {

    static Matrix distanceMatrix = new Matrix(Constants.POPULATION_SIZE, Constants.POPULATION_SIZE);
    
    public static void calculateStrength(Member member)
    {
        member.strength = member.solutionsThatThisMemberDominates.size();
    }

    public static void calculateRawFitness(Member memberToBeEvaluated, Population union)
    {
        for (Member member: union.population)
        {
            if(member.solutionsThatThisMemberDominates.contains(memberToBeEvaluated))
                memberToBeEvaluated.rawFitness += member.strength;
        }
    }

    public static void calculateDensity(Member member, Population generic, int indexOfMatrix)
    {
        calculateDistanceBetweenMembers(member,generic,indexOfMatrix);
        double sigma = calculateSigma(indexOfMatrix);
        member.density = 1 / (sigma + 2);

    }

    public static void calculateDistanceBetweenMembers(Member member,Population generic,int indexOfMatrix)
    {
        Member mi = generic.population.get(indexOfMatrix), mj;

        for (int j = 0; j < distanceMatrix.distance[0].length; j++)
        {
            if(j > indexOfMatrix) break; // todo fiz essa linha pensando em otimzar. Vamos ver se dá.
            mj = generic.population.get(j);
            
            distanceMatrix.distance[indexOfMatrix][j] = Utils.euclidianDistance(mi,mj);
        }
        
    }

    public static double calculateSigma(int indexOfMatrix)
    {
        int positionOfSigma = (int) Math.floor(Math.sqrt((double)distanceMatrix.columns));
        ArrayList<Double> orderedMatrixRow = Utils.returnOrderedArray(distanceMatrix, indexOfMatrix);
        return orderedMatrixRow.get(positionOfSigma);
    }

    public static void calculateFitness(Population union)
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


    public static void prepareForNextGen()
    {
        distanceMatrix = new Matrix(Constants.DISTANCE_MATRIX_SIZE, Constants.DISTANCE_MATRIX_SIZE);
    }

}
