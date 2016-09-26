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
            3 Dividir os indivíduos por fronteiras aplicando métrica de dominância.
            Aqui, eu ja tenho os ranks ^^
            4 Cálculo de Aptidao
                4.1 para cada individuo, orddene o rank atual de acordo com os valores da função objetivo
                para o primeiro e ultimo elemento, distancia = infinito
                para os demais elementos, calcule a distancia como
                di = di + (f(i+1) - f(i-1) / (fmax - fmin)) onde f eh o objeto atual
                i é o elemento atual do rank, fmax o valor máximo para f e fmin o minimo
           5 Calculo do Crowding distance

         */


        Function f = ProblemSCH.getInstance();

        Population.buildPopulation();
        f.applyFunction();
        //Population.printPopulationDetailed();

        Utils.dominates();
        Population.dominanceRelations();

    }
}
