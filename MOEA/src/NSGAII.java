import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by gmarson on 9/14/2016.
 * TCC UFU
 */

public class NSGAII {
    public NSGAII()
    {
        /*
            V Gerar indivíduos aleatoriamente
            V Aplicar as funções objetivos
            V Dividir os indivíduos por fronteiras aplicando métrica de dominância.
            Aqui, eu ja tenho os ranks ^^

            LOOP
                V - selecionar os pais (torneio simples)

                V - cruzamento (geração dos filhos) (crossover binario de um ponto)

                V - Calculo de fronteira

                8 - reinserção

                    4 (quando precisa, quando o tamanho da fronteira exceder o numero de individuos que eu preciso reinserir)
                    para cada individuo, ordene o rank atual de acordo com os valores da função objetivo
                    para o primeiro e ultimo elemento, distancia = infinito
                    para os demais elementos, calcule a distancia como
                    di = di + (f(i+1) - f(i-1) / (fmax - fmin)) onde f eh o objeto atual
                    i é o elemento atual do rank, fmax o valor máximo para f e fmin o minimo
         */

        Function f = ProblemSCH.getInstance();

        Population.buildPopulation();
        Scanner s = new Scanner(System.in);


        for(int i=0; i< Population.NUM_GER;i++) {
            //System.out.println("I+ "+i);

            f. applyFunction();

            //System.out.println("after applying functions");
            //Population.printPopulationDetailed();

            //System.out.println("Before crossover");
            //Population.printPopulationDetailed();
            Utils.dominatesPopulation();


            //Population.dominanceRelations();
            //System.out.println("VOU FAZER AS FRONTS");
            Fronts.makeFronts();


            //System.out.println("SAI DAS FRONTS");
            //Fronts.printFronts();

            ArrayList<Integer> indexOfparents = Selection.binaryTournament();

            //Selection.membersGoingToCrossover(parents);
            Crossover.doCrossover(2, indexOfparents);

            //System.out.println("After Crossover");
            //Population.printPopulationDetailed();
            //s.nextLine();
            Population.reinsertion();
            //s.nextLine();
            //Fronts.printFronts();
            //Population.printPopulationDetailed();
            //s.nextLine();
        }

        Population.printFirstFrontData();
        Population.printPopulation();


    }






}
