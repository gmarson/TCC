package Problems;

import SupportingFiles.Parameters;
import Crossover.CrossoverUniformKnapsack;
import Population.*;
import SupportingFiles.ProblemReader;
import SupportingFiles.Utils;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by gabrielm on 04/04/17.
 */
public class ProblemKnapsackFromFile  extends  Problem{

    public  ArrayList<KnapsackItem> items = new ArrayList<>();

    public ProblemKnapsackFromFile(String file){
        crossover = new CrossoverUniformKnapsack();

        try {
            ProblemReader.getProblemFromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Parameters.PROBLEM_SIZE = ProblemReader.numberOfObjectives;
        Parameters.QTD_ITEMS = ProblemReader.numberOfItems;
        Parameters.BAG_CAPACITY = ProblemReader.totalWeight;
        this.buildItemsFromFile();
        Parameters.MUTATION_RATE = 2/items.size();

    }

    @Override
    public void evaluateAgainstMask(Population p, int[] mask){
        for (int i = 0; i < p.population.size(); i++) {
            Member member = p.population.get(i);
            this.applyFunctionsGivenMask(member,mask);
        }
    }


    @Override
    public void evaluateAgainstObjectiveFunctions(Population p) {
        for (int i = 0; i < p.population.size(); i++) {
            Member member = p.population.get(i);
            this.applyFunctions(member);
        }
    }

    @Override
    public ArrayList<Member> generateMembers(int QtdMembers) {
        ArrayList<Member> population =  new ArrayList<>();
        for (int i = 0; i < QtdMembers; i++) {
            population.add(new Member(Utils.getRandomBinaryArray(Parameters.QTD_ITEMS)));
        }

        return population;
    }

    @Override
    public void printResolutionMessage() {
        System.out.println("Dado os seguintes itens: ");
        this.printItems();
        System.out.println("Os melhores indivíduos foram ");
    }

    @Override
    double secondFunction(Member member) {
        return 0;
    }

    @Override
    double firstFunction(Member member) {
        return 0;
    }

    @Override
    public void applyFunctions(Member member) {
        if(Parameters.PROBLEM_SIZE <2) return ;

        member.resultOfFunctions = new ArrayList<>();

        for (int i = 0; i < Parameters.PROBLEM_SIZE; i++) {

            double functionToBeInserted = 0.0;
            for (int j = 0; j < Parameters.QTD_ITEMS; j++)
            {
                if (member.binaryValue.get(j) == 1)
                {
                    functionToBeInserted += this.items.get(j).attributes.get(i);
                }
            }
            if( functionToBeInserted == 0) functionToBeInserted = 0.1;

            functionToBeInserted = calculateWeightGivenMember(member) > Parameters.BAG_CAPACITY? (2.0) : (-1 * functionToBeInserted);
            member.resultOfFunctions.add(functionToBeInserted);
        }
    }

    @Override
    public void applyFunctionsGivenMask(Member member , int[] mask){
        if (mask.length == 0){
            applyFunctions(member);
            return;
        }

        if(Parameters.PROBLEM_SIZE <2) return ;
        member.resultOfFunctions = new ArrayList<>();

        for (int i = 0; i < mask.length; i++) {

            double functionToBeInserted = 0.0;
            for (int j = 0; j < Parameters.QTD_ITEMS; j++)
            {
                if (member.binaryValue.get(j) == 1)
                {
                    functionToBeInserted += this.items.get(j).attributes.get(mask[i] - 1);
                }
            }
            if( functionToBeInserted == 0) functionToBeInserted = 0.1;

            functionToBeInserted = calculateWeightGivenMember(member) > Parameters.BAG_CAPACITY? (2.0) : (-1 * functionToBeInserted);
            member.resultOfFunctions.add(functionToBeInserted);
        }
    }

    private void buildItemsFromFile(){
        ArrayList<Double> objectivesOfItem;
        double weight;

        for (int i = 0; i < Parameters.QTD_ITEMS; i++) {
            objectivesOfItem = new ArrayList<>();

            for (int j = 0; j < Parameters.PROBLEM_SIZE; j++) {
                int offSet = j * Parameters.QTD_ITEMS;
                objectivesOfItem.add(ProblemReader.objectives.get( i + offSet));
            }

            weight = ProblemReader.weights.get(i);

            items.add(new KnapsackItem(objectivesOfItem, weight));
        }
    }

    public void printItems()
    {
        int i = 0;
        for(KnapsackItem item: items)
        {
            System.out.println("Item "+i);
            item.printItem();
            i++;
        }
    }

    private double calculateWeightGivenMember(Member member)
    {
        double totalWeight = 0.0;
        for (int i = 0; i < Parameters.QTD_ITEMS; i++) {
            if (member.binaryValue.get(i) == 1)
            {
                totalWeight += items.get(i).weight;
            }
        }
        return totalWeight;
    }


}
