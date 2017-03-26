package ManyObjective;

import Population.Population;
import Problems.*;
import Constants.*;
import Selections.*;
import Utilities.Printer;
import Population.*;
import Utilities.Utils;

import java.util.ArrayList;

/**
 * Created by gabrielm on 07/03/17.
 */
public class AEMMT {

    public static int genCounter=1;

    public void runAlgorithm(Problem problem)
    {

        Population p = new Population();
        p.population = problem.generateRandomMembers(Constants.POPULATION_SIZE);
        problem.evaluateAgainstObjectiveFunctions(p);
        Tables.buildTables(p,problem);
        Tables.fillTablesAEMMT();



        //todo isso aki ficara dentro do loop
        SelectionRankWeightedAverage aemmtSelection = new SelectionRankWeightedAverage();
        Population parentsPopulation = aemmtSelection.selectParentsGivenAllTables(Tables.tables);
        Population children = problem.crossover.crossoverAndMutation(parentsPopulation);
        this.copyMaskToChildren(parentsPopulation,children);
        insertMemberOnTables(children.population.get(0), problem );


        Printer.printMembersWithWeightedAverageAndFunctions(parentsPopulation);//todo


        /*while(genCounter < Constants.NUMBER_OF_GENERATIONS+1)
        {

            if (genCounter % 50 ==0) Tables.resetContributionAndConvergence();



        }*/

    }



    public void insertMemberOnTables(Member newMember,Problem problem){

        boolean shouldIncrease = false;
        ArrayList<Integer> positionsToIncrease = new ArrayList<>();
        int tablePosition = 0;
        problem.applyFunctions(newMember);

        for (Table table :Tables.tables)
        {

            if (table.isNonDominatedTable && !Problem.memberIsPresent(table.pop,newMember,problem)) {
                table.pop.population.add(newMember);
                table.pop.fastNonDominatedSort();
                table.pop.population = table.pop.fronts.allFronts.get(0).membersAtThisFront;
                if(Problem.memberIsPresent(table.pop,newMember,problem)){
                    shouldIncrease = true;
                }
            }
            else if(!table.isNonDominatedTable){
                Member worstMemberOfTable = table.pop.population.get(Constants.TABLE_SIZE-1);
                if (worstMemberOfTable.weightedAverage > newMember.weightedAverage){
                    table.pop.population.remove(Constants.TABLE_SIZE-1);
                    Utils.insertMemberOnCrescentOrderedArrayByWeightedAverage(newMember,table.pop.population);
                    shouldIncrease = true;
                }

            }

            if (table.mask.equals(newMember.parentTableMask1) || table.mask.equals(newMember.parentTableMask2))
                positionsToIncrease.add(tablePosition);

            tablePosition++;
        }
        if (shouldIncrease) increaseContribution(positionsToIncrease);
    }


    private  void increaseContribution(ArrayList<Integer> positionsToIncrease)
    {
            Tables.tables.get(positionsToIncrease.get(0)).contribution++;
            Tables.tables.get(positionsToIncrease.get(1)).contribution++;
    }

    private void copyMaskToChildren(Population parentsPopulation, Population children){
        for (int i = 0; i < parentsPopulation.population.size(); i++) {
            children.population.get(i).parentTableMask1 = parentsPopulation.population.get(i).parentTableMask1;
            children.population.get(i).parentTableMask2 = parentsPopulation.population.get(i).parentTableMask2;
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
    *   //TODO lembrar de colocar as variaveis do aemmt em resetAttributesAndFrontsForAllMembers()
    * */

}
