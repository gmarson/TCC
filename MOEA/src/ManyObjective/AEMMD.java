package ManyObjective;

import Fronts.Front;
import ManyObjective.TableFunctions.TableAEMMD;
import ManyObjective.TableFunctions.TableAEMMT;
import ManyObjective.TableFunctions.TableFunctions;
import Population.Population;
import Problems.Problem;
import Constants.*;
import Utilities.Printer;

/**
 * Created by gabrielm on 07/03/17.
 */
public class AEMMD {

    TableAEMMD tableAEMMD = new TableAEMMD();
    Population p = new Population();
    public Front paretto = new Front();


    public void runAlgorithm(Problem problem)
    {

        p.population = problem.generateMembers(Constants.POPULATION_SIZE);
        problem.evaluateAgainstObjectiveFunctions(p);
        tableAEMMD.buildTables(p);
        tableAEMMD.fillTables();

        tableAEMMD.mainLoop(problem);



        //Printer.printNonDominatedTable(tableAEMMD);//todo

        saveParetto(problem);
        reset();

    }

    private void reset(){
        tableAEMMD = new TableAEMMD();
        p = new Population();
        tableAEMMD.reset();

    }

    private void saveParetto(Problem problem){
        paretto.membersAtThisFront.addAll(tableAEMMD.tables.get(tableAEMMD.tables.size()-1).pop.population);
        //Problem.removeSimilar(paretto,problem);
    }




    /*
    *    XXX a tabela tem um tamanho fixo, inicialmente a pop inicial será 10x o tamanho de cada tabela
    *    Ex.: se a tablea eh 10 entao a populacao inicial eh 100
    *
    *    XXX As tabelas que avaliam um objetivo só são avaliadas por dominância, ao passo que as demais sao avaliadas por ponderacao
    *    ou seja, se em uma tabela eu tenho os objetivos a e b entao o melhor individuo eh aquele que tem a menor media entre esses
    *    dois objetivos
    *
    *    XXX NO AEMMD todas as tabelas são por dominância
    *
    *    XXX o cruzamento eh feito com um torneio para eleger as tabelas e outro torneio para eleger os dois indivíduoos, cada um contido
    *    em cada tabela. é natural que existam mais quantidade de gerações para esse tipo de algoritmo. Vamos testar com 1000 mas os manos do doutorado
    *    testaram com 10000
    *
    *    XXX apenas um indivíduo novo por geração e, se o individuo for inserido em alguma tabela,
    *    as tabelas que geraram ele tem o grau de contribuição aumentado em um
    *
    *    NO AEMMD as tabelas que recebem os filhos tem o grau aumentado em 1 e nao as que geram eles
    *
    *   a tabela de nao dominados eh calculada dando a pop inicial e vendo os nao dominados dela. A cada novo individuo,  eu vejo se ele
    *   domina todos os outros da pop nao dominada,  se ele domina entao ele vira a pop nao dominada, se ele nao domina e é dominado por algum
    *   outro individuo, ele nao será inserido lá, se ele nao domina e nao eh dominado ele sera inserido.
    * */

}
