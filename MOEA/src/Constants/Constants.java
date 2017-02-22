package Constants;


/**
 * Created by gabrielm on 10/01/17.
 */
public abstract class  Constants {

    //ALL MOEAs CONSTANTS
    public static int POPULATION_SIZE =100;
    public static int MUTATION_RATE = 5;
    public static double CROSSOVER_RATE = 1;
    public static double NUMBER_OF_GENERATIONS = 100;
    public static int TOUR_SIZE =3;

    //ONLY SPEA2 CONSTANTS
    public static int ARCHIVE_SIZE = 60;
    public static int DISTANCE_MATRIX_SIZE = ARCHIVE_SIZE + POPULATION_SIZE;

    //PROBLEM CONSTANTS
    public static int PROBLEM_SIZE;
    public static int MAX_MEMBER_VALUE;
    public static int MIN_MEMBER_VALUE;
    public static int MAX_BINARY_LEN;

    //KNAPSACK PROBLEM ONLY
    public static int QTD_ITEMS;
    public static double BAG_CAPACITY;


    //FOR TEST ONLY
    public static int SEED = 2;
    
}
