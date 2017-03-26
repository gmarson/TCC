package ManyObjective;

import Population.Population;
import Problems.Problem;
import Constants.*;
import Selections.*;
import Utilities.Printer;
import Population.*;
import Utilities.Utils;

import java.util.ArrayList;

/**
 * Created by gabrielm on 07/03/17.
 */
public class AEMMD {



    public void runAlgorithm(Problem problem)
    {

    }


    //todo implementar e chamar crossover antes de entrar aki
    public void insertMemberOnTables(Member newMember){

        for(Table table: Tables.tables)
        {
            Member worstMemberOfTable = table.pop.population.get(table.pop.population.size() -1);
            if (worstMemberOfTable.weightedAverage > newMember.weightedAverage)
            {
                table.pop.population.remove(worstMemberOfTable);
                Utils.insertMemberOnCrescentOrderedArrayByWeightedAverage(newMember,table.pop.population);
                table.convergence++;
            }

        }


    }



    /*
    *    XXX a tabela tem um tamanho fixo, inicialmente a pop inicial será 10x o tamanho de cada tabela
    *    Ex.: se a tablea eh 10 entao a populacao inicial eh 100
    *
    *    XXX As tabelas que avaliam um objetivo só são avaliadas por dominância, ao passo que as demais sao avaliadas por ponderacao
    *    ou seja, se em uma tabela eu tenho os objetivos a e b entao o melhor individuo eh aquele que tem a menor media entre esses
    *    dois objetivos
    *
    *    NO AEMMD todas as tabelas são por dominância
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
    *
    *
    *   //TODO lembrar de colocar as variaveis do aemmd em resetAttributesAndFrontsForAllMembers()
    * */

}
