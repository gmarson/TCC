package Selections;

import Constants.*;
import ManyObjective.*;
import Population.*;
import Utilities.*;
import jdk.nashorn.internal.ir.annotations.Ignore;

import java.util.ArrayList;

/**
 * Created by gabrielm on 22/03/17.
 */
public class SelectionRankWeightedAverage extends Selection {


    public Population selectParentsGivenAllTables(ArrayList<Table> tables){
        ArrayList<Table> selectedTables =  this.selectTablesAEMMT(tables);
        return this.selectParents(selectedTables.get(0),selectedTables.get(1));
    }

    private Population selectParents(Table table1, Table table2){
        Population p = new Population();
        ArrayList<Member> parents = singleTournament(table1,table2);
        for (Member m:parents)
        {
            m.parentTableMask1 = table1.mask;
            m.parentTableMask2 = table2.mask;

        }

        p.population = parents;
        return p;
    }

    private ArrayList<Table> selectTablesAEMMT(ArrayList<Table> tables){return tournamentTables(tables);}


    private ArrayList<Member> singleTournament(Table table1, Table table2){
        ArrayList<Member> selected = new ArrayList<>();
        ArrayList<Member> membersByTourFromTable1, membersByTourFromTable2;

        membersByTourFromTable1 = super.makeCompetitors(table1.pop);
        membersByTourFromTable2 = super.makeCompetitors(table2.pop);
        selected.add(returnWinnerCompetitor(membersByTourFromTable1));
        selected.add(returnWinnerCompetitor(membersByTourFromTable2));

        return selected;
    }

    @Override
    protected Member returnWinnerCompetitor(ArrayList<Member> membersByTour) {
        Member bestMember, opponentMember;
        bestMember = membersByTour.get(0);
        for (int i = 1; i <membersByTour.size() ; i++) {
            opponentMember = membersByTour.get(i);
            if (opponentMember.weightedAverage < bestMember.weightedAverage)
                bestMember = opponentMember;
        }

        return bestMember;
    }


    private ArrayList<Table> tournamentTables(ArrayList<Table> tables){

        ArrayList<Table> tablesByTourFirstGroup, tablesByTourSecondGroup;
        ArrayList<Table> selected = new ArrayList<>();

        tablesByTourFirstGroup = super.makeCompetitors(tables);
        tablesByTourSecondGroup = super.makeCompetitors(tables);
        selected.add(returnWinnerTable(tablesByTourFirstGroup));
        selected.add(returnWinnerTable(tablesByTourSecondGroup));

        return selected;
    }

    private Table returnWinnerTable(ArrayList<Table> tablesByTour){
        Table bestTable, opponentTable;
        bestTable = tablesByTour.get(0);
        for (int i = 1; i <tablesByTour.size() ; i++) {
            opponentTable = tablesByTour.get(i);
            if (bestTable.contribution < opponentTable.contribution)
                bestTable = opponentTable;
        }

        return bestTable;
    }




    @Override @Ignore
    public Population selectParents(Population population) {return null;}
    @Override @Ignore
    protected Population tournament(Population population) {
        return null;
    }

}
