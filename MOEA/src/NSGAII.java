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
                5 - selecionar os pais (torneio simples)

                6 - cruzamento (geração dos filhos) (crossover binario de um ponto)

                7 - Calculo de fronteira

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
        ArrayList<Member>p = Population.getInstance();
        f.applyFunction();
        //System.out.println("Before crossover");
        //Population.printPopulationDetailed();

        Utils.dominates();
        //Population.dominanceRelations();
        Fronts.makeFronts();
        Fronts.printFronts();
        Scanner s = new Scanner(System.in);

        ArrayList<Integer> indexOfparents = Selection.binaryTournament();
        //Selection.membersGoingToCrossover(parents);

        Crossover.doCrossover(1,indexOfparents);

        //System.out.println("After Crossover");
        //Population.printPopulationDetailed();

        //TODO vamos focar nas fronts, alguma coisa ta errada pq tem front que nao tem nenhum individuo, logo
        //TODO ela nao deveria estar listada em fronts
        Population.reinsertion();
        //Fronts.printFronts();

    }
}
