package Problems;

import Constants.*;
import Crossover.CrossoverBinaryKnapsack;
import Population.*;
import Utilities.Utils;

import java.util.ArrayList;

/**
 * Created by gabrielm on 13/02/17.
 */
public class ProblemKnapsack extends Problem{


    public static ArrayList<KnapsackItem> items = new ArrayList<>();


    public ProblemKnapsack(){
        crossover = new CrossoverBinaryKnapsack();
        Constants.PROBLEM_SIZE = 6;
        Constants.QTD_ITEMS = 10;
        Constants.BAG_CAPACITY = 100;
        this.buildItems();
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
    public ArrayList<Member> generateRandomMembers(int QtdMembers)
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
        member.resultOfFunctions.add(firstFunction(member));
        member.resultOfFunctions.add(secondFunction(member));
        member.resultOfFunctions.add(thirdFunction(member));
        member.resultOfFunctions.add(fourthFunction(member));
        member.resultOfFunctions.add(fifthFunction(member));
        member.resultOfFunctions.add(sixthFunction(member));
    }


    public double caculateWeightGivenMember(Member member)
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
                firstFunctionValue += ProblemKnapsack.items.get(j).attributes.get(0);
            }
        }

        if( firstFunctionValue == 0) firstFunctionValue = 0.1;

        return caculateWeightGivenMember(member) > Constants.BAG_CAPACITY? (2): (1/firstFunctionValue);

    }


    @Override
    public double secondFunction(Member member) {
        double secondFunctionValue = 0;
        for (int j = 0; j < Constants.QTD_ITEMS; j++)
        {
            if(member.binaryValue.get(j) == 1)
            {
                secondFunctionValue += ProblemKnapsack.items.get(j).attributes.get(1);

            }
        }

        if( secondFunctionValue == 0) secondFunctionValue = 0.1;

        return caculateWeightGivenMember(member) > Constants.BAG_CAPACITY? (2) : (1/secondFunctionValue);

    }

    public double thirdFunction(Member member){
        double thirdFunctionValue = 0;
        for (int j = 0; j < Constants.QTD_ITEMS; j++)
        {
            if(member.binaryValue.get(j) == 1)
            {
                thirdFunctionValue += ProblemKnapsack.items.get(j).attributes.get(2);

            }
        }

        if( thirdFunctionValue == 0) thirdFunctionValue = 0.1;

        return caculateWeightGivenMember(member) > Constants.BAG_CAPACITY? (2) : (1/thirdFunctionValue);
    }


    public double fourthFunction(Member member){
        double fourthFunctionValue = 0;
        for (int j = 0; j < Constants.QTD_ITEMS; j++)
        {
            if(member.binaryValue.get(j) == 1)
            {
                fourthFunctionValue += ProblemKnapsack.items.get(j).attributes.get(3);

            }
        }

        if( fourthFunctionValue == 0) fourthFunctionValue = 0.1;

        return caculateWeightGivenMember(member) > Constants.BAG_CAPACITY? (2) : (1/fourthFunctionValue);
    }

    public double fifthFunction(Member member)
    {
        double fifthFunctionValue = 0;
        for (int j = 0; j < Constants.QTD_ITEMS; j++)
        {
            if(member.binaryValue.get(j) == 1)
            {
                fifthFunctionValue += ProblemKnapsack.items.get(j).attributes.get(4);

            }
        }

        if( fifthFunctionValue == 0) fifthFunctionValue = 0.1;

        return caculateWeightGivenMember(member) > Constants.BAG_CAPACITY? (2) : (1/fifthFunctionValue);
    }

    public double sixthFunction(Member member)
    {
        double sixthFunctionValue = 0;
        for (int j = 0; j < Constants.QTD_ITEMS; j++)
        {
            if(member.binaryValue.get(j) == 1)
            {
                sixthFunctionValue += ProblemKnapsack.items.get(j).attributes.get(5);

            }
        }

        if( sixthFunctionValue == 0) sixthFunctionValue = 0.1;

        return caculateWeightGivenMember(member) > Constants.BAG_CAPACITY? (2) : (1/sixthFunctionValue);
    }

    @Override
    public void printResolutionMessage() {
        System.out.println("Dado os seguintes intens: ");
        this.printItems();
        System.out.println("Os melhores indivíduos foram ");

    }

    public void buildItems()
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

    public class KnapsackItem{
        public double weight;
        public ArrayList<Double> attributes = new ArrayList<>(); //lucro e por ai vai

        private double MAX_ATTRIBUTE_VALUE_FOR_ITEM = 10;
        private double MIN_ATTRIBUTE_VALUE_FOR_ITEM = 1;
        private double MAX_WEIGHT = 100;
        private double MIN_WEIGHT = 1;

        //todo tirar os castings depois
        public KnapsackItem()
        {
            this.weight =  Utils.getRandom((int)MIN_WEIGHT,(int)MAX_WEIGHT);
            this.createAttributesOfItems();
        }


        //todo tirar os castings depois
        public void createAttributesOfItems()
        {
            for (int i = 0; i <Constants.PROBLEM_SIZE ; i++) {
                this.attributes.add(i,(double)Utils.getRandom((int)MIN_ATTRIBUTE_VALUE_FOR_ITEM,(int)MAX_ATTRIBUTE_VALUE_FOR_ITEM));
            }
        }

        public void printItem()
        {
            System.out.println("Peso: "+this.weight);
            for (int i = 0; i < attributes.size(); i++) {
                System.out.println("Atributo "+i+" = "+attributes.get(i)+"\n");
            }
        }


    }





}
