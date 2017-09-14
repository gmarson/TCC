package SPEA2;

import SupportingFiles.Parameters;
import Population.*;
import SupportingFiles.*;

import java.util.ArrayList;

/**
 * Created by gabrielm on 16/01/17.
 */
public abstract class Fitness {

    static Matrix distanceMatrix = new Matrix(Parameters.POPULATION_SIZE, Parameters.POPULATION_SIZE);

    private static void calculateStrength(Member member)
    {
        member.strength = member.solutionsThatThisMemberDominates.size();
    }

    private static void calculateRawFitness(Member memberToBeEvaluated, Population union)
    {
        for (Member member: union.population) {
            if (member.solutionsThatThisMemberDominates.contains(memberToBeEvaluated)){
                memberToBeEvaluated.rawFitness += member.strength;
            }
        }
    }

    private static void calculateDensity(Member member, Population generic, int indexOfMatrix)
    {
        calculateDistanceBetweenMembers(generic,indexOfMatrix);
        member.sigma = calculateSigma(indexOfMatrix);

        member.density = 1.0 / (member.sigma + 2.0);
    }

    static void calculateDistanceBetweenMembers(Population generic,int indexOfMatrix)
    {
        Member mi = generic.population.get(indexOfMatrix), mj;
       
        for (int j = 0; j < distanceMatrix.distance[0].length; j++)
        { 
            mj = generic.population.get(j);
            distanceMatrix.distance[indexOfMatrix][j] = j != indexOfMatrix ? Utils.euclidianDistance(mi,mj) : 0;
        }

    }

    private static double calculateSigma(int indexOfMatrix)
    {
        int positionOfSigma = (int) Math.floor(Math.sqrt((double)distanceMatrix.columns));
        ArrayList<Double> orderedMatrixRow = Utils.returnOrderedArray(distanceMatrix, indexOfMatrix);
        return orderedMatrixRow.get(positionOfSigma);
    }

    static void calculateFitness(Population union)
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

    static void eraseMatrix(){
        distanceMatrix = new Matrix(Parameters.POPULATION_SIZE, Parameters.POPULATION_SIZE);
    }

    static void buildMatrixFromEnvironment(Population environment)
    {
        int size = environment.population.size();
        distanceMatrix = new Matrix(size,size);
    }

    static void prepareForNextGen()
    {
        distanceMatrix = new Matrix(Parameters.DISTANCE_MATRIX_SIZE, Parameters.DISTANCE_MATRIX_SIZE);
    }

    static void copyDistancesFromMatrixToMembers(Population p)
    {
        int indexOfMatrix = 0;
        for (Member member: p.population ) {
            for(int i=0;i<distanceMatrix.rows; i++)
                if (indexOfMatrix != i) {
                    Utils.insertDataOnCrescentOrderedArray(distanceMatrix.distance[indexOfMatrix][i],member.distances);
                }

            indexOfMatrix++;
        }
    }


}
