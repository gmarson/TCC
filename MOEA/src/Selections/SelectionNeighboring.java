package Selections;

import Constants.Constants;
import Population.*;
import Utilities.Utils;

import java.util.ArrayList;

/**
 * Created by gabrielm on 06/05/17.
 */
public class SelectionNeighboring extends  Selection {

    @Override
    public Population selectParents(Population populationWithSingleMember) {
        int position1stMember = Utils.getRandom(0,Constants.NEIGHBOUR_QTD);
        int position2dnMember = Utils.getRandom(0,Constants.NEIGHBOUR_QTD);

        Population parentPopulation = new Population();

        parentPopulation.addMember(populationWithSingleMember.population.get(0).closestMembers.get(position1stMember));
        parentPopulation.addMember(populationWithSingleMember.population.get(0).closestMembers.get(position2dnMember));

        return parentPopulation;
    }

    @Override
    protected Member returnWinnerCompetitor(ArrayList<Member> membersByTour) {
        return null;
    }

    @Override
    protected Population tournament(Population population) {
        return null;
    }
}
