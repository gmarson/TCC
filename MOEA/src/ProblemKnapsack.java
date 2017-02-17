import java.util.ArrayList;

/**
 * Created by gabrielm on 13/02/17.
 */
public class ProblemKnapsack extends Problem{

    public static ArrayList<KnapsackItem> items = new ArrayList<>();

    public ProblemKnapsack(){
        crossover = new CrossoverArithmetic();
        Constants.PROBLEM_SIZE = 2;
        Constants.MAX_MEMBER_VALUE = 10;
        Constants.MIN_MEMBER_VALUE = -10;
        Constants.MAX_BINARY_LEN = 11;
        Constants.QTD_ITEMS = 5;
        Constants.BAG_CAPACITY = 1000;
        this.buildItems();
    }

    @Override
    public void evaluateAgainstObjectiveFunctions(Population p)
    {
        //todo aki eu pego o somatorio dos atributos para um certo tipo de objetivo.
        /*
        * se o objetivo eh do tipo um entao eu vejo os items que estao na mochila e somo todos os atributos do tipo 1 de todos os items
        * analogamente para o tipo 2
        *
        * entao eu tenho dois quesitos de melhor opçao de escolha se eu tiver dois objetivos
        * */
    }

    @Override
    public ArrayList<Member> generateRandomMembers()
    {
        ArrayList<Member> population =  new ArrayList<>();
        for (int i = 0; i < Constants.POPULATION_SIZE; i++) {
            population.add(new Member(Utils.getRandomBinaryArray(Constants.QTD_ITEMS)));
        }

        return population;

        //PENSOO QUE ESSE VALUE NAO SERVIRA DE NADA MAS EU POSSO TRANSFORMAR O BINARIOS DE TER OU NAO ITEM EM UM NUMERO DECIMAL
        //SO PRA FICAR COESO MSM
    }

    @Override
    public void applyFunctions(Member member)
    {

    }

    @Override
    public double firstFunction(int valueOfMember)
    {
        return 0;
    }

    @Override
    public double secondFunction(int valueOfMember)
    {
        return 0;
    }

    public void buildItems()
    {
        for (int i = 0; i < Constants.QTD_ITEMS; i++) {
            items.add(new KnapsackItem());
        }
    }


    public class KnapsackItem{
        public double weight;
        public ArrayList<Double> attributes; //lucro e por ai vai

        private double MAX_ATTRIBUTE_VALUE_FOR_ITEM = 100000;
        private double MIN_ATTRIBUTE_VALUE_FOR_ITEM = -100000;
        private double MAX_WEIGHT = 100;
        private double MIN_WEIGHT = 1;

        public KnapsackItem()
        {
            this.weight =  Utils.getRandomDouble(MIN_WEIGHT,MAX_WEIGHT);
            this.createAttributesOfItems();
        }

        public void createAttributesOfItems()
        {
            for (int i = 0; i <Constants.PROBLEM_SIZE ; i++) {
                this.attributes.add(Utils.getRandomDouble(MIN_ATTRIBUTE_VALUE_FOR_ITEM,MAX_ATTRIBUTE_VALUE_FOR_ITEM));
            }
        }




    }





}
