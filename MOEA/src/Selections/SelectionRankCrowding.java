package Selections;

import Constants.Constants;
import Population.*;

import java.util.ArrayList;

public class SelectionRankCrowding extends Selection{

	@Override
	public Population selectParents(Population population)
    {
        return  tournament(population);
    }

    @Override
	protected Population tournament(Population p)
    {
        Population selected = new Population();
        ArrayList<Member> membersByTour;
        for (int i = 0; i < Constants.CROSSOVER_RATE * p.population.size(); i++)
        {
            membersByTour = super.makeCompetitors(p);
            selected.population.add(returnWinnerCompetitor(membersByTour) );
        }

        return selected;
    }

    @Override
	protected Member returnWinnerCompetitor(ArrayList<Member> membersByTour)
    {
        Member bestMember, opponentMember;
        bestMember = membersByTour.get(0);
        for (int i = 1; i < membersByTour.size(); i++) {
            opponentMember = membersByTour.get(i);
            if (!bestMember.solutionsThatThisMemberDominates.contains(opponentMember))
            {
                if(!opponentMember.solutionsThatThisMemberDominates.contains(bestMember))
                    bestMember = getMemberWithHigherCrowdingValue(bestMember, opponentMember);

                else
                    bestMember = opponentMember;
            }
        }
        return bestMember;
    }

    private Member getMemberWithHigherCrowdingValue(Member member1, Member member2)
    {
        if(member1.crowdingDistanceValue > member2.crowdingDistanceValue)
            return member1;

        return member2;

    }

}