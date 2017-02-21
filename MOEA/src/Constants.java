/**
 * Created by gabrielm on 10/01/17.
 */
public abstract class  Constants {


    //todo simplifica o problema
    //ALL MOEAs CONSTANTS
    static int POPULATION_SIZE =100;
    static int MUTATION_RATE = 5;
    static double CROSSOVER_RATE = 1;
    static double NUMBER_OF_GENERATIONS = 100;
    static int TOUR_SIZE =3;

    //ONLY SPEA2 CONSTANTS
    static int ARCHIVE_SIZE = 60;
    static int DISTANCE_MATRIX_SIZE = ARCHIVE_SIZE + POPULATION_SIZE;
    static int MAX_MATRIX_SIZE = DISTANCE_MATRIX_SIZE + POPULATION_SIZE;

    //PROBLEM CONSTANTS
    static int PROBLEM_SIZE;
    static int MAX_MEMBER_VALUE;
    static int MIN_MEMBER_VALUE;
    static int MAX_BINARY_LEN;

    //KNAPSACK PROBLEM ONLY
    static int QTD_ITEMS;
    static double BAG_CAPACITY;


    //FOR TEST ONLY
    static int SEED = 2; //todo com essa semente ta dando pau
    
}
