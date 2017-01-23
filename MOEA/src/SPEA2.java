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
            System.out.println("Geracao = "+ genCounter);//todo
            problem.evaluateAgainstObjectiveFunctions(p);
            
            System.out.println("population");//todo
            Printer.printMembersWithValue(p);//todo
            System.out.println("archive");//todo
            Printer.printMembersWithFitness(archive);//todo

            union.mergeTwoPopulations(p,archive);
            union.fastNonDominatedSort();
            
            Fitness.calculateFitness(union);
                        
            archive = union.getNonDominated();

            //todo verificar se os numeros estao em ordem de dominancia
            //todo nao vai pelo codigo do ruby, LA EH SO A LOGICA
            archive = EnvironmentalSelection.environmentalSelection(p,archive);

            selected = selectedFitness.selectParents(archive);
            System.out.println("selected no meio");//todo
            Printer.printMembersWithValue(selected);//todo

            Crossover bCrossover = new BinaryCrossover();
            p = bCrossover.crossoverAndMutation(selected);

            genCounter++;
            Fitness.prepareForNextGen();
      
        }

        Printer.printMembersWithValue(archive); //todo
    }




}
