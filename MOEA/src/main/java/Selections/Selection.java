package Selections;

import SupportingFiles.Parameters;
import Population.*;
import SupportingFiles.Utils;
import ManyObjective.TableFunctions.Table;

import java.util.ArrayList;

/**
 * Created by gmarson on 12/23/2016.
 * TCC UFU
 */
public abstract class Selection {

    public abstract Population selectParents(Population population);
    protected abstract Member returnWinnerCompetitor(ArrayList<Member> membersByTour);
    protected abstract Population tournament(Population population);

    ArrayList<Member> makeCompetitors(Population p)
    {
        ArrayList<Member> membersByTour = new ArrayList<>();
        for (int j = 0; j < Parameters.TOUR_SIZE; j++)
        {
            int randomNumberForTournament = Utils.getRandom(0,p.population.size());
            membersByTour.add(p.population.get(randomNumberForTournament));
        }
        return membersByTour;
    }

    ArrayList<Table> makeCompetitors(ArrayList<Table> tables)
    {
        ArrayList<Table> tablesByTour = new ArrayList<>();
        for (int i = 0; i < Parameters.TOUR_SIZE; i++) {
            int randomNumberForTournament = Utils.getRandom(0,tables.size());
            tablesByTour.add(tables.get(randomNumberForTournament));
        }
        return  tablesByTour;
    }

    public static Population selectParents(Table table1, Table table2){
        Population p = new Population();

        ArrayList<Member> parents = new ArrayList<Member>();

        int tableSize1 = table1.tablePopulation.population.size();
        int tableSize2 = table2.tablePopulation.population.size();

        parents.add(table1.tablePopulation.population.get(Utils.getRandom(0,tableSize1)));
        parents.add(table2.tablePopulation.population.get(Utils.getRandom(0,tableSize2)));

        for (Member m:parents)
        {
            m.parentTableMask1 = table1.mask;
            m.parentTableMask2 = table2.mask;
        }

        p.population = parents;
        return p;
    }

}