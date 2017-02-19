import java.util.ArrayList;

/**
 * Created by gabrielm on 13/02/17.
 */
public class ProblemKnapsack extends Problem{


    public static ArrayList<KnapsackItem> items = new ArrayList<>();


    public ProblemKnapsack(){
        crossover = new CrossoverBinaryKnapsack();
        Constants.PROBLEM_SIZE = 2;
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
    public ArrayList<Member> generateRandomMembers()
    {
        ArrayList<Member> population =  new ArrayList<>();
        for (int i = 0; i < Constants.POPULATION_SIZE; i++) {
            population.add(new Member(Utils.getRandomBinaryArray(Constants.QTD_ITEMS)));
        }

        return population;
    }

    @Override
    public void applyFunctions(Member member)
    {

        member.resultOfFunctions.add(firstFunction(member));
        member.resultOfFunctions.add(secondFunction(member));
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

        return firstFunctionValue > Constants.BAG_CAPACITY? (2): (1/firstFunctionValue);

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

        if( member.resultOfFunctions.get(0) ==2) return 2;

        return secondFunctionValue == 0? (1/0.1) : (1/secondFunctionValue);

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
            this.attributes.add(0,this.weight);
            for (int i = 1; i <Constants.PROBLEM_SIZE ; i++) {
                this.attributes.add(i,(double)Utils.getRandom((int)MIN_ATTRIBUTE_VALUE_FOR_ITEM,(int)MAX_ATTRIBUTE_VALUE_FOR_ITEM));
            }
        }

        public void printItem()
        {
            System.out.println("Peso: "+this.weight);
            for (int i = 1; i < attributes.size(); i++) {
                System.out.println("Atributo "+i+" = "+attributes.get(i)+"\n");
            }
        }


    }





}
