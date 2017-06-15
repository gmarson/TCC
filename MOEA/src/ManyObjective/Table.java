package ManyObjective;

import Constants.*;
import Population.*;
import Utilities.Printer;

import java.util.ArrayList;

/**
 * Created by gabrielm on 11/03/17.
 */
public class Table {

    public Population tablePopulation = new Population();
    public int contribution = 0;
    public int convergence = 0;
    public ArrayList<Integer> mask;
    public boolean isNonDominatedTable;


    public Table(ArrayList<Integer> mask){
        this.mask = mask;
        this.isNonDominatedTable = mask.size() == 0;
    }


    public void setBestMembersForSingleObjectiveTables(){

        Printer.printBinaryMembersWithAppliedFunctions(tablePopulation);//todo

        while (tablePopulation.population.size() > Constants.TABLE_SIZE)
        {
            tablePopulation.population.remove(0); //todo nao eh index 0 Pq ele eh o melhor. eh o ultimo indexx!!
        }

        System.out.println(mask);//todo
        Printer.printBinaryMembersWithAppliedFunctions(tablePopulation);//todo

    }

    public void setBestMembersForNonDominatedTable() {

        while(tablePopulation.population.size() > Constants.TABLE_SIZE)
        {
            tablePopulation.population.remove(0); //todo nao eh index 0 Pq ele eh o melhor. eh o ultimo indexx!!
        }

        if (mask.size() ==1) applyWeightedAverageForPopulation();

    }

    public void setBestMembersByWeightedAverage(){
        while(tablePopulation.population.size() > Constants.TABLE_SIZE){

            tablePopulation.population.remove(0); //todo nao eh index 0 Pq ele eh o melhor. eh o ultimo indexx!!
        }
    }

    public void resetContributionAndConvergence(){
        this.contribution = 0;
        this.convergence = 0;
    }

    private void applyWeightedAverageForPopulation(){
        for (Member m : tablePopulation.population){
            applyWeightedAverageInSingleObjectiveMember(m);
        }
    }

    private void applyWeightedAverageInSingleObjectiveMember(Member member){

        member.weightedAverage = member.resultOfFunctions.get(0);
    }




}
