package ManyObjective.MOEADFunctions;

import Constants.Constants;
import Dominance.Dominance;
import ManyObjective.MOEAD;
import Population.*;
import Problems.Problem;
import Selections.SelectionNeighborhood;
import Utilities.Matrix;
import Utilities.Utils;

import javax.rmi.CORBA.Util;
import java.util.ArrayList;

/**
 * Created by gabrielm on 06/05/17.
 */
public class MOEADFunctions {

    public static int genCounter = 0;

    private static Member generateChildrenGivenNeighborhood(ArrayList<Member> parentNeighborhood, Problem problem){

        Population parentsPopulation = SelectionNeighborhood.selectParents(parentNeighborhood);

        Population childPopulation = problem.crossover.crossoverAndMutation(parentsPopulation);

        problem.evaluateAgainstObjectiveFunctions(childPopulation);

        return childPopulation.population.get(Utils.getRandom(0,2));
    }

    public static void mainLoop(Population population, Problem problem){

        while (genCounter < Constants.NUMBER_OF_GENERATIONS){

            System.out.println("GEN = "+genCounter);//todo

            for (Member cell: population.population){

                Member child = generateChildrenGivenNeighborhood(cell.neighborhood, problem);
                addToNonDominatedPopulation(child, problem);
                updateCell(cell,child);
                updateNeighborhood(cell.neighborhood,child);
            }


            genCounter++;
        }
    }

    private static void updateCell(Member cell, Member child){

        MOEAD.scalarization.calculateFitness(child,cell.weightVector.vector);
        if(child.fitness < cell.fitness){
            replaceMember(cell,child);
        }
    }

    private static void updateNeighborhood(ArrayList<Member> neighborhood, Member child) {

        for(Member neighborhoodMember : neighborhood){

            MOEAD.scalarization.calculateFitness(child,neighborhoodMember.weightVector.vector);

            if(child.fitness < neighborhoodMember.fitness){
                replaceMember(neighborhoodMember,child);
            }
        }
    }

    private static void replaceMember(Member neighborhoodMember, Member child){
        neighborhoodMember.binaryValue = child.binaryValue;
        neighborhoodMember.resultOfFunctions = child.resultOfFunctions;
        neighborhoodMember.value = child.value;
        neighborhoodMember.fitness = child.fitness;
    }

    private static void addToNonDominatedPopulation(Member member, Problem problem){

        Dominance dominance = new Dominance();
        ArrayList<Member> toBeRemoved = new ArrayList<>();
        boolean shouldAddNewMember = true;

        if (Problem.valueOfMemberIsPresent(member,MOEAD.archive,problem)) return;

        for (Member paretoMember : MOEAD.archive.population){

            if (dominance.dominates(member,paretoMember)){
                toBeRemoved.add(paretoMember);
            }

            if (dominance.dominates(paretoMember,member)){
                shouldAddNewMember = false;
            }
        }

        MOEAD.archive.population.removeAll(toBeRemoved);
        if (shouldAddNewMember){
            MOEAD.archive.addMember(member);

        }
    }


    public static class NeighborhoodSettings {

        public static void setNeighboursForAllMembers(Matrix neighborhoods) {

            for (int i = 0; i < neighborhoods.rows ; i++) {
                for (int j = i; j < neighborhoods.rows; j++) {

                    addOrdered(i,j,neighborhoods);
                    addOrdered(j,i,neighborhoods);
                }
            }

            //TODO fazer um print pra ver se as distancia euclidianas estão em ordem

        }

        private static void addOrdered(int cellIndex, int neighbourCandidateIndex, Matrix neighborhoods){

            Member[] neighborhood = neighborhoods.memberMatrix[cellIndex];
            Member cell = neighborhood[0];
            Member child = neighborhoods.memberMatrix[neighbourCandidateIndex][0];
            double candidateDistance = Utils.euclideanDistanceBasedOnWeightVector(cell,child);
            if (candidateDistance == 0.0) return;


            int indexToInsert;
            for (indexToInsert = 1; indexToInsert < Constants.NEIGHBOURHOOD_SIZE - 1; indexToInsert++) {


                if (neighborhood[indexToInsert] == null){
                    neighborhood[indexToInsert] = child;
                    return;
                }
                else
                {
                    if (candidateDistance < Utils.euclideanDistanceBasedOnWeightVector(neighborhood[indexToInsert],neighborhood[0])){
                        break;
                    }
                }

            }

            for (int i = Constants.NEIGHBOURHOOD_SIZE -1; i > indexToInsert + 1; i--) {
                neighborhood[i] = neighborhood[i - 1];
            }
            neighborhood[indexToInsert] = child;


        }
    }

}
