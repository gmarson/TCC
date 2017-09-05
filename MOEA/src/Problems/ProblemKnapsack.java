package Problems;

import Crossover.CrossoverUniformKnapsack;
import Population.*;
import SupportingFiles.Constants;
import SupportingFiles.Utils;

import java.util.ArrayList;

/**
 * Created by gabrielm on 13/02/17.
 */
public class ProblemKnapsack extends Problem{


    public  ArrayList<KnapsackItem> items = new ArrayList<>();


    public ProblemKnapsack(){
        crossover = new CrossoverUniformKnapsack();
        Constants.PROBLEM_SIZE = 3;
        Constants.QTD_ITEMS = 10;
        Constants.BAG_CAPACITY = 100;
        this.buildItems();
        //Constants.MUTATION_RATE = 2/items.size();
    }



    @Override
    public void evaluateAgainstObjectiveFunctions(Population p)
    {
        for (int i = 0; i < p.population.size(); i++)
        {
            Member member = p.population.get(i);
            this.applyFunctions(member);
        }
    }

    @Override
    public ArrayList<Member> generateMembers(int QtdMembers)
    {
        ArrayList<Member> population =  new ArrayList<>();
        for (int i = 0; i < QtdMembers; i++) {
            population.add(new Member(Utils.getRandomBinaryArray(Constants.QTD_ITEMS)));
        }

        return population;
    }



    @Override
    public void applyFunctions(Member member)
    {
        if(Constants.PROBLEM_SIZE <2) return ;
        member.resultOfFunctions = new ArrayList<>();

        member.resultOfFunctions.add(firstFunction(member));
        member.resultOfFunctions.add(secondFunction(member));

        if(Constants.PROBLEM_SIZE >= 3) member.resultOfFunctions.add(thirdFunction(member));
        if(Constants.PROBLEM_SIZE >= 4) member.resultOfFunctions.add(fourthFunction(member));
        if(Constants.PROBLEM_SIZE >= 5) member.resultOfFunctions.add(fifthFunction(member));
        if(Constants.PROBLEM_SIZE >= 6) member.resultOfFunctions.add(sixthFunction(member));
    }


    private double calculateWeightGivenMember(Member member)
    {
        double totalWeight = 0;
        for (int i = 0; i < Constants.QTD_ITEMS; i++) {
            if (member.binaryValue.get(i) == 1)
            {
                totalWeight += items.get(i).weight;
            }
        }
        return totalWeight;
    }

    @Override
    public double firstFunction(Member member) {
        double firstFunctionValue = 0;

        for (int j = 0; j < Constants.QTD_ITEMS; j++)
        {
            if (member.binaryValue.get(j) == 1)
            {
                firstFunctionValue += this.items.get(j).attributes.get(0);
            }
        }

        if( firstFunctionValue == 0) firstFunctionValue = 0.1;

        return calculateWeightGivenMember(member) > Constants.BAG_CAPACITY? (2): (1/firstFunctionValue);

    }


    @Override
    public double secondFunction(Member member) {
        double secondFunctionValue = 0;
        for (int j = 0; j < Constants.QTD_ITEMS; j++)
        {
            if(member.binaryValue.get(j) == 1)
            {
                secondFunctionValue += this.items.get(j).attributes.get(1);

            }
        }

        if( secondFunctionValue == 0) secondFunctionValue = 0.1;

        return calculateWeightGivenMember(member) > Constants.BAG_CAPACITY? (2) : (1/secondFunctionValue);

    }

    public double thirdFunction(Member member){
        double thirdFunctionValue = 0;
        for (int j = 0; j < Constants.QTD_ITEMS; j++)
        {
            if(member.binaryValue.get(j) == 1)
            {
                thirdFunctionValue += this.items.get(j).attributes.get(2);

            }
        }

        if( thirdFunctionValue == 0) thirdFunctionValue = 0.1;

        return calculateWeightGivenMember(member) > Constants.BAG_CAPACITY? (2) : (1/thirdFunctionValue);
    }


    public double fourthFunction(Member member){
        double fourthFunctionValue = 0;
        for (int j = 0; j < Constants.QTD_ITEMS; j++)
        {
            if(member.binaryValue.get(j) == 1)
            {
                fourthFunctionValue += this.items.get(j).attributes.get(3);

            }
        }

        if( fourthFunctionValue == 0) fourthFunctionValue = 0.1;

        return calculateWeightGivenMember(member) > Constants.BAG_CAPACITY? (2) : (1/fourthFunctionValue);
    }

    public double fifthFunction(Member member)
    {
        double fifthFunctionValue = 0;
        for (int j = 0; j < Constants.QTD_ITEMS; j++)
        {
            if(member.binaryValue.get(j) == 1)
            {
                fifthFunctionValue += this.items.get(j).attributes.get(4);

            }
        }

        if( fifthFunctionValue == 0) fifthFunctionValue = 0.1;

        return calculateWeightGivenMember(member) > Constants.BAG_CAPACITY? (2) : (1/fifthFunctionValue);
    }

    public double sixthFunction(Member member)
    {
        double sixthFunctionValue = 0;
        for (int j = 0; j < Constants.QTD_ITEMS; j++)
        {
            if(member.binaryValue.get(j) == 1)
            {
                sixthFunctionValue += this.items.get(j).attributes.get(5);

            }
        }

        if( sixthFunctionValue == 0) sixthFunctionValue = 0.1;

        return calculateWeightGivenMember(member) > Constants.BAG_CAPACITY? (2) : (1/sixthFunctionValue);
    }

    @Override
    public void printResolutionMessage() {
        System.out.println("Dado os seguintes itens: ");
        this.printItems();
        System.out.println("Os melhores indivíduos foram ");

    }

    private void buildItems()
    {
        for (int i = 0; i < Constants.QTD_ITEMS; i++) {
            items.add(new KnapsackItem());
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

    @Override
    public void evaluateAgainstMask(Population p, int[] mask){
        for (int i = 0; i < p.population.size(); i++) {
            Member member = p.population.get(i);
            this.applyFunctionsGivenMask(member,mask);
        }
    }

    @Override
    public void applyFunctionsGivenMask(Member member , int[] mask){
        if (mask.length == 0){
            applyFunctions(member);
            return;
        }

        if(Constants.PROBLEM_SIZE <2) return ;

        for (int i = 0; i < mask.length; i++) {

            double functionToBeInserted = 0.0;
            for (int j = 0; j < Constants.QTD_ITEMS; j++)
            {
                if (member.binaryValue.get(j) == 1)
                {
                    functionToBeInserted += this.items.get(j).attributes.get(mask[i] - 1);
                }
            }
            if( functionToBeInserted == 0) functionToBeInserted = 0.1;

            functionToBeInserted = calculateWeightGivenMember(member) > Constants.BAG_CAPACITY? (2.0) : (1/functionToBeInserted);
            member.resultOfFunctions.add(functionToBeInserted);
        }
    }
}
