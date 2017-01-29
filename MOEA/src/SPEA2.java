import java.util.Scanner;

/** 
 * Created by gabrielm on 09/01/17.
 */
public class SPEA2 {

    Scanner s = new Scanner(System.in);

    public void runAlgorithm(){
        int genCounter = 0;
        Population p = new Population();
        Problem problem = new ProblemSCH();
        Population archive = new Population();
        Population union = new Population();
        Population selected = new Population();
        Selection selectedFitness = new SelectionArchive();
        ProblemSCH problemSCH = new ProblemSCH(); //todo
        Crossover bCrossover = new BinaryCrossover();

        p.population = problem.generateRandomMembers();

        while(genCounter < Constants.NUMBER_OF_GENERATIONS)
        {
            System.out.println("GERACAO = "+ genCounter+"===========================================");//todo

            union.mergeTwoPopulations(p,archive);

            problem.evaluateAgainstObjectiveFunctions(union);

            union.fastNonDominatedSort();
           
            Fitness.calculateFitness(union);
    
            archive = EnvironmentalSelection.environmentalSelection(p,archive);
            
            selected = selectedFitness.selectParents(archive);

            p = bCrossover.crossoverAndMutation(selected); 

            genCounter++;
            Fitness.prepareForNextGen();
            
        }

        Printer.printMembersWithValue(archive); //todo
        
    }

    public void nextRun()
    {
        Fitness.setFirstMatrix();
    }



}
