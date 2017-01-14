/**
 * Created by gabrielm on 10/01/17.
 */
public interface  Constants {

    //ALL MOEAs CONSTANTS
    int POPULATION_SIZE =10;
    int MUTATION_RATE = 5;
    double CROSSOVER_RATE = 1;
    double NUMBER_OF_GENERATIONS = 1;
    int TOUR_SIZE =3;

    //ONLY SPEA2 CONSTANTS
    int ARCHIVE_SIZE = 4;
    int DISTANCE_MATRIX_SIZE = ARCHIVE_SIZE + POPULATION_SIZE;

    //PROBLEM CONSTANTS
    int PROBLEM_SIZE = 2;
    int MAX_MEMBER_VALUE = 1000;
    int MIN_MEMBER_VALUE = -1000;
    int MAX_BINARY_LEN = 11;


    //FOR TEST ONLY
    int SEED = 1;
}
