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


    @Override @Ignore
    public Population selectParents(Population population) {return null;}
    @Override @Ignore
    protected Population tournament(Population population) {
        return null;
    }

}
