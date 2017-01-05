
import java.util.ArrayList;

/**
 * Created by gmarson on 12/23/2016.
 * TCC UFU
 */
public abstract class Selection {

    public static int TOUR_SIZE =3;


    public static Population selectParentsByRank(Population population)
    {
        return tournamentWithoutCrowding(population);
    }

    public static Population selectParentsByRankAndCrowding(Population population){

        return  tournamentWithCrowding(population);
    }

    public static Population tournamentWithoutCrowding(Population p)
    {
        Population selected = new Population();
        ArrayList<Member> membersByTour;
        for (int i = 0; i < NSGAII.CROSSOVER_RATE * p.population.size(); i++) {
            membersByTour = makeCompetitors(p);
            selected.population.add(returnWinnerCompetitorByRank(membersByTour) );
        }

        return selected;
    }

    public static Population tournamentWithCrowding(Population p)
    {
        Population selected = new Population();
        ArrayList<Member> membersByTour;
        for (int i = 0; i < NSGAII.CROSSOVER_RATE * p.population.size(); i++) {
            membersByTour = makeCompetitors(p);
            selected.population.add(returnWinnerCompetitorByRankAndCrowding(membersByTour) );
        }

        return selected;
    }

    private static ArrayList<Member> makeCompetitors(Population p)
    {
        ArrayList<Member> membersByTour = new ArrayList<>();
        for (int j = 0; j < TOUR_SIZE; j++) {
            int randomNumberForTournament = Utils.getRandom(0,p.population.size());
            membersByTour.add(p.population.get(randomNumberForTournament));
        }
        return membersByTour;
    }

    public static Member returnWinnerCompetitorByRank(ArrayList<Member> membersByTour)
    {
        Member bestMember, opponentMember;
        bestMember = membersByTour.get(0);
        for (int i = 1; i < membersByTour.size(); i++) {
            opponentMember = membersByTour.get(i);
            if (!bestMember.solutionsThatThisMemberDominates.contains(opponentMember))
                bestMember = opponentMember;
        }
        return bestMember;
    }

    public static Member returnWinnerCompetitorByRankAndCrowding(ArrayList<Member> membersByTour)
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

    public static Member getMemberWithHigherCrowdingValue(Member member1, Member member2)
    {
        if(member1.crowdingDistanceValue > member2.crowdingDistanceValue)
        //quanto maior o crowding, mais distante das areas condensadas estara esse individuo
            return member1;

        return member2;

    }


}
