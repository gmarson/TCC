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

        p.population = problem.generateRandomMembers();

        while(genCounter < Constants.NUMBER_OF_GENERATIONS)
        {
            System.out.println("GERACAO = "+ genCounter);//todo


            problem.evaluateAgainstObjectiveFunctions(p);
            
            union.mergeTwoPopulations(p,archive);
            union.fastNonDominatedSort();
            
            Fitness.calculateFitness(union);
                        
            archive = union.getNonDominated();
            Printer.printPopulationArchiveAndUnion(p,archive,union);//todo

            //todo verificar se os numeros estao em ordem de dominancia
            
            System.out.println("Indo pro environmentalSelection");
            archive = EnvironmentalSelection.environmentalSelection(p,archive);

            selected = selectedFitness.selectParents(archive);
            
            Crossover bCrossover = new BinaryCrossover();
            p = bCrossover.crossoverAndMutation(selected);

            genCounter++;
            Fitness.prepareForNextGen();
        }

        Printer.printMembersWithValue(archive); //todo
    }




}
