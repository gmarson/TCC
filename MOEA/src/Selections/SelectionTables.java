package Selections;

import ManyObjective.*;
import Population.*;
import jdk.nashorn.internal.ir.annotations.Ignore;

import java.util.ArrayList;

/**
 * Created by gabrielm on 01/04/17.
 */
public class SelectionTables extends  Selection{

    private SelectionRankWeightedAverage selectionRankWeightedAverage = new SelectionRankWeightedAverage();


    public ArrayList<Table> selectTables(ArrayList<Table> tables, String option){
        switch (option){
            case "AEMMT":
                return tournamentTablesAEMMT(tables);
            case "AEMMD":
                return tournamentTablesAEMMD(tables);
            default:
                System.out.println("Invalid Option!");
        }
        return null;
    }



    ArrayList<Member> singleTournament(Table table1, Table table2){
        ArrayList<Member> selected = new ArrayList<>();
        ArrayList<Member> membersByTourFromTable1, membersByTourFromTable2;

        membersByTourFromTable1 = super.makeCompetitors(table1.tablePopulation);
        membersByTourFromTable2 = super.makeCompetitors(table2.tablePopulation);
        selected.add(selectionRankWeightedAverage.returnWinnerCompetitor(membersByTourFromTable1));
        selected.add(selectionRankWeightedAverage.returnWinnerCompetitor(membersByTourFromTable2));

        return selected;
    }

    private ArrayList<Table> tournamentTablesAEMMT(ArrayList<Table> tables){

        ArrayList<Table> tablesByTourFirstGroup, tablesByTourSecondGroup;
        ArrayList<Table> selected = new ArrayList<>();

        tablesByTourFirstGroup = super.makeCompetitors(tables);
        tablesByTourSecondGroup = super.makeCompetitors(tables);
        selected.add(returnWinnerTableByContribution(tablesByTourFirstGroup));
        selected.add(returnWinnerTableByContribution(tablesByTourSecondGroup));

        return selected;
    }


    private ArrayList<Table> tournamentTablesAEMMD(ArrayList<Table> tables){

        ArrayList<Table> tablesByTourFirstGroup, tablesByTourSecondGroup;
        ArrayList<Table> selected = new ArrayList<>();

        tablesByTourFirstGroup = super.makeCompetitors(tables);
        tablesByTourSecondGroup = super.makeCompetitors(tables);
        selected.add(returnWinnerTableByConvergence(tablesByTourFirstGroup));
        selected.add(returnWinnerTableByConvergence(tablesByTourSecondGroup));

        return selected;
    }

    private Table returnWinnerTableByContribution(ArrayList<Table> tablesByTour){
        Table bestTable, opponentTable;
        bestTable = tablesByTour.get(0);
        for (int i = 1; i <tablesByTour.size() ; i++) {
            opponentTable = tablesByTour.get(i);
            if (bestTable.contribution < opponentTable.contribution)
                bestTable = opponentTable;
        }

        return bestTable;
    }

    private Table returnWinnerTableByConvergence(ArrayList<Table> tablesByTour){
        Table bestTable, opponentTable;
        bestTable = tablesByTour.get(0);
        for (int i = 1; i <tablesByTour.size() ; i++) {
            opponentTable = tablesByTour.get(i);
            if (bestTable.convergence < opponentTable.convergence)
                bestTable = opponentTable;
        }

        return bestTable;
    }

    @Override @Ignore
    public Population selectParents(Population population) {
        return null;
    }

    @Override @Ignore
    protected Member returnWinnerCompetitor(ArrayList<Member> membersByTour) {
        return null;
    }
    @Override @Ignore
    protected Population tournament(Population population) {
        return null;
    }
}
