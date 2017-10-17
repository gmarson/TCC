package SPEA2;

import SupportingFiles.Parameters;
import Population.*;
import SupportingFiles.*;
import com.sun.org.apache.bcel.internal.generic.POP;
import org.apache.commons.math3.util.KthSelector;

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
    //

    public static void evaluate(Population population){
        int[] strength = new int[population.size()];
        double[] fitness = new double[population.size()];
        //ao chegar nessa função a pop ja tem que estar com as relações de dominancia estabelecidas


        //strength
        for (int i = 0; i < population.size(); i++) {
            strength[i] = population.population.get(i).solutionsThatThisMemberDominates.size();
        }

        //raw fitness
        for (int i = 0; i < population.size()-1; i++) {
            for (int j = 0; j < population.size(); j++) {
                int comparison = compare(population.population.get(i),population.population.get(j));

                if (comparison < 0){
                    fitness[j] += strength[i];
                }
                else if(comparison > 0 ){
                    fitness[i] += strength[j];
                }
            }
        }

        //density
        Matrix distances =  new Matrix();
        distances.computeDistanceMatrix(population);
        KthSelector selector = new KthSelector();
        for (int i = 0; i < population.size(); i++) {
            // k-nearest member
            double kdist = selector.select(distances.distance[i],null,1);
            fitness[i] += 1.0 / (kdist + 2.0);
        }

        for (int i = 0; i < population.size(); i++) {
            population.population.get(i).fitness = fitness[i];
        }


    }


    private static int compare(Member mi, Member mj){
        if (mi.solutionsThatThisMemberDominates.contains(mj)) {
            return 1;
        }
        else if (mj.solutionsThatThisMemberDominates.contains(mi)){
            return -1;
        }
        else{
            return 0;
        }
    }




}
