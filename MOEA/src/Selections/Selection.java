package Selections;

import Constants.Constants;
import Population.*;
import Utilities.Utils;
import ManyObjective.Table;

import java.util.ArrayList;

/**
 * Created by gmarson on 12/23/2016.
 * TCC UFU
 */
public abstract class Selection {

    public abstract Population selectParents(Population population);
    protected abstract Member returnWinnerCompetitor(ArrayList<Member> membersByTour);
    protected abstract Population tournament(Population population);

    protected ArrayList<Member> makeCompetitors(Population p)
    {
        ArrayList<Member> membersByTour = new ArrayList<>();
        for (int j = 0; j < Constants.TOUR_SIZE; j++)
        {
            int randomNumberForTournament = Utils.getRandom(0,p.population.size());
            membersByTour.add(p.population.get(randomNumberForTournament));
        }
        return membersByTour;
    }

    protected  ArrayList<Table> makeCompetitors(ArrayList<Table> tables)
    {
        ArrayList<Table> tablesByTour = new ArrayList<>();
        for (int i = 0; i < Constants.TOUR_SIZE; i++) {
            int randomNumberForTournament = Utils.getRandom(0,tables.size());
            tablesByTour.add(tables.get(randomNumberForTournament));
        }
        return  tablesByTour;
    }



}
