package Selections;

import Constants.*;
import ManyObjective.*;
import Population.*;
import Utilities.*;
import jdk.nashorn.internal.ir.annotations.Ignore;

import javax.annotation.Generated;
import java.util.ArrayList;

/**
 * Created by gabrielm on 22/03/17.
 */
public class SelectionRankWeightedAverage extends Selection {

    public Population selectParents(Table table1, Table table2){ //TODO chama-la
        Population p = new Population();
        p.population = singleTournament(table1,table2);
        return p;
    }

    public ArrayList<Table> selectTablesAEMMT(ArrayList<Table> tables){return tournamentTables(tables);} // TODO chama-la

    //TODO VAMOS VER SE TA TUDO CERTO NE

    protected ArrayList<Member> singleTournament(Table table1, Table table2){
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


    public ArrayList<Table> tournamentTables(ArrayList<Table> tables){

        ArrayList<Table> tablesByTourFirstGroup, tablesByTourSecondGroup;
        ArrayList<Table> selected = new ArrayList<>();

        tablesByTourFirstGroup = super.makeCompetitors(tables);
        tablesByTourSecondGroup = super.makeCompetitors(tables);
        selected.add(returnWinnerTable(tablesByTourFirstGroup));
        selected.add(returnWinnerTable(tablesByTourSecondGroup));

        return selected;
    }

    public Table returnWinnerTable(ArrayList<Table> tablesByTour){
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
