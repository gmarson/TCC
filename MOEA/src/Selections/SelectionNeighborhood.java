package Selections;

import Constants.Constants;
import Population.*;
import Utilities.Utils;

import java.util.ArrayList;

/**
 * Created by gabrielm on 06/05/17.
 */
public class SelectionNeighborhood extends  Selection {

    public static Population selectParents(Member[] neighborhood)
    {
        int position1stMember = Utils.getRandom(0,Constants.NEIGHBOURHOOD_SIZE);
        int position2dnMember = Utils.getRandom(0,Constants.NEIGHBOURHOOD_SIZE) ;

        while(position1stMember == position2dnMember){
            position2dnMember = Utils.getRandom(0,Constants.NEIGHBOURHOOD_SIZE) ;
        }

        Population parentsPopulation = new Population();

        parentsPopulation.addMember(neighborhood[position1stMember]);
        parentsPopulation.addMember(neighborhood[position2dnMember]);
        return parentsPopulation;
    }

    @Override
    public Population selectParents(Population populationWithSingleMember) { return null; }

    @Override
    protected Member returnWinnerCompetitor(ArrayList<Member> membersByTour) {
        return null;
    }

    @Override
    protected Population tournament(Population population) {
        return null;
    }
}
