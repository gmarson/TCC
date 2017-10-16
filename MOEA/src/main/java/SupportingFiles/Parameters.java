package SupportingFiles;

import Problems.Problem;
import Problems.ProblemKnapsack;

/**
 * Created by gabrielm on 10/01/17.
 * Project : TCC.
 */

public abstract class Parameters {

    //ALL MOEAs CONSTANTS
    public static int POPULATION_SIZE = 150;
    public static int MUTATION_RATE = 5; // vai ser 2 dividido pelo numero de itens
    public static double CROSSOVER_RATE = 1;
    public static double NUMBER_OF_GENERATIONS = 200;
    public static int TOUR_SIZE = 4;

    //ONLY SPEA2 CONSTANTS
    public static int ARCHIVE_SIZE = 90;
    public static int DISTANCE_MATRIX_SIZE = ARCHIVE_SIZE + POPULATION_SIZE;

    //ONLY AEMMT AND AEMMD CONSTANTS
    public static int RESET_ON_GEN = 50;
    public static int QTD_TABLES;
    public static int TABLE_SIZE = 50;

    //ONLY MOEA-D
    public static int NEIGHBOURHOOD_SIZE = 150;

    //PROBLEM CONSTANTS
    public static int PROBLEM_SIZE = -1;
    public static int MAX_MEMBER_VALUE;
    public static int MIN_MEMBER_VALUE;
    public static int MAX_BINARY_LEN;

    //KNAPSACK PROBLEM ONLY
    public static int QTD_ITEMS;
    public static double BAG_CAPACITY;

    //FOR TEST ONLY
    public static int SEED = 1;

}
