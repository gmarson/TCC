/**
 * Created by gabrielm on 10/01/17.
 */
public abstract class  Constants {

    //ALL MOEAs CONSTANTS
    static int POPULATION_SIZE =100; //6
    static int MUTATION_RATE = 5;
    static double CROSSOVER_RATE = 1;
    static double NUMBER_OF_GENERATIONS = 100; // nessa semente, com 20 ger tem 1 e 2 e com 25 tem 0 e 2. Que loucura.
    static int TOUR_SIZE =3;

    //ONLY SPEA2 CONSTANTS
    static int ARCHIVE_SIZE = 60; //3
    static int DISTANCE_MATRIX_SIZE = ARCHIVE_SIZE + POPULATION_SIZE;
    static int MAX_MATRIX_SIZE = DISTANCE_MATRIX_SIZE + POPULATION_SIZE;

    //PROBLEM CONSTANTS
    static int PROBLEM_SIZE = 2;
    static int MAX_MEMBER_VALUE = 1000;
    static int MIN_MEMBER_VALUE = -1000;
    static int MAX_BINARY_LEN = 11;

    //FOR TEST ONLY
    static int SEED = 205;
    
}
